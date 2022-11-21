package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @TableName VueRoute
 */
@TableName(value ="VueRoute")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VueRoute implements Serializable {
    /**
     *
     */
    private Long id;

    /**
     *
     */
    @TableId
    private String authName;

    /**
     *
     */
    private String path;

    /**
     *
     */
    private Long father;

    @TableField(exist = false)
    private List<VueRoute> children;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public VueRoute(String authName, String path, Long father) {
        this.authName = authName;
        this.path = path;
        this.father = father;
        this.children = new ArrayList<>();
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
        VueRoute other = (VueRoute) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getAuthName() == null ? other.getAuthName() == null : this.getAuthName().equals(other.getAuthName()))
            && (this.getPath() == null ? other.getPath() == null : this.getPath().equals(other.getPath()))
            && (this.getFather() == null ? other.getFather() == null : this.getFather().equals(other.getFather()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getAuthName() == null) ? 0 : getAuthName().hashCode());
        result = prime * result + ((getPath() == null) ? 0 : getPath().hashCode());
        result = prime * result + ((getFather() == null) ? 0 : getFather().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", authname=").append(authName);
        sb.append(", path=").append(path);
        sb.append(", father=").append(father);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}
