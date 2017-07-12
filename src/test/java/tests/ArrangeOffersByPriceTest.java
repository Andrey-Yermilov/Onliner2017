package tests;

import UI.forms.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;


public class ArrangeOffersByPriceTest extends BaseTest {

    private String searchRequest;
    private String city;

    @Test
    @Parameters({"searchRequest","city"})
    public void readParams(String searchRequest, String city) throws Throwable{
        this.searchRequest = searchRequest;
        this.city = city;
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
        mainForm.performFastSearch(searchRequest);

        logger.step(3);
        mainForm.viewOffers();
        ItemForm itemForm = new ItemForm();

         logger.step(4);
        itemForm.arrangeByPrice();
        itemForm.assertArrangeByPriceASC(city);

        logger.step(5);
        itemForm.arrangeByPrice();
        itemForm.assertArrangeByPriceDESC(city);
    }
}
