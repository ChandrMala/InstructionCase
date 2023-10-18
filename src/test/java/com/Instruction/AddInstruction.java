package com.Instruction;



import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class AddInstruction {

	WebDriver driver;
	int i;
	
	@BeforeSuite
	public void SetBrowserproperty() {

		System.out.println("Setting up the browser property");

		System.setProperty("webdriver.chrome.driver",
				"D:\\Selenium\\chromedriver-116\\chromedriver.exe");

		driver = new ChromeDriver();
	}

	@BeforeClass
	public void LaunchUrl() throws InterruptedException {
		driver.get("https://instructions.hasbro.com/worldwide");
		driver.manage().window().maximize();
		Thread.sleep(50);
		WebElement Country = driver.findElement(By.xpath("//a[contains(@href,'/pt-br')]"));
		Country.click();
		
	}

	@BeforeMethod
	public void MarketLaunch() throws InterruptedException, IOException, AWTException
	
	{	
		//int i;
		System.out.println("Cookie suppressed susccefully");
		String InputPutExcelfilepath = "C:\\Users\\Malathy\\Fita Eclipse\\InstructionCase\\src\\test\\resources\\AddInstruction.xlsx";
		XSSFWorkbook wb = new XSSFWorkbook(InputPutExcelfilepath); 
		XSSFSheet sh = wb.getSheet("Sheet1"); 
		int rowCount = sh.getPhysicalNumberOfRows(); 
		
		
		
		System.out.println(rowCount);//displays the row count
		
		for ( i = 1; i < rowCount; i++) {
			
			String Locale = sh.getRow(i).getCell(0).getStringCellValue();
			//driver.get("Locale");
			driver.navigate().to(Locale);
			System.out.println("Markets laucnhed");
			//driver.navigate();
			
			driver.manage().window().maximize();
			Thread.sleep(50);
			
			String SkuNo = sh.getRow(i).getCell(1).getStringCellValue();

			WebElement SearchFeild = driver.findElement(By.xpath("//input[@id=\"search\"]"));
			SearchFeild.sendKeys(SkuNo);
			
			Robot robot = new Robot();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyRelease(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			Thread.sleep(500);
			System.out.println("Sku suscessful");
		}
		
	}
		
	
	
	@Test 
	public void SkuSearch(String URL) throws AWTException, InterruptedException, IOException  {		
		
		String InputPutExcelfilepath = "C:\\Users\\Malathy\\Fita Eclipse\\InstructionCase\\src\\test\\resources\\AddInstruction.xlsx";
		XSSFWorkbook wb = new XSSFWorkbook(InputPutExcelfilepath); 
		XSSFSheet sh = wb.getSheet("Sheet1"); 
		int rowCount = sh.getPhysicalNumberOfRows(); 
		//int columCount = sh.getRow(0).getPhysicalNumberOfCells();//gives no of columns		
		
		//XSSFSheet sh = wb.getSheet("Sheet1"); 
					

			WebElement ViewDetailLink = driver.findElement(By.xpath("//a[contains(@class,'All-Instructions_ViewDetails hidden lg:block text-[14px] font-demibold text-[#005EB8] ')]"));
			ViewDetailLink.click();			
			
			String Title = sh.getRow(i).getCell(7).getStringCellValue();
			String TitleExpected = Title.trim();
			System.out.println("Expected Title:"+TitleExpected);
			
			//String title = driver.findElement(By.xpath("//title")).getAttribute("innerText");
			//String SkuNo = sh.getRow(i).getCell(1).getStringCellValue();
			
			String PageTitle = driver.getTitle();
			String TitleActual = PageTitle.trim();
			System.out.println("Actual Title:"+TitleActual);
			//if (TitleExpected==TitleActual)
				if (TitleExpected.equals(TitleActual))
			{
				
				System.out.println("The Title matches");
				
			}
			
			else 
			{
				
				System.out.println("The Title not matches");
			}
			
		}
					
	
	@AfterSuite
	public void CloseBrowser() {

		driver.quit();

	}

	}

