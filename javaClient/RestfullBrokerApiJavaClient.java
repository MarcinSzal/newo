package pl.com.aay.javaClient;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;

public class RestfullBrokerApiJavaClient {

    private static final String AUTHORISATIONURL = "https://restful-booker.herokuapp.com/auth";
    private static String BOOKINGURL = "https://restful-booker.herokuapp.com/booking";

    HttpClient httpClient;
    HttpPost requestPost;
    HttpGet requestGet;
    HttpPut httpPut;
    HttpPatch httpPatch;
    HttpDelete httpDelete;
    HttpResponse response;

    public void RestfullBrokerApiJavaClient() {
        httpClient = HttpClientBuilder.create().build();
    }

    public String getAuthenticationToken() {
        httpClient = HttpClientBuilder.create().build();

        JSONObject json = new JSONObject();
        json.put("username", "admin");
        json.put("password", "password123");

        String token = "Not authorized";
        String responseJsonString = null;
        try {
            requestPost = new HttpPost(AUTHORISATIONURL);
            requestPost.addHeader("content-type", "application/json");
            StringEntity params = new StringEntity(json.toString());
            requestPost.setEntity(params);
            response = httpClient.execute(requestPost);
            responseJsonString = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {

        }
        JSONObject jsonResponse = new JSONObject(responseJsonString);
        token = jsonResponse.get("token").toString();

        return token;
    }

    public String getBookingById(int id, String token) {
        String responsJsonString = null;

        try {
            BOOKINGURL = BOOKINGURL + "/" + id;
            System.out.println(BOOKINGURL);
            requestGet = new HttpGet(BOOKINGURL);
            requestGet.addHeader("Accept", "application/json");
            response = httpClient.execute(requestGet);
            responsJsonString = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {

        }

        return responsJsonString;
    }

    public String getBookingByName(String firstName, String lastName, String token) {
        String responsJsonString = null;

        try {
            BOOKINGURL = BOOKINGURL + "?firstname=" + firstName + "&lastname=" + lastName;
            System.out.println(BOOKINGURL);
            requestGet = new HttpGet(BOOKINGURL);
            requestGet.addHeader("Accept", "application/json");
            response = httpClient.execute(requestGet);
            response.getStatusLine();
            responsJsonString = EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {

        }
        return responsJsonString;
    }

    public String createBooking(String token) {
        JSONObject json = new JSONObject();
        json.put("firstname", "Ji");
        json.put("lastname", "Bro");
        json.put("totalprice", 121);
        json.put("depositpaid", true);
        HashMap<String,String> dateMap = new HashMap<>();
        dateMap.put("checkin", "2019-02-01");
        dateMap.put("checkout", "2020-02-01");
        json.put("bookingdates", dateMap);

        String responseString = "";
        try {
            requestPost = new HttpPost(BOOKINGURL);
            requestPost.addHeader("content-type", "application/json");
            requestPost.addHeader("Accept", "application/json");
            requestPost.addHeader("Cookie", "token=" + token);
            StringEntity params = new StringEntity(json.toString());
            requestPost.setEntity(params);
            response = httpClient.execute(requestPost);
            responseString = EntityUtils.toString(response.getEntity());

        } catch (Exception ex) {

        }

        return responseString;
    }

    public String updateBooking(int id, String token) {
        JSONObject json = new JSONObject();
        json.put("firstname", "Jimmmy");
        json.put("lastname", "Brownnny");
        json.put("totalprice", 111);
        json.put("depositpaid", true);
        HashMap<String,String> dateMap = new HashMap<String,String>();
        dateMap.put("checkin", "2019-01-01");
        dateMap.put("checkout", "2020-01-01");
        json.put("bookingdates", dateMap);

        String responseString = "";
        try {
            httpPut = new HttpPut(BOOKINGURL + "/" + id);
            StringEntity params = new StringEntity(json.toString());
            httpPut.addHeader("content-type", "application/json");
            httpPut.addHeader("Accept", "application/json");
            httpPut.addHeader("Cookie", "token=" + token);
            httpPut.setEntity(params);
            response = httpClient.execute(httpPut);
            responseString = EntityUtils.toString(response.getEntity());

        } catch (Exception ex) {

        }
        return responseString;
    }


    public String updateBookingPatch(int id, String token) {

        String responseJsonString = "";

        JSONObject json = new JSONObject();
        json.put("firstname", "Ola");

        try {

            httpPatch = new HttpPatch(BOOKINGURL + "/" + id);
            StringEntity entity = new StringEntity(json.toString());
            httpPatch.addHeader("content-type", "application/json");
            httpPatch.addHeader("Accept", "application/json");
            httpPatch.addHeader("Cookie", "token=" + token);

            httpPatch.setEntity(entity);
            response = httpClient.execute(httpPatch);
            responseJsonString = EntityUtils.toString(response.getEntity());

        } catch (Exception e) {
        }

        return responseJsonString;
    }

    public String deleteBooking(int id, String token) {

        String responseJsonString = null;

        try {
            httpDelete = new HttpDelete(BOOKINGURL + "/" + id);
            httpDelete.addHeader("Accept", "application/json");
            httpDelete.addHeader("Cookie", "token=" + token);

            response = httpClient.execute(httpDelete);
            responseJsonString = EntityUtils.toString(response.getEntity());

        } catch (Exception e) { e.toString();

        }
        return responseJsonString;
    }
}
