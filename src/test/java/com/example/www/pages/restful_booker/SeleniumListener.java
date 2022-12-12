package com.example.www.pages.restful_booker;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.openqa.selenium.support.events.WebDriverListener;

import java.lang.reflect.Method;

public class SeleniumListener implements WebDriverListener {

    @Override
    public void beforeFindElement(WebDriver driver, By locator) {
        System.out.println("Finding: " + locator);
    }

    @Override
    public void afterFindElement(WebDriver driver, By locator, WebElement result) {
        System.out.println("Finding: " + locator + " , result: " + result);
    }

    @Override
    public void onError (java.lang.Object target, java.lang.reflect.Method method, java.lang.Object[] args, java.lang.reflect.InvocationTargetException e) {
        System.out.println("Exception encountered: target, " + target);
        System.out.println("Exception encountered: method, " + method);
        System.out.println("Exception encountered: args, " + args.length);
        System.out.println("Exception encountered: args, " + args[0]);
        System.out.println("Exception encountered: " + e.getCause());
    }

    @Override
    public void afterAnyCall(Object target, Method method, Object[] args, Object result) {
        System.out.println("afterAnyCall encountered: target, " + target);
        System.out.println("afterAnyCall encountered: method, " + method);
        System.out.println("afterAnyCall encountered: args, " + args.length);
        System.out.println("afterAnyCall encountered: args, " + args[0]);
        System.out.println("afterAnyCall encountered: " + result);
    }
}
