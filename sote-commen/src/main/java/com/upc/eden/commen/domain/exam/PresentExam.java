package com.upc.eden.commen.domain.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName present_exam
 */
@Data
public class PresentExam implements Serializable {

    @TableId(value = "examinee_id",type = IdType.AUTO)
    private Integer examineeId;
    private Integer examId;
    private Integer totalScore;
    private String scoreDetails;
    private String rescoreDetails;
    private Integer presentTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date submitTime;
    private Integer cuttingTime;

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
        PresentExam other = (PresentExam) that;
        return (this.getExamineeId() == null ? other.getExamineeId() == null : this.getExamineeId().equals(other.getExamineeId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getTotalScore() == null ? other.getTotalScore() == null : this.getTotalScore().equals(other.getTotalScore()))
            && (this.getScoreDetails() == null ? other.getScoreDetails() == null : this.getScoreDetails().equals(other.getScoreDetails()))
            && (this.getRescoreDetails() == null ? other.getRescoreDetails() == null : this.getRescoreDetails().equals(other.getRescoreDetails()))
            && (this.getPresentTime() == null ? other.getPresentTime() == null : this.getPresentTime().equals(other.getPresentTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getSubmitTime() == null ? other.getSubmitTime() == null : this.getSubmitTime().equals(other.getSubmitTime()))
            && (this.getCuttingTime() == null ? other.getCuttingTime() == null : this.getCuttingTime().equals(other.getCuttingTime()));
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
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}