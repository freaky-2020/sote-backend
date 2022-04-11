package com.upc.eden.commen.domain.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName exam_detail
 */
@ApiModel(description = "考生答题明细实体类")
@Data
@NoArgsConstructor
public class ExamDetail implements Serializable {

    @ApiModelProperty(value = "数据库Id", example = "1")
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "考生作答明细Id", example = "1")
    private Integer details;
    @ApiModelProperty(value = "试卷Id", example = "1")
    private Integer paperId;
    @ApiModelProperty(value = "卷内题号", example = "1")
    private Integer quesNo;
    @ApiModelProperty(value = "题型", example = "2")
    private Integer typeId;
    @ApiModelProperty(value = "考生答案", example = "2x")
    private String answer;
    @ApiModelProperty(value = "标准答案", example = "3x")
    private String realAnswer;
    @ApiModelProperty(value = "该题总分", example = "20")
    private Integer maxScore;
    @ApiModelProperty(value = "考生得分", example = "0")
    private Integer score;

    private static final long serialVersionUID = 1L;

    public ExamDetail(Integer details, Integer paperId, Integer quesNo,
                      Integer typeId, String realAnswer, Integer maxScore) {
        this.details = details;
        this.paperId = paperId;
        this.quesNo = quesNo;
        this.typeId = typeId;
        this.realAnswer = realAnswer;
        this.maxScore = maxScore;
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