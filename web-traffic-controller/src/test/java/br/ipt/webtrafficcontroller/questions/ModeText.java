package br.ipt.webtrafficcontroller.questions;

import br.ipt.webtrafficcontroller.abilities.BrowseTheWeb;
import br.ipt.webtrafficcontroller.enums.Mode;
import net.serenitybdd.screenplay.Question;

public class ModeText {
    public static Question<Mode> currentMode() {
        return Question
            .about("the current mode")
            .answeredBy(actor -> {
                BrowseTheWeb ability = actor.abilityTo(BrowseTheWeb.class);
                String modeText = ability
                    .getPage()
                    .locator("#mode")
                    .textContent();
                return Mode.fromText(modeText);
            });
    }
}