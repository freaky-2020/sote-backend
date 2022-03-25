package com.upc.eden.commen.domain.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName exam_info
 */
@Data
public class ExamInfo implements Serializable {

    private Integer examId;
    private Integer subjectId;
    private Integer paperId;
    private String examName;
    private String examNote;
    private Integer invigilatorId;
    private Integer durationTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deadline;
    private Integer allowableTime;
    private Integer noticeWay;
    private String word;
    private Integer cuttingTimes;

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
        ExamInfo other = (ExamInfo) that;
        return (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getExamName() == null ? other.getExamName() == null : this.getExamName().equals(other.getExamName()))
            && (this.getExamNote() == null ? other.getExamNote() == null : this.getExamNote().equals(other.getExamNote()))
            && (this.getInvigilatorId() == null ? other.getInvigilatorId() == null : this.getInvigilatorId().equals(other.getInvigilatorId()))
            && (this.getDurationTime() == null ? other.getDurationTime() == null : this.getDurationTime().equals(other.getDurationTime()))
            && (this.getStartTime() == null ? other.getStartTime() == null : this.getStartTime().equals(other.getStartTime()))
            && (this.getDeadline() == null ? other.getDeadline() == null : this.getDeadline().equals(other.getDeadline()))
            && (this.getAllowableTime() == null ? other.getAllowableTime() == null : this.getAllowableTime().equals(other.getAllowableTime()))
            && (this.getNoticeWay() == null ? other.getNoticeWay() == null : this.getNoticeWay().equals(other.getNoticeWay()))
            && (this.getWord() == null ? other.getWord() == null : this.getWord().equals(other.getWord()))
            && (this.getCuttingTimes() == null ? other.getCuttingTimes() == null : this.getCuttingTimes().equals(other.getCuttingTimes()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getExamId() == null) ? 0 : getExamId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getExamName() == null) ? 0 : getExamName().hashCode());
        result = prime * result + ((getExamNote() == null) ? 0 : getExamNote().hashCode());
        result = prime * result + ((getInvigilatorId() == null) ? 0 : getInvigilatorId().hashCode());
        result = prime * result + ((getDurationTime() == null) ? 0 : getDurationTime().hashCode());
        result = prime * result + ((getStartTime() == null) ? 0 : getStartTime().hashCode());
        result = prime * result + ((getDeadline() == null) ? 0 : getDeadline().hashCode());
        result = prime * result + ((getAllowableTime() == null) ? 0 : getAllowableTime().hashCode());
        result = prime * result + ((getNoticeWay() == null) ? 0 : getNoticeWay().hashCode());
        result = prime * result + ((getWord() == null) ? 0 : getWord().hashCode());
        result = prime * result + ((getCuttingTimes() == null) ? 0 : getCuttingTimes().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", examId=").append(examId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", paperId=").append(paperId);
        sb.append(", examName=").append(examName);
        sb.append(", examNote=").append(examNote);
        sb.append(", invigilatorId=").append(invigilatorId);
        sb.append(", durationTime=").append(durationTime);
        sb.append(", startTime=").append(startTime);
        sb.append(", deadline=").append(deadline);
        sb.append(", allowableTime=").append(allowableTime);
        sb.append(", noticeWay=").append(noticeWay);
        sb.append(", word=").append(word);
        sb.append(", cuttingTimes=").append(cuttingTimes);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}