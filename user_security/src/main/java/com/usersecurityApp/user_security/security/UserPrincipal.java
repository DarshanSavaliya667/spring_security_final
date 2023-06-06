package com.usersecurityApp.user_security.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.usersecurityApp.user_security.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPrincipal implements UserDetails {

    private Long id;

    private String name;

    private String username;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;


    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(Long id, String name, String username, String password, List<GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.username = username;

        this.password = password;
        this.authorities = authorities;
    }


    public static UserPrincipal create(User user) {
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();

        if (user.getIsAdmin()) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (user.getIsSuperAdmin()) {
            roles.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
        }
        List<GrantedAuthority> authorities = roles;
        return new UserPrincipal(user.getId(), user.getName(), user.getUsername(),
                user.getPassword(), authorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
