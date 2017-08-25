package ru.serbis.code;

/**
 * Инструкция LIC
 */
public class Bipush extends Ins {
    private byte value;

    public Bipush() {
        size = 2;
        code = 0x10;
    }

    public byte getValue() {
        return value;
    }

    public void setValue(byte value) {
        this.value = value;
    }
}
