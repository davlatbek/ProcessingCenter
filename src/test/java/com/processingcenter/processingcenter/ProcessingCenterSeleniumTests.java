package com.processingcenter.processingcenter;

import com.processingcenter.processingcenter.config.Configuration;
import io.qameta.allure.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by davlet on 2/2/18.
 */
@Epic("UI Tests")
@Feature("Processing center UI tests")
public class ProcessingCenterSeleniumTests {

    private static WebDriver driver;

    private final static String xpath_filter_field = "//input[@type=\"text\"][@placeholder=\"Filter by last name\"]";
    private final static String xpath_add_new_account= "//div[contains(@class, \"v-button-primary\")][1]";
    private final static String xpath_field_first_name = "//input[@type=\"text\"][@placeholder=\"first name\"]";
    private final static String xpath_field_last_name = "//input[@type=\"text\"][@placeholder=\"last name\"]";
    private final static String xpath_save_button = "//div[contains(@class, \"v-button-primary\")]//*[text()=\"Save\"]";

//    private final static String xpath_account_table = "//div[contains(@class, \"v-grid-tablewrapper\")]";
    private final static String xpath_account_table = "//*[@id=\"gwt-uid-3\"]";
    private final static String xpath_account_table_rows = "//*[@id=\"gwt-uid-3\"]//tbody//tr";

    private final static String xpath_field_from_id = "//input[@type=\"text\"][@placeholder=\"from account id\"]";
    private final static String xpath_field_to_id = "//input[@type=\"text\"][@placeholder=\"to account id\"]";
    private final static String xpath_field_amount = "//input[@type=\"text\"][@placeholder=\"amountField\"]";
    private final static String xpath_make_transfer_button = "//div[contains(@class, \"v-button-primary\")]";

    private final static String xpath_transactions_table = "//*[@id=\"gwt-uid-5\"]//tbody//tr";
    private final static String xpath_transactions_table_rows = "//*[@id=\"gwt-uid-5\"]//tbody//tr";

    private Actions actions;

