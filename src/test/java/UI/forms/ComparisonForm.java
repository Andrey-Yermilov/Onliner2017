package UI.forms;

import org.openqa.selenium.By;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.Label;
import java.util.List;

/**
 * Форма сравнения товаров
 */
public class ComparisonForm extends BaseForm {
    private static final String formLocator = "//h1[text()='Сравнение товаров']";
    private String itemPathTemplate = "(//span[@class= 'product-summary__caption'])";
    private Label lblClearComparison = new Label(By.xpath("//a[text()='Очистить сравнение']"),"Clear comparison");

    public ComparisonForm() {
        super(By.xpath(formLocator), "Items comparison");
    }

    /**
     * Подсчитать кол-во товаров в сравнении
     * @return кол-во товаров в сравнении
     */
    private int countItemsOnPage(){
        return  (findElementsByXpath(itemPathTemplate).size())/2;
    }

    /**
     * Очистить списов товаров в сравнении
     */
    public void clearComparison (){
        lblClearComparison.click();
    }

    /**
     * Проверить, что имена товаров в списке верны
     * @param allItemsNames лист с ожидаемыми именами всех товаров
     */
    public void assertItemsNames(List<String> allItemsNames) {
        int count = countItemsOnPage();
        Label lblItemName;
        String itemPath = itemPathTemplate+"[%d]";
        for (int i = 1; i <= count; i++){
            lblItemName = new Label(By.xpath(String.format(itemPath,i)),String.format("Item#%d name",i));
            String actualItemName = lblItemName.getText();
            String expectedItemName = allItemsNames.get(i-1);
            info(String.format("Item%d: Expected '%s', found '%s'", i, expectedItemName,actualItemName));
            Assert.assertTrue(expectedItemName.contains(actualItemName));
        }
    }
}
