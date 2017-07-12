package tests;

import UI.forms.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;
import java.util.List;

public class CompareItemsTest extends BaseTest {

    private String mainMenuTab ;
    private String category ;
    private String section ;
    private String subsection;

    @Test
    @Parameters({"mainMenuTab", "category", "section", "subsection"})
    public void readParams(String mainMenuTab, String category, String section, String subsection) throws Throwable{
        this.mainMenuTab = mainMenuTab;
        this.category = category;
        this.section = section;
        this.subsection = subsection;
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
        mainForm.getMainMenu().openTab(mainMenuTab);
        CatalogForm catalogForm = new CatalogForm();
        catalogForm.openSubMenu(category);
        catalogForm.openSection(section);
        catalogForm.openSubSection(subsection);

        SectionForm sectionForm = new SectionForm();

        logger.step(3);
        //sectionForm.clearComparisonList();
        sectionForm.addAllItemsToComparisonList();
        List<String> allItemsNames= sectionForm.getAllItemsNames();

        logger.step(4);
        sectionForm.goToComparisonForm();
        ComparisonForm comparisonForm = new ComparisonForm();

        logger.step(5);
        comparisonForm.assertItemsNames(allItemsNames);

        logger.step(6);
        comparisonForm.clearComparison();
        CatalogForm catalogFormNew= new CatalogForm();
    }
}


