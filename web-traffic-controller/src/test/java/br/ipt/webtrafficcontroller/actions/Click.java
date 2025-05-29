package br.ipt.webtrafficcontroller.actions;

import br.ipt.webtrafficcontroller.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;

public class Click implements Interaction {
    private final String locator;

    public Click(String locator) {
        this.locator = locator;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        BrowseTheWeb ability = actor.abilityTo(BrowseTheWeb.class);
        ability.getPage().locator(locator).click();
    }

    public static Click on(String locator) {
        return new Click(locator);
    }
}