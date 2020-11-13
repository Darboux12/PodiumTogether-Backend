package com.podium.api;

import com.podium.logger.TestLogger;
import com.podium.model.dto.request.NewsRequestDto;
import com.podium.model.dto.response.NewsResponseDto;
import com.podium.constant.PodiumLimits;
import com.podium.validator.NewsValidator;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.junit.runners.MethodSorters;
import org.springframework.http.HttpStatus;


@RunWith(JUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NewsTest {

    private static NewsRequestDto newsRequest;
    private static String valueHolder;

    @BeforeClass
    public static void beforeClass(){

            TestLogger.setUp();

            newsRequest = new NewsRequestDto(
                    "TestTitle",
                    "TestShortText",
                    "TestLinkText",
                    "TestFullText"
            );
    }

    @Test
    public void T01_Add_News_WithOut_Title_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    public void T02_Add_News_WithOut_FullText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getText();
        newsRequest.setText("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
    public void T03_Add_News_WithOut_ShortText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    public void T04_Add_News_WithOut_LinkText_ShouldReturnStatus_CONFLICT(){

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText("");

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    public void T05_Add_Valid_News_ShouldReturnStatus_OK(){
        NewsValidator.getInstance().add(newsRequest,HttpStatus.OK);
    }

    @Test
    public void T06_AddSameNewsAgain_ShouldReturnStatus_CONFLICT(){
        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);
    }

    @Test
    public void T07_Find_AllNews_ShouldReturnStatus_OK_Iterable_Containing_Added_DTO(){

        NewsResponseDto responseDto = NewsValidator
                .getInstance()
                .findByTitle(newsRequest.getTitle(),HttpStatus.OK);

        Assert.assertEquals(newsRequest.getTitle(),responseDto.getTitle());

    }
    
    @Test
    public void T08_Find_AndDeleteCreatedValidNews_ShouldReturnStatus_OK(){

        NewsResponseDto newsResponseDto =

                NewsValidator
                        .getInstance()
                        .findByTitle(newsRequest.getTitle(),HttpStatus.OK);
        
        NewsValidator
                .getInstance()
                .deleteNewsById(newsResponseDto.getId(),HttpStatus.OK);
    }

    @Test
    public void T09_AddNewsToShortTitle_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsTitleLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    public void T10_AddNewsToShortShortText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsShortTextLength - 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    public void T11_AddNewsToShortFullText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsFullTextLength - 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
    public void T12_AddNewsToShortLinkText_ShouldReturnStatus_CONFLICT(){

        String toShort = StringUtils.repeat("*", PodiumLimits.minNewsLinkTextLength - 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setLinkText(toShort);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

    @Test
    public void T13_AddNewsToLongTitle_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsTitleLength + 1);

        valueHolder = newsRequest.getTitle();
        newsRequest.setTitle(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setTitle(valueHolder);

    }

    @Test
    public void T14_AddNewsToLongShortText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsShortTextLength + 1);

        valueHolder = newsRequest.getShortText();
        newsRequest.setShortText(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setShortText(valueHolder);

    }

    @Test
    public void T15_AddNewsToLongFullText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsFullTextLength + 1);

        valueHolder = newsRequest.getText();
        newsRequest.setText(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setText(valueHolder);

    }

    @Test
    public void T16_AddNewsToLongLinkText_ShouldReturnStatus_CONFLICT(){

        String toLong = StringUtils.repeat("*", PodiumLimits.maxNewsLinkTextLength+ 1);

        valueHolder = newsRequest.getLinkText();
        newsRequest.setLinkText(toLong);

        NewsValidator.getInstance().add(newsRequest,HttpStatus.CONFLICT);

        newsRequest.setLinkText(valueHolder);

    }

}
