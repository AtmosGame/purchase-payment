package id.ac.ui.cs.advprog.purchasepayment.dto.auth;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
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
}
