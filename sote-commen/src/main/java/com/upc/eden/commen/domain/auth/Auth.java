package com.upc.eden.commen.domain.auth;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName auth
 */
@Data
public class Auth implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "数据库id", example = "1")
    private Integer id;
    @ApiModelProperty(value = "后端Restful接口url", example = "/exam/analysis/segment/{examId}")
    private String url;
    @ApiModelProperty(value = "对应接口说明", example = "键入examId，获取某考试分数分段表")
    private String urlComment;
    @ApiModelProperty(value = "该接口允许的系统角色", example = "admin,teacher")
    private String authRole;

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
        Auth other = (Auth) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUrl() == null ? other.getUrl() == null : this.getUrl().equals(other.getUrl()))
            && (this.getUrlComment() == null ? other.getUrlComment() == null : this.getUrlComment().equals(other.getUrlComment()))
            && (this.getAuthRole() == null ? other.getAuthRole() == null : this.getAuthRole().equals(other.getAuthRole()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUrl() == null) ? 0 : getUrl().hashCode());
        result = prime * result + ((getUrlComment() == null) ? 0 : getUrlComment().hashCode());
        result = prime * result + ((getAuthRole() == null) ? 0 : getAuthRole().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", url=").append(url);
        sb.append(", urlComment=").append(urlComment);
        sb.append(", authRole=").append(authRole);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}