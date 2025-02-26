package com.gtxpoint.sdk;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.stream.Collectors;

/**
 * Class for build payment URL
 */
public class PaymentPage
{
    /**
     * Encoding charset
     */
    private String CHARSET = "UTF-8";

    /**
     * Signature handler for generate signature
     */
    private SignatureHandler signatureHandler;

    /**
     * Encryptor handler for encode URL
     */
    private Encryptor encryptor;


    /**
     * com.gtxpoint.sdk.PaymentPage constructor
     * @param signHandler signature handler for generate signature
     */
    public PaymentPage(SignatureHandler signHandler) {
        signatureHandler = signHandler;
    }

    /**
     * @param signHandler
     * @param encryptor
     */
    public PaymentPage(SignatureHandler signHandler, Encryptor encryptor) {
        signatureHandler = signHandler;
        this.encryptor = encryptor;
    }

    /**
     * Method build payment URL
     * @param url string url for open PP
     * @param payment com.gtxpoint.sdk.Payment instance with payment params
     * @return string URL that you can use for redirect on payment page
     */
    public String getUrl(String url, Payment payment) {
        String signature = "&signature=".concat(encode(signatureHandler.sign(payment.getParams())));
        String query = payment.getParams().entrySet().stream()
            .map(e -> e.getKey() + "=" + encode(e.getValue()))
            .collect(Collectors.joining("&"));

        return
            url
                .concat("?")
                .concat(query)
                .concat(signature);

    }

    /**
     * @param url url for open PP
     * @param payment com.gtxpoint.sdk.Payment instance with payment params
     * @param encryptKey
     * @return
     * @throws Exception
     */
    public String getCipherUrl(String url, Payment payment, String encryptKey) throws Exception {
        String paymentLink = getUrl(url, payment);

        URL link = new URL(paymentLink);
        String protocol = link.getProtocol();
        String host = link.getHost();
        String path = link.getPath();
        String query = link.getQuery();

        String projectId = (String) payment.getParam(Payment.PROJECT_ID);

        String encryptUrl = encryptor.encrypt(path + "?" + query, encryptKey);

        return String.format("%s://%s/%s/%s", protocol, host, projectId, encryptUrl);
    }

    /**
     * Method for URL encoding payment params
     * @param param payment param value
     * @return URL encoded param
     */
    private String encode(Object param) {
        try{
            return URLEncoder.encode(param.toString(), CHARSET);
        } catch(UnsupportedEncodingException e){
            throw new RuntimeException(e);
        }
    }
}
