package ru.serbis.cp;

/**
 * Суперкласс определния константы
 */
public class Const {
    /** Номер константы */
    private short id;
    /** Тип константы */
    private byte type;
    /** Смещение константы от начала пула констант */
    private int shift;

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    /**
     * Возращает размер константы в байтах
     *
     * @return количество байт
     */
    public int getSize() {
        return 3;
    }
}
