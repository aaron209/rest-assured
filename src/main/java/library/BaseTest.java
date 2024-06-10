package library;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import java.io.IOException;

public class BaseTest {

    public static   RequestSpecification requestSpecification;
    //protected static ResponseSpecification responseSpecification;

    @BeforeSuite
    public void setBaseURI() throws IOException {
        requestSpecification = new RequestSpecBuilder().setBaseUri(PropertiesReader.readConfigFile("BaseURI")).build();
    }
    @AfterTest
    public void afterTest(){

    }
}
