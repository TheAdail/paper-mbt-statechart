package br.ipt.webtrafficcontroller.tasks;

import br.ipt.webtrafficcontroller.enums.LightColor;
import br.ipt.webtrafficcontroller.enums.Status;
import br.ipt.webtrafficcontroller.enums.TimerColor;
import br.ipt.webtrafficcontroller.questions.LightStatus;
import br.ipt.webtrafficcontroller.questions.TimerDisplay;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.ensure.Ensure;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class VerifyLightStateAndTimer implements Task {
    private final LightColor lightColor;
    private final Status status;
    private final String duration;

    public VerifyLightStateAndTimer(LightColor lightColor, Status status, String duration) {
        this.lightColor = lightColor;
        this.status = status;
        this.duration = duration;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
            Ensure.that(LightStatus.of(lightColor)).isEqualTo(status),
            Ensure.that(TimerDisplay.color()).isEqualTo(TimerColor.valueOf(
                (duration.equals("OFF") ? LightColor.OFF : lightColor).name()
            )),
            Ensure.that(TimerDisplay.value()).isEqualTo(duration)
        );
    }

    public static Task forDuration(LightColor lightColor, Status status, int duration) {
        return Task.where(
            String.format("Verify the '%s' light is '%s' with a timer value of '%d'",
                lightColor.name(), status.name(), duration),
            instrumented(VerifyLightStateAndTimer.class, lightColor, status, String.valueOf(duration))
        );
    }

    public static Task forDuration(LightColor lightColor, Status status, String duration) {
        return Task.where(
            String.format("Verify the '%s' light is '%s' with a timer value of '%s'",
                lightColor.name(), status.name(), duration),
            instrumented(VerifyLightStateAndTimer.class, lightColor, status, duration)
        );
    }
}