package com.binance;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BinanceAPI {
    private static String BASE_URL = "https://www.binance.com";

    /**
     * Method to get the latest price
     * @param symbol The symbol for the requested ticker, e.g ETHBTC
     * @return A json response with the corresponding ticker price
     * @throws Exception when request is not valid
     */
    public static String getLatestPrice(String symbol) throws Exception {
        return sendGet(BASE_URL + "/api/v1/ticker/24hr?symbol="+symbol);
    }

    /**
     * Method for getting depth of a symbol with default limit
     * @param symbol The symbol for the requested ticker, e.g ETHBTC
     * @return A json response with the corresponding ticker depth
     * @throws Exception when request is not valid
     */
    public static String getDepth(String symbol) throws Exception {
        return sendGet(BASE_URL + "/api/v1/depth?symbol="+symbol);
    }

    /**
     * Method for getting depth of a symbol with custom limit
     * @param symbol The symbol for the requested ticker, e.g ETHBTC
     * @param limit The custom depth that user wants to set
     * @return A json response with the corresponding ticker depth
     * @throws Exception when request is not valid
     */
    public static String getDepth(String symbol, Integer limit) throws Exception {
        return sendGet(BASE_URL + "/api/v1/depth?symbol="+symbol+"&limit="+limit);
    }

    // HTTP GET request
    private static String sendGet(String url) throws Exception {

        URL obj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        return (response.toString());

    }

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length",
                Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (
                connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
