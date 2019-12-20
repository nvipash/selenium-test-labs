import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleHerokuWebPagesTest {
    private static final String GOOGLE_URL = "https://www.google.com";
    private static final String HEROKU_APP_URL = "http://the-internet.herokuapp.com";
    private static final String WEBDRIVER_KEY = "webdriver.gecko.driver";
    private static final String WEBDRIVER = "geckodriver.exe";
    private static WebDriver browser;

    @BeforeClass
    public static void openBrowser() {
        System.setProperty(WEBDRIVER_KEY, WEBDRIVER);
        browser = new FirefoxDriver();
    }

    @Test
    public void search_and_check_asserts_text() {
        final String INPUT_ATTRIBUTE_TO_CHECK = "value";
        final String INPUT_NAME_ATTRIBUTE = "q";

        // Opens “www.google.com” page
        browser.get(GOOGLE_URL);

        // Search’s “Selenium 4” string
        String textToFind = "Selenium 4";
        WebElement searchInput = browser.findElement(new By.ByName(INPUT_NAME_ATTRIBUTE));
        searchInput.sendKeys(textToFind);
        searchInput.sendKeys(Keys.RETURN);

        // Asserts that title contains “Selenium 4” string
        Assert.assertEquals(textToFind, searchInput.getAttribute(INPUT_ATTRIBUTE_TO_CHECK));
    }

    @Test
    public void form_auth_and_log_out() {
        final String LOGIN_LINK_TEXT = "Form Authentication";
        final String INPUT_USERNAME_NAME_ATTRIBUTE = "username";
        final String INPUT_PASSWORD_NAME_ATTRIBUTE = "password";
        final String SECURE_AREA_HTML_ELEMENT = "h2";
        final String LOGIN_CLASS_ATTRIBUTE = "radius";

        // Opens “http://the-internet.herokuapp.com” page
        browser.get(HEROKU_APP_URL);

        // Clicks on Form Authentication
        WebElement formAuthLink = browser.findElement(By.linkText(LOGIN_LINK_TEXT));
        formAuthLink.click();

        // Log in using credentials provided on the page
        final String USERNAME_TO_INPUT = "tomsmith";
        WebElement usernameInput = browser.findElement(new By.ByName(INPUT_USERNAME_NAME_ATTRIBUTE));
        usernameInput.sendKeys(USERNAME_TO_INPUT);
        final String PASSWORD_TO_INPUT = "SuperSecretPassword!";
        WebElement passwordInput = browser.findElement(new By.ByName(INPUT_PASSWORD_NAME_ATTRIBUTE));
        passwordInput.sendKeys(PASSWORD_TO_INPUT);
        WebElement loginButton = browser.findElement(By.className(LOGIN_CLASS_ATTRIBUTE));
        loginButton.click();

        // Assert login to the Secure Area
        final String SECURE_AREA_TITLE = "Secure Area";
        WebElement secureAreaText = browser.findElement(By.cssSelector(SECURE_AREA_HTML_ELEMENT));
        Assert.assertEquals(SECURE_AREA_TITLE, secureAreaText.getText());

        // Log out
        WebElement logoutButton = browser.findElement(By.className(LOGIN_CLASS_ATTRIBUTE));
        logoutButton.sendKeys(Keys.RETURN);
    }

    @Test
    public void file_choose_and_upload() {
        final String UPLOAD_LINK_TEXT = "File Upload";
        final String UPLOAD_CLASS_ATTRIBUTE = "button";
        final String CHOOSE_FILE_ID_ATTRIBUTE = "file-upload";
        final String UPLOADED_FILES_ID_ATTRIBUTE = "uploaded-files";

        // Opens “http://the-internet.herokuapp.com/” page
        browser.get(HEROKU_APP_URL);

        // Clicks on File Upload
        WebElement uploadFileLink = browser.findElement(By.linkText(UPLOAD_LINK_TEXT));
        uploadFileLink.click();

        // Choose file from your local env for upload
        String filenameToUpload = "test-file.png";
        String fileToUploadUrl = System.getProperty("user.dir") + "\\src\\res\\" + filenameToUpload;
        WebElement chooseFileButton = browser.findElement(By.id(CHOOSE_FILE_ID_ATTRIBUTE));
        chooseFileButton.sendKeys(fileToUploadUrl);

        // Clicks on Upload button
        WebElement uploadButton = browser.findElement(By.className(UPLOAD_CLASS_ATTRIBUTE));
        uploadButton.click();

        // Assert uploaded file name
        WebElement uploadedFileDiv = browser.findElement(By.id(UPLOADED_FILES_ID_ATTRIBUTE));
        String uploadedFileText = uploadedFileDiv.getText();
        Assert.assertEquals(filenameToUpload, uploadedFileText);
    }

    @AfterClass
    public static void closeBrowser() {
        browser.quit();
    }
}