package tests;

import UI.forms.*;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import webdriver.BaseTest;

import java.io.FileInputStream;

public class AddToBookmarksTest extends BaseTest {

    private String typeOfLogin;
    private String username;
    private String password;
    private String bookmarksTab;
    private String mainMenuTab;
    private String category;
    private String section;
    private String subsection;
    private int itemNumber = 1;

    @Test
    @Parameters({"typeOfLogin", "username", "password", "bookmarksTab", "mainMenuTab", "category", "section", "subsection"})
    public void readParams(String typeOfLogin, String username, String password, String bookmarksTab, String mainMenuTab,
                           String category, String section, String subsection) throws Throwable {
        this.typeOfLogin = typeOfLogin;
        this.username = username;
        this.password = password;
        this.bookmarksTab = bookmarksTab;
        this.mainMenuTab = mainMenuTab;
        this.category = category;
        this.section = section;
        this.subsection = subsection;
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
        loginForm.login(username, password);
        mainForm.assertLoggedIn(username);

        logger.step(3);
        mainForm.goToBookmarks();
        BookmarksForm bookmarksFormInitial = new BookmarksForm();
        bookmarksFormInitial.getBookmarksTypesMenu().openTab(bookmarksTab);
        bookmarksFormInitial.emptyBookmarks();

        logger.step(4);
        mainForm.getMainMenu().openTab(mainMenuTab);
        CatalogForm catalogForm = new CatalogForm();
        catalogForm.openSubMenu(category);
        catalogForm.openSection(section);
        catalogForm.openSubSection(subsection);
        SectionForm sectionForm = new SectionForm();

        logger.step(5);
        sectionForm.goToItemForm(itemNumber);
        ItemForm itemForm = new ItemForm();
        String actualModel = itemForm.getFullItemName();

        logger.step(6);
        itemForm.addToBookmarks();
        itemForm.assertInBookmarks();

        logger.step(7);
        mainForm.goToBookmarks();
        BookmarksForm bookmarksForm = new BookmarksForm();
        bookmarksForm.getBookmarksTypesMenu().openTab(bookmarksTab);
        bookmarksForm.assertBookmarkInCatalog(itemNumber, actualModel);

        logger.step(8);
        bookmarksForm.emptyBookmarks();
    }
}


