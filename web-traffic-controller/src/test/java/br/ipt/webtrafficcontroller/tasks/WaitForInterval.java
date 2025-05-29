package br.ipt.webtrafficcontroller.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class WaitForInterval implements Task {
    private final int intervalMs;

    public WaitForInterval(int intervalMs) {
        this.intervalMs = intervalMs;
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        try {
            Thread.sleep(intervalMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting", e);
        }
    }

    public static WaitForInterval of(int intervalMs) {
        return new WaitForInterval(intervalMs);
    }
}
