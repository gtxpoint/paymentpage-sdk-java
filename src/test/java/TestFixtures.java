import com.gtxpoint.sdk.Payment;
import com.gtxpoint.sdk.SignatureHandler;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class TestFixtures {
    public static String secret = "qwerty";
    public static String paymentStatus = "success";
    public static String paymentId = "test.test-payment";
    public static String paymentSignature = "OonQm2ElTzZU80dNnsrS/ulnGU/eEMg7U1jfLRiKn0zx3GmnEOP2ne/pUOJ9LkzntYT7llgRh" +
            "TpbxcT3ldHyEA==";
    public static String testUrl = "https://test.test.test.test/payment";
    public static String baseUrl = "";
    public static String compareParams = "?project_id=123&payment_id=test_paymen" +
            "t&account_token=qwerty&interface_type=%7B%22id%22%3A+21%7D&best_before=2200-12-12T12%3A12%3A12%2B03%3A00&" +
            "signature=Isx3ivYhho57twVTh943HLLger8nUAxu8599dbieXVnbQHgl5FJ%2BUrMohHXUW3uZ16qBam%2F4nvxeFZUvaSF6gg%3D%3D";
    public static String callbackDataInvalid = "{...";
    public static String callbackDataInvalidSignatureRecursive = "{" +
        "\"body\": {" +
            "\"payment\": {" +
                "\"id\": \"" + paymentId + "1\"," +
                "\"status\": \"" + paymentStatus + "\"" +
            "}," +
            "\"signature\": \"" + paymentSignature + "\"" +
        "}" +
    "}";
    public static String callbackDataInvalidSignature = "{" +
        "\"payment\": {" +
            "\"id\": \"" + paymentId + "1\"," +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"signature\": \"" + paymentSignature + "\"" +
    "}";
    public static String callbackData = "{" +
        "\"payment\": {" +
            "\"id\": \"" + paymentId + "\"," +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"signature\": \"" + paymentSignature + "\"" +
    "}";
    public static String callbackDataGeneralSignature = "{" +
        "\"payment\": {" +
            "\"id\": \"" + paymentId + "\"," +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"general\": {" +
            "\"signature\": \"" + paymentSignature + "\"" +
        "}" +
    "}";
    public static String callbackDataWithoutPaymentId = "{" +
        "\"payment\": {" +
            "\"status\": \"" + paymentStatus + "\"" +
        "}," +
        "\"signature\": \"jRtwdhnY6bQGt39FRUVrXEBTgplUTjRpCA/37x95/c5FvdsnZMnlLUGXVRpNjKOHbawT0s9AmUy3rajlexddwQ==\"" +
    "}";
    public static String callbackDataWithoutPayment = "{" +
        "\"project_id\": 123," +
        "\"recurring\": {" +
            "\"id\": 321," +
            "\"status\": \"active\"," +
            "\"type\": \"Y\"," +
            "\"currency\": \"EUR\"," +
            "\"exp_year\": \"2025\"," +
            "\"exp_month\": \"12\"," +
            "\"period\": \"D\"," +
            "\"time\": \"11\"" +
        "}," +
        "\"signature\": \"AThqkBCZ6WZtY3WrMV28o7SM/vq6OIVF9qiVbELN4e/Ux59Lb5LFFnEuTq6bHa5pRvaPIkQGABXdpIrNLaeJdQ==\"" +
    "}";
    public static String callbackDataWithoutSign = "{" +
        "\"project_id\": 123," +
        "\"recurring\": {" +
            "\"id\": 321," +
            "\"status\": \"active\"," +
            "\"type\": \"Y\"," +
            "\"currency\": \"EUR\"," +
            "\"exp_year\": \"2025\"," +
            "\"exp_month\": \"12\"," +
            "\"period\": \"D\"," +
            "\"time\": \"11\"" +
        "}" +
    "}";
    public static String callbackDataWithArray = "{" +
        "\"errors\": [" +
            "{" +
                "\"message\": \"error1\"," +
                "\"code\": 1," +
                "\"fail\": true" +
            "}," +
            "{" +
                "\"message\": \"error2\"," +
                "\"code\": 2," +
                "\"fail\": false" +
            "}," +
            "{" +
                "\"message\": \"error3\"," +
                "\"code\": 3," +
                "\"fail\": {" +
                    "\"sub-fail\": {" +
                        "\"test\": 1" +
                    "}" +
                "}" +
            "}" +
        "]," +
        "\"sum_converted\": {" +
            "\"amount\": 10000," +
            "\"currency\": \"EUR\"" +
        "}," +
        "\"signature\": \"oXF9a0F80FBkT5plV1aVcQIVSWr3l07StkQ2izUKmy//H2S9gMX982Kgm4tXB4+Ze1S5E1jeKhwheIgYMZ4J+w==\"" +
    "}";

    public static Map getPaymentMap() {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", "test.test-payment");
        map.put("status", "success");

        return map;
    }

    public static SignatureHandler getSignatureHandler() {
        return new SignatureHandler(secret);
    }

    public static Payment getPayment() {
        Payment payment = new Payment("123", "test_payment");
        ZonedDateTime bestBefore = ZonedDateTime.parse("2200-12-12T12:12:12+03:00[Europe/Moscow]");
        DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(bestBefore);

        return
            payment
                .setParam(Payment.ACCOUNT_TOKEN, "qwerty")
                .setParam(Payment.BEST_BEFORE, bestBefore);
    }
}
