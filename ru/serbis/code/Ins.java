package ru.serbis.code;

/**
 * Суперкласс инструкции
 */
public class Ins {
    int size;
    byte code;

    public byte getCode() {
        return code;
    }

    public void setCode(byte code) {
        this.code = code;
    }
    /**
     * Возвращает размер инструкции в байтах
     *
     * @return размер инструкции в байтах
     */
    public int getSize() {
        return size;
    }

}
