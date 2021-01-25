package com.podium.validator;

import com.podium.constant.PodiumEndpoint;
import com.podium.constant.PodiumPath;
import com.podium.controller.dto.request.UserRoleUpdateControllerRequest;
import com.podium.specification.TestSpecification;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

public class AdminValidator {

    private static AdminValidator instance;

    private AdminValidator() {}

    public static AdminValidator getInstance() {
        if(instance == null) {
            instance = new AdminValidator();
        }
        return instance;
    }

    public void grantUserRole(UserRoleUpdateControllerRequest request,String token, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .patch(PodiumPath.server + PodiumEndpoint.grantUserRole)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void degradeUserRole(UserRoleUpdateControllerRequest request,String token, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .body(request)
                .when()
                .patch(PodiumPath.server + PodiumEndpoint.degradeUserRole)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

    public void synchronizeResources(String token, HttpStatus status){

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(PodiumPath.server + PodiumEndpoint.synchronizeResources)
                .then().assertThat()
                .statusCode(status.value())
                .spec(TestSpecification.buildResponseSpec());

    }

}
