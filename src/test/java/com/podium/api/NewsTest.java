package com.podium.api;

import com.podium.controller.dto.request.JwtControllerRequest;
import com.podium.logger.TestLogger;
import com.podium.controller.dto.request.NewsAddControllerRequest;
import com.podium.controller.dto.response.NewsControllerResponse;
import com.podium.constant.PodiumLimits;
import com.podium.validator.NewsValidator;
import com.podium.validator.UserValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class NewsTest {

    private static NewsAddControllerRequest newsRequest;
    private static String valueHolder;

    private static String token = "";

    private static Stream<String> provideEmptyValuesForTests(){
        return Stream.of("", " ", "  ", "       ");
    }

    private static Stream<NewsAddControllerRequest> provideNewsForTests(){

        return Stream.of(

                newsRequest = new NewsAddControllerRequest(
                        "TestTitleOne",
                        "TestShortTextOne",
                        "TestLinkTextOne",
                        "TestFullTextOne"
                ),

                newsRequest = new NewsAddControllerRequest(
                        "TestTitleTwo",
                        "TestShortTextTwo",
                        "TestLinkTextTwo",
                        "TestFullTextTwo"
                ),

                newsRequest = new NewsAddControllerRequest(
                        "TestTitleThreeThree",
                        "TestShortTextThree",
                        "TestLinkTextThree",
                        "TestFullTextThree"
                )



        );

    }

    @BeforeAll
    static void beforeClass(){

        TestLogger.setUp();

        newsRequest = new NewsAddControllerRequest
                ("TestTitle",
                        "TestShortText",
                        "TestLinkText","TextText");


    }

    @Test
    void T01_Sign_In_As_Admin_User_Get_Token(){

        token =

                UserValidator
                        .getInstance()
                        .signIn(new JwtControllerRequest("admin","adminadmin"),HttpStatus.OK)
                        .getToken();

    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T02_Add_News_Empty_Title_ShouldReturnStatus_CONFLICT(String emptyTitle){
        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(emptyTitle);
        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);
        newsRequest.setTitle(valueHolder);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T03_Add_News_Empty_FullText_ShouldReturnStatus_CONFLICT(String emptyFullText){
        valueHolder = newsRequest.getText();
        newsRequest.setText(emptyFullText);
        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);
        newsRequest.setText(valueHolder);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T04_Add_News_Empty_ShortText_ShouldReturnStatus_CONFLICT(String emptyShortText){
        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(emptyShortText);
        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);
        newsRequest.setShortText(valueHolder);
    }

    @ParameterizedTest
    @MethodSource("provideEmptyValuesForTests")
    void T05_Add_News_Empty_LinkText_ShouldReturnStatus_CONFLICT(String emptyLinkText){
        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText(emptyLinkText);
        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);
        newsRequest.setLinkText(valueHolder);
    }

    @ParameterizedTest
    @MethodSource("provideNewsForTests")
    void T06_Add_Valid_News_ShouldReturnStatus_OK(NewsAddControllerRequest requestDto){
        NewsValidator.getInstance().add(requestDto,token,HttpStatus.OK);
    }

    @ParameterizedTest
    @MethodSource("provideNewsForTests")
    void T07_AddSameNewsAgain_ShouldReturnStatus_CONFLICT(NewsAddControllerRequest requestDto){
        NewsValidator.getInstance().add(requestDto,token,HttpStatus.CONFLICT);
    }

    @Test
    void T08_Find_AllNews_ShouldReturnStatus_OK_Iterable_Containing_Added_DTOs(){

        List<String> responseNewsTitles = NewsValidator
                .getInstance()
                .findAll()
                .stream()
                .map(NewsControllerResponse::getTitle)
                .collect(Collectors.toList());

        Assertions.assertTrue(responseNewsTitles.containsAll
                        (provideNewsForTests()
                                .map(NewsAddControllerRequest::getTitle)
                                .collect(Collectors.toList())));

    }

    @ParameterizedTest
    @MethodSource("provideNewsForTests")
    void T09_Find_And_DeleteCreatedValidNews_ShouldReturnStatus_OK(NewsAddControllerRequest requestDto){

        NewsControllerResponse newsControllerResponse =

                NewsValidator
                        .getInstance()
                        .findByTitle(requestDto.getTitle(),HttpStatus.OK);

        NewsValidator
                .getInstance()
                .deleteNewsById(newsControllerResponse.getId(),token,HttpStatus.OK);
    }

    @Test
     void T10_AddNewsToShortTitle_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsTitleLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toShort);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    void T11_AddNewsToShortShortText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsShortTextLength - 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toShort);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    void T12_AddNewsToShortFullText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsFullTextLength - 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toShort);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
     void T13_AddNewsToShortLinkText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsLinkTextLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setLinkText(toShort);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    void T14_AddNewsToLongTitle_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsTitleLength + 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toLong);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    void T15_AddNewsToLongShortText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsShortTextLength + 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toLong);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    void T16_AddNewsToLongFullText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsFullTextLength + 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toLong);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
    void T17_AddNewsToLongLinkText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsLinkTextLength+ 1);

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText(toLong);

        NewsValidator.getInstance().add(newsRequest,token,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }




}
