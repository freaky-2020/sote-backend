package com.upc.eden.commen.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName auth
 */
@Data
public class Auth implements Serializable {

    private Integer id;
    private String url;
    private String urlComment;
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