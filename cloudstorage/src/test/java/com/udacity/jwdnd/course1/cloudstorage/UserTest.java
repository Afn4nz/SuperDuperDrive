package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
        @LocalServerPort
        private int port;
        private WebDriver driver;
        private String firstName = "Afnan";
        private String lastName = "Alzuair";
        private String userName = "Afn4nz";
        private String password = "1234";

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
        public void testUnauthorizedAccess() {
            driver.get("http://localhost:" + this.port + "/login");
            Assertions.assertEquals("Login", driver.getTitle());

            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());

            driver.get("http://localhost:" + this.port + "/home");
            Assertions.assertEquals("Login", driver.getTitle());
        }

        @Test
        public void testSignUpLoginLogout() {
            WebDriverWait wait = new WebDriverWait(driver, 20);

            /* Signup*/
            driver.get("http://localhost:" + this.port + "/signup");
            Assertions.assertEquals("Sign Up", driver.getTitle());

            WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
            inputFirstName.sendKeys(firstName);
            WebElement inputLastName = driver.findElement(By.id("inputLastName"));
            inputLastName.sendKeys(lastName);
            WebElement inputUsername = driver.findElement(By.id("inputUsername"));
            inputUsername.sendKeys(userName);
            WebElement inputPassword = driver.findElement(By.id("inputPassword"));
            inputPassword.sendKeys(password);
            WebElement signUpButton = driver.findElement(By.id("buttonSignUp"));
            signUpButton.click();

            /*Login*/
            driver.get("http://localhost:" + this.port + "/login");
            Assertions.assertEquals("Login", driver.getTitle());

            inputUsername = driver.findElement(By.id("inputUsername"));
            inputUsername.sendKeys(userName);
            inputPassword = driver.findElement(By.id("inputPassword"));
            inputPassword.sendKeys(password);
            WebElement loginButton = driver.findElement(By.id("login-button"));
            loginButton.click();

            Assertions.assertEquals("Home", driver.getTitle());

            /*Logout*/
            WebElement logoutButton = driver.findElement(By.id("logout-button"));
            logoutButton.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")));
            Assertions.assertEquals("Login", driver.getTitle());

            driver.get("http://localhost:" + this.port + "/home");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            Assertions.assertEquals("Login", driver.getTitle());
        }
    }

