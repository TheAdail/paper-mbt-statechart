package br.ipt.webtrafficcontroller.tasks;

import br.ipt.webtrafficcontroller.actions.Click;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class TogglePower implements Task {
    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on("button:text(\"Toggle Power\")"));
    }

    public static TogglePower perform() {
        return new TogglePower();
    }
}