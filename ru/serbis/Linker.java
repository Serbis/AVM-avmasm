package ru.serbis;

import ru.serbis.code.CodeParser;
import ru.serbis.code.Ins;
import ru.serbis.cp.ConstantPool;
import ru.serbis.cp.ConstantPoolParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Основной класс линковщика, производит корневые оперции ассемблирования
 */
public class Linker {
    /** Список строк блока пула констант */
    private List<String> constantPoolBlock;
    /** Список строк блока кода */
    private List<String> codeBlock;

    /**
     * Линкуент входящий файл ассемблерного кода. В результате работы данного
     * метода будет сгенерирован программный файл с байт-кодом виртуальной
     * машины.
     *
     * @param inputFile входящий файл с ассемблером
     * @param outFile выходной файл с байт-кодом (может не существовать)
     */
    public void link(File inputFile, File outFile) {
        Logger.getInstance().log(Logger.LogType.INFO, "Запуск парсинга файла.");
        CodeParser codeParser = new CodeParser(); //Парсер кода
        ConstantPoolParser cpp = new ConstantPoolParser(); //Парсер пула констант
        BcGen bcGen = new BcGen(); //Генератор байт-кода

        BufferedReader inr; //Пробуем прочитать входящий файл
        try {
            inr = new BufferedReader(new FileReader(inputFile));
        } catch (FileNotFoundException ignored) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Входщий файл не найден");

            return;
        }

        splitToBlocks(inr); //Распарсить файл на блоки

        ConstantPool constantPool = cpp.parse(constantPoolBlock); //Парсинг пула констант
        if (constantPool == null) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Критическая ошибка во время парсинга входящего файла.");
            return;
        }

        List<Ins> insList = codeParser.parse(codeBlock); //Парсинг кода
        if (insList == null) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Критическая ошибка во время парсинга входящего файла.");
            return;
        }

        if (!bcGen.generateToFile(outFile, constantPool, insList)) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Критическая ошибка во время генерации программного файла.");
        }

    }

    /**
     * Разделяет входящий файл на блоки строк, занося их в соответсующие
     * глобальные переменные.
     *
     * @param inr ридер входящего файла
     */
    private void splitToBlocks(BufferedReader inr) {
        Logger.getInstance().log(Logger.LogType.INFO, "Разбор файла на логические блоки.");
        constantPoolBlock = new ArrayList<>();
        codeBlock = new ArrayList<>();

        String line; //Обрабатываемая строка
        ParserMode mode = null; //В каком блоке данных находися парсер строк

        boolean clf;

        try {
            while ((line = inr.readLine()) != null) {
                if (line.contains("[CONSTANT POOL]")) {
                    mode = ParserMode.CONSTANT_POOL;
                    clf = true;
                } else if (line.contains("[CODE]")) {
                    mode = ParserMode.CODE;
                    clf = true;
                } else {
                    clf = false;
                }

                if (!clf) {
                    if (mode == null) {
                        Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка чтения входящего файла, не найден входной блок.");
                        return;
                    }
                    switch (mode) {
                        case CONSTANT_POOL:
                            constantPoolBlock.add(line);
                            break;
                        case CODE:
                            codeBlock.add(line);
                            break;
                    }
                }
            }
        } catch (IOException e) {
            Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка ввода-вывода при чтении файла");

            e.printStackTrace();
        }
    }

    /**
     * Режим работы блокового парсера
     */
    private enum ParserMode {
        CONSTANT_POOL, CODE
    }
}
