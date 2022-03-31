package com.upc.eden.commen.domain.bank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName question
 */
@ApiModel(description = "题目实体类")
public class Question implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "题目Id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "科目Id", example = "1")
    private Integer subjectId;
    @ApiModelProperty(value = "题型Id", example = "1")
    private Integer typeId;
    @ApiModelProperty(value = "难度Id", example = "1")
    private Integer difficultyId;
    @ApiModelProperty(value = "题干：图片或文档连接以 ’$$‘ 拼接在后面", example = "1+1=?$$http://baidu.com")
    private String stem;
    @ApiModelProperty("选项1（判断、填空与简答不需要）")
    private String choice1;
    @ApiModelProperty("选项2（判断、填空与简答不需要）")
    private String choice2;
    @ApiModelProperty("选项3（判断、填空与简答不需要）")
    private String choice3;
    @ApiModelProperty("选项4（判断、填空与简答不需要）")
    private String choice4;
    @ApiModelProperty(value = "答案：选择题为选项、判断题为{1:true 2:false}、填空/简答为答案", example = "扁桃体")
    private String answer;
    @ApiModelProperty(value = "解析：图片或文档链接以’$$‘拼接在后面", example = "发炎必是扁桃体$$http://baidu.com")
    private String remark;
    @ApiModelProperty(value = "题目创建日期：格式为 yyyy-MM-dd", example = "2022-03-30", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @ApiModelProperty(value = "题目更新日期：格式为 yyyy-MM-dd", example = "2022-05-29", hidden = true)
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

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
    public Integer getSubjectId() {
        return subjectId;
    }

    /**
     * 
     */
    public void setSubjectId(Integer subjectId) {
        this.subjectId = subjectId;
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
    public Integer getDifficultyId() {
        return difficultyId;
    }

    /**
     * 
     */
    public void setDifficultyId(Integer difficultyId) {
        this.difficultyId = difficultyId;
    }

    /**
     * 
     */
    public String getStem() {
        return stem;
    }

    /**
     * 
     */
    public void setStem(String stem) {
        this.stem = stem;
    }

    /**
     * 
     */
    public String getChoice1() {
        return choice1;
    }

    /**
     * 
     */
    public void setChoice1(String choice1) {
        this.choice1 = choice1;
    }

    /**
     * 
     */
    public String getChoice2() {
        return choice2;
    }

    /**
     * 
     */
    public void setChoice2(String choice2) {
        this.choice2 = choice2;
    }

    /**
     * 
     */
    public String getChoice3() {
        return choice3;
    }

    /**
     * 
     */
    public void setChoice3(String choice3) {
        this.choice3 = choice3;
    }

    /**
     * 
     */
    public String getChoice4() {
        return choice4;
    }

    /**
     * 
     */
    public void setChoice4(String choice4) {
        this.choice4 = choice4;
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
    public String getRemark() {
        return remark;
    }

    /**
     * 
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Question other = (Question) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getSubjectId() == null ? other.getSubjectId() == null : this.getSubjectId().equals(other.getSubjectId()))
            && (this.getTypeId() == null ? other.getTypeId() == null : this.getTypeId().equals(other.getTypeId()))
            && (this.getDifficultyId() == null ? other.getDifficultyId() == null : this.getDifficultyId().equals(other.getDifficultyId()))
            && (this.getStem() == null ? other.getStem() == null : this.getStem().equals(other.getStem()))
            && (this.getChoice1() == null ? other.getChoice1() == null : this.getChoice1().equals(other.getChoice1()))
            && (this.getChoice2() == null ? other.getChoice2() == null : this.getChoice2().equals(other.getChoice2()))
            && (this.getChoice3() == null ? other.getChoice3() == null : this.getChoice3().equals(other.getChoice3()))
            && (this.getChoice4() == null ? other.getChoice4() == null : this.getChoice4().equals(other.getChoice4()))
            && (this.getAnswer() == null ? other.getAnswer() == null : this.getAnswer().equals(other.getAnswer()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getUpdateTime() == null ? other.getUpdateTime() == null : this.getUpdateTime().equals(other.getUpdateTime()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getSubjectId() == null) ? 0 : getSubjectId().hashCode());
        result = prime * result + ((getTypeId() == null) ? 0 : getTypeId().hashCode());
        result = prime * result + ((getDifficultyId() == null) ? 0 : getDifficultyId().hashCode());
        result = prime * result + ((getStem() == null) ? 0 : getStem().hashCode());
        result = prime * result + ((getChoice1() == null) ? 0 : getChoice1().hashCode());
        result = prime * result + ((getChoice2() == null) ? 0 : getChoice2().hashCode());
        result = prime * result + ((getChoice3() == null) ? 0 : getChoice3().hashCode());
        result = prime * result + ((getChoice4() == null) ? 0 : getChoice4().hashCode());
        result = prime * result + ((getAnswer() == null) ? 0 : getAnswer().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getUpdateTime() == null) ? 0 : getUpdateTime().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", subjectId=").append(subjectId);
        sb.append(", typeId=").append(typeId);
        sb.append(", difficultyId=").append(difficultyId);
        sb.append(", stem=").append(stem);
        sb.append(", choice1=").append(choice1);
        sb.append(", choice2=").append(choice2);
        sb.append(", choice3=").append(choice3);
        sb.append(", choice4=").append(choice4);
        sb.append(", answer=").append(answer);
        sb.append(", remark=").append(remark);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}