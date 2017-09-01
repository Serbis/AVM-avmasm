package ru.serbis.code;

/**
 * Инструкция IF_ICMPEQ
 */
public class If_icmpeq extends Ins {
    private short insn;

    public If_icmpeq() {
        size = 2;
        code = (byte) 0xA1;
    }

    public short getInsn() {
        return insn;
    }

    public void setInsn(short insn) {
        this.insn = insn;
    }
}
