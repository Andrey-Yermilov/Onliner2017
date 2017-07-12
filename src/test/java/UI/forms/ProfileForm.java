package UI.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Label;

/**
 * Форма профиля пользователя
 */
public class ProfileForm extends  BaseForm {
    private static final String formLocator = "//div[@class='b-uprofile']";
    private Label lblEditUserData = new Label(By.xpath("//a[contains(text(),'Редактировать личные данные')]"),"Edit user's data");

    public ProfileForm() {
        super(By.xpath(formLocator), "Profile");
    }

    /**
     * Открыть форму редактирования профиля
     */
    public void openEditProfileForm(){
    lblEditUserData.click();
   }
}

