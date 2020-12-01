package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.news.NewsRequestDto;
import com.podium.model.dto.response.news.NewsResponseDto;
import com.podium.constant.PodiumLimits;
import com.podium.validator.NewsValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;


@TestMethodOrder(MethodOrderer.Alphanumeric.class)
 class NewsTest {

    private static NewsRequestDto newsRequest;
    private static String valueHolder;

    @BeforeAll
    static void beforeClass(){

            TestLogger.setUp();

            newsRequest = new NewsRequestDto(
                    "TestTitle",
                    "TestShortText",
                    "TestLinkText",
                    "TestFullText"
            );
    }

    @Test
    void T01_Add_News_WithOut_Title_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    void T02_Add_News_WithOut_FullText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getText();
        newsRequest.setText("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
    void T03_Add_News_WithOut_ShortText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    void T04_Add_News_WithOut_LinkText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    void T05_Add_Valid_News_ShouldReturnStatus_OK(){
        NewsValidator.getInstance().add(newsRequest,HttpStatus.OK);
    }

    @Test
    void T06_AddSameNewsAgain_ShouldReturnStatus_CONFLICT(){
        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);
    }

    @Test
    void T07_Find_AllNews_ShouldReturnStatus_OK_Iterable_Containing_Added_DTO(){

        NewsResponseDto responseDto = NewsValidator
                .getInstance()
                .findByTitle(newsRequest.getTitle(),HttpStatus.OK);

        Assertions.assertEquals(newsRequest.getTitle(),responseDto.getTitle());

    }
    
    @Test
    void T08_Find_AndDeleteCreatedValidNews_ShouldReturnStatus_OK(){

        NewsResponseDto newsResponseDto =

                NewsValidator
                        .getInstance()
                        .findByTitle(newsRequest.getTitle(),HttpStatus.OK);
        
        NewsValidator
                .getInstance()
                .deleteNewsById(newsResponseDto.getId(),HttpStatus.OK);
    }

    @Test
     void T09_AddNewsToShortTitle_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsTitleLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    void T10_AddNewsToShortShortText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsShortTextLength - 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    void T11_AddNewsToShortFullText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsFullTextLength - 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
     void T12_AddNewsToShortLinkText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsLinkTextLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setLinkText(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    void T13_AddNewsToLongTitle_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsTitleLength + 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    void T14_AddNewsToLongShortText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsShortTextLength + 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    void T15_AddNewsToLongFullText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsFullTextLength + 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
    void T16_AddNewsToLongLinkText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsLinkTextLength+ 1);

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

}
