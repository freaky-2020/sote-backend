package com.upc.eden.commen.domain.exam;

import java.io.Serializable;

/**
 * 
 * @TableName exam_detail
 */
public class ExamDetail implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Integer details;

    /**
     * 
     */
    private Integer paperId;

    /**
     * 
     */
    private Integer quesNo;

    /**
     * 
     */
    private Integer typeId;

    /**
     * 
     */
    private String answer;

    /**
     * 
     */
    private String realAnswer;

    /**
     * 
     */
    private Integer maxScore;

    /**
     * 
     */
    private Integer score;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Long getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Long id) {
        this.id = id;
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
    public Integer getPaperId() {
        return paperId;
    }

    /**
     * 
     */
    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    /**
     * 
     */
    public Integer getQuesNo() {
        return quesNo;
    }

    /**
     * 
     */
    public void setQuesNo(Integer quesNo) {
        this.quesNo = quesNo;
    }

    /**
     * 
     */
    public Integer getTypeId() {
        return typeId;
    }

    /**
     * 
     */
    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    /**
     * 
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * 
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    /**
     * 
     */
    public String getRealAnswer() {
        return realAnswer;
    }

    /**
     * 
     */
    public void setRealAnswer(String realAnswer) {
        this.realAnswer = realAnswer;
    }

    /**
     * 
     */
    public Integer getMaxScore() {
        return maxScore;
    }

    /**
     * 
     */
    public void setMaxScore(Integer maxScore) {
        this.maxScore = maxScore;
    }

    /**
     * 
     */
    public Integer getScore() {
        return score;
    }

    /**
     * 
     */
    public void setScore(Integer score) {
        this.score = score;
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
        ExamDetail other = (ExamDetail) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDetails() == null ? other.getDetails() == null : this.getDetails().equals(other.getDetails()))
            && (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getQuesNo() == null ? other.getQuesNo() == null : this.getQuesNo().equals(other.getQuesNo()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getRealAnswer() == null ? other.getRealAnswer() == null : this.getRealAnswer().equals(other.getRealAnswer()))
            && (this.getMaxScore() == null ? other.getMaxScore() == null : this.getMaxScore().equals(other.getMaxScore()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDetails() == null) ? 0 : getDetails().hashCode());
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getQuesNo() == null) ? 0 : getQuesNo().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getRealAnswer() == null) ? 0 : getRealAnswer().hashCode());
        result = prime * result + ((getMaxScore() == null) ? 0 : getMaxScore().hashCode());
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
        sb.append(", details=").append(details);
        sb.append(", paperId=").append(paperId);
        sb.append(", quesNo=").append(quesNo);
        sb.append(", typeId=").append(typeId);
        sb.append(", answer=").append(answer);
        sb.append(", realAnswer=").append(realAnswer);
        sb.append(", maxScore=").append(maxScore);
        sb.append(", score=").append(score);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}