package com.binance.dto;

/**
 * The DTO class for storing Binance Account Information
 */
public class BinanceAccountDTO {
  /**
   * The API Key for your Binance Account
   */
  private final String apiKey;

  /**
   * The Secret Key for your Binance Account
   */
  private final String secretKey;

  /**
   * The Constructor for the data transfer object
   * @param apiKey The API Public Key
   * @param secretKey The Secret
   */
  public BinanceAccountDTO(String apiKey, String secretKey) {
    this.apiKey = apiKey;
    this.secretKey = secretKey;
  }

  /**
   * Get the API Key
   * @return API Key
   */
  public String getApiKey() {
    return apiKey;
  }

  /**
   * Get the Secret Key
   * @return Secret Key
   */
  public String getSecretKey() {
    return secretKey;
  }
}
