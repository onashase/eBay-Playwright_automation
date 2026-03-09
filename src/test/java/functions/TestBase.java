package functions;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    protected Playwright playwright;
    protected Browser browser;
    protected Page page;
    protected BrowserContext context;

    @BeforeMethod
    public void testSetUp(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false).setSlowMo(3000));
        context = browser.newContext(new Browser.NewContextOptions().setViewportSize(null));
        page = context.newPage();

    }

    @AfterMethod
    public void closeTest(){

        if(page != null){
            page.close();
        }
        if(context != null){
            context.close();
        }
        if(browser != null){
            browser.close();
        }
        if(playwright != null){
            playwright.close();
        }
    }

}
