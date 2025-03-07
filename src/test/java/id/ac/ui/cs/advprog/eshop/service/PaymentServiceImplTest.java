package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
        MockitoAnnotations.openMocks(this);
        order = new Order("1", List.of(), 1708560000L, "John Doe", "WAITING_PAYMENT");
        paymentData = new HashMap<>();
    }

    @Test
    void testAddPaymentWithValidVoucher() {
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment = new Payment(order, "Voucher", paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment result = paymentService.addPayment(order, "Voucher", paymentData);
        assertEquals("SUCCESS", result.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucher() {
        paymentData.put("voucherCode", "INVALID12345678");
        Payment payment = new Payment(order, "Voucher", paymentData);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

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