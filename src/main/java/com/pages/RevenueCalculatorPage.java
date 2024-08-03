package com.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RevenueCalculatorPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//span[@data-index='0']")
    WebElement slider;

    @FindBy(xpath = "//input[@id=':r1d:']")
    WebElement textField;

    @FindBy(id = "cpt-99091")
    WebElement cpt99091Checkbox;

    @FindBy(id = "cpt-99453")
    WebElement cpt99453Checkbox;

    @FindBy(id = "cpt-99454")
    WebElement cpt99454Checkbox;

    @FindBy(id = "cpt-99474")
    WebElement cpt99474Checkbox;

    @FindBy(id = "total-reimbursement")
    WebElement totalReimbursementHeader;

    public RevenueCalculatorPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void scrollToElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void adjustSlider(int value) {
        scrollToElement(slider); // Ensure the slider is visible
       wait.until(ExpectedConditions.visibilityOf(slider));

        // Use JavaScript to set the slider value
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('value', arguments[1]);", slider, value);

        // Optionally, trigger change event if needed
        js.executeScript("arguments[0].dispatchEvent(new Event('change'));", slider);


    }

    public void updateTextField(int value) {
        // Scroll to the text field and ensure it is visible
        scrollToElement(textField);
        wait.until(ExpectedConditions.visibilityOf(textField));

        // Check if the text field is obscured by other elements and scroll it into view if necessary
        JavascriptExecutor js = (JavascriptExecutor) driver;
        boolean isVisible = (Boolean) js.executeScript(
                "return (arguments[0].getBoundingClientRect().top >= 0 && arguments[0].getBoundingClientRect().bottom <= window.innerHeight);",
                textField
        );

        if (!isVisible) {
            // Scroll up if the text field is not visible in the viewport
            js.executeScript("window.scrollBy(0, -250);"); // Adjust scroll amount as needed
        }

        // Ensure the text field is visible and interactable
        wait.until(ExpectedConditions.visibilityOf(textField));
        textField.clear();
        textField.sendKeys(String.valueOf(value));
    }

    public void selectCPTCodes() {
        scrollToElement(cpt99091Checkbox); // Ensure the checkboxes are visible
        wait.until(ExpectedConditions.visibilityOf(cpt99091Checkbox));
        cpt99091Checkbox.click();
        cpt99453Checkbox.click();
        cpt99454Checkbox.click();
        cpt99474Checkbox.click();
    }

    public String getTotalReimbursement() {
        scrollToElement(totalReimbursementHeader); // Ensure the header is visible
        wait.until(ExpectedConditions.visibilityOf(totalReimbursementHeader));
        return totalReimbursementHeader.getText();
    }
}
