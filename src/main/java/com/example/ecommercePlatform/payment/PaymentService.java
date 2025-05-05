package com.example.ecommercePlatform.payment;

import com.example.ecommercePlatform.payment.model.PaymentRequest;
import com.example.ecommercePlatform.payment.model.PaymentResponse;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final UserRepository userRepository;


    public PaymentResponse createPayment(PaymentRequest request) {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        Long amountInCents=user.getCart().getTotalPrice()*100;

        // 3. Build Stripe payment intent parameters

        PaymentIntentCreateParams params= PaymentIntentCreateParams.builder()
                .setAmount(amountInCents)
                .setCurrency(request.getCurrency())
                .setAutomaticPaymentMethods(
                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build()
                )
                .build();

        try{
            // 4. Call Stripe API to create a PaymentIntent
            PaymentIntent intent = PaymentIntent.create(params);
            // 5. Return the client secret to be used on the frontend
            return new PaymentResponse(intent.getClientSecret());
        }catch (StripeException exception){
            // 6. If Stripe API call fails, throw a runtime exception
            throw new RuntimeException("Stripe payment failed: " + exception.getStripeError());
        }
    }
}
