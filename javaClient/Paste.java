package pl.com.aay.javaClient;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Paste {

    private static final String AUTORISATIONURL = "https://restful-booker.herokuapp.com/auth";
    private String BOOKINGURL = "https://restful-booker.herokuapp.com/booking";

    HttpClient httpClient;
    HttpGet requestGet;
    HttpPost requestPost;
    HttpPut httpPut;
    HttpPatch httpPatch;
    HttpDelete httpDelete;
    HttpResponse response;

    public Paste() {
        httpClient = HttpClientBuilder.create().build();
    }

    public String getAuthenticatioToke() {

        JSONObject json = new JSONObject();
        json.put("username", "admin");
        json.put("password", "password123");

        String token = "NOT AUTHORIZED";
        String jsonResponseToString = "";

        try {
            requestPost = new HttpPost(AUTORISATIONURL);
            requestPost.addHeader("content-type", "application/json");
            StringEntity params = new StringEntity(json.toString());
            requestPost.setEntity(params);
            response = httpClient.execute(requestPost);
            jsonResponseToString = EntityUtils.toString(response.getEntity());
        } catch (Exception e) {

        }
        JSONObject jsonResponse = new JSONObject(jsonResponseToString);
        jsonResponse.get("token").toString();

        return token;
    }


    public String getBookingById(int id, String token) {
        String responseJsonString = null;

        try {
            BOOKINGURL = BOOKINGURL + "/" + id;
            System.out.println(BOOKINGURL);
            requestGet = new HttpGet(BOOKINGURL);
            requestGet.addHeader("accept", "application/json");
            response = httpClient.execute(requestGet);
            responseJsonString = EntityUtils.toString(response.getEntity());
        } catch (Exception p) {
        }
        return responseJsonString;
    }

        public String getBookinByName (String firstname, String lastName, String token) {
            String responseJsonString = null;

            try {
                BOOKINGURL = BOOKINGURL + "/" + "?firstname=" + firstname + "&lastname=" + lastName;
                System.out.println(BOOKINGURL);
                requestGet.addHeader("accept", "application/json");
                response = httpClient.execute(requestGet);
                response.getStatusLine().toString();
                responseJsonString = EntityUtils.toString(response.getEntity());
            } catch (Exception e) {

            }

            return responseJsonString;
        }


            public String createBooking(String token){

                String responseJsonString = null;

                JSONObject object = new JSONObject();
                object.put("firstname", "Marcin");
                object.put("lastname", "Szalbierz");
                object.put("totalprice", 123);
                object.put("depositpaid", true);
                Map<String,String> map = new HashMap<>();
                map.put("check-in", "02-02-2020");
                map.put("check-out", "02-02-2020");
                object.put("bookingdates", map);


                try {
                    requestPost = new HttpPost(BOOKINGURL);
                    requestPost.addHeader("content-type", "application/json");
                    requestPost.addHeader("Accept", "application/json");
                    requestPost.addHeader("Cookie", "token=" + token);
                    StringEntity entity = new StringEntity(object.toString());
                    requestPost.setEntity(entity);
                    response = httpClient.execute(requestPost);
                    responseJsonString = EntityUtils.toString(response.getEntity());
                } catch (Exception e) {
                }

                return responseJsonString;
            }


            public String updateBooking (String token,int id){

                String responseJsonString = null;
                JSONObject json = new JSONObject();
                json.put("firstname", "Jimmy");
                json.put("lastName", "Browwny");
                json.put("totalprice", "122");
                json.put("depositpaid", true);
                try {
                    httpPut = new HttpPut(BOOKINGURL + "/" + id);
                    httpPut.addHeader("content-type", "application/json");
                    httpPut.addHeader("Accept", "application/json");
                    httpPut.addHeader("Cookie", "token=" + token);

                    StringEntity entity = new StringEntity(json.toString());
                    httpPut.setEntity(entity);
                    response = httpClient.execute(httpPut);
                    responseJsonString = EntityUtils.toString(response.getEntity());
                } catch (Exception e) {
                }

                return responseJsonString;
            }
        }



