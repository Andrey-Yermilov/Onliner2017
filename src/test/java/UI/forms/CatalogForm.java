package UI.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.Translit;
import webdriver.elements.Label;

/**
 * Форма каталога
 */
public class CatalogForm extends BaseForm {
    private static final String formLocator = "//div[@class='catalog-navigation']//h1[contains(text(),'Каталог')]";
    private String categoryTemplate = "//span[contains(text(),'%s')]";
    private String sectionTemplate = "//div[@class='catalog-navigation-list__aside-title' and contains(text(),'%s')]";
    private String subsectionTemplate = "//span[@class = 'catalog-navigation-list__dropdown-data']//span[contains(text(),'%s')]";

    private Label lblCategory;
    private Label lblSection;
    private Label lblSubSection;

    public CatalogForm() {
        super(By.xpath(formLocator), "Catalog Onliner");
    }

    /**
     * Открыть субменю
     * @param category категория субменю
     */
    public void openSubMenu (String category){
        String categoryEn = Translit.toTranslit(category);
        lblCategory = new Label(By.xpath(String.format(categoryTemplate, category)),String.format("%s", categoryEn));
        lblCategory.click();
    }

    /**
     * Перейти в конкретный раздел
     * @param subsection раздел
     */
    public void openSubSection (String subsection){
        String subsectionEn = Translit.toTranslit(subsection);
        lblSection = new Label(By.xpath(String.format(subsectionTemplate, subsection)),String.format("%s", subsectionEn));
        lblSection.click();
    }
    /**
     * Перейти в конкретный раздел
     * @param section раздел
     */
    public void openSection (String section){
        String sectionEn = Translit.toTranslit(section);
        lblSubSection = new Label(By.xpath(String.format(sectionTemplate, section)),String.format("%s", sectionEn));
        lblSubSection.click();
    }
}

