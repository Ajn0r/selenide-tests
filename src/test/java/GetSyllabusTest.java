import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ex.ElementNotFound;

import org.junit.jupiter.api.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.byXpath;
import static com.codeborne.selenide.Selenide.*;

public class GetSyllabusTest {
  private Logger logger = LoggerFactory.getLogger(GetSyllabusTest.class);
  static {
    System.setProperty("logback.configurationFile", "src/logback.xml");
  }

  @BeforeAll
  public static void setUp() {
    Configuration.browser = "chrome";
    Configuration.startMaximized = true;

  }

  @Test
  public void downloadSyllabusTest() {
    Configuration.downloadsFolder = "C:\\Users\\ÄGARE\\IdeaProjects\\SelenideTesting\\SelenideTesting\\target\\downloads";
    //open ltu.se
    try{
      open("https://ltu.se");
      logger.info("ltu.se opened");
    } catch (Exception e) {
      logger.error("Error opening ltu.se");
    }

    //accept cookies
    try {
      var element = $(byText("Tillåt urval"));
      element.click();
      logger.info("Cookies accepted");
    } catch (ElementNotFound e) {
      logger.error("Error accepting cookies");
    }

    //click on search button
    try{
      var element = $(byXpath("//button[@class='button is-medium ltu-search-btn']"));
      element.click();
      logger.info("Search button clicked");
    } catch (ElementNotFound e) {
      logger.error("Error clicking search button");
    }

    sleep(3000);

    try{
      var element = $(byXpath("//input[@id='cludo-search-bar-input']"));
      element.sendKeys("I0015N");
      element.pressEnter();
      logger.info("Search for course I0015N");
    } catch (ElementNotFound e) {
      logger.error("Error searching for course I0015N");
    }
    sleep(3000);

    //click on the syllabus link
    try{
      var element = $(byXpath("//a[contains(@href, 'https://www.ltu.se/edu/course/I00/I0015N/I0015N-Test-av-IT-system-1.81215?kursView=kursplan')]"));
      element.click();
      logger.info("Syllabus link clicked");
    } catch (ElementNotFound e) {
      logger.error("Error clicking syllabus link");
    }
    sleep(4000);

    //click on the pdf file link and download the pdf file called "Kursplan antagna: Våren 2023, Läsperiod 4, Kurstillfälle 47000, 47455"
    try{
      var element = $(byXpath("//a[contains(@href, 'pdf')]"));
      element.click();
      logger.info("Downloaded pdf file");
    } catch (ElementNotFound e) {
      logger.error("Error downloading pdf file");
    }

  }
}
