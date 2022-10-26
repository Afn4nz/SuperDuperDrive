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
public class NoteTest {
    private String userName = "Afn4nz";
    private String password = "1234";
    private String noteTitle = "JAVA Web Developer Nanodegree";
    private String noteDescription = "Complete the first project before 13/10/2022";
    private String newNoteDescription = "The project is completed!";
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
    public void addNoteTest() {
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        login();

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        wait.withTimeout(Duration.ofSeconds(20));
        WebElement newNoteButton = driver.findElement(By.id("new-note-button"));
        wait.until(ExpectedConditions.elementToBeClickable(newNoteButton)).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("note-title"))).sendKeys(noteTitle);
        WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
        noteDescriptionInput.sendKeys(noteDescription);
        WebElement saveChanges = driver.findElement(By.id("save-changes"));
        saveChanges.click();
        Assertions.assertEquals("Result", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/home");
        notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        Boolean added = false;
        for (int i=0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            if (element.getAttribute("innerHTML").equals(noteDescription)) {
                added = true;
                break;
            }
        }

        Assertions.assertTrue(added);
    }

    @Test
    public void editNoteTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        login();

        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement editElement = null;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            editElement = element.findElement(By.id("edit-button"));
            if (editElement != null){
                break;
            }
        }

        wait.until(ExpectedConditions.elementToBeClickable(editElement)).click();
        WebElement noteDescriptionInput = driver.findElement(By.id("note-description"));
        wait.until(ExpectedConditions.elementToBeClickable(noteDescriptionInput));
        String noteOldDescription = noteDescriptionInput.getAttribute("innerHTML");
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(newNoteDescription);
        WebElement saveChanges = driver.findElement(By.id("save-changes"));
        saveChanges.click();
        Assertions.assertEquals("Result", driver.getTitle());

        Assertions.assertNotEquals(noteOldDescription, newNoteDescription);
    }

    @Test
    public void deleteNoteTest(){
        WebDriverWait wait = new WebDriverWait (driver, 20);
        JavascriptExecutor jse =(JavascriptExecutor) driver;

        login();

        driver.get("http://localhost:" + this.port + "/home");
        WebElement notesTab = driver.findElement(By.id("nav-notes-tab"));
        jse.executeScript("arguments[0].click()", notesTab);
        WebElement notesTable = driver.findElement(By.id("userTable"));
        List<WebElement> notesList = notesTable.findElements(By.tagName("td"));
        WebElement deleteElement = null;
        for (int i = 0; i < notesList.size(); i++) {
            WebElement element = notesList.get(i);
            deleteElement = element.findElement(By.id("delete"));
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
