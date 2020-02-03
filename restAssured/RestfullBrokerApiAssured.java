package pl.com.aay.restAssured;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import java.util.HashMap;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static io.restassured.RestAssured.get;

public class RestfullBrokerApiAssured {

    private static final String AUTHORISATIONURL = "https://restful-booker.herokuapp.com/auth";
    private static  String BOOKINGURL = "https://restful-booker.herokuapp.com/booking";

    public RestfullBrokerApiAssured (){

    }

    public String getAuthenticationToken () {

        JSONObject json = new JSONObject();
        json.put("username", "admin");
        json.put("password", "password123");

        String token="Not authorized";
        String responseJsonString = null;
        try {
            Response response = given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .body(json.toString())
                    .when()
                    .post(AUTHORISATIONURL);
            responseJsonString=response.asString();
        }
        catch (Exception ex) {

        }
        JSONObject jsonResponse = new JSONObject(responseJsonString);
        token=jsonResponse.get("token").toString();

        return token;
    }

    public String getBookingById(int id,String token){
        String responsJsonString = null;

        try {
            BOOKINGURL=BOOKINGURL+"/"+ id;
            System.out.println(BOOKINGURL);
            Response response = get(BOOKINGURL);
            responsJsonString = response.asString();
        }
        catch (Exception ex) {

        }

        return responsJsonString;
    }

    public String getBookingById2(int id,String token){
        String responsJsonString = null;

        try {
            BOOKINGURL=BOOKINGURL+"/"+ id;
            System.out.println(BOOKINGURL);
            Response response = given()
                    .header("Accept", "application/json")
                    .when()
                    .get(BOOKINGURL);
        }
        catch (Exception ex) {

        }

        return responsJsonString;
    }

    public String getBookingByName(String firstName,String lastName,String token){
        String responsJsonString = null;

        try {
            BOOKINGURL=BOOKINGURL+ "?firstname="+firstName+"&lastname="+lastName;
            System.out.println(BOOKINGURL);
            Response response = get(BOOKINGURL);
            responsJsonString = response.asString();
        }
        catch (Exception ex) {

        }
        return responsJsonString;
    }

    public String getBookingByName2(String firstName,String lastName,String token){
        String responsJsonString = null;

        try {
            BOOKINGURL=BOOKINGURL+ "?firstname="+firstName+"&lastname="+lastName;
            System.out.println(BOOKINGURL);

            Response response = given()
                    .header("Accept", "application/json")
                    .when()
                    .get(BOOKINGURL);
            responsJsonString = response.asString();
        }
        catch (Exception ex) {

        }
        return responsJsonString;
    }

    public String createBooking(String token){
        JSONObject json = new JSONObject();
        json.put("firstname", "Ji");
        json.put("lastname", "Bro");
        json.put("totalprice", 121);
        json.put("depositpaid", true);
        HashMap<String, String> dateMap = new HashMap<>();
        dateMap.put("checkin","2019-02-01");
        dateMap.put("checkout","2020-02-01");
        json.put("bookingdates",dateMap);

        String responseString="";
        try {
            Response response = given()
                    .header("content-type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "token="+token)
                    .body(json.toString())
                    .when()
                    .post(BOOKINGURL);
            responseString=response.asString();
        }
        catch (Exception ex) {

        }

        return responseString;
    }

    public String updateBooking(int id, String token){
        JSONObject json = new JSONObject();
        json.put("firstname", "Jimmmy");
        json.put("lastname", "Brownnny");
        json.put("totalprice", 111);
        json.put("depositpaid", true);
        HashMap <String, String> dateMap = new HashMap<String, String>();
        dateMap.put("checkin","2019-01-01");
        dateMap.put("checkout","2020-01-01");
        json.put("bookingdates",dateMap);

        String responseString="";
        try {
            Response response = given()
                    .header("content-type", "application/json")
                    .header("Accept", "application/json")
                    .header("Cookie", "token="+token)
                    .body(json.toString())
                    .when()
                    .put(BOOKINGURL+"/"+id);
            responseString=response.asString();
        }
        catch (Exception ex) {

        }
        return responseString;
    }

    // Należy skorzystać z obiektu .patch();
    public String updateBookingPatch(String id,String token) {
        String responseJsonString = null;
        JSONObject json = new JSONObject();
        json.put("totalprice", "222");

        try {
            BOOKINGURL = BOOKINGURL + "/" + id;

            Response response = given()
                    .header("content-type", "application/json")
                    .header("accept", "application/json")
                    .header("cookie", "token =" + token)
                    .body(json.toString())
                    .when()
                    .patch(BOOKINGURL);
            responseJsonString = response.asString();
        } catch (Exception x) { x.printStackTrace(); }
        return responseJsonString;
    }

    public String deleteBooking(String id,String token){
        String response = "null";

            JSONObject json = new JSONObject();
            json.put("firstname", "Jimmmy");
            json.put("lastname", "Brownnny");
            json.put("totalprice", 111);
            json.put("depositpaid", true);
            HashMap <String, String> dateMap = new HashMap<String, String>();
            dateMap.put("checkin","2019-01-01");
            dateMap.put("checkout","2020-01-01");
            json.put("bookingdates",dateMap);

            try{
                BOOKINGURL = BOOKINGURL + "/" + id;

                Response resp = given()
                                .header("content-type","application/json")
                                .header("accept","application/json")
                                .header("cookie","token="+ token)
                                .body(json.toString())
                                .delete(BOOKINGURL);
                response = resp.asString();
            }

            catch(Exception e){e.printStackTrace();
        }
            return response;
    }

}

