package com.upc.eden.commen.domain.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.internal.$Gson$Preconditions;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName papers
 */
@ApiModel(description = "试卷实体类")
@Data
public class Papers implements Serializable {

    @ApiModelProperty(value = "试卷Id", example = "1")
    @TableId(value = "paper_id",type = IdType.AUTO)
    private Integer paperId;
    @ApiModelProperty(value = "创建人Id", example = "2")
    private Integer makerId;
    @ApiModelProperty(value = "科目Id", example = "1")
    private Integer subjectId;
    @ApiModelProperty(value = "题型Id", example = "2")
    private Integer typeId;
    @ApiModelProperty(value = "题目Id", example = "5")
    private Integer questionId;
    @ApiModelProperty(value = "试题分数", example = "10")
    private Integer score;
    @ApiModelProperty(value = "试卷创建时间(格式为yyyy-MM-dd)", example = "2022-03-22")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createTime;

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
        Papers other = (Papers) that;
        return (this.getPaperId() == null ? other.getPaperId() == null : this.getPaperId().equals(other.getPaperId()))
            && (this.getMakerId() == null ? other.getMakerId() == null : this.getMakerId().equals(other.getMakerId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getQuestionId() == null ? other.getQuestionId() == null : this.getQuestionId().equals(other.getQuestionId()))
            && (this.getScore() == null ? other.getScore() == null : this.getScore().equals(other.getScore()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getPaperId() == null) ? 0 : getPaperId().hashCode());
        result = prime * result + ((getMakerId() == null) ? 0 : getMakerId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getQuestionId() == null) ? 0 : getQuestionId().hashCode());
        result = prime * result + ((getScore() == null) ? 0 : getScore().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", paperId=").append(paperId);
        sb.append(", makerId=").append(makerId);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", typeId=").append(typeId);
        sb.append(", questionId=").append(questionId);
        sb.append(", score=").append(score);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}