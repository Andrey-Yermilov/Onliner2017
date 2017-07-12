package UI.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.Translit;
import java.util.List;

/**
 * Форма с новостями раздела 'Недвижимость'
 */
public class RealtForm extends BaseForm {
    private static final String formLocator = "//li[@class='b-main-navigation__item b-main-navigation__item-active']/a[contains(text(),'Недвижимость')]";

    public RealtForm() {
        super(By.xpath(formLocator), "Realt, Onliner");
    }

    /**
     * Проверить ссылки на тайлах
     * @param expectedTileLink ожилаемая ссылка
     */
    public void assertTilesLinks(String expectedTileLink){
        List<WebElement> list  = findElementsByXpath("//a[@class='b-tile-section']");
        int i=0;
        for (WebElement element : list){
            String actualTileLink = element.getText();
            String actualTileLinkEn = Translit.toTranslit(actualTileLink);
            String expectedTileLinkEn = Translit.toTranslit(expectedTileLink);
            i++;
            info(String.format("Tile %d: Expected %s, found %s",i, expectedTileLinkEn, actualTileLinkEn));
            Assert.assertTrue(expectedTileLink.compareToIgnoreCase(actualTileLink)==0);
        }
    }
}

