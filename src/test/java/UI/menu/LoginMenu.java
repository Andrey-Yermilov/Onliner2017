package UI.menu;

import org.openqa.selenium.By;
import webdriver.elements.BaseElement;
import webdriver.elements.Button;

/**
 * Меню логина на сайт
 */
public class LoginMenu extends BaseElement {
    private static final String menuLocator = "//div[@class='auth-bar auth-bar--top']";
    private String enterLinkTemplate = "//div[@class='auth-bar auth-bar--top']/div[contains(@title, '%s')]";
    public LoginMenu() {
        super(By.xpath(menuLocator), "Login menu");
    }

    /**
     * Открыть форму логина
     * @param typeOfLogin тип логина на сайт
     */
    public void openLoginForm(String typeOfLogin) {
        Button btnLogin;
        if (typeOfLogin.contains("text")){
            btnLogin  = new Button(By.xpath("//div[@class='auth-bar auth-bar--top']/div[contains(text(),'Вход')]"),
                    String.format("%s login", typeOfLogin));
        }
        else {
            btnLogin  = new Button(By.xpath(String.format(enterLinkTemplate, typeOfLogin)),String.format("%s login", typeOfLogin));
        }
        btnLogin.click();
    }

    protected String getElementType() {
        return getLoc("loc.menu");
    }
}