import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.events.EventFiringWebDriverFactory;
import io.appium.java_client.events.api.Listener;
import io.appium.java_client.events.api.general.AlertEventListener;
import io.appium.java_client.events.api.general.ElementEventListener;
import io.appium.java_client.events.api.general.NavigationEventListener;
import io.appium.java_client.events.api.general.WindowEventListener;
import org.apache.http.util.TextUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.DesiredCapabilities;

@RunWith(JUnit4.class)
public class SampleTest {

    private AndroidDriver driver;

    @Before
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:platformVersion", "10");
        desiredCapabilities.setCapability("appium:deviceName", "MI_5X");
        desiredCapabilities.setCapability("appium:appPackage", "com.whatsapp");
        desiredCapabilities.setCapability("appium:appActivity", "com.whatsapp.Main");
        desiredCapabilities.setCapability("appium:resetKeyboard", true);
        desiredCapabilities.setCapability("appium:noReset", true);
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 0);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);

        URL remoteUrl = new URL("http://localhost:4723/wd/hub/");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    @Test
    public void sampleTest() {
//        Activity activity = new Activity("com.whatsapp", "com.whatsapp.Main");
//        activity
//        driver.startActivity(activity);
//        driver.get("whatsapp://send?phone=15237347221"); // ?????????
        driver.get("whatsapp://send?phone=923491446066"); // ?????????
        String currentPackage = driver.getCurrentPackage();
        String activityName = driver.currentActivity();
        LogUtils.log("currentPackage???" + currentPackage);
        LogUtils.log("activityName???" + activityName);
        if ("com.whatsapp".equals(currentPackage)) {
            if (".ContactPicker".equals(activityName)) { // ????????????
                checkPicker();
            }
        }
    }

    private void checkPicker() {
        LogUtils.log("DDDDDDDD");
        WebElement element = null;
        try {
            element = driver.findElementById("android:id/button1");
            LogUtils.log("CCCCCCCCCCCCCC");
        } catch (Exception e) {
            LogUtils.log("EEEEEEEEEEEE");
            String activityName = driver.currentActivity();
            if (".Conversation".equals(activityName)) { // ????????????
                LogUtils.log("????????????????????????");
            }
        }
        if (element != null && element.isDisplayed()) {
            element = driver.findElementById("android:id/message");
            String text = element.getText();
            LogUtils.log(element.getText());
            if ("????????????".equals(text)) {
                LogUtils.log("AAAAAAAa");
            } else if (text.endsWith("?????? WhatsApp ?????????")) {
                LogUtils.log("?????????????????????");
            } else if (!TextUtils.isEmpty(text)) {
                LogUtils.log("????????????");
                LogUtils.log(text);
            }
        } else {
            element = driver.findElementById("android:id/message");
            if (element != null) {
                String text = element.getText();
                LogUtils.log(element.getText());
                if ("????????????".equals(text)) {
                    checkPicker();
                }
            }
        }
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}
