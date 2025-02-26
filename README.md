# GtxPoint payment page SDK

This is a set of libraries in the Java language to ease integration of your service
with the GtxPoint Payment Page.

## Payment flow

![Payment flow](flow.png)

### Get URL for payment

```java
String secretKey = "secret";
String encryptKey = "encrypt_secret";
String projectId = "11";

Gate gate = new Gate(secretKey);
Payment payment = new Payment(projectId);

payment
    .setParam(Payment.PAYMENT_ID, "some payment id")
    .setParam(Payment.PAYMENT_AMOUNT, 1001)
    .setParam(Payment.PAYMENT_CURRENCY, "EUR");

String paymentUrl = gate.getPurchasePaymentPageUrl(your_pp_url, payment);

// Use the `getPurchasePaymentPageCipherUrl` method to encode the payment link.
String paymentUrl = gate.getPurchasePaymentPageCipherUrl(your_pp_url, payment, encryptKey);
``` 

`paymentUrl` here is the signed & encoded URL.

### Handle callback from GtxPoint

You'll need to autoload this code in order to handle notifications:

```java
Gate gate = new Gate("secret");
Callback callback = gate.handleCallback(callbackData);
```

`data` is the JSON string received from payment system;

`callback` is the Callback object describing properties received from payment system;
`callback` implements these methods:
1. `callback.getPaymentStatus();`
   Get payment status.
2. `callback.getPayment();`
   Get all payment data.
3. `callback.getPaymentId();`
   Get payment ID in your system.
