package ru.serbis.code;

/**
 * Инструкция IADD
 */
public class Return extends Ins {
    public Return() {
        size = 1;
        code = (byte) 0xB1;
    }
}
