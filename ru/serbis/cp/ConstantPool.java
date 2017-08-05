package ru.serbis.cp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Пул констант
 */
public class ConstantPool {
    /** Список констант */
    private List<Const> pool;

    /**
     * Инициализирует список констант
     */
    public ConstantPool() {
        pool = new ArrayList<>();
    }

    /**
     * Размещает новую константу в пуле
     *
     * @param c константа
     */
    public void put(Const c) {
        int shift = getLastShift();
        if (shift == -1)
            c.setShift(0);
        else
            c.setShift(shift + getLastSize());

        pool.add(c);
    }

    /**
     * Возвращает адресный сдвиг последней константы в пуле
     *
     * @return сдвиг в байтах
     */
    public int getLastShift() {
        if (pool.size() == 0)
            return -1;
        return pool.get(pool.size() - 1).getShift();
    }

    /**
     * Вовразает резмер последнего элемента в байтах
     *
     * @return размер элемента
     */
    public int getLastSize() {
        if (pool.size() == 0)
            return 0;
        return pool.get(pool.size() - 1).getSize();
    }

    /**
     * Возаращает адресный сдвиг константы с заданным номеро ячейки
     *
     * @param id номер ячейи
     * @return сдвиг в байтах
     */
    public int getShiftById(int id) {
        int shift = -1;
        for (Const c: pool) {
            if (c.getId() == id)
                shift = c.getShift();
        }

        return shift;
    }

    /**
     * Возвращает размер пула констант в байтах
     *
     * @return размер пула констант в байтах
     */
    public int getTotalSizeInBytes() {
        int size = 0;

        for (Const c: pool) {
            size += c.getSize();
        }

        return size;
    }

    /**
     * Возвращает итератор пула
     *
     * @return итератор
     */
    public Iterator<Const> getIterator() {
        return pool.iterator();
    }
}
