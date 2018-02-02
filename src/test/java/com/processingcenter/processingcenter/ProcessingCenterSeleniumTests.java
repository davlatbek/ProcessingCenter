package com.processingcenter.processingcenter;

import com.processingcenter.processingcenter.config.Configuration;
import io.qameta.allure.Feature;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
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
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get(Configuration.URL);
    }

    @Test
    public void addNewAccountTest(){
        WebElement addNewAccountButton = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/div/div[2]/div[3]/div/div/div/div[1]/div/div[3]/div"));
        addNewAccountButton.click();

        WebElement textFieldName = driver.findElement(By.xpath("//*[@id=\"gwt-uid-7\"]"));
        WebElement textFieldLastName = driver.findElement(By.xpath("//*[@id=\"gwt-uid-9\"]"));
        WebElement buttonSave = driver.findElement(By.xpath("//*[@id=\"ROOT-2521314\"]/div/div[2]/div[3]/div/div[3]/div/div[5]/div/div[1]"));

        textFieldName.sendKeys("First Name");
        textFieldLastName.sendKeys("Last Name");
        buttonSave.click();

    }

    @AfterTest
    public static void tearDown() {
        driver.quit();
    }
}
