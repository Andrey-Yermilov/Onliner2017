package UI.menu;

import org.openqa.selenium.By;
import webdriver.Translit;
import webdriver.elements.BaseElement;
import webdriver.elements.Button;
import webdriver.elements.Label;

/**
 * Главное меню
 */
public class UserSettingsMenu extends BaseElement {
    private static final String menuLocator =
            "//div[@class='b-top-profile__item b-top-profile__item_arrow js-toggle-bar b-top-profile__item_opened']";
    private Label lblUserName = new Label(By.xpath("//div[@class='b-top-profile__name']/a"), "User name");
    private Label lblExit = new Label(By.xpath("//a[contains(text(),'Выйти')]"), "Exit");
    public UserSettingsMenu() {
        super(By.xpath(menuLocator), "Main menu");
    }

    /**
     * Перейти на форму профиля пользователя
     */
    public void goToProfileForm() {
        lblUserName.click();
    }
    /**
     * Выйти
     */
    public void exit() {
        lblExit.click();
    }

    protected String getElementType() {
        return getLoc("loc.menu");
    }
}