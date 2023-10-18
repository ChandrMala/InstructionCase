package com.Instruction;


	

	import java.awt.AWTException;
	import java.awt.Robot;
	import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
	import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	import org.openqa.selenium.By;
	import org.openqa.selenium.JavascriptExecutor;
	import org.openqa.selenium.WebDriver;
	import org.openqa.selenium.WebElement;
	import org.openqa.selenium.chrome.ChromeDriver;
	import org.testng.annotations.BeforeClass;
	import org.testng.annotations.BeforeMethod;
	import org.testng.annotations.BeforeSuite;
	import org.testng.annotations.Test;
	import org.testng.asserts.SoftAssert;

	public class AddInstF6646 {

		WebDriver driver;
		int i;
		int rowCount;
		XSSFWorkbook wb;
		XSSFSheet sh;
		CellStyle Passstyle;
		CellStyle Failstyle;
		String InputPutExcelfilepath;

		@BeforeSuite
		public void beforeSuite() {
			System.setProperty("webdriver.chrome.driver","D:\\Selenium\\chromedriver-116\\chromedriver.exe");
			driver = new ChromeDriver();
		}

		@BeforeClass
		public void beforeClass() throws InterruptedException {
			driver.get("https://instructions.hasbro.com/worldwide");
			driver.manage().window().maximize();
			Thread.sleep(50);
		}

		@BeforeMethod()
		public void TestDatasetup() throws IOException {
			InputPutExcelfilepath = "C:\\Users\\Malathy\\Fita Eclipse\\InstructionCase\\src\\test\\resources\\AddInstruction.xlsx";
			
			wb = new XSSFWorkbook(new FileInputStream(InputPutExcelfilepath));
			sh = wb.getSheet("Sheet1");
			rowCount = sh.getPhysicalNumberOfRows();
			System.out.println("no of rows: "+rowCount);//displays the row count
			
		
		}

		@Test(priority = 0)
		public void MarketLaunchAndValidation() throws AWTException, IOException {
			SoftAssert softAssert = new SoftAssert();
			int testCaseCounter = 0;
			String locale = "";
			//cell coloring 
			XSSFCellStyle Passstyle = wb.createCellStyle();  
			Passstyle.setFillBackgroundColor(IndexedColors.GREEN.getIndex());  
			Passstyle.setFillPattern(FillPatternType.LEAST_DOTS);  
	        
	        
			XSSFCellStyle Failstyle = wb.createCellStyle();  
			Failstyle.setFillBackgroundColor(IndexedColors.RED.getIndex());  
			Failstyle.setFillPattern(FillPatternType
					.LEAST_DOTS); 
			for (i = 1; i < rowCount; i++) {
				testCaseCounter++;

				try {
					// navigate to the market
					locale = sh.getRow(i).getCell(0).getStringCellValue();
					driver.navigate().to(locale);
					System.out.println(locale);

					// Enter the sku
					WebElement skuSearchBox = driver.findElement(By.xpath("//input[@type='search']"));
					String SkuNo = sh.getRow(i).getCell(1).getStringCellValue();
					skuSearchBox.sendKeys(SkuNo);
					Robot robot = new Robot();
					robot.keyPress(KeyEvent.VK_ENTER);
					robot.keyRelease(KeyEvent.VK_ENTER);

					// verify the brand
					WebElement brand = driver.findElement(By.xpath(
							"//div[@class='grid lg:flex w-full']/div/h4[contains(@class,'text-[14px] mb-[0.5rem] md:mb-[0px] md:text-[18px]')]"));
					String BranActVal = brand.getText();
					String brandval = sh.getRow(i).getCell(3).getStringCellValue();
					String brandvalue = brandval + " (" + SkuNo + ")";
					System.out.println("Expected Brand Value: " + brandvalue);
					System.out.println("Actual Brand Value: " + BranActVal);
					
					if (BranActVal.equals(brandvalue))
					//softAssert.assertEquals(brandvalue, brand.getText());
					{
					System.out.println("The Brand Value matches");
					Row row = sh.getRow(i);
					Cell cell = row.createCell(3);
					cell.setCellValue("pass");
					cell.setCellStyle(Passstyle);					
					
					}
					else
					{
					System.out.println("The Brand Value Mismatches");
					Row row = sh.getRow(i);
					Cell cell = row.createCell(3);									
					cell.setCellStyle(Failstyle);
					}

					// verify the Product Title 
					WebElement ProductTitle = driver.findElement(By.xpath(
							"//h4[@class='hidden md:block md:text-[22px] 2xl:text-[24px] mb-[1rem] md:mb-[0px] font-bold text-[#000000]']"));
					String ActualProductTitle = ProductTitle.getText().trim();
					String ProductTitleExcp = sh.getRow(i).getCell(5).getStringCellValue();
					String ExpectedProductTitle = ProductTitleExcp.trim();
					System.out.println("Expected ProductTitle : " + ProductTitleExcp);
					System.out.println("Actual ProductTitle : " + ActualProductTitle);
					//softAssert.assertEquals(systemnameval, systemname.getText());
					if (ActualProductTitle.equals(ExpectedProductTitle))
					
						{
						System.out.println("The Product Title matches");
						Row row = sh.getRow(i);
						Cell cell = row.createCell(5);
						cell.setCellValue("pass");
						cell.setCellStyle(Passstyle);					
						
						}
						else
						{
						System.out.println("The Product Title Mismatches");
						Row row = sh.getRow(i);
						Cell cell = row.createCell(5);	
						cell.setCellValue("Fail");
						cell.setCellStyle(Failstyle);
						}
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("window.scrollBy(0, 1000);");

					WebElement ViewDetailLink = driver.findElement(By.xpath(
							"//a[contains(@class,'All-Instructions_ViewDetails hidden lg:block text-[14px] font-demibold text-[#005EB8] ')]"));
					ViewDetailLink.click();

					// Verify the description
					WebElement desc = driver.findElement(By.xpath(
							"//div[@class=' mt-4 text-[16px] md:text-[18px] text-[#000000] leading-[27px] break-words']"));
					String description = desc.getText().replaceAll("[\t\n]", ""); // Remove leading/trailing whitespace
					String descriptionvalue = sh.getRow(i).getCell(9).getStringCellValue(); // Remove
					String Inputdescriptionvalue = 	descriptionvalue.replaceAll("[\t\n]", "");																		// leading/trailing
																									// whitespace
					System.out.println("Expected Description: " + Inputdescriptionvalue);
					System.out.println("Actual Description: " + description);
					//softAssert.assertEquals(descriptionvalue, description);
					//softAssert.assertAll();
					if (description.equals(Inputdescriptionvalue))
						
					{
					System.out.println("The Product Description matches");
					Row row = sh.getRow(i);
					Cell cell = row.createCell(9);
					//cell.setCellValue("pass");
					cell.setCellStyle(Passstyle);					
					
					}
					else
					{
					System.out.println("The Product Description Mismatches");
					Row row = sh.getRow(i);
					Cell cell = row.createCell(9);	
					cell.setCellValue("Fail");
					cell.setCellStyle(Failstyle);
					}
				} 
				
				
			
				catch (Throwable e) {
					// Test case failed, log the failure with the test case number and market
					System.out.println("Test Case #" + testCaseCounter + " Failed for Market: " + locale);
					e.printStackTrace();
				}
				
			}
			FileOutputStream fos = new FileOutputStream(InputPutExcelfilepath);
			wb.write(fos);
			fos.close();
			System.out.println("Done");
			
		}
	}


