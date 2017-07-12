package UI.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.ComboBox;
import webdriver.elements.Label;
import static webdriver.Translit.toTranslit;

/**
 * Форма автобарахолки
 */
public class AutoBaraholkaForm extends BaseForm {
    private static final String formLocator = "//h1[contains(text(),'Автобарахолка')]";
    private String itemPath = "//tr[contains(@class,'carRow')]";
    private ComboBox cbbPriceMin = new ComboBox(By.xpath("//select[@name='min-price']"), "Minimal price");
    private ComboBox cbbPriceMax = new ComboBox(By.xpath("//select[@name='max-price']"), "Maximal price");
    private ComboBox cbbManufacturer = new ComboBox(By.xpath("//select[@class='manufacture']"), "Manufacturer");
    private ComboBox cbbYearMin = new ComboBox(By.xpath("//select[@name='min-year']"), "Minimal year");
    private ComboBox cbbYearMax = new ComboBox(By.xpath("//select[@name='max-year']"), "Maximal year");
    private ComboBox cbbMaxMileage = new ComboBox(By.xpath("//select[@name='max-mileage']"), "Maximal mileage");

    public AutoBaraholkaForm() {
        super(By.xpath(formLocator), "AutoBaraholka, Onliner");
    }

    /**
     * Задать минимальную цену
     * @param priceMin минимаьлная цена
     */
    public void setPriceMin (int priceMin){
        waitForJQuery();
        cbbPriceMin.click();
        StringBuffer priceMinStringB = new StringBuffer();
        priceMinStringB = priceMinStringB.append(priceMin);
        if (priceMinStringB.length()>4){
            priceMinStringB.insert((priceMinStringB.length() - 3), ' ');
        }
        String visibleText = priceMinStringB.toString()+" $";

        cbbPriceMin.chooseValue(visibleText);

    }

    /**
     * Задать максимальную цену
     * @param priceMax максимальная цена
     */
    public void setPriceMax (int priceMax){
        waitForJQuery();
        cbbPriceMax.click();
        StringBuffer priceMinStringB = new StringBuffer();
        priceMinStringB = priceMinStringB.append(priceMax);
        if (priceMinStringB.length()>4){
            priceMinStringB.insert((priceMinStringB.length()-3),' ');
        }
        String visibleText = priceMinStringB.toString()+" $";
        cbbPriceMax.chooseValue(visibleText);
    }

    /**
     * Задать производителя
     * @param manufacturer производитель
     */
    public void setManufacturer (String manufacturer){
        waitForJQuery();
        cbbManufacturer.click();
        Label lblManufacturer = new Label(By.xpath(String.format("//select[@class='manufacture']//option[contains(text(),'%s')]",manufacturer)),manufacturer);
        lblManufacturer.click();
            }

    /**
     * Задать минимальнвый год производства
     * @param yearMin минимальнвый год производства
     */
    public void setYearMin (int yearMin){
        waitForJQuery();
        cbbYearMin.click();
        String yearMinString = Integer.toString(yearMin);
        cbbYearMin.chooseValue(yearMinString);
    }

    /**
     * Задать максимальнвый год производства
     * @param yearMax максимальнвый год производства
     */
    public void setYearMax (int yearMax){
        waitForJQuery();
        cbbYearMax.click();
        String yearMaxString = Integer.toString(yearMax);
        cbbYearMax.chooseValue(yearMaxString);
    }

    /**
     * Задать максимальный пробег
     * @param maxMileage максимальный пробег
     */
    public void setMaxMileage (int maxMileage){
        waitForJQuery();
        cbbMaxMileage.click();
        StringBuffer maxMileageStringB = new StringBuffer();
        maxMileageStringB = maxMileageStringB.append(maxMileage);
        if (maxMileageStringB.length()>4){
            maxMileageStringB.insert((maxMileageStringB.length()-3),' ');
        }
        String visibleText = String.format("До %s км", maxMileageStringB.toString());
        cbbMaxMileage.chooseValue(visibleText);
    }

