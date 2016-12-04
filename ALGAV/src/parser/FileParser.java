package parser;

import java.io.*;
import java.util.Arrays;
import java.util.Set;

public class FileParser implements Runnable {
    private static final String SEPARATOR = " ";

    private final Reader source;
    private Set<String> strings = null;

    FileParser(String source, Set<String> strings) {
        Reader source1;
        try {
            source1 = new FileReader(source);
        } catch (FileNotFoundException e) {
            source1 = null;
            System.err.println("Source incorrect :" + source);
        }
        this.source = source1;
        this.strings = strings;
    }

    Set<String> readFile() {
        try (BufferedReader reader = new BufferedReader(source)) {

            reader.lines().forEach(line -> strings.addAll(Arrays.asList(line.split(SEPARATOR))));

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return strings;
    }

    @Override
    public void run() {
        readFile();
    }
}