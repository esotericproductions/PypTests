package front.helpers;

import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;

/**
 * Created by jwinters on 11/7/16.
 */
public final class TextTransfer implements ClipboardOwner {


    /**
     * Empty implementation of the ClipboardOwner interface.
     */
    @Override public void lostOwnership(Clipboard aClipboard, Transferable aContents){
        //do nothing
    }

    /**
     * Place a String on the clipboard, and make this class the
     * owner of the Clipboard's contents.
     */
    public void setClipboardContents(String aString) {

        StringSelection stringSelection = new StringSelection(aString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, this);

    }

    /**
     * Get the String residing on the clipboard.
     *
     * @return any text found on the Clipboard; if none found, return an
     * empty String.
     */
    public String getClipboardContents() {

        String result = "";

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        Transferable contents = clipboard.getContents(null);

        boolean hasTransferableText = (contents != null) && contents.isDataFlavorSupported(DataFlavor.stringFlavor);

        if (hasTransferableText) {

                try {

                    result = (String) contents.getTransferData(DataFlavor.stringFlavor);

                } catch (UnsupportedFlavorException | IOException ex) {

                    ex.printStackTrace();
            }
        }

        return result;
    }
}
