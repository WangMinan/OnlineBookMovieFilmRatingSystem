package com.example.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private String authname;

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

    public VueRoute(String authname, String path, Long father) {
        this.authname = authname;
        this.path = path;
        this.father = father;
        this.children = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VueRoute vueRoute = (VueRoute) o;

        if (!Objects.equals(id, vueRoute.id)) return false;
        if (!Objects.equals(authname, vueRoute.authname)) return false;
        if (!Objects.equals(path, vueRoute.path)) return false;
        if (!Objects.equals(father, vueRoute.father)) return false;
        return Objects.equals(children, vueRoute.children);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (authname != null ? authname.hashCode() : 0);
        result = 31 * result + (path != null ? path.hashCode() : 0);
        result = 31 * result + (father != null ? father.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "VueRoute{" +
                "id=" + id +
                ", authName='" + authname + '\'' +
                ", path='" + path + '\'' +
                ", father=" + father +
                ", children=" + children +
                '}';
    }
}
