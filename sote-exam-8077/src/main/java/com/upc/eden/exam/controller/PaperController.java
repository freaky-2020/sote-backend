package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.clients.BankClient;
import com.upc.eden.commen.domain.auth.SecurityUser;
import com.upc.eden.commen.domain.bank.BankRequire;
import com.upc.eden.commen.domain.bank.Question;
import com.upc.eden.commen.domain.exam.ExamInfo;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.exam.api.PaperInfoApi;
import com.upc.eden.exam.service.ExamInfoService;
import com.upc.eden.exam.service.PaperService;
import io.swagger.annotations.*;
import io.swagger.models.auth.In;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author: CS Dong
 * @Date: 2022/03/31/16:39
 * @Description:
 */

@Api(tags = { "试卷业务接口文档"} )
@RestController
@RequestMapping("/paper")
public class PaperController {

    @Resource
    private PaperService paperService;
    @Resource
    private ExamInfoService examInfoService;
    @Resource
    private BankClient bankClient;
    @Resource
    private AuthClient authClient;

    @ApiOperation("依据paperId拉取该试卷目前所有的题目：paperId拼接在路径中")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    @GetMapping("/{paperId}/get")
    public Map<Integer, List<Paper>> getPaper(@PathVariable Integer paperId) {

        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        wrapper.orderBy(true, true, "ques_no");
        List<Paper> records = paperService.list(wrapper);

        Map<Integer, List<Paper>> res = new HashMap<>();
        for (int i=1; i<=5; i++) res.put(i, new ArrayList<>());

        for (Paper record: records) {
            if (record != null) {
                Integer typeId = record.getTypeId();
                res.get(typeId).add(record);
            }
        }
        return res;
    }

