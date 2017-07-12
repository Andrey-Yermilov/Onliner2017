package UI.forms;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.ComboBox;
import webdriver.elements.Label;
import webdriver.elements.TextBox;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Форма с курсами валют
 */
public class CurrencyForm extends BaseForm {
    private static final String formLocator = "//h1[contains(text(),'Лучшие курсы валют')]";
    private Button btnExchangeMethod;
    private TextBox txbAmountOfMoney = new TextBox(By.xpath("//div[@class='amount-i']/input"),"Amount of money");
    private ComboBox cbbFromCurrency = new ComboBox(By.xpath("//select[@name='currency-in']"), "From currency");
    private ComboBox cbbToCurrency = new ComboBox(By.xpath("//select[@name='currency-out']"), "To currency");;
    private String exchangeMethodTemplate = "//label[contains(text(),'%s')]";
    private Label lblRate;

    public CurrencyForm() {
        super(By.xpath(formLocator), "Currencies, Onliner");
    }

    /**
     * Задать сумму и валюты для обмена
     * @param amountOfMoney сумма
     * @param fromCurrency обмен из какой валюты
     * @param toCurrency обмен в какую валюту
     * @param exchangeMethod метод обмена (продать/купить)
     */
    public void setValuesForExchange(int amountOfMoney, String fromCurrency, String toCurrency, String exchangeMethod){
        setExchangeMethod(exchangeMethod);
        txbAmountOfMoney.setText(Integer.toString(amountOfMoney));
        setFromCurrency(fromCurrency);
        setToCurrency(toCurrency);
    }

    /**
     * Задать, из какой валюты должен осуществляться обмен
     * @param fromCurrency обмен из какой валюты
     */
    private void setFromCurrency (String fromCurrency){
        cbbFromCurrency.click();
        cbbFromCurrency.chooseValue(fromCurrency);
    }

    /**
     * Задать, в какую валюту должен осуществляться обмен
     * @param toCurrency обмен в какую валюту
     */
    private void setToCurrency (String toCurrency){
        cbbToCurrency.click();
        cbbToCurrency.chooseValue(toCurrency);
    }

    /**
     * Задать метод обмена
     * @param exchangeMethod метод обмена (продать/купить)
     */
    private void setExchangeMethod (String exchangeMethod){
        if (exchangeMethod.equals("Продать")){
            btnExchangeMethod = new Button(By.xpath(String.format(exchangeMethodTemplate,exchangeMethod )),"Sell");
        }
        if (exchangeMethod.equals("Купить")){
            btnExchangeMethod = new Button(By.xpath(String.format(exchangeMethodTemplate,exchangeMethod)),"Buy");
        }
        btnExchangeMethod.click();
    }

    /**
     * Проверить, что выводимая на экран конечная сумма, корректна
     * @param amountOfMoney сумма
     * @param fromCurrency обмен из какой валюты
     * @param toCurrency обмен в какую валюту
     * @param exchangeMethod метод обмена (продать/купить)
     */
    public void assertExchangeValue(int amountOfMoney, String fromCurrency, String toCurrency, String exchangeMethod){
        Label lblActualValue = new Label(By.xpath("//b[@class='js-cur-result']"),"Actual value");
        String actualValueString = lblActualValue.getText();
        double actualValue = stringToDouble(actualValueString);
        String rateString =  chooseRate(fromCurrency,toCurrency,exchangeMethod);
        double actualRate = stringToDouble(rateString);
        if(fromCurrency.equals("RUB") || toCurrency.equals("RUB")){
            actualRate /=100;
        }
        double expectedValue = calculateExpectedValue(actualRate, amountOfMoney, fromCurrency, toCurrency);

        if (!(actualValueString.contains(","))){
            BigDecimal actualValueRounded = roundValue(actualValue,0);
            BigDecimal expectedValueRounded = roundValue(expectedValue,0);
            info(String.format("Expected %s %s, found %s %S", actualValueRounded.toPlainString(),toCurrency, expectedValueRounded.toPlainString(),toCurrency));
            Assert.assertEquals(actualValueRounded,expectedValueRounded);
        }
        else{
            BigDecimal actualValueRounded = roundValue(actualValue,4);
            BigDecimal expectedValueRounded = roundValue(expectedValue,4);
            info(String.format("Expected %s %s, found %s %S", actualValueRounded.toPlainString(),toCurrency, expectedValueRounded.toPlainString(),toCurrency));
            Assert.assertEquals(actualValueRounded,expectedValueRounded);
        }
    }

