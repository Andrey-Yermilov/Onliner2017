package UI.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.Label;
import webdriver.elements.TextBox;
import java.util.Random;

/**
 * Форма редактирования профиля пользователя
 */
public class EditProfileForm extends BaseForm {
    private static final String formLocator = "//ul[@class='b-utabs']";
    private String targetlinkTemplate = "//a[contains(@href,'%s')]";

    public EditProfileForm() {
        super(By.xpath(formLocator), "Edit user's profile");
    }

    /**
     * Открыть вкладку с типом радактируемых данных
     * @param menuLink название открываемой вкладки
     */
    public void openTab(String menuLink) {
        Label lblMenuLink = new Label(By.xpath(String.format(targetlinkTemplate, menuLink)),String.format("%s tab", menuLink));
        lblMenuLink.click();
    }

    /**
     * Генерировать новый пароль
     * @param currentPassword текущий пароль
     * @return новый пароль
     */
    public String generateNewPassword(String currentPassword){
        Random random = new Random();
        int newDigitalPart =random.nextInt(1000000)+1;
        String newDigitalPartString = String.format("%06d",newDigitalPart);
        String lettersPart = currentPassword.replaceAll("[0-9]", "");
        return lettersPart+newDigitalPartString;
    }

    /**
     * Сменить пароль пользователя
     * @param currentPassword текущий пароль
     * @param newPassword новый пароль
     */
    public void changePassword(String currentPassword, String newPassword){
        String txbTemplate = "//input[@name = '%s']";
        TextBox txbCurrentPassword = new TextBox(By.xpath(String.format(txbTemplate,"old_password")), "Current password");
        TextBox txbNewPassword = new TextBox(By.xpath(String.format(txbTemplate,"password")), "New password");
        TextBox txbRepeatNewPassword = new TextBox(By.xpath(String.format(txbTemplate,"password_confirm")), "Confirm new password");
        Button btnChangePassword = new Button(By.xpath("//span[contains(text(),'Изменить пароль')]"), "Change password");
        txbCurrentPassword.type(currentPassword);
        txbNewPassword.type(newPassword);
        txbRepeatNewPassword.type(newPassword);
        btnChangePassword.click();
    }
}
