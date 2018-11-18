package front.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * Created by jdwinters on 7/19/17.
 */
public class PypCulturePage {

    @AndroidFindBy(id = "searchview")
    private AndroidElement searchField;

    @AndroidFindBy(id = "culture_recycler")
    private AndroidElement cultureRecycler;

    @AndroidFindBy(id = "fuzzy_holder")
    private AndroidElement fuzzyHolder;

    @AndroidFindBy(id = "fuzz_go_holder")
    private AndroidElement fuzzGoHolder;

    @AndroidFindBy(id = "profile_holder")
    private AndroidElement brand;

    @AndroidFindBy(id = "img_pop")
    private AndroidElement popup;

    @AndroidFindBy(id = "share_button")
    private AndroidElement shareButton;

    @AndroidFindBy(id = "profile_image")
    private AndroidElement profileImage;

    public MobileElement getSearch() { return searchField; }
    public MobileElement getCultureRecycler() { return cultureRecycler; }
    public List<MobileElement> getCultureRecyclerElems() {
        return cultureRecycler.findElements(By.className("android.widget.RelativeLayout"));
    }
    public MobileElement getPop() { return popup; }
    public MobileElement getFuzzGo() { return fuzzGoHolder; }
    public MobileElement getShare() { return shareButton; }
    public MobileElement getBrand() { return brand; }

    public MobileElement getProfileImage() { return profileImage; }

    public void sendQuery(String query) { searchField.sendKeys(query); }

    public void tapCultureElem(MobileElement me) { me.click(); }

    public void tapVisibleFuzzGo() { fuzzGoHolder.click(); }

    public void tapBranding() { brand.click(); }

}
