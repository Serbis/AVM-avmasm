package ru.serbis.code;

/**
 * Инструкция ISTORE
 */
public class IStore extends Ins {
    private byte var;

    public IStore() {
        code = 0x05;
    }

    public byte getVar() {
        return var;
    }

    public void setVar(byte var) {
        this.var = var;
    }
}
