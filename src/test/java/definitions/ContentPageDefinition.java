package definitions;

import Util.TestContext;
import io.cucumber.java.en.Then;
import pages.ContentPage;
import pages.LoginPage;


public class ContentPageDefinition {

    private ContentPage contentPOMpage;
    private LoginPage loginPOMpage;
    private TestContext context;

    public ContentPageDefinition(TestContext testContext) {
        this.context = testContext;
        this.loginPOMpage = new LoginPage(context.getDriver());
        this.contentPOMpage = new ContentPage(context.getDriver());
    }

    @Then("Verify that Author successfully creates a folder if doesn't exist already")
    public void Verify_that_Author_successfully_creates_a_folder_if_doesnt_exists_already(){
        contentPOMpage.createFolder();
    }

    @Then("Verify that Author successfully creates a document")
    public void Verify_that_Author_successfully_creates_a_document() throws InterruptedException {
        contentPOMpage.createDocument();
    }
}
