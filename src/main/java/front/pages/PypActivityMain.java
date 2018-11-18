package front.pages;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class PypActivityMain {

    @AndroidFindBy(id = "hider_tester")
    private AndroidElement hider;

    public MobileElement getBB() { return hider; }

    public void tapHider() throws Exception { hider.click(); Thread.sleep(2500); }
}
