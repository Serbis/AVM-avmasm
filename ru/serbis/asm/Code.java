package ru.serbis.asm;

import ru.serbis.code.Ins;

import java.util.ArrayList;
import java.util.List;

public class Code {
    private List<Method> methods = new ArrayList<>();

    public Code() {
    }

    public Code(Code other) {
        this.methods = other.methods;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public int getMethodSignatureBlockSize() {
        int size = 0;
        for (Method m: methods) {
            size += 2;
            size += m.getSignature().length();
            size += 4;
            //for (Ins ins: m.getIstrunctions()) {
            //    size += ins.getSize();
            //}
        }

        return size;
    }

    public int getMethodAddress(int position) {
        int adr = 0;

        if (methods.get(position).getIstrunctions().size() == 0)
            return -1;

        for (int i = 0; i < position; i++) {
            for (Ins ins: methods.get(i).getIstrunctions()) {
                adr += ins.getSize();
            }
        }

        return adr;
    }
}
