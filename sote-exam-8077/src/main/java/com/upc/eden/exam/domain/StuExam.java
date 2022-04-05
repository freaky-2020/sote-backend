package com.upc.eden.exam.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName stu_exam
 */
public class StuExam implements Serializable {
    /**
     * 
     */
    private Integer examineeId;

    /**
     * 
     */
    private Integer examId;

    /**
     * 
     */
    private Integer details;

    /**
     * 
     */
    private Integer presentTime;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private Integer totalScore;

    /**
     * 
     */
    private Date startTime;

    /**
     * 
     */
    private Date submitTime;

    /**
     * 
     */
    private Integer cuttingTime;

    /**
     * 
     */
    private Integer cheat;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getExamineeId() {
        return examineeId;
    }

    /**
     * 
     */
    public void setExamineeId(Integer examineeId) {
        this.examineeId = examineeId;
    }

    /**
     * 
     */
    public Integer getExamId() {
        return examId;
    }

    /**
     * 
     */
    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    /**
     * 
     */
    public Integer getDetails() {
        return details;
    }

    /**
     * 
     */
    public void setDetails(Integer details) {
        this.details = details;
    }

    /**
     * 
     */
    public Integer getPresentTime() {
        return presentTime;
    }

    /**
     * 
     */
    public void setPresentTime(Integer presentTime) {
        this.presentTime = presentTime;
    }

    /**
     * 
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 
     */
    public Integer getTotalScore() {
        return totalScore;
    }

    /**
     * 
     */
    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
    }

    /**
     * 
     */
    public Date getStartTime() {
        return startTime;
    }

    /**
     * 
     */
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     */
    public Date getSubmitTime() {
        return submitTime;
    }

    /**
     * 
     */
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    /**
     * 
     */
    public Integer getCuttingTime() {
        return cuttingTime;
    }

    /**
     * 
     */
    public void setCuttingTime(Integer cuttingTime) {
        this.cuttingTime = cuttingTime;
    }

    /**
     * 
     */
    public Integer getCheat() {
        return cheat;
    }

    /**
     * 
     */
    public void setCheat(Integer cheat) {
        this.cheat = cheat;
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
        StuExam other = (StuExam) that;
        return (this.getExamineeId() == null ? other.getExamineeId() == null : this.getExamineeId().equals(other.getExamineeId()))
            && (this.getExamId() == null ? other.getExamId() == null : this.getExamId().equals(other.getExamId()))
            && (this.getDetails() == null ? other.getDetails() == null : this.getDetails().equals(other.getDetails()))
            && (this.getPresentTime() == null ? other.getPresentTime() == null : this.getPresentTime().equals(other.getPresentTime()))
            && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
            && (this.getTotalScore() == null ? other.getTotalScore() == null : this.getTotalScore().equals(other.getTotalScore()))
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
        result = prime * result + ((getDetails() == null) ? 0 : getDetails().hashCode());
        result = prime * result + ((getPresentTime() == null) ? 0 : getPresentTime().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getTotalScore() == null) ? 0 : getTotalScore().hashCode());
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
        sb.append(", details=").append(details);
        sb.append(", presentTime=").append(presentTime);
        sb.append(", status=").append(status);
        sb.append(", totalScore=").append(totalScore);
        sb.append(", startTime=").append(startTime);
        sb.append(", submitTime=").append(submitTime);
        sb.append(", cuttingTime=").append(cuttingTime);
        sb.append(", cheat=").append(cheat);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}