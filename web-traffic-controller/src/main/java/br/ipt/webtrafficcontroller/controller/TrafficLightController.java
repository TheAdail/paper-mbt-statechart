package br.ipt.webtrafficcontroller.controller;

import br.ipt.webtrafficcontroller.service.TrafficLightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/traffic")
public class TrafficLightController {
    private final TrafficLightService service;

    public TrafficLightController(TrafficLightService service) {
        this.service = service;
    }

    @GetMapping("/state")
    public ResponseEntity<String> getState() {
        return ResponseEntity.ok(service.getCurrentState());
    }

    @GetMapping("/mode")
    public ResponseEntity<String> getMode() {
        return ResponseEntity.ok(service.getCurrentMode());
    }

    @PostMapping("/toggleBlink")
    public ResponseEntity<String> toggleBlink() {
        service.toggleBlink();
        return ResponseEntity.ok(service.getCurrentMode());
    }

    @PostMapping("/togglePower")
    public ResponseEntity<String> togglePower() {
        service.togglePower();
        return ResponseEntity.ok(service.getCurrentMode());
    }
}
