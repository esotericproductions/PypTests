package front.helpers;

import java.awt.image.BufferedImage;

/**
 * Created by jdwinters on 7/19/17.
 */
public class BufferedImageWrapper {

    private final String        imageType;
    private final BufferedImage bufferedimage;

    /**
     *
     * @param imageType
     * @param bufferedimage
     */
    public BufferedImageWrapper(String imageType, BufferedImage bufferedimage) {

        this.imageType = imageType;
        this.bufferedimage = bufferedimage;
    }

    public String getImageType() { return imageType; }

    public BufferedImage getBufferedimage() { return bufferedimage; }
}
