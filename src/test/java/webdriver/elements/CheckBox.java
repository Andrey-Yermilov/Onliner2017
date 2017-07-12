package webdriver.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;

public class CheckBox extends BaseElement {

    public CheckBox(final By locator, final String name) {
        super(locator, name);
    }
    public CheckBox(String string, String name) {
        super(string, name);
    }

    protected String getElementType() {
        return getLoc("loc.checkbox");
    }
        public boolean isEnabled(){
        return this.getElement().isEnabled();
    }

    public void check() {
        waitForIsElementPresent();
        info(getLoc("loc.checking"));

              if (browser.getDriver() instanceof JavascriptExecutor) {
            ((JavascriptExecutor)browser.getDriver()).executeScript("arguments[0].style.border='3px solid red'", element);
        }
        element.click();
    }
     public boolean isChecked() {
         return element.isSelected();
     }

    public CheckBox(By locator) {
        super(locator);
    }
}
