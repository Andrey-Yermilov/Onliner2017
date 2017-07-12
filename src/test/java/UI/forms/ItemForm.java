package UI.forms;

import org.openqa.selenium.By;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.CheckBox;
import webdriver.elements.Label;

import java.util.ArrayList;

/**
 * Форма конкретного товара
 */
public class ItemForm extends BaseForm {
    private static final String formLocator = "//span[contains(text(), 'Описание и фото')]";
    private CheckBox chbAddToBookmarks = new CheckBox(By.xpath("//span[contains(text(),'В закладк')]/preceding-sibling::span"), "Add to bookmarks");
    private Label lblFullItemName = new Label(By.xpath("//h1[@class='catalog-masthead__title']"), "Full item name");
    private Label lblArrangeByPrice = new Label(By.xpath("//div[@class='b-offers-list-line-1__group b-offers-sort']//span[contains(text(),'По цене')]"), "By price");

    public ItemForm() {
        super(By.xpath(formLocator), "Item page");
    }

    /**
     * Получить полное имя товара без категории, к которой он относится
     *
     * @return Производитель + модель  товара
     */
    public String getFullItemName() {
        return lblFullItemName.getText().replaceAll("[А-Яа-я]", "").trim();
    }

    /**
     * Добавить товар в закладки
     */
    public void addToBookmarks() {
        chbAddToBookmarks.check();
    }

    /**
     * Проверить, находится ли товар в закладках
     */
    public void assertInBookmarks() {
        Assert.assertTrue(new Label(By.xpath("//span[contains(text(),'В закладках')]"), "In bookmarks").isPresent());
    }

    /**
     * Упорядочить предложения по цене
     */
    public void arrangeByPrice() {
        lblArrangeByPrice.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Выбрать город для отображения предложений
     *
     * @param city город
     */
    public void chooseCity(String city) {
        Label lblCity = new Label(By.xpath(String.format("//div[@class='b-checkgroup']//span[contains(@id, '%s')]", city)), city);
        lblCity.click();
        Assert.assertTrue(new Label(By.xpath(String.format("//div[@class='b-checkgroup']//span[contains(@id, '%s') and contains(@class, 'selected')]", city)), String.format("Prices for '%s'", city)).isPresent());
    }

    /**
     * Подсчитать количество всех предложений от продавцов для выбранного города
     *
     * @param city город
     * @return количество всех предложений
     */
    private int countOffers(String city) {
        // String pathTemplate = "//tr[contains(@class, 'state_add-to-cart m-divider') and contains(@id, '%s')]";
        return findElementsByXpath("//tr[contains(@class, 'm-divider')]").size();
    }

    /**
     * Получить список всех цен
     *
     * @param city город
     * @return лист со всемми ценами
     */
    private ArrayList<Integer> getAllPrices(String city) {
        int count = countOffers(city);
        ArrayList<Integer> listOfAllPrices = new ArrayList<Integer>();
        for (int i = 1; i <= count; i++) {
            listOfAllPrices.add(getItemPrice(i, city));
        }
        return listOfAllPrices;
    }

    /**
     * Получить цену i-ого предложения
     *
     * @param i    номер предложения
     * @param city город
     * @return цена товара для i-ого предложения
     */
    private int getItemPrice(int i, String city) {

        String itemPath = "(//tr[contains(@class, 'm-divider')]//a/span[contains(@data-bind, 'price')])" + "[%d]";
        Label lblPrice = new Label(By.xpath(String.format(itemPath, i)), String.format("Item#%d price", i));
        return Integer.parseInt(lblPrice.getText().replaceAll("[^0-9]", ""));
    }

    /**
     * Проверить упорядочивание предложений по цене по возрастанию
     *
     * @param city город, для которого осуществляется проверка
     */
    public void assertArrangeByPriceASC(String city) {

        info("Assert sorting by price ascending");

        ArrayList<Integer> listOfAllPrices = getAllPrices(city);
        int count = listOfAllPrices.size();
        for (int i = 0; i < count - 1; i++) {
            int item = listOfAllPrices.get(i);
            info(String.format("Item%d price: '%d'", i + 1, item));
            Assert.assertTrue(item <= listOfAllPrices.get(i + 1));
        }
        info(String.format("Item%d price: '%d'", count, listOfAllPrices.get(count - 1)));
    }

    /**
     * Проверить упорядочивание предложений по цене по убыванию
     *
     * @param city город, для которого осуществляется проверка
     */
    public void assertArrangeByPriceDESC(String city) {
        info("Assert sorting by price descending");
        ArrayList<Integer> listOfAllPrices = getAllPrices(city);
        int count = listOfAllPrices.size();
        for (int i = 0; i < count - 1; i++) {
            int item = listOfAllPrices.get(i);
            info(String.format("Item%d price: '%d'", i + 1, item));
            Assert.assertTrue(item >= listOfAllPrices.get(i + 1));
        }
        info(String.format("Item%d price: '%d'", count, listOfAllPrices.get(count - 1)));
    }
}
