package com.upc.eden.commen.domain.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName stu_exam
 */
@Data
@ApiModel(description = "学生-考试实体类")
public class StuExam implements Serializable {

    @ApiModelProperty(value = "考生Id", example = "1")
    @TableId(value = "examinee_id",type = IdType.AUTO)
    private Integer examineeId;
    @ApiModelProperty(value = "考试信息Id", example = "1")
    private Integer examId;
    @ApiModelProperty(value = "考生总得分", example = "85")
    private Integer totalScore;
    @ApiModelProperty(value = "考生各题得分明细，以字典格式存储", example = "{1:10 2:10 5:20 6:15}")
    private String scoreDetails;
    @ApiModelProperty(value = "复审各题得分明细，以以字典格式存储", example = "{1:10 2:10 5:20 6:20}")
    private String rescoreDetails;
    @ApiModelProperty(value = "第几次参加该考试", example = "2")
    private Integer presentTime;
    @ApiModelProperty(value = "考生开始考试时间(格式为yyyy-MM-dd HH:mm:ss)", example = "2022-03-22 20:34:37")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty(value = "考生提交时间(格式为yyyy-MM-dd HH:mm:ss)", example = "2022-03-22 21:54:10")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    @ApiModelProperty(value = "切屏次数", example = "0")
    private Integer cuttingTime;
    @ApiModelProperty(value = "是否判定作弊:{ 1:是 2:否 }", example = "0")
    private Integer cheat;

    private static final long serialVersionUID = 1L;

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
        StuExam other = (StuExam) that;
        return (this.getExamineeId() == null ? other.getExamineeId() == null : this.getExamineeId().equals(other.getExamineeId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getTotalScore() == null ? other.getTotalScore() == null : this.getTotalScore().equals(other.getTotalScore()))
            && (this.getScoreDetails() == null ? other.getScoreDetails() == null : this.getScoreDetails().equals(other.getScoreDetails()))
            && (this.getRescoreDetails() == null ? other.getRescoreDetails() == null : this.getRescoreDetails().equals(other.getRescoreDetails()))
            && (this.getPresentTime() == null ? other.getPresentTime() == null : this.getPresentTime().equals(other.getPresentTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getSubmitTime() == null ? other.getSubmitTime() == null : this.getSubmitTime().equals(other.getSubmitTime()))
            && (this.getCuttingTime() == null ? other.getCuttingTime() == null : this.getCuttingTime().equals(other.getCuttingTime()))
            && (this.getCheat() == null ? other.getCheat() == null : this.getCheat().equals(other.getCheat()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExamineeId() == null) ? 0 : getExamineeId().hashCode());
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getTotalScore() == null) ? 0 : getTotalScore().hashCode());
        result = prime * result + ((getScoreDetails() == null) ? 0 : getScoreDetails().hashCode());
        result = prime * result + ((getRescoreDetails() == null) ? 0 : getRescoreDetails().hashCode());
        result = prime * result + ((getPresentTime() == null) ? 0 : getPresentTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getSubmitTime() == null) ? 0 : getSubmitTime().hashCode());
        result = prime * result + ((getCuttingTime() == null) ? 0 : getCuttingTime().hashCode());
        result = prime * result + ((getCheat() == null) ? 0 : getCheat().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", examineeId=").append(examineeId);
        sb.append(", examId=").append(examId);
        sb.append(", totalScore=").append(totalScore);
        sb.append(", scoreDetails=").append(scoreDetails);
        sb.append(", rescoreDetails=").append(rescoreDetails);
        sb.append(", presentTime=").append(presentTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", cuttingTime=").append(cuttingTime);
        sb.append(", cheat=").append(cheat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}