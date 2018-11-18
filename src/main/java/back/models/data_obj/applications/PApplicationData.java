package back.models.data_obj.applications;

import front.helpers.Helper_RandomString;
import io.appium.java_client.MobileElement;

/**
 * Created by jdwinters on 7/15/17.
 */
public class PApplicationData implements PDataObject {

    private int                   tag = 0;

    MobileElement ae;


    private final String id    = new Helper_RandomString(5).nextString();
    private final String bigId = new Helper_RandomString(8).nextString();


    public MobileElement getAe() {
        return ae;
    }

    public void setAe(MobileElement ae) {
        this.ae = ae;
    }

    @Override
    public void setTag(int tagz) {
        this.tag = tagz;
    }

    @Override
    public int getTag() {
        return tag;
    }
}
