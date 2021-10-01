package api;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ApiTest {

    private String baseUrl = "https://restful-booker.herokuapp.com/booking";


    private String createBookingPayload(User user) {
        String payload = "{\n" +
                "    \"firstname\" : \"" + user.firstName + "\",\n" +
                "    \"lastname\" : \"" + user.lastName + "\",\n" +
                "    \"totalprice\" : 111,\n" +
                "    \"depositpaid\" : true,\n" +
                "    \"bookingdates\" : {\n" +
                "        \"checkin\" : \"2018-01-01\",\n" +
                "        \"checkout\" : \"2019-01-01\"\n" +
                "    },\n" +
                "    \"additionalneeds\" : \"Breakfast\"\n" +
                "}";
        return payload;

    }

    enum User {
        John("John", "Macintosh"),
        DeeJay("Dee", "Jay"),
        BloomyReachson("Bloomy", "Reachson");

        public String firstName;
        public String lastName;

        User(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

    }

    private int createBookings(User user) {
        return given().body(createBookingPayload(user))
                .contentType(ContentType.JSON)
                .when().post(baseUrl)
                .then().assertThat().statusCode(equalTo(200))
                .extract().body().jsonPath().get("bookingid");
    }

    private String createAuthToken() {
        return
                given()
                        .body("{\n" +
                                "    \"username\" : \"admin\",\n" +
                                "    \"password\" : \"password123\"\n" +
                                "}")
                        .contentType(ContentType.JSON)
                        .when().post("https://restful-booker.herokuapp.com/auth")
                        .then().extract().body().jsonPath().get("token");
    }

    @Test
    public void test_patch_update_user_lastname() {
        User user = User.DeeJay;
        int bookingId = createBookings(user);
        String lastName = "Jayson";

        //given
        get(baseUrl + "/" + bookingId)
                .then().body("lastname", equalTo(user.lastName));

        //when updated the lastname
        given().body("{\"lastname\": \"" + lastName + "\"}")
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + createAuthToken())
                .when().patch(baseUrl + "/" + bookingId)
                .then().statusCode(equalTo(200));

        // then it should be updated
        get(baseUrl + "/" + bookingId)
                .then().body("lastname", equalTo(lastName));
    }

    @Test
    public void test_patch_update_booking_dates_and_verify_price_change() throws ParseException {
        User user = User.BloomyReachson;
        int bookingId = createBookings(user);

        //given
        JsonPath json = get(baseUrl + "/" + bookingId)
                .then().extract().body().jsonPath();
        String checkoutDate = json.get("bookingdates.checkout");
        int oldPrice = json.get("totalprice");

        SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
        Instant updatedDate = formatter.parse(checkoutDate).toInstant().plus(7, ChronoUnit.DAYS);
        //when update the checkout date
        int updatedTotalPrice = given().body("{\"totalprice\":" + (oldPrice + 1000) + ",\"bookingdates.checkout\": \"" + formatter.format(new Date(updatedDate.toEpochMilli())) + "\"}")
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + createAuthToken())
                .when().patch(baseUrl + "/" + bookingId)
                .then().statusCode(equalTo(200))
                .extract().body().jsonPath().get("totalprice");

        //then price should be updated correctly
        Assert.assertEquals(1000, updatedTotalPrice - oldPrice);
    }

    @Test
    public void test_delete_booking_for_a_user() throws ParseException {
        User user = User.John;

        //given
        int bookingId = createBookings(user);

        //when the booking is deleted
        given()
                .contentType(ContentType.JSON)
                .header("Cookie", "token=" + createAuthToken())
                .when().delete(baseUrl + "/" + bookingId)
                .then().statusCode(equalTo(201));


        //then the booking is not available any more
        get(baseUrl + "/" + bookingId)
                .then().statusCode(equalTo(404));
    }
}
