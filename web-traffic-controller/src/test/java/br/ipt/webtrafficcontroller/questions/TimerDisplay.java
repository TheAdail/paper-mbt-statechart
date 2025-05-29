package br.ipt.webtrafficcontroller.questions;

import br.ipt.webtrafficcontroller.abilities.BrowseTheWeb;
import br.ipt.webtrafficcontroller.enums.TimerColor;
import net.serenitybdd.screenplay.Question;

public class TimerDisplay {
    public static Question<TimerColor> color() {
        return Question
            .about("the current timer color")
            .answeredBy(actor -> {
                BrowseTheWeb ability = actor.abilityTo(BrowseTheWeb.class);
                String className = ability
                    .getPage()
                    .locator("#timerDisplay")
                    .getAttribute("class");
                return TimerColor.fromClassName(className);
            });
    }

    public static Question<String> value() {
        return Question
            .about("the current timer value")
            .answeredBy(actor -> {
                BrowseTheWeb ability = actor.abilityTo(BrowseTheWeb.class);
                return ability
                    .getPage()
                    .locator("#timerDisplay")
                    .textContent();
            });
    }
}