import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StoreHelper {

    public static void writeToTxt(String url,String page_title,String page_control) {
        try {
            String fileName = new SimpleDateFormat("yyyyMMddHHmm'_results.txt'").format(new Date());
            FileWriter writer = new FileWriter(fileName, true);
            writer.write("Link:"+(url));
            writer.write("\r\n");
            writer.write("Page Title:"+(page_title));
            writer.write("\r\n");
            writer.write("Status:"+(page_control));
            writer.write("\r\n");
            writer.write("------------------------------------------------------------");
            writer.write("\r\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
