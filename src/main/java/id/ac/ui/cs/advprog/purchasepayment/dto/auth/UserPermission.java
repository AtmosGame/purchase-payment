package id.ac.ui.cs.advprog.purchasepayment.dto.auth;

public enum UserPermission {
    USER_READ("user:read"),
    CART_READ_SELF("cart:read_self"),
    CART_DELETE("cart:delete");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}