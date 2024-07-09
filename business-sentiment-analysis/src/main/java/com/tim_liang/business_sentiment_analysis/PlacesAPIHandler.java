package com.tim_liang.business_sentiment_analysis;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;
import java.util.*;

public class PlacesAPIHandler {

    private static final String API_KEY = "API_KEY";
    public List<String> reviews;
    public String name;

    PlacesAPIHandler() {
        this.reviews = new ArrayList<>();
        this.name = "";
    }

    public void getAPIData(String placeID) {
        try {
            String s = String.format(
                    "https://maps.googleapis.com/maps/api/place/details/json?fields=name,reviews&reviews_sort=newest&place_id=%s&key=%s",
                    placeID, API_KEY);
            URL url = (new URI(s)).toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Check if connect is made
            int responseCode = conn.getResponseCode();

            // 200 OK
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                // Close the scanner
                scanner.close();

                // JSON simple library Setup with Maven is used to convert strings to JSON
                JSONParser parse = new JSONParser();
                JSONObject dataObject = (JSONObject) parse.parse(String.valueOf(informationString));

                // Get the first JSON object in the JSON array
                JSONObject result = (JSONObject) dataObject.get("result");
                JSONArray reviewsObj = (JSONArray) result.get("reviews");
                this.name = (String) result.get("name");
                for (int i = 0; i < 5; i++) {
                    JSONObject review = (JSONObject) reviewsObj.get(i);
                    String reviewText = (String) review.get("text");
                    if (reviewText.length() > 0) {
                        this.reviews.add(reviewText);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("invalid ID or API key");
        }
    }
}
