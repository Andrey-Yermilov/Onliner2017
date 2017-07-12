package UI.forms;

import UI.menu.BookmarksTypesMenu;
import org.openqa.selenium.By;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.CheckBox;
import webdriver.elements.Label;

/**
 * Форма с закладками пользователя
 */
public class BookmarksForm extends  BaseForm {
    private static final String formLocator = "//div[@class='b-hdtopic']";
    private BookmarksTypesMenu bookmarksTypesMenu = new BookmarksTypesMenu();
    private CheckBox chbSelectAll = new CheckBox(By.xpath("//input[@id='selectAllBookmarks']"),"Select all");
    private Label lblDeleteSelected = new Label(By.xpath("//a[@class='pmchk__del']"), "Delete selected");

    public BookmarksForm() {
        super(By.xpath(formLocator), "Bookmarks");
    }

    /**
     * Получить меню с типами закладок (на форуме, в кателоге и т.п)
     * @return меню с типами закладок
     */
    public BookmarksTypesMenu getBookmarksTypesMenu() {
        return bookmarksTypesMenu;
    }

    /**
     * Очистить список закладок
     */
    public void emptyBookmarks(){
        if (!(new Label(By.xpath("//p[text()='Нет закладок']"), "No bookmarks").isPresent())) {
            chbSelectAll.check();
            lblDeleteSelected.click();
            Assert.assertTrue(new Label(By.xpath("//p[text()='Нет закладок']"), "No bookmarks").isPresent());}
    }

    /**
     * Проверить, что заклдака верна
     * @param i номер закладки на странице
     * @param expectedModel ожидаемая модель устройства
     */
    public void assertBookmarkInCatalog (int i,String expectedModel){
        String itemNameTemplate = "(//strong[@class='pname']/a)[%d]";
        Label lblItemName = new Label(By.xpath(String.format(itemNameTemplate,i)),"Short item name");
        String actualModel = lblItemName.getText();
        info(String.format("Expected '%s', found '%s'",expectedModel, actualModel));
        Assert.assertTrue(expectedModel.contains(actualModel));
    }
}