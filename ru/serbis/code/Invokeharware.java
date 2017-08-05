package ru.serbis.code;

/**
 * Инструкция INVOKEHARDWARE
 */
public class Invokeharware extends Ins {
    private byte call;

    public Invokeharware() {
        size = 2;
        code = 0x07;
    }

    public byte getCall() {
        return call;
    }

    public void setCall(byte call) {
        this.call = call;
    }
}
