package firefox;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import junit.framework.Assert;

public class FirefoxTestDesktop {
	static FirefoxDriver ffDriver = null;
	static WebDriverWait wdWait = null;
	
	final static String GOOGLE_URL = "https://mail.google.com";
	final static String GOOGLE_URL_HOMEPAGE = "https://mail.google.com/mail/u/0/#inbox";
	final static String TRASH_URL = "https://mail.google.com/mail/u/0/#trash";
	final static String SENT_URL = "https://mail.google.com/mail/u/0/#sent";
	
	static String EMAIL_ADDRESS1 = "nghiatestng01@gmail.com";
	static String EMAIL_ADDRESS2 = "nghiatestng02@gmail.com";
	static String PASSWORDEMAILSEND = "Nghi@T3st";
	static String PASSWORDEMAILTO = "Nghi@T3st";
	final static String SUBJECT1 = "It is Nghia Test Subject";
	final static String BODY1 = "It is Nghia Test Body";
	final static String MSG_NOEMAIL = "No conversations in the Trash. Who needs to delete when you have so much storage?!";
	final static String MSG_NOEMAIL_PRIMARY = "You have no mail.\nPlease enjoy your day!";
	
	@Parameters({ "emailSend", "passwordEmailSend", "emailTo", "passwordEmailTo" })
	@BeforeTest
	public void SetupEnvironment()
	{
		ffDriver = new FirefoxDriver();
		ffDriver.manage().deleteAllCookies();
		ffDriver.navigate().refresh();
		ffDriver.manage().window().maximize();
		
		wdWait = new WebDriverWait(ffDriver, 5);
	}
	
	@AfterTest
	public void PosCondition()
	{
		ffDriver.quit();
	}
	
	@Test(priority = 1, description = "should not accept empty email or invalid email")
	public void GoogleLogin_Invalid()
	{
		ffDriver.get(GOOGLE_URL);
		ffDriver.findElementById("Email").sendKeys(EMAIL_ADDRESS1);
		ffDriver.findElementById("next").click();
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
		ffDriver.findElementById("Passwd").sendKeys("Invalid");
		ffDriver.findElementById("signIn").click();
		
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errormsg_0_Passwd")));
		String errorMsg = ffDriver.findElementById("errormsg_0_Passwd").getText();
		Assert.assertEquals("The email and password you entered don't match.", errorMsg);
	}
	
}
