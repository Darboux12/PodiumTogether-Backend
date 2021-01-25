package com.podium.api;

import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.controller.dto.request.SubjectAddControllerRequest;
import com.podium.controller.dto.request.UserRoleUpdateControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.validator.AdminValidator;
import com.podium.validator.UserValidator;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.util.Set;

@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class AdminTest {

    private static String username = "TEST USERNAME_ONE";
    private static String token = "";

    @BeforeAll
    static void beforeClass(){
        TestLogger.setUp();
    }

    @Test
    void T01_Sign_In_As_Admin_User_Get_Token(){

        token =

        UserValidator
                .getInstance()
                .signIn(new JwtControllerRequest("admin","adminadmin"),HttpStatus.OK)
                .getToken();

    }

    @Test
    void T02_Grant_Admin_Role_Return_Status_OK(){

        AdminValidator
                .getInstance()
                .grantUserRole(new UserRoleUpdateControllerRequest(username,"admin"),token,HttpStatus.OK);

    }

    @Test
    void T03_Find_User_Contains_Role_Admin(){

        boolean isPresent = UserValidator
                .getInstance()
                .findUserByUsername(username,token,HttpStatus.OK)
                .getRoles().contains("admin");

        Assertions.assertTrue(isPresent);

    }

    @Test
    void T04_Degrade_Role_To_Subscriber_Return_Status_OK(){

        AdminValidator
                .getInstance()
                .degradeUserRole(new UserRoleUpdateControllerRequest(username,"admin"),token,HttpStatus.OK);

    }

    @Test
    void T05_Grant_Admin_Role_To_Yourself_Return_Status_CONFLICT(){

        AdminValidator
                .getInstance()
                .grantUserRole(new UserRoleUpdateControllerRequest("admin","admin"),token,HttpStatus.CONFLICT);

    }

    @Test
    void T06_Degrade_Admin_Role_From_Yourself_Return_Status_CONFLICT(){

        AdminValidator
                .getInstance()
                .degradeUserRole(new UserRoleUpdateControllerRequest("admin","admin"),token,HttpStatus.CONFLICT);

    }

    @Test
    void T07_Sign_In_As_Subscriber_User_Get_Token(){

        token =

                UserValidator
                        .getInstance()
                        .signIn(new JwtControllerRequest("TEST USERNAME_TWO","TEST PASSWORD TWO"),HttpStatus.OK)
                        .getToken();

    }

    @Test
    void T08_Grant_Admin_Role_Without_Authority_Return_Status_UNAUTHORIZED(){

        AdminValidator
                .getInstance()
                .grantUserRole(new UserRoleUpdateControllerRequest(username,"admin"),token,HttpStatus.UNAUTHORIZED);

    }

    @Test
    void T09_Degrade_Admin_Role_Without_Authority_Return_Status_UNAUTHORIZED(){

        AdminValidator
                .getInstance()
                .degradeUserRole(new UserRoleUpdateControllerRequest(username,"admin"),token,HttpStatus.UNAUTHORIZED);

    }

    @Test
    void T10_SynchronizeResources_Without_Admin_Authority_Return_Status_UNAUTHORIZED(){

        AdminValidator.getInstance().synchronizeResources(token,HttpStatus.UNAUTHORIZED);
    }

}
