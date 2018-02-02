package com.processingcenter.processingcenter;

import com.processingcenter.processingcenter.config.Configuration;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

/**
 * Created by davlet on 2/2/18.
 */
@Feature("Selenium automation tests")
public class ProcessingCenterSeleniumTests {

    private static WebDriver driver;

    @BeforeTest
    public void setUp(){
        System.setProperty("webdriver.gecko.driver", Configuration.PATH_GECKO_DRIVER);
        driver = new SafariDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get(Configuration.URL);
    }

    @Test
    public void addNewAccountTest(){

    }

    @AfterTest
    public static void tearDown() {
        driver.quit();
    }
}
