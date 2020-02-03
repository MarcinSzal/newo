package pl.com.aay.javaClient;

import io.restassured.response.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.awt.print.Book;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JavaClientImplementtion {

    private static final String AUTHORISATIONURL = "https://restful-booker.herokuapp.com/auth";
    private static String BOOKINGURL = "https://restful-booker.herokuapp.com/booking";


    private HttpClient httpClient;
    private HttpPost post;
    private HttpGet get;
    private HttpPut put;
    private HttpPatch httpPatch;
    private HttpDelete httpDelete;
    private HttpResponse response;

    public JavaClientImplementtion() {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
        httpClient = HttpClientBuilder.create().build();
    }

    public String getToken() {
        JSONObject json = new JSONObject();
        json.put("username", "admin");
        json.put("password", "password123");
        String stringResponse;
        String token = null;

        try {
            post = new HttpPost(AUTHORISATIONURL);
            post.addHeader("Content-type", "Application/json");
            post.addHeader("Accept", "Application/json");
            StringEntity entity = new StringEntity(json.toString());
            post.setEntity(entity);
            response = httpClient.execute(post);
            stringResponse = EntityUtils.toString(response.getEntity());

            JSONObject obj = new JSONObject(stringResponse);
            token = obj.get("token").toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    public String getBookingIds(String token) {
        String stringResponse = null;
        String statusLine = "";

        try {
            get = new HttpGet(BOOKINGURL);
            get.addHeader("accept", "application/json");
            get.addHeader("cookie", "token=" + token);
            response = httpClient.execute(get);
            stringResponse = EntityUtils.toString(response.getEntity());
            statusLine = response.getStatusLine().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringResponse + " !!! " + statusLine;
    }

    public String getReservationData(String token, int id) {
        String stringresponse = "";

        try {
            BOOKINGURL = BOOKINGURL + "/" + id;
            get = new HttpGet(BOOKINGURL);
            get.addHeader("accept", "application/json");
            get.addHeader("cookie", "token=" + token);
            response = httpClient.execute(get);
            stringresponse = EntityUtils.toString(response.getEntity());

        } catch (Throwable e) {
            e.getStackTrace();
        }
        JSONObject obo = new JSONObject(stringresponse);

        return obo.get("bookingdates").toString();
    }

    public String createReservation(String token) {
        String stringResponse = null;
        String statusLine = null;
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
            post = new HttpPost(BOOKINGURL);
            post.addHeader("content-type", "application/json");
            post.addHeader("accept", "application/json");
            post.addHeader("cookie", "token=" + token);
            StringEntity entity = new StringEntity(obj.toString());
            post.setEntity(entity);
            response = httpClient.execute(post);
            statusLine = response.getStatusLine().toString();
            stringResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse + " " + statusLine;
    }

    public String deleteReservation(String token, int id) {       // chyba nie mozna skasować pojedynczych danych z danej rezerwacji. np. imienia i nazwiska ?
        String stringResponse = "";
        String line = null;
        //JSONObject obj = new JSONObject();                        // niepotrzebne tworzenie jsona bo nie mozna chyba?? tych danych usunąć
        //obj.put("firstname", "Marcin");
        //obj.put("lastname", "bosko");
        try {
            BOOKINGURL = BOOKINGURL + "/" + id;
            httpDelete = new HttpDelete(BOOKINGURL);
            httpDelete.addHeader("content-type", "application/json");
            httpDelete.addHeader("cookie", "token=" + token);
            response = httpClient.execute(httpDelete);
            line = response.getStatusLine().toString();
            stringResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse + " " + line;
    }

    public String patchReservation(String token, int id) {
        String stringresponse = null;
        String statusLine = null;
        JSONObject json = new JSONObject();
        json.put("firstname", "Andrew");
        json.put("lastname", "Molo");

        try {
            BOOKINGURL = BOOKINGURL + "/" + id;
            httpPatch = new HttpPatch(BOOKINGURL);
            httpPatch.addHeader("content-type", "application/json");
            httpPatch.addHeader("accept", "application/json");
            httpPatch.addHeader("cookie", "token=" + token);
            StringEntity entity = new StringEntity(json.toString());
            httpPatch.setEntity(entity);
            response = httpClient.execute(httpPatch);
            stringresponse = EntityUtils.toString(response.getEntity());
            statusLine = response.getStatusLine().toString();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringresponse + " " + statusLine;
    }

    public String putReservationOnid(String token, int id){
        String stringResponse = null;
        String statusLine = null;
        JSONObject obj = new JSONObject();
        obj.put("firstName", "Zenon");
        obj.put("lastname", "Pigwa");
        obj.put("totalprice", 234);
        obj.put("depositepaid", false);
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("checkin", "2013-02-25");
        dataMap.put("checkout", "2014-10-27");
        obj.put("bookingdates", dataMap);
        obj.put("additionalneeds", "supper");

        try {
            put = new HttpPut(BOOKINGURL+"/"+id);
            put.addHeader("content-type", "application/json");
            put.addHeader("accept", "application/json");
            put.addHeader("cookie", "token=" + token);
            StringEntity entity = new StringEntity(obj.toString());
            put.setEntity(entity);
            response = httpClient.execute(put);
            statusLine = response.getStatusLine().toString();
            stringResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse + " " + statusLine;

    }

    public String postReservationOnNewId(String token,int id){
        String stringResponse = null;
        String statusLine = null;
        JSONObject obj = new JSONObject();
        obj.put("firstName", "Zenon");
        obj.put("lastname", "Pigwa");
        obj.put("totalprice", 234);
        obj.put("depositepaid", false);
        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("checkin", "2013-02-25");
        dataMap.put("checkout", "2014-10-27");
        obj.put("bookingdates", dataMap);
        obj.put("additionalneeds", "supper");

        try {
            post = new HttpPost(BOOKINGURL+"/"+id);
            post.addHeader("content-type", "application/json");
            post.addHeader("accept", "application/json");
            post.addHeader("cookie", "token=" + token);
            StringEntity entity = new StringEntity(obj.toString());
            post.setEntity(entity);
            response = httpClient.execute(post);
            statusLine = response.getStatusLine().toString();
            stringResponse = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringResponse + " " + statusLine;

    }

    public String postSomething(String token, int id){
        String response2 = null;
        String status = null;
        JSONObject obj = new JSONObject();
        obj.put("asassa","asassas");

        try{
            Response response = given().contentType("application/json").accept("application/json").body(obj.toString()).when().post(BOOKINGURL);
            response2 = response.asString();
            status = response.getStatusLine();
        }
        catch(Exception e){e.printStackTrace();
        }
        return response2 + status;
    }

    }



