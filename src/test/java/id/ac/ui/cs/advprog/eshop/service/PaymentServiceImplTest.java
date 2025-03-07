package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Order order;
    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        products = new ArrayList<Product>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        MockitoAnnotations.openMocks(this);
        order = new Order("13652556-012a-4c07-b546-54eb1396d79b",
                products, 1708560000L, "Safira Sudrajat");
        paymentData = new HashMap<>();
    }

    @Test
    void testAddPaymentWithValidVoucher() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment savedPayment = invocation.getArgument(0);
            return savedPayment; // Return the modified object
        });

        Payment result = paymentService.addPayment(order, "Voucher", paymentData);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        paymentData.put("voucherCode", "INVALID12345678");

        when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> {
            Payment savedPayment = invocation.getArgument(0);
            return savedPayment;
        });

        Payment result = paymentService.addPayment(order, "Voucher", paymentData);
        assertEquals("REJECTED", result.getStatus());
    }

    @Test
    void testSetStatusToSuccess() {
        Payment payment = new Payment(order, "Voucher", paymentData);
        when(paymentRepository.save(payment)).thenReturn(payment);

        Payment result = paymentService.setStatus(payment, "SUCCESS");
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testGetPaymentById() {
        Payment payment = new Payment(order, "Voucher", paymentData);
        when(paymentRepository.getPayment("1")).thenReturn(payment);

        Payment result = paymentService.getPayment("1");
        assertNotNull(result);
        assertEquals(payment, result);
    }

    @Test
    void testGetAllPayments() {
        Payment payment = new Payment(order, "Voucher", paymentData);
        when(paymentRepository.getAllPayments()).thenReturn(List.of(payment));

        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(1, payments.size());
    }
}