package com.example.www.step_definitions;

import com.example.www.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.picocontainer.PicoFactory;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.picocontainer.annotations.Inject;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

public class Hooks {
//    @Inject
    private World world;

//    @Inject
    private DriverManager driverManager;

    Scenario scenario;

    public Hooks(World world, DriverManager driverManager) {
        this.world = world;
        this.driverManager = driverManager;
    }

//    @Before
//    public void before(Scenario scenario) {
//        this.scenario = scenario;
////        String browser = System.getProperty("browser");
//        String browser = "chrome";
//        switch (browser.toLowerCase()) {
//            case "firefox":
//                WebDriverManager.firefoxdriver().setup();
//                world.driver = new FirefoxDriver();
//                break;
//            case "chrome":
//            default:
//                WebDriverManager.chromedriver().setup();
//                world.driver = new ChromeDriver();
//                break;
//        }
//
//        world.driver.manage().window().maximize();
//    }

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
        world.driver = driverManager.getDriver();

        world.driver.manage().window().maximize();
    }

    @After
    public void after(Scenario scenario) throws IOException {
        /*
            Take screenshot if scenario failed
         */
        if (scenario.isFailed()) {
            TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
            File scrShotFile = screenshot.getScreenshotAs(OutputType.FILE);

            String screenshotsFolder = System.getProperty("user.dir") + "/src/test/java/output/screenshots/";
            File screenshotDirectory = new File(screenshotsFolder);
            if (!screenshotDirectory.exists()) {
                screenshotDirectory.mkdir();
            }

            File destFile = new File(screenshotsFolder + scenario.getName() + ".png");
            FileUtils.copyFile(scrShotFile, destFile);
        }

        if (world.driver != null) {
            world.driver.quit();
        }

    }

    @AfterStep
    public void attachScreenshot() throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(scrShotFile);
        BufferedImage bi = ImageIO.read(is);

        Image tmp = bi.getScaledInstance((int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(
                (int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();

        this.scenario.attach(bytes, "image/png", "screenshot");

        baos.close();
    }

    public void noResize() {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        this.scenario.attach(screenshot.getScreenshotAs(OutputType.BYTES), "image/png", "screenshot");
    }

    public void workingResize() throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(scrShotFile);
        BufferedImage bi = ImageIO.read(is);

        System.out.println("bi.getWidth(): " + bi.getWidth());
        System.out.println("bi.getHeight(): " + bi.getHeight());

        Image tmp = bi.getScaledInstance((int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(
                (int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), bi.getType());

        System.out.println("newImage.getWidth(): " + newImage.getWidth());
        System.out.println("newImage.getHeight(): " + newImage.getHeight());

        Graphics2D g = newImage.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();

        System.out.println("bi.getWidth(): " + bi.getWidth());
        System.out.println("bi.getHeight(): " + bi.getHeight());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();

        this.scenario.attach(bytes, "image/png", "screenshot");

        baos.close();
    }

    /*
        crops the image instead of scaling
     */
    public void wrongResize() throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(scrShotFile);
        BufferedImage bi = ImageIO.read(is);

        System.out.println("bi.getWidth(): " + bi.getWidth());
        System.out.println("bi.getHeight(): " + bi.getHeight());

        BufferedImage newImage = new BufferedImage(
                (int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), bi.getType());

        Graphics2D g = newImage.createGraphics();
        g.drawImage(bi, 0, 0, null);
        g.dispose();

        System.out.println("bi.getWidth(): " + bi.getWidth());
        System.out.println("bi.getHeight(): " + bi.getHeight());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();

        this.scenario.attach(bytes, "image/png", "screenshot");
    }

    public void anotherResize() throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(scrShotFile);
        BufferedImage bi = ImageIO.read(is);

        File compressedImageFile = new File("compressed_image.png");
        OutputStream os = new FileOutputStream(compressedImageFile);

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.05f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(bi, null, null), param);

        FileInputStream fl = new FileInputStream(compressedImageFile);
        byte[] arr = new byte[(int) compressedImageFile.length()];
        fl.read(arr);
        fl.close();

        this.scenario.attach(arr, "image/png", "screenshot");

        os.close();
        ios.close();
        writer.dispose();
    }

    public void resizeNoAlpha() throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(scrShotFile);
        BufferedImage bi = ImageIO.read(is);

        Image tmp = bi.getScaledInstance((int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(
                (int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();

        this.scenario.attach(bytes, "image/png", "screenshot");

        baos.close();
    }

    public void dontknowifitworks() throws IOException {
        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
        InputStream is = new ByteArrayInputStream(scrShotFile);
        BufferedImage bi = ImageIO.read(is);

        Image tmp = bi.getScaledInstance((int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), Image.SCALE_SMOOTH);
        BufferedImage newImage = new BufferedImage(
                (int) (bi.getWidth() * 0.5), (int) (bi.getHeight() * 0.5), BufferedImage.TYPE_INT_RGB);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(tmp, 0, 0, null);
        g.dispose();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(newImage, "png", baos);
        byte[] bytes = baos.toByteArray();

        //////////////////////
        is = new ByteArrayInputStream(bytes);
        bi = ImageIO.read(is);

        OutputStream os = baos;

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("png");
        ImageWriter writer = (ImageWriter) writers.next();

        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
        writer.setOutput(ios);

        ImageWriteParam param = writer.getDefaultWriteParam();

        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        param.setCompressionQuality(0.05f);  // Change the quality value you prefer
        writer.write(null, new IIOImage(bi, null, null), param);

        this.scenario.attach(bytes, "image/png", "screenshot");

        baos.close();
    }

    public void unknowsolution() {
        //        TakesScreenshot screenshot = ((TakesScreenshot) world.driver);
//        byte[] scrShotFile = screenshot.getScreenshotAs(OutputType.BYTES);
//        InputStream is = new ByteArrayInputStream(scrShotFile);
//        BufferedImage bi = ImageIO.read(is);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        ImageIO.write(bi, "png", baos);
//        byte[] bytes = baos.toByteArray();
//
//        this.scenario.attach(bytes, "image/png", "screenshot");
    }

}
