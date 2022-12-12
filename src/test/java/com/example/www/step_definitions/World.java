package com.example.www.step_definitions;

import com.example.www.utils.DriverManager;
import org.openqa.selenium.WebDriver;

public class World {

    /*
        Usable when step definitions has become too long, and we want to split it into several files
        We can use this World Class and PicoContainer dependency injection to share state/data
        between the different step classes
     */

    WebDriver driver;
    DriverManager driverManager;

    public World() {
        System.out.println("world is created");
    }

}
