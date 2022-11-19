package com.example.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @TableName Assessment
 */
@TableName(value ="Assessment")
@Data
public class Assessment implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     *
     */
    private Long userid;

    /**
     *
     */
    private Long objectid;

    /**
     *
     */
    private String objecttype;

    /**
     *
     */
    private String assessment;

    /**
     * 发布该条留言的日期
     */
    private String postdate;

    /**
     * 逻辑删除字段,0未删除,1已删除
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isdeleted;

    @TableField(exist = false)
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
        Assessment other = (Assessment) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()))
            && (this.getObjectid() == null ? other.getObjectid() == null : this.getObjectid().equals(other.getObjectid()))
            && (this.getObjecttype() == null ? other.getObjecttype() == null : this.getObjecttype().equals(other.getObjecttype()))
            && (this.getAssessment() == null ? other.getAssessment() == null : this.getAssessment().equals(other.getAssessment()))
            && (this.getPostdate() == null ? other.getPostdate() == null : this.getPostdate().equals(other.getPostdate()))
            && (this.getIsdeleted() == null ? other.getIsdeleted() == null : this.getIsdeleted().equals(other.getIsdeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());
        result = prime * result + ((getObjectid() == null) ? 0 : getObjectid().hashCode());
        result = prime * result + ((getObjecttype() == null) ? 0 : getObjecttype().hashCode());
        result = prime * result + ((getAssessment() == null) ? 0 : getAssessment().hashCode());
        result = prime * result + ((getPostdate() == null) ? 0 : getPostdate().hashCode());
        result = prime * result + ((getIsdeleted() == null) ? 0 : getIsdeleted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", objectid=").append(objectid);
        sb.append(", objecttype=").append(objecttype);
        sb.append(", assessment=").append(assessment);
        sb.append(", postdate=").append(postdate);
        sb.append(", isdeleted=").append(isdeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
