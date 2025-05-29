package br.ipt.webtrafficcontroller.enums;

public enum LightColor {
    RED("redLight", "light-red"),
    YELLOW("yellowLight", "light-yellow"),
    GREEN("greenLight", "light-green"),
    OFF("off", "light-off");

    private final String id;
    private final String cssClass;

    LightColor(String id, String cssClass) {
        this.id = id;
        this.cssClass = cssClass;
    }

    public String getId() {
        return id;
    }

    public String getCssClass() {
        return cssClass;
    }
}
