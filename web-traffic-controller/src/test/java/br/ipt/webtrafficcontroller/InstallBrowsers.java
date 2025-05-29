package br.ipt.webtrafficcontroller;

import com.microsoft.playwright.*;

public class InstallBrowsers {
    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            playwright.firefox().launch(new BrowserType.LaunchOptions().setHeadless(true));
            playwright.webkit().launch(new BrowserType.LaunchOptions().setHeadless(true));
            System.out.println("Browsers installed successfully.");
        }
    }
}