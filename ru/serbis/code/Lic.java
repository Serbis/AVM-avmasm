package ru.serbis.code;

/**
 * Инструкция LIC
 */
public class Lic extends Ins {
    private short peid;

    public Lic() {
        code = 0x06;
    }

    public short getPeid() {
        return peid;
    }

    public void setPeid(short peid) {
        this.peid = peid;
    }
}
