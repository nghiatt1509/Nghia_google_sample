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
	
	static String EMAIL_ADDRESS1 = "krypton.portal@gmail.com";
	static String EMAIL_ADDRESS2 = "nghiatestng02@gmail.com";
	static String PASSWORDEMAILSEND = "Admin@123456789";
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
	
	@Test(priority = 1, description = "Should go to email inbox")
	public void GoogleLogin()
	{
		ffDriver.get(GOOGLE_URL);
		ffDriver.findElementById("Email").sendKeys(EMAIL_ADDRESS1);
		ffDriver.findElementById("next").click();
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
		ffDriver.findElementById("Passwd").sendKeys(PASSWORDEMAILSEND);
		
		if (ffDriver.findElementById("PersistentCookie").isSelected()) {
			ffDriver.findElementById("PersistentCookie").click();
		}
		
		ffDriver.findElementById("signIn").click();
		
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Inbox']")));
	}
	
	@Test(priority = 2, description = "Should send an email without any error")
	public void SendMail()
	{
		ComposeMail(SUBJECT1, EMAIL_ADDRESS2, BODY1);
		ffDriver.findElementByXPath("//a[@title='Sent Mail']").click();
	}
	
	public void ComposeMail(String subject, String mailTo, String content)
	{
		wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[@title='Inbox']")));
		ffDriver.findElementByXPath("//div[@class='z0']/div[(.)='COMPOSE']").click();
		
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='To']")));
		ffDriver.findElementByXPath("//*[@aria-label='To']").sendKeys(mailTo);
		ffDriver.findElementByXPath("//input[@name='subjectbox']").sendKeys(subject);
		//wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@aria-label='Message Body']")));
		ffDriver.findElementByXPath("//div[@id=':bg']").sendKeys(content);
		wdWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='J-J5-Ji']/div[(.)='Send']")));
		ffDriver.findElementByXPath("//div[@class='J-J5-Ji']/div[(.)='Send']").click();	
		
		ffDriver.findElementByXPath("//*[contains(@title,'Sent Mail')]").click();
		ffDriver.findElementByXPath("//*[(.)='" + subject + "']").click();
	}
	
	public void Login(String username, String password)
	{
		ffDriver.get(GOOGLE_URL);
		ffDriver.findElementById("Email").sendKeys(EMAIL_ADDRESS1);
		ffDriver.findElementById("next").click();
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
		ffDriver.findElementById("Passwd").sendKeys(PASSWORDEMAILSEND);
		
		if (ffDriver.findElementById("PersistentCookie").isSelected()) {
			ffDriver.findElementById("PersistentCookie").click();
		}
		
		ffDriver.findElementById("signIn").click();
		
		wdWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Inbox']")));	
	}
}
