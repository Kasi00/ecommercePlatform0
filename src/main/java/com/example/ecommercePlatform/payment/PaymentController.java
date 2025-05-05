package com.example.ecommercePlatform.payment;

import com.example.ecommercePlatform.payment.model.PaymentRequest;
import com.example.ecommercePlatform.payment.model.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ReflectiveScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/create-payment")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentRequest request) {
         PaymentResponse response=paymentService.createPayment(request);
        return ResponseEntity.ok(response);
    }

}
