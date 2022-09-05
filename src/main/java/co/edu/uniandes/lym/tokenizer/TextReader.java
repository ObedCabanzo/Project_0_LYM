package main.java.co.edu.uniandes.lym.tokenizer;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Optional;

public class TextReader {
    private static final Charset encoding = Charset.defaultCharset();
    private final File file;
    private Reader buffer;

    public TextReader(File file){
        this.file = file;
        init();
    }

    private void init() {
        InputStream in;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Reader reader = new InputStreamReader( in , encoding);
        // buffer for efficiency
        buffer = new BufferedReader(reader);
    }

    public Optional<String> getNextChar(){
        int c;
        Optional<String> chr = Optional.empty();
        try {
            if ((c = buffer.read()) != -1){
                chr = Optional.of(String.valueOf((char) c));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return chr;
    }

}
