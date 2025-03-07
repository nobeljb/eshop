package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order, method, paymentData);

        if (method.equals("Voucher")) {
            String voucherCode = paymentData.get("voucherCode");
            if (voucherCode != null && voucherCode.length() == 16 && voucherCode.startsWith("ESHOP")
                    && voucherCode.replaceAll("[^0-9]", "").length() == 8) {
                payment.setStatus(PaymentStatus.SUCCESS.getValue());
            } else {
                payment.setStatus(PaymentStatus.REJECTED.getValue());
            }
        } else if (method.equals("CashOnDelivery")) {
            if (paymentData.get("address") == null || paymentData.get("address").isEmpty()
                    || paymentData.get("deliveryFee") == null || paymentData.get("deliveryFee").isEmpty()) {
                payment.setStatus(PaymentStatus.REJECTED.getValue());
            }
        } else if (method.equals("BankTransfer")) {
            if (paymentData.get("bankName") == null || paymentData.get("bankName").isEmpty()
                    || paymentData.get("referenceCode") == null || paymentData.get("referenceCode").isEmpty()) {
                payment.setStatus(PaymentStatus.REJECTED.getValue());
            }
        }

        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.getPayment(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }
}
