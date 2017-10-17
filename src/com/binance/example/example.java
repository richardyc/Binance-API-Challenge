package com.binance.example;

import com.binance.BinanceAPI;
import com.binance.dto.BinanceAccountDTO;
import com.binance.dto.OrderType;

import java.math.BigDecimal;

/**
 * Example for Usage of the BinanceAPI
 *
 * @author Richard He
 *
 * Copyright 2017-, Richard He
 * Released under the MIT License
 */
public class example {
  public static void main(String[] args) throws Exception {
    /**
     * Sample Account Information for testing purposes
     */
    BinanceAccountDTO account = new BinanceAccountDTO("vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A",
        "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j");

    /**
     * One instance of binance api for testing
     */
    BinanceAPI api = new BinanceAPI(account);

    /*
    Getting Latest Price of a symbol
     */
    BinanceAPI.getLatestPrice("ETHBTC");

    /*
    Getting depth of a symbol
     */
    BinanceAPI.getDepth("ETHBTC");

    /*
    Placing a LIMIT order
     */
    api.buy("ETHBTC", BigDecimal.valueOf(10.5), BigDecimal.valueOf(0.0578), OrderType.LIMIT);

    /*
    Placing a MARKET order
     */
    api.buy("ETHBTC", BigDecimal.valueOf(10.5), OrderType.MARKET);

    /*
    Checking an order’s status (By Symbol)
     */
    api.checkOrderStat("ETHBTC");

    /*
    Checking an order’s status (By Symbol and orderID)
     */
    long orderid = 7610385;
    api.checkOrderStat("ETHBTC", orderid);

    /*
    Cancelling an order (By Symbol)
     */
    api.cancelOrder("ETHBTC");

    /*
    Cancelling an order (By Symbol and orderID)
     */
    orderid = 7610385;
    api.cancelOrder("ETHBTC", orderid);

    /*
    Getting list of open orders
     */
    api.getOpenOrders("ETHBTC");

    /*
    Getting list of current position
     */
    api.getCurrentPositions();
  }
}
