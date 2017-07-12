package UI.menu;

import org.openqa.selenium.By;
import webdriver.Translit;
import webdriver.elements.BaseElement;
import webdriver.elements.Label;

/**
 * Меню с типами закладок
 */
public class BookmarksTypesMenu extends BaseElement {
    private static final String menuLocator = "//div[@class='b-hdtopic']";
    private String tabTemplate = "//div[@class='b-hdtopic']//a[text()='%s']";

    public BookmarksTypesMenu() {
        super(By.xpath(menuLocator), "Login menu");
    }

    /**
     * Открыть закладки
     * @param tab тип закладдок
     */
    public void openTab(String tab) {
        String tabEN = Translit.toTranslit(tab);
        Label lblTab = new Label(By.xpath(String.format(tabTemplate, tab)),String.format("%s tab", tabEN));
        lblTab.click();
    }

    protected String getElementType() {
        return getLoc("loc.menu");
    }
}