package br.ipt.webtrafficcontroller.enums;

public enum TimerColor {
    RED("timer-red"),
    GREEN("timer-green"),
    YELLOW("timer-off"),
    OFF("timer"),
    UNKNOWN("");

    private final String cssClass;

    TimerColor(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }

    public static TimerColor fromClassName(String className) {
        for (TimerColor color : values()) {
            if (className.contains(color.getCssClass())) {
                return color;
            }
        }
        return UNKNOWN;
    }
}
