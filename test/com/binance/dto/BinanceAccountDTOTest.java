package com.binance.dto;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by richardhe on 10/16/17.
 */
public class BinanceAccountDTOTest {
  BinanceAccountDTO newAccount =
      new BinanceAccountDTO("vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A"
          ,"NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j");

  @Test
  public void getApiKey() throws Exception {
    String expectedApiKey = "vmPUZE6mv9SD5VNHk4HlWFsOr6aKE2zvsw0MuIgwCIPy6utIco14y7Ju91duEh8A";
    assertEquals(expectedApiKey, newAccount.getApiKey());
  }

  @Test
  public void getSecretKey() throws Exception {
    String expectedSecret = "NhqPtmdSJYdKjVHjA7PZj4Mge3R5YNiP1e3UZjInClVN65XAbvqqM6A7H5fATj0j";
    assertEquals(expectedSecret, newAccount.getSecretKey());
  }

}