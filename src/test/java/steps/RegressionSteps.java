package steps;

import config.RequestBody;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import library.BaseTest;
import library.DataGroup;
import library.PropertiesReader;
import library.YamlReader;
import org.testng.Assert;

import java.io.FileNotFoundException;

import static io.restassured.RestAssured.given;

public class RegressionSteps extends BaseTest {

    private String file, scenario;
    private YamlReader yamlReader;
    private RequestBody requestBody = new RequestBody();
    Response response;
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @Given("Test {int} for {string}")
    public void test_for(int testNo, String testScenario) throws FileNotFoundException {
        String[] arrScenario = testScenario.split(":");
        file = arrScenario[0].trim();
        yamlReader = new YamlReader(file);
        DataGroup.initialTestData = yamlReader.getTestData();
        DataGroup.currentTestData = DataGroup.initialTestData.get(file).get(testNo);
        scenario = (String) DataGroup.currentTestData.get("scenario");
    }

    @Then("The Client calls {string} as well as {string} and sends request with body")
    public void the_client_sends_request_with_body(String applicationName, String apiPath) {
        String body = null;
        try {
            yamlReader = new YamlReader(applicationName);
            DataGroup.defaultTestData = yamlReader.getDefaultData().get(applicationName);
            DataGroup.finalTestData = yamlReader.getFinalTestData(DataGroup.defaultTestData, DataGroup.currentTestData);
            body = requestBody.setRequestBody(apiPath, DataGroup.finalTestData);
            System.out.println(DataGroup.finalTestData);
            requestSpecification = new RequestSpecBuilder().setBaseUri(PropertiesReader.readConfigFile("BaseURI")).setContentType(ContentType.JSON).build();
            responseSpecification = new ResponseSpecBuilder().expectContentType(ContentType.JSON).build();
            response = given().log().all().spec(requestSpecification).body(body).when().post(PropertiesReader.readConfigFile("Resource")).then().log().all().spec(responseSpecification).extract().response();
            System.out.println("The response is" +response);
           // response = given().spec(requestSpecification).contentType("application/json").body(body).log().body()
                  //  .when().post(PropertiesReader.readConfigFile("Resource")).then().log().body().extract().response();
        } catch (Exception e) {
            e.printStackTrace();
            Assert.assertTrue(false);
        }
    }

    @And("The Client validates response code as {string}")
    public void the_client_validates_response(String responseCode) {
        Integer actualStatusCode = null;
        Integer expectedStatusCode = (Integer) DataGroup.finalTestData.get(responseCode);
        try {
            actualStatusCode = response.then().extract().statusCode();
            System.out.println("Hello");
            System.out.println(actualStatusCode);

            if (!actualStatusCode.equals(expectedStatusCode)) {
                Assert.assertTrue(false, "The actual code does not match");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @And("The Client validates {string} with value {string} in response body")
    public void the_client_validates_with_value(String expectedField, String expectedValue) {

        String actualResult = null;
        try {
            expectedField = String.valueOf(DataGroup.finalTestData.get(expectedField));
            expectedValue = String.valueOf(DataGroup.finalTestData.get(expectedValue));
            // actualResult = String.valueOf(response.then().assertThat().body(expectedField,equalTo(expectedValue)));
            actualResult = response.jsonPath().get(expectedField).toString();
            System.out.println(actualResult);
            System.out.println(expectedValue);
            if(!String.valueOf(expectedValue).equalsIgnoreCase(actualResult)){
                Assert.assertTrue(false,"The expected value does not match with actual result");}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
