package tests;

import UI.forms.CurrencyForm;
import UI.forms.MainForm;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;

public class CurrencyExchangeTest extends BaseTest {

    private int amountOfMoney;
    private String fromCurrency;
    private String toCurrency;
    private String exchangeMethod;

    @Test
    @Parameters({ "amountOfMoney", "fromCurrency", "toCurrency", "exchangeMethod"})
    public void readParams(int amountOfMoney, String fromCurrency, String toCurrency, String exchangeMethod) throws Throwable{
        this.amountOfMoney = amountOfMoney;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.exchangeMethod = exchangeMethod;
        xTest();
    }

    @Test (enabled = false)
    public void xTest() throws Throwable {
        super.xTest();
    }

    public void runTest() {
        logger.step(1);
        MainForm mainForm = new MainForm();

        logger.step(2);
        mainForm.goToCurrencyForm();
        CurrencyForm currencyForm = new CurrencyForm();

        logger.step(3);
        currencyForm.setValuesForExchange(amountOfMoney, fromCurrency, toCurrency, exchangeMethod);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.step(4);
        currencyForm.assertExchangeValue(amountOfMoney, fromCurrency, toCurrency, exchangeMethod);
    }
}


