import com.codeborne.selenide.Configuration;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import java.io.File;
import java.io.IOException;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;

import static org.junit.jupiter.api.Assertions.*;

public class SelenideTest {
  private Logger logger = LoggerFactory.getLogger(SelenideTest.class);
  static {
    System.setProperty("logback.configurationFile", "src/logback.xml");
  }

  @BeforeAll
  public void setUp() {
    Configuration.browser = "chrome";
    Configuration.startMaximized = true;
  }

  @Test
  public void loginTest(){
    // Code to retrieve login credentials
    File jsonFile = new File("C:\\temp\\ltu.json.txt");
    String email = null;
    String password = null;

    try {
      ObjectMapper objectMapper = new ObjectMapper();
      JsonNode jsonNode = objectMapper.readTree(jsonFile);

      email = jsonNode.get("ltuCredentials").get("email").asText();
      password = jsonNode.get("ltuCredentials").get("password").asText();

    } catch (IOException e) {
      logger.error("Error reading credentials from file");
    }

    //test to log in to ltu.com with valid credentials and check that the login was successful, first accept cookies and then login
    open("https://www.ltu.se");
    try {
      $(byText("Tillåt urval")).click();
      logger.info("Cookies accepted");
    } catch (Exception e) {
      logger.error("Cookies not accepted");
    }

    // target and click the student option
    try {
      $(byText("Student")).click();
      logger.info("Student option found");
    } catch (Exception e) {
      logger.error("Student option not found");
    }

    // click the button with the text "Logga in"
    try {
      $(byText("Logga in")).click();
      $(By.id("username")).sendKeys(email);
      $(By.id("password")).sendKeys(password);
      // target a btn by name=submit and click it to login
      $(By.name("submit")).click();
    } catch (Exception e) {
      logger.error("Login failed");
    }

  sleep(3000);
    // check that the login was successful
    try {
      $(byText("Kursrum")).isDisplayed();
      assertTrue($(byText("Kursrum")).isDisplayed());
      logger.info("Login successful");
    } catch (Exception e) {
      logger.error("Login failed");
    }

    //click on the link that contains "intyg" text
    try {
      $(byText("Intyg »")).click();
      logger.info("Intyg link found");
    } catch (Exception e) {
      logger.error("Intyg link not found");
    }
    sleep(3000);
  }
}