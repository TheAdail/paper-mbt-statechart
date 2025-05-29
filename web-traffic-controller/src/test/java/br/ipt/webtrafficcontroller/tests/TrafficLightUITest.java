package br.ipt.webtrafficcontroller.tests;

import br.ipt.webtrafficcontroller.ScreenPlayTestBase;
import br.ipt.webtrafficcontroller.enums.Mode;
import br.ipt.webtrafficcontroller.enums.Status;
import br.ipt.webtrafficcontroller.enums.TimerColor;
import br.ipt.webtrafficcontroller.tasks.*;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import br.ipt.webtrafficcontroller.abilities.BrowseTheWeb;
import br.ipt.webtrafficcontroller.enums.LightColor;
import br.ipt.webtrafficcontroller.questions.LightStatus;
import br.ipt.webtrafficcontroller.questions.ModeText;
import br.ipt.webtrafficcontroller.questions.TimerDisplay;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.actors.Stage;
import net.serenitybdd.screenplay.ensure.Ensure;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TrafficLightUITest extends ScreenPlayTestBase {
    // Durations are in milliseconds
    static int RED_LIGHT_DURATION      = 8000;
    static int YELLOW_LIGHT_DURATION   = 3000;
    static int GREEN_LIGHT_DURATION    = 5000;
    static int BLINKING_LIGHT_DURATION =  500;
    static int TIMER_CHECK_INTERVAL    = 2000;
    static int TIMER_BUFFER_INTERVAL   =  100;

    private Actor operator;

    @BeforeEach
    void setUpStage() {
        Stage stage = new Stage(new OnlineCast());
        OnStage.setTheStage(stage);
        operator = Actor.named("Transport Staff").whoCan(BrowseTheWeb.with(page));
    }

    @Test()
    @Order(1)
    void shouldStartInOffState() {
        operator.should(
            seeThat(ModeText.currentMode(), equalTo(Mode.OFF)),
            seeThat(LightStatus.of(LightColor.RED), equalTo(Status.OFF)),
            seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.OFF)),
            seeThat(LightStatus.of(LightColor.GREEN), equalTo(Status.OFF)),
            seeThat(TimerDisplay.value(), equalTo("OFF"))
        );
    }

    @Test
    @Order(2)
    void shouldToggleToNormalModeAndShowRed() {
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.OFF)));
        operator.attemptsTo(TogglePower.perform());
        operator.should(
            seeThat(ModeText.currentMode(), equalTo(Mode.NORMAL)),
            seeThat(LightStatus.of(LightColor.RED), equalTo(Status.ON)),
            seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.OFF)),
            seeThat(LightStatus.of(LightColor.GREEN), equalTo(Status.OFF)),
            seeThat(TimerDisplay.color(), equalTo(TimerColor.RED)),
            seeThat(TimerDisplay.value(), equalTo(String.valueOf(RED_LIGHT_DURATION / 1000)))
        );
        operator.attemptsTo(TogglePower.perform());
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.OFF)));
    }

    @Test
    @Order(3)
    void shouldSwitchToBlinkingMode() {
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.OFF)));
        operator.attemptsTo(TogglePower.perform());
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.NORMAL)));
        operator.attemptsTo(ToggleBlink.perform());
        operator.should(
            seeThat(ModeText.currentMode(), equalTo(Mode.BLINKING)),
            seeThat(TimerDisplay.color(), equalTo(TimerColor.OFF)),
            seeThat(TimerDisplay.value(), equalTo("OFF")),
            seeThat(LightStatus.of(LightColor.RED), equalTo(Status.OFF)),
            seeThat(LightStatus.of(LightColor.GREEN), equalTo(Status.OFF)),
            seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.ON))
        );
        operator.attemptsTo(WaitForLight.until(LightColor.YELLOW, Status.OFF, BLINKING_LIGHT_DURATION));
        operator.should(seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.OFF)));
        operator.attemptsTo(WaitForLight.until(LightColor.YELLOW, Status.ON, BLINKING_LIGHT_DURATION));
        operator.should(seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.ON)));
        operator.attemptsTo(TogglePower.perform());
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.OFF)));
    }

    @Test
    @Order(4)
    void shouldCycleThroughNormalStates() {
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.OFF)));
        operator.attemptsTo(TogglePower.perform());
        operator.should(
            seeThat(ModeText.currentMode(), equalTo(Mode.NORMAL)),
            seeThat(LightStatus.of(LightColor.RED), equalTo(Status.ON)),
            seeThat(TimerDisplay.color(), equalTo(TimerColor.RED)),
            seeThat(TimerDisplay.value(), equalTo(String.valueOf(RED_LIGHT_DURATION / 1000)))
        );
        operator.attemptsTo(WaitForInterval.of(TIMER_CHECK_INTERVAL));
        operator.should(seeThat(LightStatus.of(LightColor.RED), equalTo(Status.ON)));
        operator.attemptsTo(WaitForInterval.of(RED_LIGHT_DURATION - TIMER_CHECK_INTERVAL + TIMER_BUFFER_INTERVAL));
        operator.should(
            seeThat(LightStatus.of(LightColor.GREEN), equalTo(Status.ON)),
            seeThat(TimerDisplay.color(), equalTo(TimerColor.GREEN)),
            seeThat(TimerDisplay.value(), equalTo(String.valueOf(GREEN_LIGHT_DURATION / 1000)))
        );
        operator.attemptsTo(WaitForInterval.of(TIMER_CHECK_INTERVAL));
        operator.should(seeThat(LightStatus.of(LightColor.GREEN), equalTo(Status.ON)));
        operator.attemptsTo(WaitForInterval.of(GREEN_LIGHT_DURATION - TIMER_CHECK_INTERVAL + TIMER_BUFFER_INTERVAL));
        operator.should(
            seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.ON)),
            seeThat(TimerDisplay.value(), equalTo("OFF"))
        );
        operator.attemptsTo(WaitForInterval.of(TIMER_CHECK_INTERVAL));
        operator.should(seeThat(LightStatus.of(LightColor.YELLOW), equalTo(Status.ON)));
        operator.attemptsTo(WaitForInterval.of(YELLOW_LIGHT_DURATION - TIMER_CHECK_INTERVAL + TIMER_BUFFER_INTERVAL));
        operator.should(seeThat(LightStatus.of(LightColor.RED), equalTo(Status.ON)));
        operator.attemptsTo(TogglePower.perform());
        operator.should(seeThat(ModeText.currentMode(), equalTo(Mode.OFF)));

