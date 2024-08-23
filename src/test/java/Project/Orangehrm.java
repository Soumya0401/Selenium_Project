package Project;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;



public class Orangehrm 
{
	public String baseURL = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
	public WebDriver driver;

@BeforeTest
public void set_browser()
{
	 System.out.println("Before Test Execution");
	 driver=new ChromeDriver();
	 driver.manage().window().maximize();
	 driver.get(baseURL);
	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(60));
}

@Test(priority=1 ,enabled=true)
public void doLoginwithInvalidCrdentilas() throws InterruptedException
{
	driver.findElement(By.name("username")).sendKeys("Admin");
    driver.findElement(By.name("password")).sendKeys("apple@123");
    driver.findElement(By.xpath("//button[@type='submit']")).submit();

    String message_expected = "Invalid credentials";
    
    // Wait for the message to appear
       Thread.sleep(5000);
    
    String message_actual = driver.findElement(By.xpath("//p[@class='oxd-text oxd-text--p oxd-alert-content-text']")).getText();
    Assert.assertTrue(message_actual.contains(message_expected));
}

@Test(priority=2, enabled=false)
public void LoginCredentials() 
{
	driver.findElement(By.name("username")).sendKeys("Admin");
	driver.findElement(By.name("password")).sendKeys("admin123");
	driver.findElement(By.xpath("//button[@type='submit']")).submit();
	
	String pagetitle= driver.getTitle();
//	if(pagetitle.equals("OrangeHRM"))
//	{
//		System.out.println("Login Successful");
//		
//	}
//	else
//	{
//		System.out.println("Login Failed");
//	}	
	
	Logout();
	Assert.assertEquals("OrangeHRM",pagetitle);
}

@Test(priority=3,enabled=true)
public void addEmployee()
{
	Login();
	driver.findElement(By.xpath("//span[text()='PIM']")).click();
	
	driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
	
	driver.findElement(By.name("firstName")).sendKeys("Abhay");
	
	driver.findElement(By.name("lastName")).sendKeys("Singh");
	
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	
	String confirmationMessage = driver.findElement(By.xpath("//h6[text()='Personal Details']")).getText();
	
	if(confirmationMessage.contains("Personal Details"))
	{
		System.out.println("Employee Added Successfully! ");
	}
	else
	{
		System.out.println("Failed to Add Employee! ");
	}
	
	Logout();
	Assert.assertEquals("Personal Details", confirmationMessage);
}

@Test(priority=4, enabled=false)
public void searchEmployeeByName()
{
	Login();
	
	driver.findElement(By.xpath("//span[text()='PIM']")).click();
	
	driver.findElement(By.xpath("//a[text()='Employee List']")).click();
	
	driver.findElement(By.xpath("(//input[@placeholder='Type for hints...'])[1]")).sendKeys("Soumya Abhay");
	
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	
	List<WebElement>elementlist = driver.findElements(By.xpath("//span[@class='oxd-text oxd-text--span']"));
	
//	for(int i=0 ;i<elementlist.size();i++)
//	{
//		System.out.println("At Index"+ i + "text is :" +elementlist.get(i).getText());
//	}
	
	String expected_message ="Record Found";
	String actual_message = elementlist.get(0).getText();
	System.out.println(actual_message);
	
	
	Logout();
    //Assert.assertTrue(actual_message.contains(expected_message));	
}

@Test(priority=5 ,enabled=false)
public void searchEmployeeById()
{
	
    String empid = "0410";
	Login();
	
	driver.findElement(By.xpath("//span[text()='PIM']")).click();
	
	driver.findElement(By.xpath("//a[text()='Employee List']")).click();

	driver.findElement(By.xpath("(//input[@class='oxd-input oxd-input--active'])[2]")).sendKeys("empid");
	
	driver.findElement(By.xpath("//button[@type='submit']")).click();
	
	Logout();

}
@Test(priority=6)
public void addEmployeeImages() throws AWTException {
    Login();
    
     driver.findElement(By.xpath("//span[text()='PIM']")).click();
	
	driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
	
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // Click the button to add employee image
    WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='oxd-icon-button oxd-icon-button--solid-main employee-image-action']")));
    uploadButton.click();

    // Copy file path to clipboard
    StringSelection str = new StringSelection("C:\\Users\\rajso\\Desktop\\download.jpg");
    Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

    // Create Robot instance for keyboard operations
    Robot rb = new Robot();

    // Simulate Ctrl+V to paste the file path
    rb.keyPress(KeyEvent.VK_CONTROL);
    rb.keyPress(KeyEvent.VK_V);
    rb.keyRelease(KeyEvent.VK_V);
    rb.keyRelease(KeyEvent.VK_CONTROL);

    // Simulate pressing Enter
    rb.keyPress(KeyEvent.VK_ENTER);
    rb.keyRelease(KeyEvent.VK_ENTER);
    
    driver.findElement(By.xpath("//button[@type='submit']")).click();
    
    Logout();
}

@Test(priority=7, enabled=true)
public void deleteEmployee()
{
	Login();
	
	 driver.findElement(By.xpath("//span[text()='PIM']")).click();
		
	 driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		
	 driver.findElement(By.xpath("(//input[@placeholder='Type for hints...'])[1]")).sendKeys("Soumya Abhay");
		
	 driver.findElement(By.xpath("//button[@type='submit']")).click();
	 
	 driver.findElement(By.xpath("//i[@class='oxd-icon bi-trash']")).click();
	 
	 driver.findElement(By.xpath("//button[normalize-space()='Yes, Delete']")).click();
	 
	 String message = driver.findElement(By.xpath("//span[normalize-space()='(1) Record Found']")).getText();
	 
	 Assert.assertEquals(message , "No record Found");
	 
	 Logout();
	
}

public void Login()
{
	driver.findElement(By.name("username")).sendKeys("Admin");
	driver.findElement(By.name("password")).sendKeys("admin123");
	driver.findElement(By.xpath("//button[@type='submit']")).submit(); 
}

public void Logout() 
{
	driver.findElement(By.xpath("//p[@class='oxd-userdropdown-name']")).click();
	//driver.findElement(By.xpath("//a[normalize-space()='Logout']")).click();
	List<WebElement> elementlist= driver.findElements(By.xpath("//a[@class='oxd-userdropdown-link']"));
//	for(int i=0;i<elementlist.size();i++)
//	{
//		Thread.sleep(3000);
//		System.out.println(i + ":" + elementlist.get(i).getText());
//		
//	}
	elementlist.get(3).click();
}
@AfterTest
public void teardown() throws InterruptedException
{
	
	Thread.sleep(5000);
	driver.close();
	driver.quit();	
}
}
	
	   
	
	
	


