package com.example.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

import com.example.pojo.Work;
import lombok.Data;

/**
 *
 * @TableName Music
 */
@TableName(value ="Music")
@Data
public class Music extends Work implements Serializable {
    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 逻辑删除字段,0未删除,1已删除
     */
    @TableLogic(value = "0", delval = "1")
    private Integer isdeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public Music(String name, String type, String publishyear, String description, String picurl) {
        this.name = name;
        this.type = type;
        this.publishyear = publishyear;
        this.description = description;
        this.picurl = picurl;
        this.worktype = "music";
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
        Music other = (Music) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getPublishyear() == null ? other.getPublishyear() == null : this.getPublishyear().equals(other.getPublishyear()))
            && (this.getDescription() == null ? other.getDescription() == null : this.getDescription().equals(other.getDescription()))
            && (this.getPicurl() == null ? other.getPicurl() == null : this.getPicurl().equals(other.getPicurl()))
            && (this.getIsdeleted() == null ? other.getIsdeleted() == null : this.getIsdeleted().equals(other.getIsdeleted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getPublishyear() == null) ? 0 : getPublishyear().hashCode());
        result = prime * result + ((getDescription() == null) ? 0 : getDescription().hashCode());
        result = prime * result + ((getPicurl() == null) ? 0 : getPicurl().hashCode());
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
        sb.append(", name=").append(name);
        sb.append(", type=").append(type);
        sb.append(", publishyear=").append(publishyear);
        sb.append(", description=").append(description);
        sb.append(", picurl=").append(picurl);
        sb.append(", isdeleted=").append(isdeleted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
