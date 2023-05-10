package id.ac.ui.cs.advprog.purchasepayment.dto.auth;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    private Integer id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private String profilePicture;
    private String bio;

    @Nullable
    private String applications;

    private Boolean active;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.name().equals("ADMIN")) {
            return UserRole.ADMIN.getGrantedAuthority();
        } else {
            return UserRole.USER.getGrantedAuthority();
        }
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.active;
    }

    @Override
    public boolean isEnabled() {
        return this.active;
    }

    public static User fromUserResponse(UserResponse userResponse) {
        return User.builder()
                .id(userResponse.getId())
                .username(userResponse.getUsername())
                .password(userResponse.getPassword())
                .role(userResponse.getRole())
                .profilePicture(userResponse.getProfilePicture())
                .bio(userResponse.getBio())
                .applications(userResponse.getApplications())
                .active(userResponse.getActive())
                .build();
    }
}
