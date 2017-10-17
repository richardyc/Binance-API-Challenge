package com.binance;

import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Created by richardhe on 10/16/17.
 */
public class BinanceAPITest {
  @Test
  public void getDepth() throws Exception {
    System.out.println(BinanceAPI.getDepth("ETHBTC", 200));
    System.out.println(BinanceAPI.getDepth("ETHBTC"));
  }

  @Test
  public void getLatestPrice() throws Exception {
    System.out.println(BinanceAPI.getLatestPrice("ETHBTC"));
  }


  @Test
  public void generateSignature() throws Exception {
    String actual = BinanceAPI.getSignatureKey("NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j",
        "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559");
    String expected = "c8db56825ae71d6d79447849e617115f4a920fa2acdcab2b053c4b2838bd6b71";
    assertEquals(actual, expected);
  }
}