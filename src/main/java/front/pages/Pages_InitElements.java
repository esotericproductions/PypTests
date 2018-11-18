package front.pages;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class Pages_InitElements {


    PypActivityMain pam = new PypActivityMain();
    PypCulturePage cultureTab = new PypCulturePage();
    PypVidsPage vidsTab = new PypVidsPage();

    public Pages_InitElements(WebDriver driver) {

        PageFactory.initElements(new AppiumFieldDecorator(driver), pam);
        PageFactory.initElements(new AppiumFieldDecorator(driver), cultureTab);
        PageFactory.initElements(new AppiumFieldDecorator(driver), vidsTab);
    }

    public PypActivityMain getPam() { return pam; }
    public PypCulturePage getCulture()   { return cultureTab; }
    public PypVidsPage getVidsTab() { return vidsTab; }
}
