package de.seifenarts.controller;

import de.seifenarts.service.interfaces.KlarnaPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/webhooks")
@RequiredArgsConstructor
public class KlarnaWebhookController {

   private final KlarnaPaymentService klarnaPaymentService;

    @PostMapping("/klarna")
    public ResponseEntity<Void> handleKlarnaWebhook(@RequestBody String payload) {
        klarnaPaymentService.handleWebhook(payload);
        return ResponseEntity.ok().build();
    }
}
