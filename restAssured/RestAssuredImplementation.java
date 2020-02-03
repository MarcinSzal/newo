package pl.com.aay.restAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;

public class RestAssuredImplementation {

    private static final String AUTHORISATIONURL = "https://restful-booker.herokuapp.com/auth";
    private static String BOOKINGURL = "https://restful-booker.herokuapp.com/booking";

    public RestAssuredImplementation() {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
    }

    public String getToken() {
        JSONObject json = new JSONObject();
        json.put("username", "admin");
        json.put("password", "password123");
        String responseStatus = null;
        String token = null;

        try {
            Response response = given()
                    .contentType("application/json")
                    .accept(ContentType.JSON)
                    .body(json.toString())
                    .when()
                    .post(AUTHORISATIONURL);
            token = response.asString();
            responseStatus = response.getStatusLine();
            JSONObject obj = new JSONObject(token);
            token = obj.get("token").toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token + " ---- " + responseStatus;
    }

    public String createReservation(String token) {
        String status = null;
        String responseD = null;
        JSONObject obj = new JSONObject();
        obj.put("firstName", "Marcin");
        obj.put("lastname", "Bosko");
        obj.put("totalprice", 222);
        obj.put("depositepaid", false);
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("checkin", "2013-02-23");
        dataMap.put("checkout", "2014-10-23");
        obj.put("bookingdates", dataMap);
        obj.put("additionalneeds", "Lunch");

        try {
            Response response = given()
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("cookie", "token=" + token)
                    .body(obj.toString())
                    .when()
                    .post(BOOKINGURL);
            responseD = response.asString();                                          // analogicznie jak ponizej
            status = response.getStatusLine();
        } catch (Throwable d) {
            d.getStackTrace();
        }

        return responseD + "---" + status;                                                      // błąd serwera w tescie
    }

    public String getReservation(int id) {
        String stringResponse = null;
        String status = null;

        try {
            Response response = get(BOOKINGURL + "/" + id);
            stringResponse = response.getBody().asString();                                    // jaka jest różnica pomiedzy toString() i asString()?
            status = response.getStatusLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse + "----" + status;
    }

    public String getReservationId() {
        String stringResponse = null;
        String status = null;
        try {
            Response response = get(BOOKINGURL);
            stringResponse = response.getBody().asString();
            status = response.getStatusLine();
        } catch (Throwable x) {
            x.getStackTrace();
        }
        return stringResponse + " ----  " + status;
    }

    public String getReservationDates(int id) {
        String stringResponse = "";
        String status = "";
        try {
            Response response = get(BOOKINGURL + "/" + id);
            stringResponse = response.getBody().asString();
            status = response.getStatusLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject obj = new JSONObject(stringResponse);

        return obj.get("bookingdates").toString() + "  " + status;           // pobiera daty danej rezerwacji
    }

    public String patchReservation(String token, int id) {
        String stringResponse = null;
        String status = null;
        JSONObject json = new JSONObject();
        Map<String,String> map = new HashMap<>();
        map.put("date", "28-03-2020");
        map.put("date2", "29-03-2020");
        json.put("bookingdates", map);

        try {
            Response response = given()
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("cookie", token)
                    .body(json.toString())
                    .patch(BOOKINGURL + "/" + id);

            stringResponse = response.getBody().asString();
            status = response.getStatusLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse + " " + status;          // 403 forbidden
    }

    public String deleteReservation(String token, int id) {
        String stringResponse = "";
        String status = "";
        try {
            Response resp = given()
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("cookie", token)                                            // brak body() - czy przy delete powinno byc?
                    .when()
                    .delete(BOOKINGURL + "/" + id);

            stringResponse = resp.getBody().asString();
            status = resp.getStatusLine();
        } catch (Throwable y) {
            y.printStackTrace();
        }
        return status + " "+stringResponse;
    }
}
