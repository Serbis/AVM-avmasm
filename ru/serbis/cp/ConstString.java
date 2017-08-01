package ru.serbis.cp;

/**
 * Определение строковой константы
 */
public class ConstString extends Const {
    /** Строка */
    private String value;

    public ConstString() {
        super.setType((byte) 0x01);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Возращает размер константы в байтах
     *
     * @return количество байт
     */
    @Override
    public int getSize() {
        return 2 + value.length();
    }
}