    /**
     * Подсчитать количество результатов на странице
     * @return кол-во результатов на странице
     */
    public int countItemsOnPage(){
        waitForJQuery();
        return  findElementsByXpath(itemPath).size();
    }

    /**
     * Проверить, что производитель, указанный в объявлении, верный
     * @param i номер проверяемого объявления на странице
     * @param expectedManufacturer производитель
     */
    public void assertManufacturer(int i, String expectedManufacturer){
        String lblPath = String.format("(%s//span//a[contains(@href, 'car')])[%d]",itemPath,i);
        Label lblModel = new Label(By.xpath(lblPath), String.format("Item#%d: Model",i));
        String actualModel = lblModel.getText().trim();
        info(String.format("Item%d: Expected '%s', found '%s'",i,expectedManufacturer, toTranslit(actualModel)));
        Assert.assertTrue(actualModel.contains(expectedManufacturer));
    }

    /**
     * Проверить, что цена в долларах, указанная в объявлении, верна
     * @param i номер проверяемого объявления на странице
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     */
    public void assertPriceDollars(int i, int minPrice, int maxPrice){
        String lblPath = String.format("(%s//div[@class='cost-i']//p[@class='small'])[%d]",itemPath,i);
        Label lblModel = new Label(By.xpath(lblPath), String.format("Item#%d: Price",i));
        String actualPriceString = lblModel.getText().replaceAll("\\s","").split("\\$")[0];
        int actualPrice = Integer.parseInt(actualPriceString);
        info(String.format("Item%d price: Expected between '%d$' and '%d$', found '%d$'",i,minPrice, maxPrice, actualPrice));
        Assert.assertTrue(minPrice<=actualPrice && actualPrice<=maxPrice);
    }

    /**
     * Проверить, что производитель, указанный в объявлении, верный
     * @param i номер проверяемого объявления на странице
     * @param minYear минимальнвый год производства
     * @param maxYear максимальнвый год производства
     */
    public void assertYear(int i, int minYear, int maxYear){
        String lblPath = String.format("(%s//span[@class='year'])[%d]",itemPath,i);
        Label lblYear = new Label(By.xpath(lblPath), String.format("Item#%d: Year",i));
        String actualYearString = lblYear.getText();
        int actualYear = Integer.parseInt(actualYearString);
        info(String.format("Item%d year: Expected between '%d' and '%d', found '%d'",i,minYear, maxYear, actualYear));
        Assert.assertTrue(minYear<=actualYear && actualYear<=maxYear);
    }

    /**
     * Проверить, что пробег, указанный в объявлении, верный
     * @param i номер проверяемого объявления на странице
     * @param maxMileage максимальнвый пробег
     */
    public void assertMileage(int i, int maxMileage){
        String lblPath = String.format("(%s//span[@class='dist']/strong)[%d]",itemPath,i);
        Label lblMileage = new Label(By.xpath(lblPath), String.format("Item#%d: Mileage",i));
        String actualYearString = lblMileage.getText().replaceAll("[^0-9]", "");
        int actualMileage = Integer.parseInt(actualYearString);
        info(String.format("Item%d mileage: Expected less than '%d', found '%d'",i,maxMileage, actualMileage));
        Assert.assertTrue(maxMileage>=actualMileage);
    }

    /**
     * Проверка результатов на странице на соответствие заданный критериям поиска
     * @param expectedManufacturer производитель
     * @param minPrice минимальная цена
     * @param maxPrice максимальная цена
     * @param minYear минимальнвый год производства
     * @param maxYear максимальнвый год производства
     * @param maxMileage максимальнвый пробег
     */
    public void assertSearchResults(String expectedManufacturer, int minPrice, int maxPrice, int minYear, int maxYear, int maxMileage ){
        waitForJQuery();
        int j = countItemsOnPage();
        for (int i = 1; i <= j; i++) {
            assertManufacturer(i,expectedManufacturer);
            assertPriceDollars(i, minPrice, maxPrice);
            assertYear(i, minYear, maxYear);
            assertMileage(i, maxMileage);
        }
    }
}

