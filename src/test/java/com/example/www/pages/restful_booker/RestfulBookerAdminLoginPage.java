package com.example.www.pages.restful_booker;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;

/*
    Loadable component is a base class that provides a standard way of ensuring that pages are loaded
 */
public class RestfulBookerAdminLoginPage extends LoadableComponent<RestfulBookerAdminLoginPage> {
    private final WebDriver driver;

    public RestfulBookerAdminLoginPage(WebDriver driver) {
        this.driver = driver;
    }

    /*
        is used to navigate to the page
     */
    @Override
    protected void load() {
        driver.get("https://automationintesting.online/#/admin");
    }

    /*
        is used to determine we are on the right page
     */
    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        Assert.assertEquals("Not in the restful booker website","https://automationintesting.online/#/admin",url);
    }
}
