package com.podium.api;

import com.podium.helper.*;
import com.podium.model.entity.Contact;
import com.podium.model.entity.Subject;
import com.podium.model.request.ContactRequest;
import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;


import java.text.ParseException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContactTest {

    private static ContactRequest contactRequest;

    private static Subject addedSubject;

    @BeforeClass
    public static void beforeClass(){

        TestLogger.setUp();
        contactRequest = Constant.getValidContactRequest();
    }

    @Test
    public void T01_addValidSubject_ShouldReturnStatus_200() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .param("subject","TestSubject")
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T02_addExistingSubject_ShouldReturnStatus_409() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .param("subject","TestSubject")
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(409)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T03_addExistingSubject_ShouldReturnHeader_AlreadyExist() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .param("subject","TestSubject")
                .when()
                .post(Path.server + Endpoint.addSubject)
                .then().assertThat()
                .statusCode(409)
                .header("Already-Exist",equalTo("Subject"))
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T04_findExistingSubject_ShouldReturnStatus_200() throws ParseException {

        addedSubject =

        given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("name","TestSubject")
                .when()
                .get(Path.server + Endpoint.findSubjectByName)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(Subject.class);

        contactRequest.setSubject(addedSubject.getName());

    }

    @Test
    public void T05_addValidContact_ShouldReturnStatus_200() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .body(contactRequest)
                .when()
                .post(Path.server + Endpoint.addContact)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T06_findValidContact_ShouldReturnStatus_200() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .queryParam("userEmail",contactRequest.getUserEmail())
                .queryParam("subject",contactRequest.getSubject())
                .queryParam("message",contactRequest.getMessage())
                .when()
                .get(Path.server + Endpoint.findContact)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());
    }

    @Test
    public void T07_findValidContact_ShouldReturnEntity_Contact() throws ParseException {

        given()
                .spec(TestSpecification.buildRequestSpec())
                .contentType(ContentType.JSON)
                .queryParam("userEmail",contactRequest.getUserEmail())
                .queryParam("subject",contactRequest.getSubject())
                .queryParam("message",contactRequest.getMessage())
                .when()
                .get(Path.server + Endpoint.findContact)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec())
                .extract().as(Contact.class);
    }

    @Test
    public void T08_deleteValidContact_ShouldReturnStatus_200() throws ParseException {

        Contact contact =

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .contentType(ContentType.JSON)
                        .queryParam("userEmail",contactRequest.getUserEmail())
                        .queryParam("subject",contactRequest.getSubject())
                        .queryParam("message",contactRequest.getMessage())
                        .when()
                        .get(Path.server + Endpoint.findContact)
                        .then().assertThat()
                        .statusCode(200)
                        .spec(TestSpecification.buildResponseSpec())
                        .extract().as(Contact.class);

        int id = contact.getContactId();

        given()
                .spec(TestSpecification.buildRequestSpec())
                .pathParam("id",id)
                .when()
                .delete(Path.server + Endpoint.deleteContact)
                .then().assertThat()
                .statusCode(200)
                .spec(TestSpecification.buildResponseSpec());



    }

    @Test
    public void T09_deleteSubject_ShouldReturnStatus_200() throws ParseException {

                given()
                        .spec(TestSpecification.buildRequestSpec())
                        .pathParam("name","TestSubject")
                        .when()
                        .delete(Path.server + Endpoint.deleteSubject)
                        .then().assertThat()
                        .statusCode(200)
                        .spec(TestSpecification.buildResponseSpec());


    }



}
