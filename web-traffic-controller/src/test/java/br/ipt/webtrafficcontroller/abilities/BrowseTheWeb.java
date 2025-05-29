package br.ipt.webtrafficcontroller.abilities;

import com.microsoft.playwright.Page;
import net.serenitybdd.screenplay.Ability;

public class BrowseTheWeb implements Ability {
    private final Page page;

    public BrowseTheWeb(Page page) {
        this.page = page;
    }

    public Page getPage() {
        return page;
    }

    public static BrowseTheWeb with(Page page) {
        return new BrowseTheWeb(page);
    }
}
