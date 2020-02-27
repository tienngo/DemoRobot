package customlib;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import pom.RESTRequest;
import pom.apiobject.BankAccount;

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
	
	public Response loginAs(String userName, String password) {
		RESTRequest loginRequest = new RESTRequest("parabank/login.htm");
		Form form = new Form();
		form.param("username", userName);
		form.param("password", password);
		return loginRequest.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED));
	}
	
	public BankAccount getDetailOfBankAccount(Response sessionCookie, String bankAccount) {
		RESTRequest getAllAccountsRequest = new RESTRequest("parabank/services_proxy/bank/accounts/" + bankAccount);
		getAllAccountsRequest.configHeader("Cookie", sessionCookie.getHeaderString("Set-Cookie").toString());
		Response response = getAllAccountsRequest.get();
		BankAccount bankAccountInfo = response.readEntity(BankAccount.class);
		System.err.print("Bank Account Source:\n");
		System.err.print("- Id: " + bankAccountInfo.getId() + "\n");
		System.err.print("- customerId: " + bankAccountInfo.getCustomerId() + "\n");
		System.err.print("- balance: " + bankAccountInfo.getBalance() + "\n");
		System.err.print("- type: " + bankAccountInfo.getType() + "\n");
		return bankAccountInfo;
	}
	
	public BankAccount createNewBankAccount(Response sessionCookie, BankAccount bankAccountSource) {
		RESTRequest createNewBankAccountRequest = new RESTRequest("parabank/services_proxy/bank/createAccount?customerId=" + bankAccountSource.getCustomerId() + "&newAccountType=0&fromAccountId=" + bankAccountSource.getId());
		createNewBankAccountRequest.configHeader("Cookie", sessionCookie.getHeaderString("Set-Cookie").toString());
		Response response = createNewBankAccountRequest.post(Entity.entity("null", MediaType.APPLICATION_JSON));
		BankAccount bankAccountInfo = response.readEntity(BankAccount.class);
		System.err.print("Bank Account Target:\n");
		System.err.print("- Id: " + bankAccountInfo.getId() + "\n");
		System.err.print("- customerId: " + bankAccountInfo.getCustomerId() + "\n");
		System.err.print("- balance: " + bankAccountInfo.getBalance() + "\n");
		System.err.print("- type: " + bankAccountInfo.getType() + "\n");
		return bankAccountInfo;
	}
	
	public void transferFunds(Response sessionCookie, BankAccount bankAccountSource, BankAccount bankAccountTarget, Double amount) {
		RESTRequest transferFundsRequest = new RESTRequest("parabank/services_proxy/bank/transfer?fromAccountId=" + bankAccountSource.getId() + "&toAccountId=" + bankAccountTarget.getId() + "&amount=" + amount);
		transferFundsRequest.configHeader("Cookie", sessionCookie.getHeaderString("Set-Cookie").toString());
		Response response = transferFundsRequest.post(Entity.entity("null", MediaType.APPLICATION_JSON));
		System.err.print("Bank Account Source:\n");
		System.err.print("- Id: " + bankAccountSource.getId() + "\n");
		System.err.print("- customerId: " + bankAccountSource.getCustomerId() + "\n");
		System.err.print("- balance: " + bankAccountSource.getBalance() + "\n");
		System.err.print("- type: " + bankAccountSource.getType() + "\n");

		System.err.print("Bank Account Target:\n");
		System.err.print("- Id: " + bankAccountTarget.getId() + "\n");
		System.err.print("- customerId: " + bankAccountTarget.getCustomerId() + "\n");
		System.err.print("- balance: " + bankAccountTarget.getBalance() + "\n");
		System.err.print("- type: " + bankAccountTarget.getType() + "\n");
		
		System.err.print("Transfer Message: " + response.readEntity(String.class) + "\n");
	}
	
	
}
