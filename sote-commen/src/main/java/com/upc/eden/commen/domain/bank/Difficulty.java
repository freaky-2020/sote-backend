package com.upc.eden.commen.domain.bank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 
 * @TableName difficulty
 */
@ApiModel(description = "题目难度实体类")
public class Difficulty implements Serializable {

    @ApiModelProperty("难度级别Id: { 1:简单 2:适中 3:困难 4:压轴 }")
    private Integer id;

    @ApiModelProperty("难度级别")
    private String difficultyComment;

    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     */
    public String getDifficultyComment() {
        return difficultyComment;
    }

    /**
     * 
     */
    public void setDifficultyComment(String difficultyComment) {
        this.difficultyComment = difficultyComment;
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
        Difficulty other = (Difficulty) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDifficultyComment() == null ? other.getDifficultyComment() == null : this.getDifficultyComment().equals(other.getDifficultyComment()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDifficultyComment() == null) ? 0 : getDifficultyComment().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", difficultyComment=").append(difficultyComment);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}