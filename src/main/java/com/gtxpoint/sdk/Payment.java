package com.gtxpoint.sdk;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

/**
 * Class for preparing payment params
 * Has constants like TYPE_* - possible types of payment
 * and some constants with params names
 */
public class Payment
{
    public static final String PROJECT_ID = "project_id";
    public static final String ACCOUNT_TOKEN = "account_token";
    public static final String CARD_OPERATION_TYPE = "card_operation_type";
    public static final String BEST_BEFORE = "best_before";
    public static final String CLOSE_ON_MISSCLICK = "close_on_missclick";
    public static final String CSS_MODAL_WRAP = "css_modal_wrap";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String FORCE_ACS_NEW_WINDOW = "force_acs_new_window";
    public static final String FORCE_PAYMENT_METHOD = "force_payment_method";
    public static final String LANGUAGE_CODE = "language_code";
    public static final String LIST_PAYMENT_BLOCK = "list_payment_block";
    public static final String MERCHANT_FAIL_URL = "merchant_fail_url";
    public static final String MERCHANT_SUCCESS_URL = "merchant_success_url";
    public static final String MODE = "mode";
    public static final String PAYMENT_AMOUNT = "payment_amount";
    public static final String PAYMENT_CURRENCY = "payment_currency";
    public static final String PAYMENT_DESCRIPTION = "payment_description";
    public static final String PAYMENT_ID = "payment_id";
    public static final String RECURRING_REGISTER = "recurring_register";
    public static final String CUSTOMER_FIRST_NAME = "customer_first_name";
    public static final String CUSTOMER_LAST_NAME = "customer_last_name";
    public static final String CUSTOMER_PHONE = "customer_phone";
    public static final String CUSTOMER_EMAIL = "customer_email";
    public static final String CUSTOMER_COUNTRY = "customer_country";
    public static final String CUSTOMER_STATE = "customer_state";
    public static final String CUSTOMER_CITY = "customer_city";
    public static final String CUSTOMER_DAY_OF_BIRTH = "customer_day_of_birth";
    public static final String CUSTOMER_SSN = "customer_ssn";
    public static final String BILLING_POSTAL = "billing_postal";
    public static final String BILLING_COUNTRY = "billing_country";
    public static final String BILLING_REGION = "billing_region";
    public static final String BILLING_CITY = "billing_city";
    public static final String BILLING_ADDRESS = "billing_address";
    public static final String REDIRECT = "redirect";
    public static final String REDIRECT_FAIL_MODE = "redirect_fail_mode";
    public static final String REDIRECT_FAIL_URL = "redirect_fail_url";
    public static final String REDIRECT_ON_MOBILE = "redirect_on_mobile";
    public static final String REDIRECT_SUCCESS_MODE = "redirect_success_mode";
    public static final String REDIRECT_SUCCESS_URL = "redirect_success_url";
    public static final String REDIRECT_TOKENIZE_MODE = "redirect_tokenize_mode";
    public static final String REDIRECT_TOKENIZE_URL = "redirect_tokenize_url";
    public static final String REGION_CODE = "region_code";
    public static final String TARGET_ELEMENT = "target_element";
    public static final String TERMINAL_ID = "terminal_id";
    public static final String BASEURL = "baseurl";
    public static final String PAYMENT_EXTRA_PARAM = "payment_extra_param";

    public static final String TYPE_PURCHASE = "purchase";
    public static final String TYPE_PAYOUT = "payout";
    public static final String TYPE_RECURRING = "recurring";

    private static final String INTERFACE_TYPE = "{\"id\": 21}";

    /**
     * Map with payment params
     */
    private HashMap<String, Object> params = new HashMap<String, Object>();

    /**
     * com.gtxpoint.sdk.Payment constructor
     * @param projectId site id in our system
     */
    public Payment(String projectId) {
        this
            .setParam(PROJECT_ID, projectId)
            .setParam("interface_type", INTERFACE_TYPE);
    }

    /**
     * com.gtxpoint.sdk.Payment constructor
     * @deprecated
     * @param projectId site id in our system
     * @param paymentId payment id in your system
     */
    public Payment(String projectId, String paymentId) {
        this(projectId);
        this.setParam(PAYMENT_ID, paymentId);
    }

    /**
     * Setter for payment params
     * @param key name of param
     * @param object value of param
     * @return self instance for fluent interface
     */
    public Payment setParam(String key, Object object) {
        Object value = object;

        if (key.equals(BEST_BEFORE) && object instanceof ZonedDateTime) {
            ZonedDateTime objectDateTime = ZonedDateTime.class.cast(object);
            value = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(objectDateTime);
        }

        params.put(key, value);

        return this;
    }

    /**
     * Method return payment params
     * @return map with params
     */
    public HashMap<String, Object> getParams() {
        return params;
    }

    /**
     * Get paymnet param by key
     * @param $key
     * @return object param
     */
    public Object getParam(String $key) {
        return params.get($key);
    }
}
