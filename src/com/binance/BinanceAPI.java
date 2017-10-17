package com.binance;

import com.binance.dto.BinanceAccountDTO;
import com.binance.dto.OrderType;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Demonstration of the Binance API features
 *
 * @author Richard He
 *
 * Copyright 2017-, Richard He
 * Released under the MIT License
 */
public class BinanceAPI {

    /**
     * The account for this instance.
     */
    private final BinanceAccountDTO account;

    /**
     * The Constructor for each Binance API Instance.
     * @param account The given account for this api instance
     */
    public BinanceAPI(BinanceAccountDTO account) {
        this.account = account;
    }

    /**
     * The base url for all requests
     */
    private static final String BASE_URL = "https://www.binance.com";

    /**
     * Method to get the latest price
     * @param symbol The symbol for the requested ticker, e.g ETHBTC
     * @return A json response with the corresponding ticker price
     * @throws Exception when request is not valid
     */
    public static String getLatestPrice(String symbol) throws Exception {
        return sendGet("/api/v1/ticker/24hr?symbol="+symbol);
    }

    /**
     * Method for getting depth of a symbol with default limit
     * @param symbol The symbol for the requested ticker, e.g ETHBTC
     * @return A json response with the corresponding ticker depth
     * @throws Exception when request is not valid
     */
    public static String getDepth(String symbol) throws Exception {
        return sendGet("/api/v1/depth?symbol="+symbol);
    }

    /**
     * Method for getting depth of a symbol with custom limit
     * @param symbol The symbol for the requested ticker, e.g ETHBTC
     * @param limit The custom depth that user wants to set
     * @return A json response with the corresponding ticker depth
     * @throws Exception when request is not valid
     */
    public static String getDepth(String symbol, Integer limit) throws Exception {
        return sendGet("/api/v1/depth?symbol=" + symbol + "&limit=" + limit);
    }

    /**
     * Get signature given the secret key and message to encode
     * @param secret The secret key given by Biance Account DTO
     * @param message The messge to be encoded
     * @return The string with the hex encoded message
     * @throws Exception The encoded failure
     */
    static String getSignatureKey(String secret, String message) throws Exception {
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return Hex.encodeHexString(sha256_HMAC.doFinal(message.getBytes()));
        }
        catch (Exception e){
            System.out.println("Error");
        }
        return secret;
    }

    /**
     * The function for buying with limit and market orders
     * @param symbol The symbol for the target ticker
     * @param quantity The quantity to buy
     * @param price The price of the ideal limit orders
     * @param orderType The type of the order
     * @return A String if successfully ordered
     * @throws Exception If the post request failed
     */
    public String buy(String symbol, BigDecimal quantity, BigDecimal price, OrderType orderType) throws Exception {
        if (orderType.equals(orderType.LIMIT)) {
            long curTime = System.currentTimeMillis();
            String buyMsg = "symbol=" + symbol + "&side=BUY&type=" + orderType.toString() + "&timeInForce=GTC&quantity="
                + quantity + "&price=" + price + "&recvWindow=6000000&timestamp=" + curTime;
            String signature = getSignatureKey(account.getSecretKey(), buyMsg);
            return executePost("/api/v3/order/test", buyMsg + "&signature=" + signature);
        } else {
            return "Error: Specified a price with a non-limit order type.";
        }
    }

    /**
     * The function for buying with limit and market orders
     * @param symbol The symbol for the target ticker
     * @param quantity The quantity to buy
     * @param orderType The type of the order
     * @return A String if successfully ordered
     * @throws Exception If the post request failed
     */
    public String buy(String symbol, BigDecimal quantity, OrderType orderType) throws Exception {
        if (orderType.equals(orderType.MARKET)) {
            long curTime = System.currentTimeMillis();
            String buyMsg = "symbol=" + symbol + "&side=BUY&type=" + orderType.toString() + "&timeInForce=GTC&quantity="
                + quantity + "&recvWindow=6000000&timestamp=" + curTime;
            String signature = getSignatureKey(account.getSecretKey(), buyMsg);
            return executePost("/api/v3/order", buyMsg + "&signature=" + signature);
        } else {
            return "Error: Did not specify a price with a limit order type.";
        }
    }

    /**
     * Check order status based on order symbol
     * @param symbol The symbol of the traded currency
     * @return The returned json content
     * @throws Exception If the get request failed
     */
    public String checkOrderStat(String symbol) throws Exception {
        String msg = "symbol=" + symbol + "&timestamp=" + System.currentTimeMillis();
        return sendGet("/api/v3/order?" + msg + getSignatureKey(account.getSecretKey(), msg));
    }

    /**
     * Check order status based on order symbol
     * @param symbol The symbol of the traded currency
     * @param orderID The order id of the traded currency
     * @return The returned json content
     * @throws Exception If the get request failed
     */
    public String checkOrderStat(String symbol, Long orderID) throws Exception {
        String msg = "symbol=" + symbol + "&orderId=" + orderID + "&timestamp=" + System.currentTimeMillis();
        return sendGet("/api/v3/order?" + msg + getSignatureKey(account.getSecretKey(), msg));
    }

    /**
     * Cancel order based on symbol
     * @param symbol The symbol of the traded currency
     * @return The returned json content of canceled order
     * @throws Exception If the delete request failed
     */
    public String cancelOrder(String symbol) throws Exception {
        String msg = "symbol=" + symbol + "&timestamp=" + System.currentTimeMillis();
        return sendDelete("/api/v3/order?" + msg + getSignatureKey(account.getSecretKey(), msg));
    }

    /**
     * Cancel specific order based on orderID
     * @param symbol The symbol of the traded currency
     * @param orderID The order ID
     * @return The returned json content of the canceled order
     * @throws Exception If the delete request failed
     */
    public String cancelOrder(String symbol, Long orderID) throws Exception {
        String msg = "symbol=" + symbol + "&orderId=" + orderID + "&timestamp=" + System.currentTimeMillis();
        return sendDelete("/api/v3/order?" + msg + getSignatureKey(account.getSecretKey(), msg));
    }

    /**
     * Get list of open orders
     * @param symbol The symbol of the traded currency
     * @return The returned json content
     * @throws Exception If the get request failed
     */
    public String getOpenOrders(String symbol) throws Exception {
        String msg = "symbol=" + symbol + "&timestamp=" + System.currentTimeMillis();
        return sendGet("/api/v3/openOrders?" + msg + getSignatureKey(account.getSecretKey(), msg));
    }

    /**
     * Get list of Current Positions
     * @return The returned json content
     * @throws Exception If the get request failed
     */
    public String getCurrentPositions() throws Exception {
        String msg = "timestamp=" + System.currentTimeMillis();
        return sendGet("/api/v3/account?" + msg + getSignatureKey(account.getSecretKey(), msg));
    }

    /**
     * Given an url, return the json response
     * @param url The given url
     * @return A string representation of json
     * @throws Exception when request is not valid
     */
    private static String sendGet(String url) throws Exception {

        URL obj = new URL(BASE_URL + url);

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

    /**
     * Given an url, return the json response
     * @param url The given url
     * @return A string representation of json
     * @throws Exception when request is not valid
     */
    private static String sendDelete(String url) throws Exception {

        URL obj = new URL(BASE_URL + url);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is DELETE
        con.setRequestMethod("DELETE");

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'DELETE' request to URL : " + url);
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

    /**
     * Method for executing POST requests
     * @param targetURL The target url
     * @param urlParameters The parameters for the POST request
     * @return A string with the json content of the response
     */
    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(BASE_URL + targetURL);
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
