import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {
    private PaymentRepository paymentRepository;
    private Payment payment;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        products = new ArrayList<Product>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        Order order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(order, "Voucher", paymentData);
    }

    @Test
    void testSaveCreate() {
        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.getPayment(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {
        paymentRepository.save(payment);
        Payment newPayment = new Payment(payment.getOrder(), payment.getMethod(), payment.getPaymentData());
        newPayment.setStatus("SUCCESS");
        Payment result = paymentRepository.save(newPayment);
        Payment findResult = paymentRepository.getPayment(newPayment.getId());

        assertEquals(newPayment.getId(), result.getId());
        assertEquals(newPayment.getId(), findResult.getId());
        assertEquals("SUCCESS", findResult.getStatus());
    }

    @Test
    void testGetPaymentByIdIfFound() {
        paymentRepository.save(payment);
        Payment findResult = paymentRepository.getPayment(payment.getId());

        assertNotNull(findResult);
        assertEquals(payment.getId(), findResult.getId());
    }

    @Test
    void testGetPaymentByIdIfNotFound() {
        Payment findResult = paymentRepository.getPayment("INVALID_ID");
        assertNull(findResult);
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.save(payment);
        List<Payment> payments = paymentRepository.getAllPayments();

        assertEquals(1, payments.size());
        assertEquals(payment.getId(), payments.get(0).getId());
    }
}
