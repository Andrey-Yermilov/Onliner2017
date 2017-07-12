package UI.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.CheckBox;
import webdriver.elements.Label;
import java.util.ArrayList;
import java.util.List;

/**
 * Форма конкретного раздела каталога
 */
public class SectionForm extends BaseForm {
    private static final String formLocator = "//h1[@class = 'schema-header__title']";
    private String itemPathTemplate = "(//div[@class='schema-product__group']//span[contains(@data-bind, 'full_name')])";
    private String addToCompareTemplate = "(//div[@class='schema-product__group']//span[@class='i-checkbox__faux'])[%d]";

    public SectionForm () {
        super(By.xpath(formLocator), "Section Form. Onliner");
    }

    /**
     * Открыть форму конкретного товара
     * @param i номер товара на странице
     */
    public void goToItemForm(int i){
        String itemPath = itemPathTemplate+"[%d]";
        Label lblItem = new Label(By.xpath(String.format(itemPath,i)),String.format("Link to item #%d page",i));
        lblItem.click();
    }

    /**
     * Подсчитать количество товаров на странице
     * @return количество товаров на странице
     */
    private int countItemsOnPage(){
        return  findElementsByXpath(itemPathTemplate).size();
    }

    /**
     * Получить имя товара
     * @param i номер товара на странице
     * @return имя товара
     */
    private String getItemName(int i){
        String itemPath = itemPathTemplate+"[%d]";
        Label lblItem = new Label(By.xpath(String.format(itemPath,i)),String.format("Item#%d Name",i));
        return lblItem.getText();
    }

    /**
     * Добавить товар к сравнению
     * @param i номер товара на странице
     */
    private void addItemToComparisonList (int i){
        CheckBox chbAddCompareList = new CheckBox(By.xpath(String.format(addToCompareTemplate,i)),String.format("Item%d: add to comparison list",i));
        chbAddCompareList.check();
    }

    /**
     * Добавить все товары на странице к сравнению
     */
    public void addAllItemsToComparisonList (){
        int count = countItemsOnPage();
        for (int i = 1; i <= count; i++) {
            addItemToComparisonList(i);
        }
    }

    /**
     * Получить имена всех товаров на странице
     * @return лист с именами всех товаров на странице
     */
    public List<String> getAllItemsNames(){
        int count = countItemsOnPage();
        ArrayList <String> listOfAllItemsNames = new ArrayList<String>();
        for (int i = 1; i <= count; i++) {
            listOfAllItemsNames.add(getItemName(i)) ;
        }
        return listOfAllItemsNames;
    }

    /**
     * Очистить список сравнения, если он не пустой
     */
    public void clearComparisonList() {
        String emptyComparisonFormPath = "//div[@class='compare-button compare-button_visible']//a[contains (@title,'Очистить')]";
        if (new Button(By.xpath(emptyComparisonFormPath), "Clear comparison list").isPresent()) {
            Label lblEmptyComparisonList = new Label(By.xpath(emptyComparisonFormPath), "Clear comparison list");
            lblEmptyComparisonList.click();
            lblEmptyComparisonList.click();
        }
    }

    /**
     * Открыть форму сравнения товаров
     */
    public void goToComparisonForm(){
        Button lblGoToComparison = new Button(By.xpath("//a[contains (@class, 'compare-button')]//span[contains(text(),'товар')]"), "Go to comparison form");
        lblGoToComparison.click();
    }
}

