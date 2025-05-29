package br.ipt.webtrafficcontroller.service;

import br.ipt.webtrafficcontroller.statemachine.TrafficLightCtrl;
import com.yakindu.core.TimerService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Service
public class TrafficLightService {
    private final TrafficLightCtrl stateMachine;
    private final SimpMessagingTemplate messagingTemplate;
    private final TimerService timerService;

    public TrafficLightService(SimpMessagingTemplate messagingTemplate) {
        this.stateMachine = new TrafficLightCtrl();
        this.timerService = new TimerService();
        this.messagingTemplate = messagingTemplate;
    }

    @PostConstruct
    public void init() {
        // Set timer service
        stateMachine.setTimerService(timerService);
        // Subscribe to traffic light observables
        stateMachine.trafficLight().getDisplayRed().subscribe(v -> sendStateUpdate("RED"));
        stateMachine.trafficLight().getDisplayYellow().subscribe(v -> sendStateUpdate("YELLOW"));
        stateMachine.trafficLight().getDisplayGreen().subscribe(v -> sendStateUpdate("GREEN"));
        stateMachine.trafficLight().getDisplayNone().subscribe(v -> sendStateUpdate("NONE"));
        // Subscribe to timer observables
        stateMachine.timer().getUpdateTimerColor().subscribe(color -> sendTimerUpdate("color", color));
        stateMachine.timer().getUpdateTimerValue().subscribe(value -> sendTimerUpdate("value", value));
        // Start the state machine
        stateMachine.enter();
    }

    @PreDestroy
    public void shutdown() {
        stateMachine.exit();
        timerService.cancel();
    }

    public void toggleBlink() {
        stateMachine.raiseToggleBlink();
    }

    public void togglePower() {
        stateMachine.raiseTogglePower();
    }

    public String getCurrentState() {
        if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_NORMAL_NORMAL_RED)) {
            return "RED";
        } else if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_NORMAL_NORMAL_YELLOW)) {
            return "YELLOW";
        } else if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_NORMAL_NORMAL_GREEN)) {
            return "GREEN";
        } else if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_BLINKING_BLINKING_YELLOW)) {
            return "BLINKING YELLOW";
        } else if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_BLINKING_BLINKING_BLACK)) {
            return "BLACK";
        } else {
            return "NONE"; // MAIN_OFF or other states
        }
    }

    public String getCurrentMode() {
        if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_BLINKING)) {
            return "BLINKING";
        } else if (stateMachine.isStateActive(TrafficLightCtrl.State.MAIN_OPERATING_TRAFFICLIGHT_NORMAL)) {
            return "NORMAL";
        } else {
            return "OFF";
        }
    }

    private void sendStateUpdate(String state) {
        messagingTemplate.convertAndSend("/topic/state", state);
    }

    private void sendTimerUpdate(String type, Object value) {
        messagingTemplate.convertAndSend("/topic/timer", new TimerUpdate(type, value));
    }

    // DTO for timer updates
    public static class TimerUpdate {
        private final String type;
        private final Object value;

        public TimerUpdate(String type, Object value) {
            this.type = type;
            this.value = value;
        }

        public String getType() {
            return type;
        }

        public Object getValue() {
            return value;
        }
    }
}
