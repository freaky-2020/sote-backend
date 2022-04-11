package com.upc.eden.exam.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.upc.eden.commen.clients.AuthClient;
import com.upc.eden.commen.domain.auth.SecurityUser;
import com.upc.eden.commen.domain.bank.Question;
import com.upc.eden.commen.domain.exam.Paper;
import com.upc.eden.exam.service.PaperService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @ApiOperation("从题库中选择题目（可多个）加入试卷：paperId拼接在路径中，返回添加成功的条数")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    @PostMapping("/{paperId}/addFromBank")
    public int add(@PathVariable Integer paperId, @RequestBody List<Question> questions) {

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
                record.setMakerId(3);

                if (paperService.save(record)) ++res;
            }
        }
        return res;
    }

    @ApiOperation("自定义题目加入试卷：paperId拼接在路径中，返回是否成功（true or false）")
    @PostMapping("/{paperId}/addBySelf")
    @ApiImplicitParams({@ApiImplicitParam(name = "paperId", value = "创建考试时返回的试卷Id", paramType = "path")})
    public boolean add(@PathVariable Integer paperId, Question question) {

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
        record.setMakerId(3);

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
}
