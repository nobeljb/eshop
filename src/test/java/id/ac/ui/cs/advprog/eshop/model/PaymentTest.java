import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    private Order order;
    private Map<String, String> paymentData;
    private Payment payment;

    @BeforeEach
    void setUp() {
        order = new Order("1", List.of(), 1708560000L, "John Doe", OrderStatus.WAITING_PAYMENT.getValue());
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(order, "Voucher", paymentData);
    }

    @Test
    void testCreatePayment() {
        assertNotNull(payment.getId());
        assertEquals("Voucher", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("ESHOP1234ABC5678", payment.getPaymentData().get("voucherCode"));
        assertEquals(order, payment.getOrder());
    }

    @Test
    void testSetStatusToSuccess() {
        payment.setStatus("SUCCESS");
        assertEquals("SUCCESS", payment.getStatus());
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusToRejected() {
        payment.setStatus("REJECTED");
        assertEquals("REJECTED", payment.getStatus());
        assertEquals("FAILED", order.getStatus());
    }
}
