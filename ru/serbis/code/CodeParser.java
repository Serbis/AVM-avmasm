package ru.serbis.code;

import ru.serbis.Logger;
import ru.serbis.asm.Code;
import ru.serbis.asm.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * Пареср ассмблерного кода
 */
public class CodeParser {

    /**
     * Производит парсинг кода, получив на вход определение кода, дополняет
     * его инструкциями и возвращает в качестве результата
     *
     * @param code определение кода
     * @return модифицированный объект определения кода
     */
    public Code parse(Code code) {
        Code cd = new Code(code);
        Logger.getInstance().log(Logger.LogType.INFO, "Парсинг кода.");

        for (Method m: cd.getMethods()) {
            List<Ins> insList = processCodeBlock(m.getLines());
            if (insList == null)
                return null;
            m.setIstrunctions(insList);
        }

        return cd;
    }

    /**
     * Распарсивает отдельный блок инструкций, получив на вход список строк
     * выдает на выходе список инструкций
     *
     * @param lines список строк
     * @return список инструкций
     */
    private List<Ins> processCodeBlock(List<String> lines) {
        List<Ins> insList = new ArrayList<>();

        for(int i = 0; i < lines.size(); i++) {
            String l = lines.get(i).replaceAll("//.*", ""); //Убрать комментарии

            if (l.indexOf("iadd") == 0) {
                insList.add(new IAdd());
            } else if (l.indexOf("isub") == 0) {
                insList.add(new ISub());
            } else if (l.indexOf("idiv") == 0) {
                insList.add(new IDiv());
            } else if (l.indexOf("imul") == 0) {
                insList.add(new IMul());
            } else if (l.indexOf("istore") == 0) {
                String spl[] = l.split("_");
                if (spl.length != 2) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Некорректный аргумент инструкции istore. Строка " + i);
                    return null;
                }
                spl[1] = clean(spl[1]);
                IStore iStore = new IStore();
                try {
                    iStore.setVar(Byte.parseByte(spl[1]));
                } catch (Exception e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неверный тип аргумента инструкции istore.  Строка " + i);
                    return null;
                }
                insList.add(iStore);
            } else if (l.indexOf("iload") == 0) {
                String spl[] = l.split("_");
                if (spl.length != 2) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Некорректный аргумент инструкции iload. Строка " + i);
                    return null;
                }
                spl[1] = clean(spl[1]);
                ILoad iLoad = new ILoad();
                try {
                    iLoad.setVar(Byte.parseByte(spl[1]));
                } catch (Exception e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неверный тип аргумента инструкции iLoad.  Строка " + i);
                    return null;
                }
                insList.add(iLoad);
            } else if (l.indexOf("lic") == 0) {
                String spl[] = l.split(" ");
                if (spl.length != 2) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Некорректный аргумент инструкции lic. Строка " + i);
                    return null;
                }
                spl[1] = clean(spl[1]);
                Lic lic = new Lic();
                try {
                    lic.setPeid(Short.parseShort(spl[1]));
                } catch (Exception e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неверный тип аргумента инструкции lic.  Строка " + i);
                    return null;
                }
                insList.add(lic);
            } else if (l.indexOf("invokehardware") == 0) {
                String spl[] = l.split(" ");
                if (spl.length != 2) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Некорректный аргумент инструкции invokehardware. Строка " + i);
                    return null;
                }
                Invokeharware invokeharware = new Invokeharware();
                try {
                    invokeharware.setCall(Byte.parseByte(spl[1]));
                } catch (Exception e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неверный тип аргумента инструкции invokehardware.  Строка " + i);
                    return null;
                }
                insList.add(invokeharware);
            }  else if (l.indexOf("if_icmpeq") == 0) {
                String spl[] = l.split(" ");
                if (spl.length != 2) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Некорректный аргумент инструкции if_icmpeq. Строка " + i);
                    return null;
                }
                If_icmpeq if_icmpeq = new If_icmpeq();
                try {
                    if_icmpeq.setInsn(Short.parseShort(spl[1]));
                } catch (Exception e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неверный тип аргумента инструкции if_icmpeq.  Строка " + i);
                    return null;
                }
                insList.add(if_icmpeq);
            } else if (l.indexOf("return") == 0) {
                insList.add(new Return());
            } else if (l.indexOf("bipush") == 0) {
                String spl[] = l.split(" ");
                if (spl.length != 2) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Некорректный аргумент инструкции bipush. Строка " + i);
                    return null;
                }
                spl[1] = clean(spl[1]);
                Bipush bipush = new Bipush();
                try {
                    bipush.setValue(Byte.parseByte(spl[1]));
                } catch (Exception e) {
                    Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неверный тип аргумента инструкции bipush.  Строка " + i);
                    return null;
                }
                insList.add(bipush);
            } else {
                Logger.getInstance().log(Logger.LogType.ERROR, "Ошибка парсинга кода. Неизвестная инструкция. Строка " + i);
                return null;
            }
        }

        return insList;
    }

    /**
     * Удаляет все пробелы и табуляции из строки
     *
     * @param str строка для очистки
     * @return очищенная строка
     */
    private String clean(String str) {
        String rs = str.replaceAll(" ", "");
        rs = rs.replaceAll("\t", "");

        return rs;
    }

}
