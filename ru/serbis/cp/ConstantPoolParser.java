package ru.serbis.cp;

import ru.serbis.Logger;

import java.util.List;

/**
 * Парсер пула констант
 */
public class ConstantPoolParser {

    /**
     * Производит парсинг пула констант. Получает на входи список строк
     * блока пула констант а выводит объект определение пула констант.
     *
     * @param lines список строк блока пула констнат
     * @return представление пула констнт
     */
    public ConstantPool parse(List<String> lines) {
        Logger.getInstance().log(Logger.LogType.INFO, "Парсинг пула констант.");
        ConstantPool cp = new ConstantPool();

        for (int i = 0; i < lines.size(); i++) {
            String l = lines.get(i);
            String[] spl = l.split("\t");
            if (spl.length == 0) {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка формата элемента пула констант. Строка " + i);
                return null;
            }
            if (spl.length < 3) {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка формата элемента пула констант. Элемент не может содержать меньше трех подэлементов. Строка " + i);
                return null;
            }

            short id;
            try {
                id = Short.parseShort(spl[0]);
            } catch (NumberFormatException ignored) {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка формата элемента пула констант. Идентификатор не является числом или выходит за размерность в 2 байта. Строка " + i);
                return null;
            }

            switch (spl[1]) {
                case "INT":
                    ConstInt constInt = new ConstInt();
                    constInt.setId(id);
                    try {
                        constInt.setValue(Integer.parseInt(spl[2]));
                    } catch (NumberFormatException ignored) {
                        Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка формата элемента пула констант. Ошибка парсинга значения константы типа INT. Строка " + i);
                        return null;
                    }
                    cp.put(constInt);

                    break;
                case "STRING":
                    ConstString constString = new ConstString();
                    constString.setId(id);
                    constString.setValue(spl[2]);
                    cp.put(constString);
                    break;
                case "LINK":
                    ConstLink constLink = new ConstLink();
                    constLink.setId(id);
                    try {
                        constLink.setLink(Short.parseShort(spl[2]));
                    } catch (NumberFormatException ignored) {
                        Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка формата элемента пула констант. Ошибка парсинга значения константы типа INT. Строка " + i);
                        return null;
                    }
                    cp.put(constLink);
                    break;
                default:
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка формата элемента пула констант. Неизвесеный тип элемента. Строка " + i);
                    return null;

            }
        }

        return cp;
    }
}
