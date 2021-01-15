package com.podium.controller;

import com.podium.constant.PodiumEndpoint;
import com.podium.controller.dto.request.UserRoleUpdateControllerRequest;
import com.podium.controller.validation.validator.annotation.PodiumValidateController;
import com.podium.service.AdminService;
import com.podium.service.ResourceService;
import com.podium.service.dto.request.UserRoleUpdateServiceRequest;
import com.podium.service.exception.PodiumEntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PodiumValidateController
public class AdminController {

    private AdminService adminService;

    public AdminController( AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/resources/synchronize")
    public ResponseEntity synchronizeResources(){
        this.adminService.synchronizeResourcesWithSystemFiles();
        return ResponseEntity.ok().build();
    }

    @PatchMapping(PodiumEndpoint.grantUserRole)
    public ResponseEntity grantUserRole(@RequestBody UserRoleUpdateControllerRequest request) throws PodiumEntityNotFoundException {
        this.adminService.grantUserRole(this.convertGrantRoleRequestToServiceRequest(request));
        return ResponseEntity.ok().body("User role was successfully granted!");
    }

    private UserRoleUpdateServiceRequest convertGrantRoleRequestToServiceRequest(UserRoleUpdateControllerRequest request){
        return new UserRoleUpdateServiceRequest(
                request.getUsername(),
                request.getRole()
        );
    }


}
