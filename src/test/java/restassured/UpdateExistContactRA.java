package restassured;

import dto.ContactDTO;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class UpdateExistContactRA {

    String token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoibWFyZ29AZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3NTE4MTkxNzYsImlhdCI6MTc1MTIxOTE3Nn0.Ae5hLaWG44f_7DuCHwjT_CZef-a8QIO4y--IxCd1g2U";
    String id;

    int i = new Random().nextInt(1000) + 1000;
    ContactDTO contactDTO = ContactDTO.builder()
            .name("Donna")
            .lastName("Doww")
            .email("donna" + i + "@gmail.com")
            .phone("12365956" + i)
            .address("Haifa")
            .description("The best")
            .build();

    @BeforeMethod
    public void preCondition() {
        RestAssured.baseURI = "https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath = "v1";



        String message = given()
                .body(contactDTO)
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .post("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().path("message");
        String[] all = message.split(": ");
        id = all[1];
    }


    @Test
    public void updateExistContactSuccess(){
        String name = contactDTO.getName();
        contactDTO.setId(id);
        contactDTO.setName("wwwww");

        given()
                .body(contactDTO)
                .header("Authorization",token)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message",containsString("Contact was updated"));
    }
}
