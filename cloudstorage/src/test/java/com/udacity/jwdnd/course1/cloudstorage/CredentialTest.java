package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.time.Duration;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTest {
    private String userName = "Afn4nz";
    private String password = "1234";
    private String credentialURL = "https://www.udacity.com/";
    String newUsername = "Afnan";

    private WebDriver driver;
    @LocalServerPort
    private int port;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addCredentialTest() {
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        login();

        WebElement credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsTab);
        wait.withTimeout(Duration.ofSeconds(20));
        WebElement newCredentialButton = driver.findElement(By.id("new-credential-button"));
        wait.until(ExpectedConditions.elementToBeClickable(newCredentialButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("credential-url"))).sendKeys(credentialURL);
        WebElement credentialsUsernameInput = driver.findElement(By.id("credential-username"));
        credentialsUsernameInput.sendKeys(userName);
        WebElement credPassword = driver.findElement(By.id("credential-password"));
        credPassword.sendKeys(password);
        WebElement submit = driver.findElement(By.id("save-credential-button"));
        submit.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        credentialsTab = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsTab);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement> credentialsList = credentialsTable.findElements(By.tagName("td"));
        Boolean created = false;
        for (int i=0; i < credentialsList.size(); i++) {
            WebElement element = credentialsList.get(i);
            if (element.getAttribute("innerHTML").equals(userName)) {
                created = true;
                break;
            }
        }

        Assertions.assertTrue(created);
    }

    @Test
    public void editCredentialTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        login();

        WebElement credentialsUsernameInput = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsUsernameInput);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List<WebElement>credentialsList = credentialsTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (int i = 0; i < credentialsList.size(); i++) {
            WebElement element = credentialsList.get(i);
            editElement = element.findElement(By.id("edit-credential-button"));
            if (editElement != null){
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        credentialsUsernameInput = driver.findElement(By.id("credential-username"));
        wait.until(ExpectedConditions.elementToBeClickable(credentialsUsernameInput));
        credentialsUsernameInput.clear();
        credentialsUsernameInput.sendKeys(newUsername);
        WebElement saveChanges = driver.findElement(By.id("save-credential-button"));
        saveChanges.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertNotEquals(userName, newUsername);
    }

    @Test
    public void deleteCredentialTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        login();

        WebElement credentialsUsernameInput = driver.findElement(By.id("nav-credentials-tab"));
        jse.executeScript("arguments[0].click()", credentialsUsernameInput);
        WebElement credentialsTable = driver.findElement(By.id("credentialTable"));
        List <WebElement> credentialsList = credentialsTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (int i = 0; i < credentialsList.size(); i++) {
            WebElement element = credentialsList.get(i);
            deleteElement = element.findElement(By.id("delete-credential"));
            if (deleteElement != null){
                break;
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(deleteElement)).click();
        Assertions.assertEquals("Result", driver.getTitle());
    }

    private void login(){
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.sendKeys(userName);
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.sendKeys(password);
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        Assertions.assertEquals("Home", driver.getTitle());
    }
}
