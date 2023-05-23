package id.ac.ui.cs.advprog.purchasepayment.dto.auth;

public enum UserPermission {
    USER_READ("user:read"),
    CHECK_PURCHASED("purchased_app:check_purchased_app"),
    CART_READ_SELF("cart:read_self"),
    CART_DELETE("cart:delete"),
    UPDATE_CART("cart:update");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}