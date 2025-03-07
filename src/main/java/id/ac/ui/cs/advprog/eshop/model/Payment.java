package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;
    Order order;

    public Payment(Order order, String method, Map<String, String> paymentData) {
        this.id = UUID.randomUUID().toString();
        this.method = method;
        this.paymentData = paymentData;
        this.order = order;
        this.status = "PENDING";
    }

    public void setStatus(String status) {
        if (PaymentStatus.contains(status)) {
            this.status = status;
            if (this.order != null) {
                if ("SUCCESS".equals(status)) {
                    this.order.setStatus("SUCCESS");
                } else if ("REJECTED".equals(status)) {
                    this.order.setStatus("FAILED");
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }
}