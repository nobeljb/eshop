package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentData.get(i).getId().equals(payment.getId())) {
                paymentData.set(i, payment);
                return payment;
            }
        }
        paymentData.add(payment);
        return payment;
    }

    public Payment getPayment(String paymentId) {
        return paymentData.stream()
                .filter(payment -> payment.getId().equals(paymentId))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> getAllPayments() {
        return new ArrayList<>(paymentData);
    }
}
