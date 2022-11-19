package com.example.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.domain.Admin;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * @author : [wangminan]
 * @description : [实现UserDetails接口]
 * 覆写记得把所有false改为true
 * 需要将用户信息全部封装为UserDetailsImpl对象 传递给AuthenticationManager
 */
@Data
@NoArgsConstructor
public class LoginAdmin implements UserDetails {

    //@Autowired
    private Admin admin;

    // 权限
    private List<String> permissions;

    public LoginAdmin(Admin admin, List<String> permissions) {
        this.admin = admin;
        this.permissions = permissions;
    }

    @JSONField(serialize = false) // 不接受redis序列化 只要序列化permissions即可
    private List<GrantedAuthority> authorities;

    /**
     * 获取用户权限
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(authorities!=null){
            return authorities;
        }

        // 要把permissions封装为GrantedAuthority对象
//        使用遍历的方式获取权限
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for(String permission : permissions){
//            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permission);
//            authorities.add(simpleGrantedAuthority);
//        }
//        return authorities;
//
//        // stream方式获取权限
//        authorities
//                = permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
//        return authorities;
        return null;
    }

    @Override
    public String getPassword() {
        return admin.getPassword();
    }

    @Override
    public String getUsername() {
        return admin.getUsername();
    }

    /**
     * 账户是否未过期
     * 重写之后记得更改
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
