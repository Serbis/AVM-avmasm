package ru.serbis.code;

/**
 * Инструкция ILOAD
 */
public class ILoad extends Ins {
    private byte var;

    public ILoad() {
        size = 2;
        code = 0x04;
    }

    public byte getVar() {
        return var;
    }

    public void setVar(byte var) {
        this.var = var;
    }

}
