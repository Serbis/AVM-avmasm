package ru.serbis.cp;

/**
 * Определение ссылочной константы
 */
public class ConstLink extends Const {
    /** Номер ячейки пула констант на которую ведет ссылка */
    private short link;

    public ConstLink() {
        super.setType((byte) 0x02);
    }

    public short getLink() {
        return link;
    }

    public void setLink(short link) {
        this.link = link;
    }

    /**
     * Возращает размер константы в байтах
     *
     * @return количество байт
     */
    @Override
    public int getSize() {
        return 4; //Потому что эта константа транслируется в адрес типа INT
    }
}
