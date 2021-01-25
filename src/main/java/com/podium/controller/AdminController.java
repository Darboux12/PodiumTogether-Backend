package com.podium.controller;

import com.podium.configuration.JwtTokenUtil;
import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.converter.ControllerRequestConverter;
import com.podium.controller.dto.request.UserRoleUpdateControllerRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.AdminService;
import com.podium.service.exception.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class AdminController {

    private AdminService adminService;

    public AdminController( AdminService adminService) {
        this.adminService = adminService;
    }

    // ADMIN
    @GetMapping(PodiumEndpoint.synchronizeResources)
    public ResponseEntity synchronizeResources(Authentication authentication) throws PodiumAuthorityException, PodiumEntityNotFoundException {
        this.adminService.synchronizeResourcesWithSystemFiles(authentication.getName());
        return ResponseEntity.ok().build();
    }

    // ADMIN
    @PatchMapping(PodiumEndpoint.grantUserRole)
    public ResponseEntity grantUserRole(@RequestBody UserRoleUpdateControllerRequest request, Authentication authentication) throws PodiumEntityNotFoundException, PodiumAuthorityException, PodiumUserRoleAlreadyGivenException, PodiumSelfPromotionError {
        this.adminService.grantUserRole(ControllerRequestConverter.getInstance().convertGrantRoleRequestToServiceRequest(request,authentication.getName()));
        return ResponseEntity.ok().body("User role was successfully granted!");
    }

    // ADMIN
    @PatchMapping(PodiumEndpoint.degradeUserRole)
    public ResponseEntity degradeUserRole(@RequestBody UserRoleUpdateControllerRequest request, Authentication authentication) throws PodiumEntityNotFoundException, PodiumUserRoleException, PodiumUserDefaultRoleException, PodiumAuthorityException, PodiumSelfDegradationException {
        this.adminService.degradeUserRole(ControllerRequestConverter.getInstance().convertGrantRoleRequestToServiceRequest(request,authentication.getName()));
        return ResponseEntity.ok().body("User role was successfully taken away!");
    }

}
