package com.gtxpoint.sdk;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * Class for make/check signature
 */
public class SignatureHandler
{
    /**
     * Delimiter for separate key and value
     */
    private static final char DELIMITER_KEY = ':';

    /**
     * Delimiter for separate params
     */
    private static final String DELIMITER_PARAM = ";";

    /**
     * Ignore keys from signature
     */
    private static final String[] IGNORE_KEYS = new String[]{"frame_mode"};

    /**
     * Crypto algorithm
     */
    private String ALGORITHM = "HmacSHA512";

    /**
     * Need sort params before sign or not need
     */
    private boolean sortParams = true;

    /**
     * Site salt
     */
    private String secret = "";

    /**
     * com.gtxpoint.sdk.SignatureHandler constructor
     * @param projectSecret site sale
     */
    public SignatureHandler(String projectSecret) {
        secret = projectSecret;
    }

    /**
     * Setter for sortParams
     * @param sort need sort or not need
     * @return self for fluent interface
     */
    public SignatureHandler setSortParams(boolean sort) {
        sortParams = sort;

        return this;
    }

    /**
     * Method for check signature
     * @param sign signature for check
     * @param params parameters with which signature was obtained
     * @return that signature valid or not valid
     */
    public boolean check(String sign, Map<String, Object> params) {
        return sign.equals(sign(params));
    }

    /**
     * Method for make signature
     * @param params parameters with which signature will obtained
     * @return signature
     */
    public String sign(Map<String, Object> params) {
        Map paramsToSing = getParamsToSing(params, "", IGNORE_KEYS);
        List<String> paramsListToSing = new ArrayList<String>(paramsToSing.values());

        if (sortParams) {
            Collections.sort(paramsListToSing);
        }

        String paramsStringToSing = String.join(DELIMITER_PARAM, paramsListToSing);

        try {
            Mac shaHMAC = Mac.getInstance(ALGORITHM);
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), ALGORITHM);
            shaHMAC.init(secretKey);
            String hash = Base64.getEncoder().encodeToString(shaHMAC.doFinal(paramsStringToSing.getBytes()));

            return hash;
        } catch (GeneralSecurityException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * Method for preparing params
     * @param params map with params
     * @param prefix add before key
     * @param ignore ignore specific keys
     * @return prepared map with params
     */
    private Map getParamsToSing(Map params, String prefix, String[] ignore) {
        ignore = ignore != null ? ignore : new String[]{};
        Map paramsToSign = new HashMap<String, String>();

        for (Map.Entry<String, Object> entry : ((Map<String, Object>) params).entrySet()) {
            if (Arrays.stream(ignore).anyMatch(entry.getKey()::equals)) {
                continue;
            }

            String key = prefix + (prefix.equals("") ? "" : DELIMITER_KEY) + entry.getKey();
            Object valueObject = entry.getValue();

            if (valueObject instanceof Boolean) {
                valueObject = Boolean.valueOf(valueObject.toString()) ? '1' : '0';
            }

            if (valueObject instanceof List) {
                HashMap<String, Object> tempMap = new HashMap<String, Object>();

                for (int i = 0; i < ((List) valueObject).size(); i++){
                    tempMap.put(String.valueOf(i), ((List) valueObject).get(i));
                }

                valueObject = tempMap;
            }

            if (valueObject instanceof Map) {
                paramsToSign.putAll(getParamsToSing((Map) valueObject, key, ignore));
            } else {
                paramsToSign.put(key, key + DELIMITER_KEY + valueObject.toString());
            }
        }

        return paramsToSign;
    }
}