//        operator.attemptsTo(
//            Ensure.that(ModeText.currentMode()).isEqualTo(Mode.OFF),
//            TogglePower.perform(),
//            Ensure.that(ModeText.currentMode()).isEqualTo(Mode.NORMAL),

//            VerifyLightStateAndTimer.forDuration(LightColor.RED, Status.ON, RED_LIGHT_DURATION / 1000),
            // Checking the red timer value after 2000ms
//            WaitForInterval.of(TIMER_CHECK_INTERVAL),
//            VerifyLightStateAndTimer.forDuration(LightColor.RED, Status.ON, (RED_LIGHT_DURATION - TIMER_CHECK_INTERVAL) / 1000),
            // Check transition to green
//            WaitForLight.until(LightColor.GREEN, Status.ON, RED_LIGHT_DURATION - TIMER_CHECK_INTERVAL + TIMER_BUFFER_INTERVAL),
//            VerifyLightStateAndTimer.forDuration(LightColor.GREEN, Status.ON, GREEN_LIGHT_DURATION / 1000),
            // Checking the green timer value after 2000ms
//            WaitForInterval.of(TIMER_CHECK_INTERVAL),
//            VerifyLightStateAndTimer.forDuration(LightColor.GREEN, Status.ON, (GREEN_LIGHT_DURATION - TIMER_CHECK_INTERVAL) / 1000),
            // Check transition to yellow
//            WaitForLight.until(LightColor.YELLOW, Status.ON, GREEN_LIGHT_DURATION - TIMER_CHECK_INTERVAL + TIMER_BUFFER_INTERVAL),
//            VerifyLightStateAndTimer.forDuration(LightColor.YELLOW, Status.ON, "OFF"),
            // Checking the yellow timer value after 2000ms
//            WaitForInterval.of(TIMER_CHECK_INTERVAL),
//            VerifyLightStateAndTimer.forDuration(LightColor.YELLOW, Status.ON, "OFF"),
            // Check transition to red
//            WaitForLight.until(LightColor.RED, Status.ON, YELLOW_LIGHT_DURATION - TIMER_CHECK_INTERVAL + TIMER_BUFFER_INTERVAL),
//            VerifyLightStateAndTimer.forDuration(LightColor.RED, Status.ON, RED_LIGHT_DURATION / 1000),
//            TogglePower.perform(),
//            Ensure.that(ModeText.currentMode()).isEqualTo(Mode.OFF)
//        );
    }

}