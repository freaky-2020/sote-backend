package com.upc.eden.commen.domain.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 
 * @TableName user
 */
@ApiModel(description = "用户实体类")
@Data
@NoArgsConstructor
public class User implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "账户id", example = "1", hidden = true)
    private Integer id;
    @ApiModelProperty(value = "账号", example = "1904011106")
    private String userName;
    @ApiModelProperty(value = "密码", example = "123456")
    private String password;
    @ApiModelProperty(value = "角色id { 1:管理员 2:教师 3:学生 }", example = "1")
    private Integer roleId;
    @ApiModelProperty(value = "账号状态 { 1:正常 2:停用 }", example = "1")
    private Integer userStatus;
    @ApiModelProperty(value = "用户名", example = "dcs")
    private String realName;
    @ApiModelProperty(value = "学校", example = "中国石油大学(华东)")
    private String userUniv;
    @ApiModelProperty(value = "单位/部门", example = "计算机科学与技术学院")
    private String userUnit;

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
        User other = (User) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserName() == null ? other.getUserName() == null : this.getUserName().equals(other.getUserName()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getRoleId() == null ? other.getRoleId() == null : this.getRoleId().equals(other.getRoleId()))
            && (this.getUserStatus() == null ? other.getUserStatus() == null : this.getUserStatus().equals(other.getUserStatus()))
            && (this.getRealName() == null ? other.getRealName() == null : this.getRealName().equals(other.getRealName()))
            && (this.getUserUniv() == null ? other.getUserUniv() == null : this.getUserUniv().equals(other.getUserUniv()))
            && (this.getUserUnit() == null ? other.getUserUnit() == null : this.getUserUnit().equals(other.getUserUnit()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserName() == null) ? 0 : getUserName().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getRoleId() == null) ? 0 : getRoleId().hashCode());
        result = prime * result + ((getUserStatus() == null) ? 0 : getUserStatus().hashCode());
        result = prime * result + ((getRealName() == null) ? 0 : getRealName().hashCode());
        result = prime * result + ((getUserUniv() == null) ? 0 : getUserUniv().hashCode());
        result = prime * result + ((getUserUnit() == null) ? 0 : getUserUnit().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userName=").append(userName);
        sb.append(", password=").append(password);
        sb.append(", userRole=").append(roleId);
        sb.append(", userStatus=").append(userStatus);
        sb.append(", realName=").append(realName);
        sb.append(", userUniv=").append(userUniv);
        sb.append(", userUnit=").append(userUnit);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}