package com.upc.eden.commen.domain.bank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 
 * @TableName subject
 */
@ApiModel("科目实体类")
public class Subject implements Serializable {

    @ApiModelProperty("科目Id")
    private Integer id;

    @ApiModelProperty("科目名")
    private String subjectName;

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
    public String getSubjectName() {
        return subjectName;
    }

    /**
     * 
     */
    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
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
        Subject other = (Subject) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSubjectName() == null ? other.getSubjectName() == null : this.getSubjectName().equals(other.getSubjectName()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSubjectName() == null) ? 0 : getSubjectName().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", subjectName=").append(subjectName);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}