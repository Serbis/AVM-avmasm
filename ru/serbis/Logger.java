package ru.serbis;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Выводит в стандартный вывод строки лога в заданном формате
 */
public class Logger {
    /** Экземпляр синглетона */
    private static Logger instance = null;
    /** Формат даты строки лога */
    private SimpleDateFormat sdf;

    /**
     * Ициниализирует синглетон в части форматы даты
     */
    private Logger() {
        sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
    }

    /**
     * Возаращает экземпляр синглетона
     *
     * @return экземпляр синглетона
     */
    public static Logger getInstance() {
        if (instance == null)
            instance = new Logger();

        return instance;
    }

    /**
     * Выводит с тандатный вывод форматированную строку лога
     *
     * @param lt тип записи
     * @param message текст лога
     */
    public void log(LogType lt, String message) {
        String sb = "[" + sdf.format(new Date()) + "]" +
                "[" + lt + "]" +
                " - " + message;

        System.out.println(sb);
    }

    /**
     * Типы логов
     */
    public enum LogType {
        INFO, ERROR, WARNING
    }

}
