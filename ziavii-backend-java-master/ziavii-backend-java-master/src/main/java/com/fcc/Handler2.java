package com.fcc;

import java.util.TreeMap;

import com.paytm.pg.merchant.PaytmChecksum;

public class Handler2 {
public static void main(String[] args) throws Exception {
	/* initialize an hash */
    TreeMap<String, String> params = new TreeMap<String, String>();
    params.put("MID", "tRFOqZ48004688438252");
    params.put("ORDERID", "11");
    /**
     * Generate checksum by parameters we have
     * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
     */
    String paytmChecksum = PaytmChecksum.generateSignature(params, "tNQsnAQechoXlKcb");
    boolean verifySignature = PaytmChecksum.verifySignature(params, "tNQsnAQechoXlKcb", paytmChecksum);
    System.out.println("generateSignature Returns: " + paytmChecksum);
    System.out.println("verifySignature Returns: " + verifySignature);


    /* initialize JSON String */
    String body = "{\"mid\":\"tRFOqZ48004688438252\",\"orderId\":\"11\"}";

    /**
     * Generate checksum by parameters we have in body
     * Find your Merchant Key in your Paytm Dashboard at https://dashboard.paytm.com/next/apikeys
     */
    paytmChecksum = PaytmChecksum.generateSignature(body, "tNQsnAQechoXlKcb");
    verifySignature = PaytmChecksum.verifySignature(body, "tNQsnAQechoXlKcb", paytmChecksum);
    System.out.println("generateSignature Returns: " + paytmChecksum);
    System.out.println("verifySignature Returns: " + verifySignature);
}
}
