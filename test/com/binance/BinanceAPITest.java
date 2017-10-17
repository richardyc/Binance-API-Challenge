package com.binance;

import com.binance.dto.BinanceAccountDTO;
import com.binance.dto.OrderType;
import org.junit.Test;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.junit.Assert.*;

/**
 * Tests for the Binance API
 *
 * @author Richard He
 *
 * Copyright 2017-, Richard He
 * Released under the MIT License
 */
public class BinanceAPITest {
  /**
   * Sample Account Information for testing purposes
   */
  BinanceAccountDTO account = new BinanceAccountDTO("vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A",
      "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j");

  /**
   * One instance of binance api for testing
   */
  BinanceAPI api = new BinanceAPI(account);

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
    String actual = BinanceAPI.getSignatureKey(account.getSecretKey(),
        "symbol=LTCBTC&side=BUY&type=LIMIT&timeInForce=GTC&quantity=1&price=0.1&recvWindow=5000&timestamp=1499827319559");
    String expected = "c8db56825ae71d6d79447849e617115f4a920fa2acdcab2b053c4b2838bd6b71";
    assertEquals(actual, expected);
  }

  @Test
  public void testBuy() throws Exception {
    System.out.println(api.buy("ETHBTC", BigDecimal.valueOf(10.5), BigDecimal.valueOf(0.0578), OrderType.LIMIT));
  }
}