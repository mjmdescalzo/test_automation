package com.example.www.step_definitions;

import com.example.www.pages.restful_booker.RestfulBookerHomePage;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;
import org.picocontainer.annotations.Inject;

public class RestfulBookerStepDefs {

//    @Inject
    private World world;

//    @Inject
    RestfulBookerHomePage restfulBookerHomePage;

    public RestfulBookerStepDefs(World world, RestfulBookerHomePage restfulBookerHomePage) {
        this.world = world;
        this.restfulBookerHomePage = restfulBookerHomePage;
    }

    @Given("^that I am in the restful booker site$")
    public void thatIAmInTheRestfulBookerSite() {
//        restfulBookerHomePage = new RestfulBookerHomePage(world.driver).get();
        restfulBookerHomePage.get();
    }

    @Then("the Header banner image is displayed")
    public void theHeaderBannerImageIsDisplayed() {
        restfulBookerHomePage.clickBookThisRoomBtn();
    }


    @And("I fail the test")
    public void iFailTheTest() {
        Assert.assertEquals("pass", "fail");
    }
}
