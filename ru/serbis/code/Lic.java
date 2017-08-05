package ru.serbis.code;

/**
 * Инструкция LIC
 */
public class Lic extends Ins {
    private int peid;

    public Lic() {
        size = 5;
        code = 0x06;
    }

    public int getPeid() {
        return peid;
    }

    public void setPeid(short peid) {
        this.peid = peid;
    }
}
