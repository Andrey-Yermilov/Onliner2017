package tests;

import UI.forms.EditProfileForm;
import UI.forms.LoginForm;
import UI.forms.MainForm;
import UI.forms.ProfileForm;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;

import java.io.FileInputStream;

public class ChangePasswordTest extends BaseTest {

    private String currentPassword;
    private String newPassword;
    private String username;
    private String typeOfLogin;
    private String typeOfUserProfileTab;

    @Test
    @Parameters({"username", "password", "typeOfUserProfileTab", "typeOfLogin"})
    public void readParams(String username, String password, String typeOfUserProfileTab, String typeOfLogin) throws Throwable {
        this.username = username;
        this.currentPassword = password;
        this.typeOfUserProfileTab = typeOfUserProfileTab;
        this.typeOfLogin = typeOfLogin;
        xTest();
    }

    @Test(enabled = false)
    public void xTest() throws Throwable {
        super.xTest();
    }

    public void runTest() {
        logger.step(1);
        MainForm mainForm = new MainForm();

        logger.step(2);
        mainForm.getLoginMenu().openLoginForm(typeOfLogin);
        LoginForm loginForm = new LoginForm();
        loginForm.login(username, currentPassword);
        mainForm.assertLoggedIn(username);

        logger.step(3);
        mainForm.goToProfileForm();
        ProfileForm profileForm = new ProfileForm();
        profileForm.openEditProfileForm();
        EditProfileForm editProfileForm = new EditProfileForm();
        editProfileForm.openTab(typeOfUserProfileTab);

        logger.step(4);
        newPassword = editProfileForm.generateNewPassword(currentPassword);
        editProfileForm.changePassword(currentPassword, newPassword);
        mainForm.exit();
        LoginForm loginFormAfterLoginOut = new LoginForm();

        logger.step(5);
        loginFormAfterLoginOut.login(username, newPassword);
        mainForm.assertLoggedIn(username);

        logger.step(6);
        mainForm.goToProfileForm();
        profileForm.openEditProfileForm();
        editProfileForm.openTab(typeOfUserProfileTab);
        editProfileForm.changePassword(newPassword, currentPassword);
        mainForm.exit();
    }
}


