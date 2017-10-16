package com.binance;

import org.junit.Test;

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

}