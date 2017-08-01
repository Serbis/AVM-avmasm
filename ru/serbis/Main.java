package ru.serbis;

import java.io.File;

public class Main {

    public static void main(String[] args) {
	Linker linker = new Linker();
	linker.link(new File("/home/serbis/tmp/fft.avma"), new File("/home/serbis/tmp/fft.avmb"));
    }
}
