package back.models.exceptions;

import org.openqa.selenium.ElementNotVisibleException;

/**
 * Created by jwinters on 7/19/17.
 */
public class CustomElementNotFoundException extends ElementNotVisibleException {

    public CustomElementNotFoundException(String message) {

        super("Element not visible in method: " + message);
    }

}
