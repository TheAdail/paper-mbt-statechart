package br.ipt.webtrafficcontroller.enums;

public enum Mode {
    OFF("OFF"),
    NORMAL("NORMAL"),
    BLINKING("BLINKING"),
    UNKNOWN("UNKNOWN");

    private final String text;

    Mode(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Mode fromText(String text) {
        for (Mode mode : values()) {
            if (mode.getText().equalsIgnoreCase(text)) {
                return mode;
            }
        }
        return UNKNOWN;
    }
}