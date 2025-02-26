import com.gtxpoint.sdk.Callback;
import com.gtxpoint.sdk.Gate;
import com.gtxpoint.sdk.ProcessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;

public class GateTest
{
    private Gate gate;

    @Before
    public void initTest() throws NoSuchPaddingException, NoSuchAlgorithmException {
        gate = new Gate(TestFixtures.secret);
    }

    @After
    public void afterTest()
    {
        gate = null;
    }

    @Test
    public void getPurchasePaymentPageUrl()
    {
        assertEquals(TestFixtures.baseUrl.concat(TestFixtures.compareParams), gate.getPurchasePaymentPageUrl(TestFixtures.baseUrl, TestFixtures.getPayment()));
    }

    @Test
    public void getPurchasePaymentPageCipherUrlWithoutQuery() throws Exception
    {
        String cipherUrl = gate.getPurchasePaymentPageCipherUrl(TestFixtures.testUrl, TestFixtures.getPayment(), TestFixtures.secret);

        String[] query = cipherUrl.split("\\?");

        assertEquals(query.length, 1);
    }

    @Test
    public void handleCallback() throws ProcessException
    {
        assertEquals(Callback.class, gate.handleCallback(TestFixtures.callbackData).getClass());
    }
}
