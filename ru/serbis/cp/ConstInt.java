package ru.serbis.cp;

/**
 * Определение целочисленной константы
 */
public class ConstInt extends Const {
    /** Целое число */
    private int value;

    public ConstInt() {
        super.setType((byte) 0x00);
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Возращает размер константы в байтах
     *
     * @return количество байт
     */
    @Override
    public int getSize() {
        return 4;
    }
}
