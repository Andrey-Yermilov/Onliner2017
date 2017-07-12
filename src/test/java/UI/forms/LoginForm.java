package UI.forms;

import org.openqa.selenium.By;
import webdriver.BaseForm;
import webdriver.elements.Button;
import webdriver.elements.TextBox;

/**
 * Форма логина пользователя
 */
public class LoginForm extends BaseForm{
    private static final String formLocator = "//div[@class='auth-box__inner auth-box__inner--form']";
    private Button btnEnter = new Button(By.xpath("//button[@type='submit' and contains(text(),'Войти')]"),"Enter");
    private TextBox txbLogin = new TextBox(By.xpath("//input[@type='text' and contains(@placeholder, 'Ник')]"), "Login");
    private TextBox txbPassword = new TextBox(By.xpath("//input[@type='password' and contains(@data-bind, 'login')]"), "Password");

    public LoginForm() {
        super(By.xpath(formLocator), "Login");
    }

    /**
     * Залогиниться
     * @param login логин пользователя
     * @param password пароль пользователя
     */
    public void login(String login, String password){
        txbLogin.type(login);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        txbPassword.type(password);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        btnEnter.click();
    }
}
