import com.gtxpoint.sdk.Payment;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PaymentTest
{
    private Payment payment;

    @Before
    public void initTest()
    {
        payment = (new Payment("123")).setParam(Payment.PAYMENT_ID, "test_payment");
    }

    @After
    public void afterTest()
    {
        payment = null;
    }

    @Test
    public void setParam()
    {
        assertTrue(payment.setParam(Payment.ACCOUNT_TOKEN, "qwerty") instanceof Payment);
    }

    @Test
    public void getParams()
    {
        Clock clock = Clock.system(ZoneId.of("Europe/Moscow"));
        ZonedDateTime bestBefore = ZonedDateTime.now(clock).plusYears(1);

        payment
            .setParam(Payment.ACCOUNT_TOKEN, "qwerty")
            .setParam(Payment.BEST_BEFORE, bestBefore);

        Map<String, String> condition = new HashMap<String, String>(){{
            put("project_id", "123");
            put("payment_id", "test_payment");
            put("interface_type", "{\"id\": 21}");
            put("account_token", "qwerty");
            put("best_before", DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(bestBefore));
        }};

        assertEquals(condition, payment.getParams());
    }
}
