package com.upc.eden.auth.domain;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.upc.eden.auth.mapper.RoleMapper;
import com.upc.eden.commen.domain.Role;
import com.upc.eden.commen.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: CS Dong
 * @Date: 2022/03/13/17:30
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecurityUser implements UserDetails {

    @Resource
    PasswordEncoder passwordEncoder;

    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private String role;

    private Collection<SimpleGrantedAuthority> authorities;

    public SecurityUser(User user, String roleName) {

        this.setId(user.getId());
        this.setUsername(user.getUserName());
        this.setPassword(user.getPassword());
        this.setEnabled(user.getUserStatus() == 1);
        this.setRole(roleName);
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Stream.of(this.getRole())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

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
        return this.enabled;
    }
}
