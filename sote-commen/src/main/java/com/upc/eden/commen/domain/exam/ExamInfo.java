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
 * @TableName exam_info
 */
@ApiModel(description = "考试信息实体类")
@Data
public class ExamInfo implements Serializable {

    @ApiModelProperty(value = "考试信息Id", example = "1")
    @TableId(value = "exam_id",type = IdType.AUTO)
    private Integer examId;
    @ApiModelProperty(value = "科目Id", example = "1")
    private Integer subjectId;
    @ApiModelProperty(value = "试卷Id", example = "1")
    private Integer paperId;
    @ApiModelProperty(value = "考试名", example = "高等数学期中测试")
    private String examName;
    @ApiModelProperty(value = "考试须知", example = "本场考试允许作弊")
    private String examNote;
    @ApiModelProperty(value = "监考人Id", example = "2")
    private String invigilatorId;
    @ApiModelProperty(value = "考试持续时间(分钟为单位)", example = "120")
    private Integer durationTime;
    @ApiModelProperty(value = "考试开放时间(格式为yyyy-MM-dd HH:mm:ss)", example = "2022-03-23 08:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty(value = "考试截至时间(格式为yyyy-MM-dd HH:mm:ss)", example = "2022-03-24 23:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date deadline;
    @ApiModelProperty(value = "每人允许参考次数", example = "2")
    private Integer allowableTime;
    @ApiModelProperty(value = "参加考试方式:{1:链接+密钥口令 2:勾选}", example = "1")
    private Integer noticeWay;
    @ApiModelProperty(value = "密钥口令", example = "Y@%@FMF$")
    private String word;
    @ApiModelProperty(value = "允许最多切屏次数", example = "2")
    private Integer cuttingTimes;
    @ApiModelProperty(value = "考试结果是否公布：{0:不公布 1:已公布}", example = "0")
    private Integer isPublic;
    @ApiModelProperty(value = "是否为套用卷：{0:否 1:是}", example = "0")
    private Integer isCopy;
    @ApiModelProperty(value = "被套用次数", example = "20")
    private Integer copiedTime;

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
            && (this.getCuttingTimes() == null ? other.getCuttingTimes() == null : this.getCuttingTimes().equals(other.getCuttingTimes()))
            && (this.getIsPublic() == null ? other.getIsPublic() == null : this.getIsPublic().equals(other.getIsPublic()))
            && (this.getIsCopy() == null ? other.getIsCopy() == null : this.getIsCopy().equals(other.getIsCopy()))
            && (this.getCopiedTime() == null ? other.getCopiedTime() == null : this.getCopiedTime().equals(other.getCopiedTime()));
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
        result = prime * result + ((getIsPublic() == null) ? 0 : getIsPublic().hashCode());
        result = prime * result + ((getIsCopy() == null) ? 0 : getIsCopy().hashCode());
        result = prime * result + ((getCopiedTime() == null) ? 0 : getCopiedTime().hashCode());
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
        sb.append(", isPublic=").append(isPublic);
        sb.append(", isCopy=").append(isCopy);
        sb.append(", copiedTime=").append(copiedTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}