    @BeforeTest
    @Description("Setup driver and other config params before test")
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", Configuration.PATH_CHROME_DRIVER);
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, SECONDS);
        driver.get(Configuration.LOCALHOST);
        actions = new Actions(driver);
    }

    @Test(priority = 0, description = "Add new account to database table test")
    @Severity(SeverityLevel.BLOCKER)
    public void addNewAccountTest(){

        WebElement addNewAccountButton = driver.findElement(By.xpath(xpath_add_new_account));
        addNewAccountButton.click();

        //find add form elements
        WebElement textFieldName = driver.findElement(By.xpath(xpath_field_first_name));
        WebElement textFieldLastName = driver.findElement(By.xpath(xpath_field_last_name));
        WebElement buttonSave = driver.findElement(By.xpath(xpath_save_button));

        //count current number of rows in table
        List<WebElement> tableAccounts = driver.findElements(By.xpath(xpath_account_table));
        List<WebElement> rowCollections = tableAccounts.get(0)
                .findElements(By.xpath(xpath_account_table_rows));

        //fill out form, then save to db
        textFieldName.sendKeys("New First Name");
        textFieldLastName.sendKeys("New Last Name");
        actions.moveToElement(buttonSave).click().build().perform();

        //wait some time for appearing of newly added data in new row
        System.out.println("asdfasfd" + rowCollections.size());
        WebElement lastRow = (new WebDriverWait(driver, 3))
                .until(ExpectedConditions
                        .presenceOfElementLocated(
                                By.xpath(xpath_account_table_rows + "["
                                        + (rowCollections.size() + 1) + "]")));
        List<WebElement> lastRowDatas = lastRow.findElements(By.xpath("td"));

        //check if added account present in table
        Assert.assertEquals("New First Name", lastRowDatas.get(1).getText());
        Assert.assertEquals("New Last Name", lastRowDatas.get(2).getText());
    }

    @Test(priority = 0, description = "Delete account from database table test")
    @Severity(SeverityLevel.BLOCKER)
    public void deleteAccountFromDbTest() throws InterruptedException {

        //count current number of rows in table, will delete the last one
        WebElement tableAccounts = driver.findElement(By.xpath(xpath_account_table));
        List<WebElement> rowCollections = tableAccounts.findElements(By.xpath(xpath_account_table_rows));
        String idToDelete = rowCollections.get(rowCollections.size()-1).findElement(By.xpath("td[1]")).getText();

        //locate delete button and press
        WebElement deleteButton = rowCollections.get(rowCollections.size()-1).findElement(By.xpath("td[7]"));
        deleteButton.click();
        Thread.sleep(2000);

        //check if deleted account removed from table
        List<WebElement> rowCollectionsAfterDeletion = tableAccounts
                .findElements(By.xpath(xpath_account_table_rows));
        String lastRowsId = rowCollectionsAfterDeletion.get(rowCollectionsAfterDeletion.size() - 1)
                .findElement(By.xpath("td[1]")).getText();
        System.out.println("idtodelete " + idToDelete + "   lastRowsid " + lastRowsId);
        Assert.assertNotEquals(idToDelete, lastRowsId);
    }

    @Test(priority = 0, description = "Make transfer of money from one account id to another id")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentTest() throws InterruptedException {

        //get id of last transaction first
        WebElement trxAccounts = driver.findElement(By.xpath(xpath_transactions_table));
        List<WebElement> rowCollections = trxAccounts.findElements(By.xpath(xpath_transactions_table_rows));
        String lastId = rowCollections.get(rowCollections.size() - 1).findElement(By.xpath("td[1]")).getText();

        //locate elements first
        WebElement from_id = driver.findElement(By.xpath(xpath_field_from_id));
        WebElement to_id = driver.findElement(By.xpath(xpath_field_to_id));
        WebElement amount = driver.findElement(By.xpath(xpath_field_amount));
        List<WebElement> buttonAddNewTransaction = driver.findElements(By.xpath(xpath_make_transfer_button));

        //fill form and click
        from_id.sendKeys("1");
        to_id.sendKeys("2");
        amount.sendKeys("1");
        buttonAddNewTransaction.get(1).click();
        Thread.sleep(2000);

        //assert
        WebElement tableAccountsAfter = driver.findElement(By.xpath(xpath_transactions_table));
        List<WebElement> rowCollectionsAfter =
                tableAccountsAfter.findElements(By.xpath(xpath_transactions_table_rows));
        String newId = rowCollectionsAfter.get(rowCollectionsAfter.size() - 1)
                .findElement(By.xpath("td[1]")).getText();
        String newFromId = rowCollectionsAfter.get(rowCollectionsAfter.size() - 1)
                .findElement(By.xpath("td[2]")).getText();
        String newToId = rowCollectionsAfter.get(rowCollectionsAfter.size() - 1)
                .findElement(By.xpath("td[3]")).getText();
        String newAmount = rowCollectionsAfter.get(rowCollectionsAfter.size() - 1)
                .findElement(By.xpath("td[4]")).getText();
        Assert.assertNotEquals(lastId, newId);
        Assert.assertEquals("1", newFromId);
        Assert.assertEquals("2", newToId);
        Assert.assertEquals("1", newAmount);
    }

    @Test(priority = 0, description = "Make transfer of money with insifficient funds from one account id to another id")
    @Severity(SeverityLevel.BLOCKER)
    public void paymentWithInsufficientFundsTest() throws InterruptedException {

        //get 2 accounts to send from and to send to from table in the end
        WebElement tableAccounts = driver.findElement(By.xpath(xpath_account_table));
        List<WebElement> rowAccountsCollections = tableAccounts.findElements(By.xpath(xpath_account_table_rows));
        String idFromAccount = rowAccountsCollections.get(rowAccountsCollections.size() - 1)
                .findElement(By.xpath("td[1]")).getText();
        Integer balanceFromAccount
                = Integer.valueOf(rowAccountsCollections.get(rowAccountsCollections.size() - 1)
                .findElement(By.xpath("td[4]")).getText());
        String idToAccount = rowAccountsCollections.get(rowAccountsCollections.size() - 2)
                .findElement(By.xpath("td[1]")).getText();


        WebElement transactionsTableBefore = driver.findElement(By.xpath(xpath_transactions_table));
        List<WebElement> rowTransactionsCollectionsBefore
                = transactionsTableBefore.findElements(By.xpath(xpath_transactions_table_rows));
        Integer transactionsCount = rowTransactionsCollectionsBefore.size();

        //locate elements first
        WebElement from_id = driver.findElement(By.xpath(xpath_field_from_id));
        WebElement to_id = driver.findElement(By.xpath(xpath_field_to_id));
        WebElement amount = driver.findElement(By.xpath(xpath_field_amount));
        List<WebElement> buttonAddNewTransaction = driver.findElements(By.xpath(xpath_make_transfer_button));

        //fill form, increase amount to send by +1 and click make transfer
        from_id.sendKeys(idFromAccount);
        to_id.sendKeys(idToAccount);
        amount.sendKeys((++balanceFromAccount).toString());
        buttonAddNewTransaction.get(1).click();

        //check if the number of transactions in table are the same as before
        WebElement tableTransactionsAfter = driver.findElement(By.xpath(xpath_transactions_table));
        List<WebElement> rowTransactionsAfter = tableTransactionsAfter
                .findElements(By.xpath(xpath_transactions_table_rows));
        Integer transactionsCountAfter = rowTransactionsAfter.size();
        Assert.assertEquals(transactionsCountAfter, transactionsCount);
    }

//    @Test(priority = 0, description = "Topup the balance of an account by amount of money test")
//    @Severity(SeverityLevel.BLOCKER)
//    public void topupBalanceTest(){
//
//    }
//
//    @Test(priority = 0, description = "Withdraw from balance of an account by amount of money test")
//    @Severity(SeverityLevel.BLOCKER)
//    public void withdrawBalanceTest(){
//
//    }

    @AfterTest(description = "Quit driver after test")
    public static void tearDown() {
        driver.quit();
    }
}
