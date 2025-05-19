package it.erika.gymtrack.controllers;

import it.erika.gymtrack.services.PaymentService;
import java.util.UUID;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PutMapping(path = "{id}/pay")
    public void pay(@PathVariable(name = "id") UUID id) {
        paymentService.pay(id);
    }
}
