import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class main {
    public static WebElement findElement(By by) {
        return driver.findElement(by);
    }
    public static void ClickElement(By by) {
        findElement(by).click();
    }
    public static void sendKeys(By by , String text) {
        findElement(by).sendKeys(text);
    }
    protected static String baseUrl = "https://www.amazon.com.tr";
    public static WebDriver driver;
    public static FluentWait fuluentWait;
    private static int deger;
    private static int subdeger;

    public static void main(String[] args) throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "web_driver/chromedriver.exe");
        driver = new ChromeDriver();
        fuluentWait = new FluentWait(driver);
        fuluentWait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500));
        driver.get(baseUrl);
        driver.manage().window().maximize();
       clickShopCategoryandCountTheLinks();
      startTheProcess();
       uploadToDropBox();
    }
    public static void clickShopCategoryandCountTheLinks() throws InterruptedException{

        driver.findElement(By.xpath("//a[@id='nav-hamburger-menu']")).click();
        System.out.println("Shop By Category butonuna tıklandı.");
        Thread.sleep(5000);
        List<WebElement> toplamKategori = driver.findElements(By.xpath("//ul[contains(@class,'hmenu-visible')]//li"));
        deger = toplamKategori.size();
        System.out.println("Toplam Kategori alanında bulunan değerlerin sayısı:"+toplamKategori.size());
    }
    public static void uploadToDropBox() throws InterruptedException {
        String filePath = "C:/Users/testinium/Desktop/AmazonTracker/*.txt";
        String url = "https://www.dropbox.com/";
        String selectLinkOpeninNewTab = Keys.chord(Keys.CONTROL,"t");
        driver.findElement(By.linkText("url")).sendKeys(selectLinkOpeninNewTab);
        driver.get(url);
        ClickElement(By.xpath("//button[@id='sign-up-in']"));
        sendKeys(By.xpath("//input[@id='login_email05956824467883037']"),"osman.sirin@openpayd.com");
        sendKeys(By.xpath("//input[@id='login_password6540566217333224']"),"Password1!");
        ClickElement(By.xpath("//div[@class='signin-text']"));
        Thread.sleep(10000);
        sendKeys(By.xpath("//div[@class='ue-effect-container uee-AppActionsView-SecondaryActionMenu-text-upload-file']"),filePath);
    }
    public static void startTheProcess() throws InterruptedException {

        int numberOfLinksOnThePage = deger;
        for(int page=2;page<=numberOfLinksOnThePage;page++) {
            System.out.println("Toplam Sayfa Numarası :"+numberOfLinksOnThePage+ ", İşlem Yapılan Sayfa Numarası :" +page+" ");
            WebElement toplamdeger =  driver.findElement(By.xpath("//ul[contains(@class,'hmenu-visible')]//li["+page+"]"));
            toplamdeger.click();
            Thread.sleep(2000);
            List<WebElement> subLink = driver.findElements(By.xpath("(//*[@class='hmenu hmenu-visible hmenu-translateX']/li//*[@class='hmenu-item'])"));
            subdeger = subLink.size();
            System.out.println("Toplam Bulunan Alt Başlık Değeri...:"+subdeger);
            for (int i = 1; i <=subdeger; i++) {
                driver.findElement(By.xpath("//ul[contains(@class,'hmenu-visible')]//li["+page+"]")).click();
                Thread.sleep(2000);
                driver.findElement(By.xpath("(//*[@class='hmenu hmenu-visible hmenu-translateX']/li//*[@class='hmenu-item'])["+i+"]")).click();
                Thread.sleep(2000);
                String url = driver.getCurrentUrl();
                String page_title = driver.getTitle();
                if (page_title.contentEquals("Amazon.com Page Not Found"))
                {
                    String page_control = "Page is not working.";
                    StoreHelper.writeToTxt(url,page_title,page_control);
                }
                else
                {
                    String page_control = "Page is Working. OK!.";
                    StoreHelper.writeToTxt(url,page_title,page_control);
                }
                driver.navigate().back();
                System.out.println(page+". Sekmeden "+i+". alt sayfasının verileri alındı");
                driver.findElement(By.xpath("//a[@id='nav-hamburger-menu']")).click();
                Thread.sleep(2000);
            }
        }
    }
}