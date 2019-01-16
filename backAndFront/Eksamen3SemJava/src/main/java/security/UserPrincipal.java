package security;

import java.security.Principal;

public class UserPrincipal implements Principal {

    private String email;
    private String role;

    /* Create a UserPrincipal, given the Entity class User*/
//    public UserPrincipal(Employee emp) {
//        this.email = emp.getEmail();
//        this.role = emp.getRole().toString();
//    }

    public UserPrincipal(String username, String role) {
        super();
        this.email = username;
        this.role = role;
    }

    @Override
    public String getName() {
        return email;
    }

    public String getEmail() {
        return email;
    }

    public boolean isUserInRole(String role) {
        return this.role.equals(role);
    }

}
