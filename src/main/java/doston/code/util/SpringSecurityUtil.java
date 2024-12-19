package doston.code.util;


import doston.code.enums.ProfileRole;
import doston.code.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityUtil {


    public static CustomUserDetails getCurrentEntity() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetail = (CustomUserDetails) authentication.getPrincipal();

        return userDetail;
    }

    public static Long getCurrentUserId() {
        CustomUserDetails userDetail = getCurrentEntity();

        return userDetail.getId();
    }

    public static ProfileRole getCurrentUserRole() {
        return getCurrentEntity().getRole();
    }



}
