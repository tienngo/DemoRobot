package customlib;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pom.RESTRequest;

public class CustomJavaLib {

	private WebDriver driver;
	
	public void initBrowser() {
		System.setProperty("webdriver.chrome.driver", "src/main/resources/driver/chromedriver.exe");
		ChromeOptions gc_options = new ChromeOptions();
        gc_options.addArguments("window-size=1200x600");
        driver = new ChromeDriver(gc_options);
	}
	
	public void accessToUrl(String url) {
		driver.get(url);
	}
	
	public void closeBrowserByJavaLib() {
		driver.close();
	}
	
	public Response postRegisterAPI(String jsonBody) {
		RESTRequest registerRequest = new RESTRequest("register.htm");
		Response response = registerRequest.post(Entity.entity("null", MediaType.APPLICATION_JSON));
		return response;
	}
}
