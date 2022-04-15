package com.upc.eden.commen.domain.bank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName bank_require
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "题目变动申请实体类")
public class BankRequire implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "申请人userName", example = "10086")
    private Integer requestUserName;
    @ApiModelProperty(value = "申请类型：{1:增 2:删 3:改}", example = "10086")
    private Integer doWay;
    @ApiModelProperty(value = "删改前提下，该题目在题库中的id", example = "1")
    private Integer quesId;
    @ApiModelProperty(value = "科目id", example = "1")
    private Integer subjectId;
    @ApiModelProperty(value = "题型id", example = "1")
    private Integer typeId;
    @ApiModelProperty(value = "难度id", example = "1")
    private Integer difficultyId;
    @ApiModelProperty(value = "题干", example = "请问您是？")
    private String stem;
    @ApiModelProperty(value = "选项1", example = "钝角")
    private String choice1;
    @ApiModelProperty(value = "选项2", example = "直角")
    private String choice2;
    @ApiModelProperty(value = "选项3", example = "锐角")
    private String choice3;
    @ApiModelProperty(value = "选项4", example = "牛角")
    private String choice4;
    @ApiModelProperty(value = "正确答案", example = "4")
    private String answer;
    @ApiModelProperty(value = "答案解析", example = "牛角是钝角")
    private String remark;
    @ApiModelProperty(value = "申请时间", hidden = true)
    private Date requireTime;

    private static final long serialVersionUID = 1L;

    public BankRequire(Question question) {
        this.quesId = question.getId();
        this.subjectId = question.getSubjectId();
        this.typeId = question.getTypeId();
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
        BankRequire other = (BankRequire) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getRequestUserName() == null ? other.getRequestUserName() == null : this.getRequestUserName().equals(other.getRequestUserName()))
            && (this.getDoWay() == null ? other.getDoWay() == null : this.getDoWay().equals(other.getDoWay()))
            && (this.getQuesId() == null ? other.getQuesId() == null : this.getQuesId().equals(other.getQuesId()))
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
            && (this.getRequireTime() == null ? other.getRequireTime() == null : this.getRequireTime().equals(other.getRequireTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getRequestUserName() == null) ? 0 : getRequestUserName().hashCode());
        result = prime * result + ((getDoWay() == null) ? 0 : getDoWay().hashCode());
        result = prime * result + ((getQuesId() == null) ? 0 : getQuesId().hashCode());
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
        result = prime * result + ((getRequireTime() == null) ? 0 : getRequireTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", requestUserName=").append(requestUserName);
        sb.append(", doWay=").append(doWay);
        sb.append(", quesId=").append(quesId);
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
        sb.append(", requireTime=").append(requireTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}