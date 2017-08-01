package ru.serbis;

import ru.serbis.code.*;
import ru.serbis.cp.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;

/**
 * Генератор байт-кода
 */
public class BcGen {
    /** Размер заголовка в байтах*/
    private final int HEADER_SIZE = 4;

    /** Глобальный буффер для записи */
    private ByteBuffer bf;

    public boolean generateToFile(File of, ConstantPool cp, List<Ins> code) {
        Logger.getInstance().log(Logger.LogType.INFO, "Запуск генерации байт-кода.");

        if (!of.exists()) {
            try {
                if (!of.createNewFile()) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при создании выходящего файла. createNewFile вернул false.");

                    return false;
                }

            } catch (IOException e) {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при создании выходного файла. Ошибка ввода-вывода.");
                e.printStackTrace();
                return false;
            }
        }

        FileOutputStream fos;
        try {
           fos = new FileOutputStream(of);
        } catch (FileNotFoundException e) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при открытия fos в выходной файл.");
            e.printStackTrace();

            return false;
        }

        if (!processHeader(cp, fos))
            return false;
        if (!processConstantPool(cp, fos))
            return false;
        if (!processCode(cp, code, fos))
            return false;

        return true;
    }

    /**
     * Записывает в файл блок заголовка, заголовок состоит из :
     *      -Размер пула констант (4 байта)
     *
     * @param cp пул констант
     * @param fos поток записи
     * @return результат выполнения операции
     */
    private boolean processHeader(ConstantPool cp, FileOutputStream fos) {
        Logger.getInstance().log(Logger.LogType.INFO, "Генерация заголовка.");
        int size = cp.getTotalSizeInBytes();
        bf = ByteBuffer.allocate(4);
        bf.putInt(size);

        try {
            fos.write(bf.array());
        } catch (IOException e) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи заголовка. Ошибка записи размера пула констант.");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Записывает в файл блок пула констант.
     *
     * @param cp пул констант
     * @param fos поток записи
     * @return результат выполнения операции
     */
    private boolean processConstantPool(ConstantPool cp, FileOutputStream fos) {
        Logger.getInstance().log(Logger.LogType.INFO, "Генерация пула констант.");
        Iterator<Const> itc = cp.getIterator();
        while (itc.hasNext()) {
            Const cn = itc.next();
            if (cn instanceof ConstInt) {
                ConstInt constInt = (ConstInt) cn;
                try {
                    bf = ByteBuffer.allocate(4);
                    bf.putInt(constInt.getValue());
                    fos.write(bf.array());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи пула констант. Ошибка ввода-вывода при записи константы типа INT с id " + cn.getId() + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (cn instanceof ConstLink) {
                ConstLink constLink = (ConstLink) cn;
                try {
                    bf = ByteBuffer.allocate(4);
                    bf.putInt(getCpEntryAddress(constLink.getLink(), cp));
                    fos.write(bf.array());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи пула констант. Ошибка ввода-вывода при записи константы типа LINK с id " + cn.getId() + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (cn instanceof ConstString) {
                ConstString constString = (ConstString) cn;
                try {
                    bf = ByteBuffer.allocate(2);
                    bf.putShort((short) constString.getValue().length());
                    fos.write(bf.array());
                    fos.write(constString.getValue().getBytes());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи пула констант. Ошибка ввода-вывода при записи константы типа LINK с id " + cn.getId() + " .");
                    e.printStackTrace();
                    return false;
                }
            } else {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи пула констант. Неизвестная конатнта c id " + cn.getId() + ".");
            }
       }

       return true;
    }

    /**
     * Записывает в файл блок кода
     *
     * @param cp пул констант
     * @param code список инструкций
     * @param fos поток записи
     * @return результат выполнения операции
     */
    private boolean processCode (ConstantPool cp, List<Ins> code, FileOutputStream fos) {
        Logger.getInstance().log(Logger.LogType.INFO, "Генерация байт-кода.");
        for (int i = 0; i < code.size(); i++) {
            Ins ins = code.get(i);
            if (ins instanceof IAdd) {
                IAdd iAdd = (IAdd) ins;
                try {
                    fos.write(iAdd.getCode());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции iadd " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof ISub) {
                ISub iSub = (ISub) ins;
                try {
                    fos.write(iSub.getCode());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции isub " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof IDiv) {
                IDiv iDiv = (IDiv) ins;
                try {
                    fos.write(iDiv.getCode());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции idiv " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof IMul) {
                IMul iMul = (IMul) ins;
                try {
                    fos.write(iMul.getCode());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции imul " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof ILoad) {
                ILoad iLoad = (ILoad) ins;
                try {
                    fos.write(iLoad.getCode());
                    fos.write(iLoad.getVar());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции iload " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof IStore) {
                IStore iStore = (IStore) ins;
                try {
                    fos.write(iStore.getCode());
                    fos.write(iStore.getVar());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции store " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof Lic) {
                Lic lic = (Lic) ins;
                bf = ByteBuffer.allocate(4);
                bf.putInt(getCpEntryAddress(lic.getPeid(), cp));
                try {
                    fos.write(lic.getCode());
                    fos.write(bf.array());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции lic " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else if (ins instanceof Invokeharware) {
                Invokeharware invokeharware = (Invokeharware) ins;
                try {
                    fos.write(invokeharware.getCode());
                    fos.write(invokeharware.getCall());
                } catch (IOException e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Ошибка ввода-вывода при записи инструкции invokeharware " + i + " .");
                    e.printStackTrace();
                    return false;
                }
            } else {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка при записи кода. Неизвестная инструкция на строке " + i + " .");
            }
        }

        return true;
    }

    private int getCpEntryAddress(short id, ConstantPool cp) {
        return HEADER_SIZE + cp.getShiftById(id);
    }
}
