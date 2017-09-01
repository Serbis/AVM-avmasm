package ru.serbis.asm;

import ru.serbis.code.Ins;

import java.util.ArrayList;
import java.util.List;


public class Method {
    private String signature;
    private List<String> lines = new ArrayList<>();
    private List<Ins> istrunctions;

    public Method(String signature, List<String> lines) {
        this.signature = signature;
        this.lines = lines;
    }


    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public List<Ins> getIstrunctions() {
        return istrunctions;
    }

    public void setIstrunctions(List<Ins> istrunctions) {
        this.istrunctions = istrunctions;
    }
}
