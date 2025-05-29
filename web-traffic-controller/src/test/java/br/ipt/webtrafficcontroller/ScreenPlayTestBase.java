package br.ipt.webtrafficcontroller;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class ScreenPlayTestBase {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false)); // Set headless to true for CI
    }

    @BeforeEach
    void createPage() {
        page = browser.newPage();
        page.navigate("http://localhost:8081");
    }

    @AfterEach
    void closePage() {
        if (page != null) {
            page.close();
        }
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }
}
