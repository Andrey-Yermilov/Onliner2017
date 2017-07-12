package UI.forms;

import UI.menu.*;
import org.openqa.selenium.By;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.Label;
import webdriver.elements.TextBox;

/**
 * Главная страница сайта onliner
 */
public class MainForm extends BaseForm {
    private static final String formLocator = "//div[contains(@class,'main-page-grid')]";
    private Label lblUserName;
    private Label lblBookmarks;
    private Label lblExit;
    private Label lblCurrency = new Label(By.id("currency-informer"), "Currencies");
    private Button btnAllOffers = new Button(By.xpath("//a[@class='button button_orange product__button']"), "All Offers");
    private TextBox txbSearch = new TextBox(By.xpath("//input[@class='fast-search__input']"), "Search");
    private MainMenu mainMenu = new MainMenu();

    private LoginMenu loginMenu = new LoginMenu();

    public MainForm() {
        super(By.xpath(formLocator), "Onliner main");
    }

    /**
     * Геттер для главного меню
     *
     * @return главное меню
     */
    public MainMenu getMainMenu() {
        return mainMenu;
    }

    /**
     * Геттер для меню логина
     *
     * @return меню логина
     */
    public LoginMenu getLoginMenu() {
        return loginMenu;
    }



    /**
     * Перейти на старницу валют
     */
    public void goToCurrencyForm() {
        lblCurrency.click();
    }

    /**
     * Выйти
     */
    public void exit() {
        expandUserSettings();
        UserSettingsMenu UserSettingsMenu = new UserSettingsMenu();
        UserSettingsMenu.exit();
     }
    /**
     * Развернуть вкладку настроек пользователя
     */
    public void expandUserSettings() {
        Button expandUserSettings = new Button(By.xpath("//div[@class='b-top-profile__preview']"), "User settings");
        expandUserSettings.click();

    }
    /**
     * Перейти на форму профиля пользователя
     */
    public void goToProfileForm() {
        UserSettingsMenu UserSettingsMenu = new UserSettingsMenu();
        UserSettingsMenu.goToProfileForm();
    }

    /**
     * Проверить,что пользователь залогинен
     *
     * @param expectedUsername имя пользователя
     */
    public void assertLoggedIn(String expectedUsername) {

        expandUserSettings();
        lblUserName = new Label(By.xpath("//div[@class='b-top-profile__name']/a"), "User name");
        String actualLogin = lblUserName.getText();
        info(String.format("Username: Expected %s, found %s", expectedUsername, actualLogin));
        Assert.assertTrue(actualLogin.contains(expectedUsername));
    }

    /**
     * Перейти на форму закладок пользователя
     */
    public void goToBookmarks() {
        lblBookmarks = new Label(By.xpath("//a[@class='b-top-profile__favorites']"), "Bookmarks");
        lblBookmarks.click();
    }

    /**
     * Осуществить поиск
     *
     * @param request поисковой запрос
     */
    public void performFastSearch(String request) {
        txbSearch.setText(request);
        switchToModalFrame("modal-iframe");
    }

    /**
     * Перейти к предложениям продавцов
     */
    public void viewOffers() {
        btnAllOffers.click();
        switchToMainFrame();
    }
}