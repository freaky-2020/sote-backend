package com.upc.eden.commen.domain.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.upc.eden.commen.domain.bank.Question;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName paper
 */
@ApiModel(description = "试卷实体类")
@NoArgsConstructor
@Data
public class Paper implements Serializable {

    @ApiModelProperty(value = "数据库Id", example = "1")
    @TableId(value = "paper_id",type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "试卷Id", example = "1")
    private Integer paperId;
    @ApiModelProperty(value = "试卷题号", example = "1")
    private Integer quesNo;
    @ApiModelProperty(value = "题库Id", example = "3")
    private Integer bankId;
    @ApiModelProperty(value = "创建人Id", example = "2")
    private String makerId;
    @ApiModelProperty(value = "科目Id", example = "1")
    private Integer subjectId;
    @ApiModelProperty(value = "题型Id", example = "2")
    private Integer typeId;
    @ApiModelProperty(value = "难度Id", example = "1")
    private Integer difficultyId;
    @ApiModelProperty(value = "题干：图片或文档连接以 ’$$‘ 拼接在后面", example = "1+1=?$$http://baidu.com")
    private String stem;
    @ApiModelProperty("选项1（判断、填空与简答不需要）")
    private String choice1;
    @ApiModelProperty("选项2（判断、填空与简答不需要）")
    private String choice2;
    @ApiModelProperty("选项3（判断、填空与简答不需要）")
    private String choice3;
    @ApiModelProperty("选项4（判断、填空与简答不需要）")
    private String choice4;
    @ApiModelProperty(value = "答案：选择题为选项、判断题为{1:true 2:false}、填空/简答为答案", example = "扁桃体")
    private String answer;
    @ApiModelProperty(value = "解析：图片或文档链接以’$$‘拼接在后面", example = "发炎必是扁桃体$$http://baidu.com")
    private String remark;
    @ApiModelProperty(value = "试题分数（默认为10）", example = "10")
    private Integer score;

    private static final long serialVersionUID = 1L;

    public Paper(Question question) {

        this.typeId = question.getTypeId();
        this.subjectId = question.getSubjectId();
        this.bankId = question.getId();
        this.difficultyId = question.getDifficultyId();
        this.stem = question.getStem();
        this.choice1 = question.getChoice1();
        this.choice2 = question.getChoice2();
        this.choice3 = question.getChoice3();
        this.choice4 = question.getChoice4();
        this.answer = question.getAnswer();
        this.remark = question.getRemark();
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Paper other = (Paper) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getQuesNo() == null ? other.getQuesNo() == null : this.getQuesNo().equals(other.getQuesNo()))
            && (this.getBankId() == null ? other.getBankId() == null : this.getBankId().equals(other.getBankId()))
            && (this.getMakerId() == null ? other.getMakerId() == null : this.getMakerId().equals(other.getMakerId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getDifficultyId() == null ? other.getDifficultyId() == null : this.getDifficultyId().equals(other.getDifficultyId()))
            && (this.getStem() == null ? other.getStem() == null : this.getStem().equals(other.getStem()))
            && (this.getChoice1() == null ? other.getChoice1() == null : this.getChoice1().equals(other.getChoice1()))
            && (this.getChoice2() == null ? other.getChoice2() == null : this.getChoice2().equals(other.getChoice2()))
            && (this.getChoice3() == null ? other.getChoice3() == null : this.getChoice3().equals(other.getChoice3()))
            && (this.getChoice4() == null ? other.getChoice4() == null : this.getChoice4().equals(other.getChoice4()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getQuesNo() == null) ? 0 : getQuesNo().hashCode());
        result = prime * result + ((getBankId() == null) ? 0 : getBankId().hashCode());
        result = prime * result + ((getMakerId() == null) ? 0 : getMakerId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getDifficultyId() == null) ? 0 : getDifficultyId().hashCode());
        result = prime * result + ((getStem() == null) ? 0 : getStem().hashCode());
        result = prime * result + ((getChoice1() == null) ? 0 : getChoice1().hashCode());
        result = prime * result + ((getChoice2() == null) ? 0 : getChoice2().hashCode());
        result = prime * result + ((getChoice3() == null) ? 0 : getChoice3().hashCode());
        result = prime * result + ((getChoice4() == null) ? 0 : getChoice4().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", paperId=").append(paperId);
        sb.append(", quesNo=").append(quesNo);
        sb.append(", bankId=").append(bankId);
        sb.append(", makerId=").append(makerId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", typeId=").append(typeId);
        sb.append(", difficultyId=").append(difficultyId);
        sb.append(", stem=").append(stem);
        sb.append(", choice1=").append(choice1);
        sb.append(", choice2=").append(choice2);
        sb.append(", choice3=").append(choice3);
        sb.append(", choice4=").append(choice4);
        sb.append(", answer=").append(answer);
        sb.append(", remark=").append(remark);
        sb.append(", score=").append(score);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}