    /**
     * Округлить полученное значение
     * @param initialValue начальное значение
     * @param scale масштаб округления
     * @return округлённое значение
     */
    private BigDecimal roundValue(double initialValue, int scale){
        BigDecimal bigDecimal = new BigDecimal(initialValue);
        return bigDecimal.setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * Подсчёт ожидаемой неокруглённой суммы
     * @param rate курс
     * @param amountOfMoney сумма
     * @param fromCurrency обмен из какой валюты
     * @param toCurrency обмен в какую валюту
     * @return сумма
     */
    private double calculateExpectedValue(double rate, double amountOfMoney, String fromCurrency, String toCurrency){
        double expectedValue=0;

        if ((   fromCurrency.equals("USD") && toCurrency.equals("BYN"))||
                (fromCurrency.equals("EUR") && toCurrency.equals("BYN"))||
                (fromCurrency.equals("RUB") && toCurrency.equals("BYN"))||
                (fromCurrency.equals("EUR") && toCurrency.equals("USD"))||
                (fromCurrency.equals("EUR") && toCurrency.equals("RUB"))||
                (fromCurrency.equals("USD") && toCurrency.equals("RUB")))
        {
            expectedValue= amountOfMoney*rate;
        }


        else {expectedValue = amountOfMoney/rate;}
        return expectedValue;
    }

    /**
     * Перевод String значениея цены в Double
     * @param value значение
     * @return Double
     */
    private double stringToDouble(String value){
        value = value.replaceAll("\\s","").replaceAll(",",".");
        return Double.parseDouble(value);
    }

    /**
     * Выбрать необходимый курс пересчёта
     * @param fromCurrency обмен из какой валюты
     * @param toCurrency обмен в какую валюту
     * @param exchangeMethod метод обмена (продать/купить)
     * @return курс
     */
    private String chooseRate (String fromCurrency, String toCurrency, String exchangeMethod){
        String rateTemplate;
        if (exchangeMethod.equals("Продать")){
            if(fromCurrency.equals("BYN")){

                rateTemplate = "(//b[contains(text(),'%s')]/../../..//p[contains(@class,'value')]/b)[2]";
                if (toCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD" )),"Actual rate");
                }
                else if (toCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR" )),"Actual rate");
                }
                else if (toCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB" )),"Actual rate");
                }
            }
            else if (toCurrency.equals("BYN")){

                rateTemplate = "(//b[contains(text(),'%s')]/../../..//p[contains(@class,'value')]/b)[2]";
                if (fromCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD" )),"Actual rate");
                }
                else if (fromCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR" )),"Actual rate");
                }
                else if (fromCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB" )),"Actual rate");
                }
            }
            else {
                rateTemplate="(//b[contains (text(),'%s') and contains (text(),'%s')]/../../..//p[contains(@class,'value')]/b)[%d]";
                if (fromCurrency.equals("USD") && toCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD","EUR", 2)),"Actual rate");
                }
                else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR","USD", 1)),"Actual rate");
                }
                else if (fromCurrency.equals("RUB") && toCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB","EUR", 2)),"Actual rate");
                }
                else if (fromCurrency.equals("EUR") && toCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR","RUB", 1)),"Actual rate");
                }
                else if (fromCurrency.equals("RUB") && toCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB","USD", 2)),"Actual rate");
                }
                else  if (fromCurrency.equals("USD") && toCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD","RUB", 1)),"Actual rate");
                }
            }
        }

        else if (exchangeMethod.equals("Купить")){
            if(fromCurrency.equals("BYN")){
                rateTemplate = "(//b[contains(text(),'%s')]/../../..//p[contains(@class,'value')]/b)[1]";
                if (toCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD" )),"Actual rate");
                }
                else if (toCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR" )),"Actual rate");
                }
                else if (toCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB" )),"Actual rate");
                }
            }
            else if (toCurrency.equals("BYN")){
                rateTemplate = "(//b[contains(text(),'%s')]/../../..//p[contains(@class,'value')]/b)[2]";
                if (fromCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD" )),"Actual rate");
                }
                else if (fromCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR" )),"Actual rate");
                }
                else if (fromCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB" )),"Actual rate");
                }
            }
            else {
                rateTemplate="(//b[contains (text(),'%s') and contains (text(),'%s')]/../../..//p[contains(@class,'value')]/b)[%d]";
                if (fromCurrency.equals("USD") && toCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD","EUR", 1)),"Actual rate");
                }
                else if (fromCurrency.equals("EUR") && toCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR","USD", 2)),"Actual rate");
                }
                else if (fromCurrency.equals("RUB") && toCurrency.equals("EUR")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB","EUR", 1)),"Actual rate");
                }
                else if (fromCurrency.equals("EUR") && toCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"EUR","RUB", 2)),"Actual rate");
                }
                else if (fromCurrency.equals("RUB") && toCurrency.equals("USD")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"RUB","USD", 1)),"Actual rate");
                }
                else  if (fromCurrency.equals("USD") && toCurrency.equals("RUB")){
                    lblRate = new Label(By.xpath(String.format(rateTemplate,"USD","RUB", 2)),"Actual rate");
                }
            }
        }
        return lblRate.getText();

    }
}

