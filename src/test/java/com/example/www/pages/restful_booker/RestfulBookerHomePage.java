package com.example.www.pages.restful_booker;

import com.example.www.utils.DriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.decorators.WebDriverDecorator;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.function.Function;

/*
    Loadable component is a base class that provides a standard way of ensuring that pages are loaded
 */
public class RestfulBookerHomePage extends LoadableComponent<RestfulBookerHomePage> {
    private final WebDriver driver;
    private final FluentWait<WebDriver> fluentWait;
    private final Wait<WebDriver> explicitWait;

//    public RestfulBookerHomePage(WebDriver driver) {
//        this.driver = driver;
//
//        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
//
//        fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
//    }

    public RestfulBookerHomePage(DriverManager driverManager) {
        this.driver = driverManager.getDriver();

        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));

        fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
    }

//    public RestfulBookerHomePage(ChromeDriver driver) {
//        this.driver = driver;
//
//        explicitWait = new WebDriverWait(driver, Duration.ofSeconds(30));
//
//        fluentWait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);
//    }

    private By advertBanner = By.cssSelector("img[src=\"https://automationintesting.online/js/../images/1e0cc75173ea62607f68904ea0193cbc-ait-banner.png\"]");
    private By logo = By.cssSelector("img[alt=\"Hotel logoUrl\"]");
    private By hotelDescription = By.cssSelector("div[class~=\"hotel-description\"]");
    private By bookThisRoomBtn = By.cssSelector("button[class~=\"openBooking\"]");
    private By bookThisRoomBtnNonexist = By.cssSelector("button[class~=\"openBookingss\"]");

    /*
        is used to navigate to the page
     */
    @Override
    protected void load() {
//        driver.get("https://automationintesting.online/#/");
        driver.get("https://www.2degrees.nz/broadband/plans?discount=yes");
        driver.findElement(By.id("AddressSearch-input")).sendKeys("test");
    }

    /*
        is used to determine we are on the right page
     */
    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
//        Assert.assertEquals("Not in the restful booker website", "https://automationintesting.online/#/", url);
        Assert.assertEquals("Not in the restful booker website", "https://www.2degrees.nz/broadband/plans?discount=yes", url);
    }

    /*
        Explicit wait
        wait will run repeatedly until its return value is truthy.
        A “truthful” return value is anything that evaluates to boolean true in the language at hand, such as a string,
        number, a boolean, an object (including a WebElement), or a populated (non-empty) sequence or list

        That means an empty list evaluates to false.
     */
    public WebElement getAdvertBanner() {
        /*
            Explicit wait with ExpectedConditions
        */
        //return explicitWait.until(ExpectedConditions.visibilityOfElementLocated(advertBanner));

        /*
            Explicit wait with lambda function
         */
        return explicitWait.until(driver -> driver.findElement(advertBanner));
    }

    /*
        Fluent wait
     */
    public void clickBookThisRoomBtn() {
        /*
            Function example
         */
//        fluentWait.until(new Function<WebDriver, WebElement>() {
//            @Override
//            public WebElement apply(WebDriver webDriver) {
//                return driver.findElement(bookThisRoomBtn);
//            }
//        }).click();

        /*
            Lambda example
         */
        fluentWait.until(webDriver -> driver.findElement(bookThisRoomBtn)).click();
    }

    public void clickBookThisRoomBtnEvent() {
        fluentWait.until(webDriver -> driver.findElement(bookThisRoomBtn)).click();

//        SeleniumListener seleniumListener = new SeleniumListener();
        //WebDriver decorated = new EventFiringDecorator(seleniumListener).decorate(driver);
//        WebDriver decorated = new CustomEventFiringDecorator(seleniumListener).decorate(driver);
        //decorated.findElement(bookThisRoomBtn);
        // fluentWait.until(webDriver -> decorated.findElement(bookThisRoomBtn)); // valid
//        fluentWait.until(webDriver -> decorated.findElement(bookThisRoomBtnNonexist)); // invalid

    }
}
