package lesson05;

import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class MyFirstTest {

    static WebDriver driver;
    static WebElement SearchField;
    static List<WebElement> Tips;
    static String FirstTip;
    static String SearchText = "Dress";

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @BeforeClass
    public static void setUp() {
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(25, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(25, TimeUnit.SECONDS);

        driver.get("http://automationpractice.com/index.php");

        SearchField = driver.findElement(By.id("search_query_top"));
        SearchField.clear();
        SearchField.sendKeys(SearchText);
        Tips = driver.findElements(By.xpath("//*[@id=\"index\"]/div[2]/ul/li"));
        FirstTip = driver.findElement(By.xpath("//*[@id=\"index\"]/div[2]/ul/li[1]")).getText();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test(timeout = 5000l)
    public void verifyFirstTipIsCorrect() {
        Assert.assertThat(FirstTip, CoreMatchers.containsString(SearchText));
    }

    @Test(timeout = 5000l)
    public void verifyFirstTipIsCorrect_viaAssertTrue() {
        Assert.assertTrue("First tip text was wrong", FirstTip.contains("Dress1"));
    }

/*
1. Напишите тест, похожий на рассмотренный на занятии 5 (ввод посикового запроса и проверка первой подсказки),
только проверьте, что все подсказки выпадающего списка содержат вводимый текст.
1.1. Искать список всех подсказок через findElements;
1.2. Проверку всех подсказок сделать с помощью Stream API.
2. Запустить сьют через мавен и через IDE.
3. Закоммитить изменения, залить их на репозиторий GitHub и прислать ссылку.
*/

    @Test(timeout = 5000l)
    public void verifyFirstTipIsCorrect_viaStream() {
        Assert.assertThat(Tips.stream()
                        .map(s -> s.getText())
                        .findFirst()
                        .orElse(""),
                CoreMatchers.containsString(SearchText));
    }

    @Test(timeout = 5000l)
    public void verifyAllTipsAreCorrect_viaStream() {
        Tips.stream().forEach(s -> collector.checkThat(s.getText(), CoreMatchers.containsString(SearchText)));
    }
}