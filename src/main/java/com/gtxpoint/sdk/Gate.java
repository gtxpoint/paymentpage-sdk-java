package com.gtxpoint.sdk;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

/**
 * Class for communicate with our
 */
public class Gate
{
    /**
     * com.gtxpoint.sdk.SignatureHandler instance for check signature
     */
    private SignatureHandler signatureHandler;

    /**
     * com.gtxpoint.sdk.PaymentPage instance for build payment URL
     */
    private PaymentPage paymentPageUrlBuilder;

    /**
     * com.gtxpoint.sdk.Gate constructor
     * @param secret site salt
     */
    public Gate(String secret) throws NoSuchPaddingException, NoSuchAlgorithmException {
        signatureHandler = new SignatureHandler(secret);
        paymentPageUrlBuilder = new PaymentPage(signatureHandler, Encryptor.getInstance());
    }

    /**
     * Method for set base payment page URL
     * @param url payment page URL
     * @return self for fluent interface
     */
    public Gate setBaseUrl(String url) {
        paymentPageUrlBuilder.setBaseUrl(url);

        return this;
    }

    /**
     * Method build encrypted payment URL
     * @param payment com.gtxpoint.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on payment page
     */
    public String getPurchasePaymentPageUrl(Payment payment) {
        return paymentPageUrlBuilder.getUrl(payment);
    }

    /**
     * Method build encrypted URL
     *
     * @param payment Payment instance with payment params
     * @param encryptKey String Encryption key
     * @return string cipher URL that you can use for redirect on payment page
     */
    public String getPurchasePaymentPageCipherUrl(Payment payment, String encryptKey) throws Exception {
        return paymentPageUrlBuilder.getCipherUrl(payment, encryptKey);
    }

    /**
     * Method for handling callback
     * @param data raw callback data in JSON format
     * @return com.gtxpoint.sdk.Callback instance
     * @throws ProcessException throws when signature is invalid
     */
    public Callback handleCallback(String data) throws ProcessException {
        return new Callback(data, signatureHandler);
    }
}
