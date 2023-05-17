import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class GetTranscriptTest {
  private Logger logger = LoggerFactory.getLogger(GetTranscriptTest.class);

  static {
    System.setProperty("logback.xml.configurationFile", "src/logback.xml");
  }

  @BeforeAll
  public static void setUp() {
    Configuration.browser = "chrome";
    Configuration.startMaximized = true;
  }

  @Test
  public void downloadTranscriptTest(){
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
      Configuration.downloadsFolder = "C:\\Users\\ÄGARE\\IdeaProjects\\SelenideTesting\\SelenideTesting\\target\\downloads";
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
        $(byId("username")).sendKeys(email);
        $(byId("password")).sendKeys(password);
        // target a btn by name=submit and click it to login
        $(byName("submit")).click();
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
      try {
        switchTo().window(1);
      } catch (Exception e) {
        logger.error("Switching to new window failed");
      }
      try {
        var button = $(byXpath("//button[@class='btn btn-secondary ms-2']"));
        button.click();
      } catch (Exception e) {
        logger.error("Använd bara nödvändiga button not found");
      }

      try {
        // get this element with xpath: //a[@aria-label='Inloggning via ditt lärosäte / Login via your university']
        var element = $(byXpath("//a[@aria-label='Inloggning via ditt lärosäte / Login via your university']"));
        element.click();
        logger.info("Logga in link found");
      } catch (Exception e) {
        logger.error("Logga in link not found");
      }
      sleep(2000);

      // activate this search field and type in "Luleå tekniska universitet" //*[@id='searchinput']
      try {
        var searchField = $(byXpath("//*[@id='searchinput']"));
        searchField.click();
        searchField.sendKeys("Luleå tekniska universitet");
        logger.info("Search field found");
      } catch (Exception e) {
        logger.error("Search field not found");
      }
      //click the link //li[@aria-label='Select Lulea University of Technology']
      try {
        var link = $(byXpath("//li[@aria-label='Select Lulea University of Technology']"));
        link.click();
        logger.info("Link found");
      } catch (Exception e) {
        logger.error("Link not found");
      }
      sleep(3000);

      //click the menu btn //button[contains(@aria-label, 'Menu')]
      try {
        var menuBtn = $(byXpath("//button[contains(@aria-label, 'Meny')]"));
        menuBtn.click();
        logger.info("Menu button found");
      } catch (Exception e) {
        logger.error("Menu button not found");
      }
      sleep(1000);

      //click this link //a[@href='/student/app/studentwebb/intyg']
      try {
        var link = $(byXpath("//a[@href='/student/app/studentwebb/intyg']"));
        link.click();
        logger.info("Link found");
      } catch (Exception e) {
        logger.error("Link not found");
      }
      sleep(2000);

      //click the create btn //button[@title='Create']
      try {
        var createBtn = $(byXpath("//button[@title='Skapa intyg']"));
        createBtn.click();
        logger.info("Create button found");
      } catch (Exception e) {
        logger.error("Create button not found");
      }
      sleep(2000);

      try {
        // Find the dropdown element
        SelenideElement dropdown = $("#intygstyp");
        // Select the option with value '1: Object'
        dropdown.selectOptionByValue("1: Object");
      } catch (Exception e) {
        logger.error("Switching to new window failed");
      }

      try {
        //click the "Skapa" btn
        var button = $(byCssSelector("ladok-root button[class$='me-lg-3']"));
        button.click();
        logger.info("Button found");
      } catch (Exception e) {
        logger.error("Button not found");
      }
      sleep(2000);

      //click the link for the transcript
      try {
        var link = $(byXpath("//html/body/ladok-root/div/main/div/ladok-intyg/ladok-listning-av-skapade-intyg/div/div/ladok-accordion/div/ladok-list-kort[1]/div/div[1]/div/div[1]/a"));
        link.click();
        logger.info("Link found");
      } catch (Exception e) {
        logger.error("Link not found");
      }
      sleep(8000);
    }
}