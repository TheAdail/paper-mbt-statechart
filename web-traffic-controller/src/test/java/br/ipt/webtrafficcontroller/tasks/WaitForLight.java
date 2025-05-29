package br.ipt.webtrafficcontroller.tasks;

import br.ipt.webtrafficcontroller.abilities.BrowseTheWeb;
import br.ipt.webtrafficcontroller.enums.LightColor;
import br.ipt.webtrafficcontroller.enums.Status;
import com.microsoft.playwright.Page.WaitForFunctionOptions;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

import static net.serenitybdd.screenplay.Tasks.instrumented;

public class WaitForLight implements Task {
    private final LightColor lightColor;
    private final String expectedClass;
    private final Status expectedStatus;
    private final int timeoutMs;

    public WaitForLight(LightColor lightColor, Status expectedStatus, int timeoutMs) {
        this.lightColor = lightColor;
        this.expectedStatus = expectedStatus;
        this.expectedClass = expectedStatus.equals(Status.OFF) ? LightColor.OFF.getCssClass() : lightColor.getCssClass();
        this.timeoutMs = timeoutMs;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb ability = actor.abilityTo(BrowseTheWeb.class);
        ability.getPage().waitForFunction(
            "document.getElementById('" + lightColor.getId() + "').classList.contains('" + expectedClass + "')",
            null,
            new WaitForFunctionOptions().setTimeout(timeoutMs)
        );
    }

    public static Task until(LightColor lightColor, Status expectedStatus, int timeoutMs) {
        return Task.where(
                String.format("Wait for the '%s' to be '%s' within %d ms",
                    lightColor.getId(), expectedStatus, timeoutMs),
                instrumented(WaitForLight.class, lightColor, expectedStatus, timeoutMs)
        );
    }
}