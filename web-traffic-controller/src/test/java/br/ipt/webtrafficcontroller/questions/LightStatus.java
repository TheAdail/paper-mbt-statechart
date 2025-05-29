package br.ipt.webtrafficcontroller.questions;

import br.ipt.webtrafficcontroller.abilities.BrowseTheWeb;
import br.ipt.webtrafficcontroller.enums.LightColor;
import br.ipt.webtrafficcontroller.enums.Status;
import net.serenitybdd.screenplay.Question;

public class LightStatus {

    public static Question<Status> of(LightColor lightColor) {
        return Question
            .about(String.format("the status of the '%s'", lightColor.getId()))
            .answeredBy(actor -> {
                BrowseTheWeb ability = actor.abilityTo(BrowseTheWeb.class);
                String className = ability
                    .getPage()
                    .locator('#' + lightColor.getId())
                    .getAttribute("class");
               return className.contains(lightColor.getCssClass()) ? Status.ON : Status.OFF;
            });
    }
}