    @ApiOperation("依据paperId拉取该试卷目前所有的题目（考生专用）：paperId拼接在路径中")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path"),
            @ApiImplicitParam(name = "userName", value = "学生账号", paramType = "path")})
    @GetMapping("/{paperId}/{userName}/get")
    public Map<Integer, List<Paper>> stuGetPaper(@PathVariable Integer paperId,
                                                 @PathVariable String userName) {

        QueryWrapper<Paper> wrapper = new QueryWrapper<>();
        wrapper.eq("paper_id", paperId);
        wrapper.orderBy(true, true, "ques_no");
        List<Paper> records = paperService.list(wrapper);

        Map<Integer, List<Paper>> res = new HashMap<>();
        for (int i=1; i<=5; i++) res.put(i, new ArrayList<>());

        for (Paper record: records) {
            if (record != null) {
                Integer typeId = record.getTypeId();
                res.get(typeId).add(record);
            }
        }

        Random rd = new Random(Integer.parseInt(userName));
        for (int i=1; i<=5; i++) {
            if (res.get(i).size() > 0) Collections.shuffle(res.get(i), rd);
        }
        return res;
    }

    @ApiOperation("从题库中选择题目（可多个）加入试卷：paperId拼接在路径中，返回添加成功的条数")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    @PostMapping("/{paperId}/addFromBank")
    public int add(@PathVariable Integer paperId, @RequestBody List<Question> questions) {

        UpdateWrapper<ExamInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", paperId).set("is_copy", 0);
        examInfoService.update(null, updateWrapper);

        int res = 0;
        for (Question question : questions) {
            if (question != null) {
                Paper record = new Paper(question);
                record.setPaperId(paperId);
                record.setScore(10);

                // 插入题目算法：插入对应题型的尾部并修正全局题号
                int typeId = question.getTypeId();
                Integer maxQuesNoByType = paperService.getMaxQuesNoByType(paperId, typeId);
                // 如果该题是当前题型的第一个题
                if (maxQuesNoByType == null) {
                    // 如果是第一题型则直接插入为第一题并修正后续题号
                    if(typeId == 1) {
                        paperService.reviseQuesNoAdd(paperId, 1);
                        record.setQuesNo(1);
                        // 否则，从上一题型开始遍历，直至获取到某前面题型的题号尾
                    } else {
                        Integer lastMaxQuesNo = null;
                        Integer t = typeId;
                        while (lastMaxQuesNo == null && t > 0) {
                            lastMaxQuesNo = paperService.getMaxQuesNoByType(paperId, t-1);
                            --t;
                            // 前面题型均无题
                        } if(lastMaxQuesNo == null) {
                            paperService.reviseQuesNoAdd(paperId, 1);
                            record.setQuesNo(1);
                            // 前面题型有题，则置入后面
                        } else {
                            paperService.reviseQuesNoAdd(paperId, lastMaxQuesNo+1);
                            record.setQuesNo(lastMaxQuesNo+1);
                        }
                    }
                    // 如果不是当前题型的第一个题，则直接插在后面
                } else {
                    paperService.reviseQuesNoAdd(paperId, maxQuesNoByType+1);
                    record.setQuesNo(maxQuesNoByType+1);
                }

//                SecurityUser currentUser = authClient.getCurrentUser();
//                record.setMakerId(currentUser.getId());
                record.setMakerId(String.valueOf(3));

                if (paperService.save(record)) ++res;
            }
        }
        return res;
    }

    @ApiOperation("自定义题目加入试卷：paperId拼接在路径中，返回是否成功（true or false）")
    @PostMapping("/{paperId}/addBySelf")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    public boolean add(@PathVariable Integer paperId, Question question) {

        UpdateWrapper<ExamInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", paperId).set("is_copy", 0);
        examInfoService.update(null, updateWrapper);

        Paper record = new Paper(question);
        record.setPaperId(paperId);
        record.setScore(10);

        // 插入题目算法：插入对应题型的尾部并修正全局题号
        int typeId = question.getTypeId();
        Integer maxQuesNoByType = paperService.getMaxQuesNoByType(paperId, typeId);
        // 如果该题是当前题型的第一个题
        if (maxQuesNoByType == null) {
            // 如果是第一题型则直接插入为第一题并修正后续题号
            if(typeId == 1) {
                paperService.reviseQuesNoAdd(paperId, 1);
                record.setQuesNo(1);
                // 否则，从上一题型开始遍历，直至获取到某前面题型的题号尾
            } else {
                Integer lastMaxQuesNo = null;
                Integer t = typeId;
                while (lastMaxQuesNo == null && t > 0) {
                    lastMaxQuesNo = paperService.getMaxQuesNoByType(paperId, t-1);
                    --t;
                // 前面题型均无题
                } if(lastMaxQuesNo == null) {
                    paperService.reviseQuesNoAdd(paperId, 1);
                    record.setQuesNo(1);
                    // 前面题型有题，则置入后面
                } else {
                    paperService.reviseQuesNoAdd(paperId, lastMaxQuesNo+1);
                    record.setQuesNo(lastMaxQuesNo+1);
                }
            }
            // 如果不是当前题型的第一个题，则直接插在后面
        } else {
            paperService.reviseQuesNoAdd(paperId, maxQuesNoByType+1);
            record.setQuesNo(maxQuesNoByType+1);
        }

//        SecurityUser currentUser = authClient.getCurrentUser();
//        record.setMakerId(currentUser.getId());
        record.setMakerId(String.valueOf(3));

        return paperService.save(record);
    }

    @ApiOperation("提交试卷/修改试卷，提交需要修改/提交的题目的集合(可单个、可批量)")
    @PutMapping("/update")
    public int update(@RequestBody List<Paper> papers) {

        int res = 0;
        for (Paper paper: papers) {
            if(paper != null) {
                UpdateWrapper<Paper> wrapper = new UpdateWrapper<>();
                wrapper.eq("id", paper.getId());
                if(paperService.update(paper, wrapper)) ++res;
            }
        }
        return res;
    }

    @ApiOperation("删除试卷中的题目，提交需要删除的题目的集合(可单个、可批量) - 删除是以paper_id与ques_no为依据的")
    @DeleteMapping("/delete")
    public int delete(@RequestBody List<Paper> papers) {

        UpdateWrapper<ExamInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", papers.get(0).getPaperId()).set("is_copy", 0);
        examInfoService.update(null, updateWrapper);

        int res = 0;
        for (Paper paper: papers) {
            if(paper != null) {
                QueryWrapper<Paper> wrapper = new QueryWrapper<>();
                wrapper.eq("paper_id", paper.getPaperId());
                wrapper.eq("ques_no", paper.getQuesNo());
                if(paperService.remove(wrapper)) ++res;
            }
        }
        // 更正题号(目前仅限单删)
        Integer paperId = papers.get(0).getPaperId();
        Integer quesNo = papers.get(0).getQuesNo();
        paperService.reviseQuesNo(paperId, quesNo);
        return res;
    }

    @ApiOperation("智能组卷，返回message")
    @GetMapping("/auto/{paperId}")
    public String autoAdd(@PathVariable Integer paperId, Integer diff, Integer subjectId,
                          Integer q1, Integer q2, Integer q3, Integer q4, Integer q5,
                          Integer c1, Integer c2, Integer c3, Integer c4, Integer c5) {

        UpdateWrapper<ExamInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", paperId).set("is_copy", 0);
        examInfoService.update(null, updateWrapper);

        // 1、删除试卷中原有题目
        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", paperId);
        paperService.remove(paperQueryWrapper);

        // 2、边际判断
        List<Question> ques1 = bankClient.getQuesByType(1, subjectId);
        if (ques1.size() < q1) return "题库中该科目单选题数目不足！";
        List<Question> ques2 = bankClient.getQuesByType(2, subjectId);
        if (ques2.size() < q2) return "题库中该科目多选题数目不足！";
        List<Question> ques3 = bankClient.getQuesByType(3, subjectId);
        if (ques3.size() < q3) return "题库中该科目判断题数目不足！";
        List<Question> ques4 = bankClient.getQuesByType(4, subjectId);
        if (ques4.size() < q4) return "题库中该科目填空题数目不足！";
        List<Question> ques5 = bankClient.getQuesByType(5, subjectId);
        if (ques5.size() < q5) return "题库中该科目简答题数目不足！";

        // 3、智能组卷
        Integer count = 0;
        Random random = new Random(diff);

        Collections.shuffle(ques1, random);
        for (int i=0; i<q1; i++) {
            Question question = ques1.get(i);
            Paper paper = new Paper(question);
            paper.setPaperId(paperId);
            paper.setScore(c1);
            paper.setQuesNo(++count);
            paperService.save(paper);
        }
        Collections.shuffle(ques2, random);
        for (int i=0; i<q2; i++) {
            Question question = ques2.get(i);
            Paper paper = new Paper(question);
            paper.setPaperId(paperId);
            paper.setScore(c2);
            paper.setQuesNo(++count);
            paperService.save(paper);
        }
        Collections.shuffle(ques3, random);
        for (int i=0; i<q3; i++) {
            Question question = ques3.get(i);
            Paper paper = new Paper(question);
            paper.setPaperId(paperId);
            paper.setScore(c3);
            paper.setQuesNo(++count);
            paperService.save(paper);
        }
        Collections.shuffle(ques4, random);
        for (int i=0; i<q4; i++) {
            Question question = ques4.get(i);
            Paper paper = new Paper(question);
            paper.setPaperId(paperId);
            paper.setScore(c4);
            paper.setQuesNo(++count);
            paperService.save(paper);
        }
        Collections.shuffle(ques5, random);
        for (int i=0; i<q5; i++) {
            Question question = ques5.get(i);
            Paper paper = new Paper(question);
            paper.setPaperId(paperId);
            paper.setScore(c5);
            paper.setQuesNo(++count);
            paperService.save(paper);
        }

        return "智能组卷成功！";
    }

    @ApiOperation("获取试卷库中全部可套用试卷，返回体详情见Schemas")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "paperName", value = "试卷名（可模糊）", defaultValue = "高等数学"),
            @ApiImplicitParam(name = "subjectId", value = "科目Id", defaultValue = "1"),
            @ApiImplicitParam(name = "startTime", value = "创建日期起始检索值，注意格式", defaultValue = "1970-01-01"),
            @ApiImplicitParam(name = "endTime", value = "创建日期截止检索值，注意格式", defaultValue = "2999-12-31"),
            @ApiImplicitParam(name = "leftDiff", value = "起始难度系数", defaultValue = "1"),
            @ApiImplicitParam(name = "rightDiff", value = "截止难度系数", defaultValue = "10")})
    @GetMapping("/paperBank")
    public List<PaperInfoApi> getAllPaperInfo(@RequestParam(required = false) String paperName,
                                              @RequestParam(required = false) Integer subjectId,
                                              @RequestParam(required = false) String startTime,
                                              @RequestParam(required = false) String endTime,
                                              @RequestParam(required = false) Integer leftDiff,
                                              @RequestParam(required = false) Integer rightDiff) {

        List<PaperInfoApi> res = new ArrayList<>();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        QueryWrapper<ExamInfo> examInfoQueryWrapper = new QueryWrapper<>();
        examInfoQueryWrapper.eq("is_copy", 0).eq("is_public", 1)
                .orderBy(true, true, "subject_id")
                .orderBy(true, false, "copied_time")
                .orderBy(true, false, "deadline");
//                .orderBy(true, true, "difficulty");

        if (paperName != null) examInfoQueryWrapper.like("exam_name", paperName);
        if (subjectId != null) examInfoQueryWrapper.eq("subject_id", subjectId);
        if (startTime != null) {
            startTime += " 00:00:00";
            examInfoQueryWrapper.ge("deadline", startTime);
        }
        if (endTime != null) {
            endTime += " 23:59:59";
            examInfoQueryWrapper.le("deadline", endTime);
        }
//        if (leftDiff != null) examInfoQueryWrapper.ge("difficulty", leftDiff);
//        if (rightDiff != null) examInfoQueryWrapper.le("difficulty", rightDiff);

        List<ExamInfo> allPaperId = examInfoService.list(examInfoQueryWrapper);

        for (ExamInfo each: allPaperId) {
            if (each == null) continue;
            Integer paperId = each.getPaperId();
            String publicTime = df.format(each.getDeadline());
            String examName = each.getExamName();
            Integer copiedTime = each.getCopiedTime();
            Integer tSubjectId = each.getSubjectId();
//            Integer difficulty = each.getDifficulty();

            PaperInfoApi paperInfoApi = new PaperInfoApi();
            paperInfoApi.setPaperId(paperId);
            paperInfoApi.setPublicTime(publicTime);
            paperInfoApi.setPaperName(examName);
            paperInfoApi.setCopiedTime(copiedTime);
//            paperInfoApi.setDifficulty(difficulty);
            paperInfoApi.setDifficulty(1);
            paperInfoApi.setSubjectId(tSubjectId);

            res.add(paperInfoApi);
        }
        return res;
    }

    @ApiOperation("选择现有试卷载入本试卷，返回message")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nowPaperId", value = "当前试卷Id", paramType = "path"),
            @ApiImplicitParam(name = "referPaperId", value = "要导入的试卷Id", paramType = "path")})
    @GetMapping("/addFromAlready/{nowPaperId}/{referPaperId}")
    public String addFromAlready(@PathVariable Integer nowPaperId, @PathVariable Integer referPaperId) {

        QueryWrapper<Paper> paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", nowPaperId);
        paperService.remove(paperQueryWrapper);

        UpdateWrapper<ExamInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("paper_id", nowPaperId).set("is_copy", 1);
        examInfoService.update(null, updateWrapper);

        paperQueryWrapper = new QueryWrapper<>();
        paperQueryWrapper.eq("paper_id", referPaperId)
                .orderBy(true, true, "ques_no");
        List<Paper> questions = paperService.list(paperQueryWrapper);

        for (Paper ques: questions) {
            if (ques == null) continue;
            ques.setPaperId(nowPaperId);
            ques.setId(null);
            paperService.save(ques);
        }

        return "载入成功！您仍可在此基础上进行试题的增加、删除与修改！";
    }
}
