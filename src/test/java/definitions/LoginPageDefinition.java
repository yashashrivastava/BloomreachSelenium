package definitions;

import Util.TestContext;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pages.LoginPage;

import java.util.concurrent.TimeUnit;

public class LoginPageDefinition {

    private LoginPage loginPOMpage;
    private TestContext context;

    public LoginPageDefinition(TestContext testContext){
        this.context = testContext;
        this.loginPOMpage = new LoginPage(context.getDriver());
    }


    @Given("^User is on cms login page$")
    public void user_is_on_cms_login_page(){
        context.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        context.getDriver().get(context.getProp().getProperty("url"));
        context.getDriver().manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
    }


    @Then("Verify that user is successfully logged in as Author")
    public void Verify_that_user_is_successfully_logged_in_as_Author(){
        loginPOMpage.login(context.getProp().getProperty("authorUerName"), context.getProp().getProperty("authorPassword"));
    }

    @And("^Logout and quit the Browser$")
    public void logout_and_quit_the_Browser() throws InterruptedException {
        context.logOut();
        context.quitBrowser();
    }
}
