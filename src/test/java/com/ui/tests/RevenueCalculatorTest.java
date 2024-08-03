package com.ui.tests;

import com.pages.HomePage;
import com.pages.RevenueCalculatorPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

public class RevenueCalculatorTest {
    WebDriver driver;
    HomePage homePage;
    RevenueCalculatorPage revenueCalculatorPage;

    @BeforeClass
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // Remove headless for debugging
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); // Added implicit wait
        driver.get("https://www.fitpeo.com");
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        revenueCalculatorPage = new RevenueCalculatorPage(driver);
    }

    @Test
    public void testRevenueCalculator() {
        homePage.goToRevenueCalculator();


     revenueCalculatorPage.adjustSlider(820);
    revenueCalculatorPage.updateTextField(560);

        // Select CPT codes
        revenueCalculatorPage.selectCPTCodes();

        // Validate total recurring reimbursement
        String totalReimbursement = revenueCalculatorPage.getTotalReimbursement();
        Assert.assertEquals(totalReimbursement, "$110700", "Total Reimbursement does not match expected value.");
    }

    @AfterClass
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
