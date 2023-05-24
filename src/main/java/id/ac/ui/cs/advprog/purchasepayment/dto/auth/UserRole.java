package id.ac.ui.cs.advprog.purchasepayment.dto.auth;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import com.google.common.collect.Sets;

import java.util.Set;
import java.util.stream.Collectors;

import static id.ac.ui.cs.advprog.purchasepayment.dto.auth.UserPermission.*;

public enum UserRole {
    ADMIN(Sets.newHashSet(USER_READ)),
    DEVELOPER(Sets.newHashSet(USER_READ)),
    USER(Sets.newHashSet(USER_READ, CART_READ_SELF, CART_DELETE, CHECK_PURCHASED, UPDATE_CART));

    private final Set<UserPermission> permissions;


    UserRole(Set<UserPermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getGrantedAuthority() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream().map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
