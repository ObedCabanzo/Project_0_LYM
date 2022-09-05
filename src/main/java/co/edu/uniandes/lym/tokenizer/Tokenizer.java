package main.java.co.edu.uniandes.lym.tokenizer;
import java.util.LinkedList;
import java.util.Optional;

public class Tokenizer {
    private final Terminals terminals;

    private final LinkedList<Token> tokens;

    public Tokenizer(Terminals terminals){
        this.terminals = terminals;
        tokens = new LinkedList<>();
    }

    public LinkedList<Token> tokenize(TextReader textReader){
        Optional<String> chr = textReader.getNextChar();
        String token = "";
        while (chr.isPresent()){
            if (terminals.isPartialMatch(token + chr.get())){
                token += chr.get();
                chr = textReader.getNextChar();
            } else {
                if (terminals.isMatch(token)){
                    tokens.add(terminals.createToken(token));
                    token = "";
                } else { throw new RuntimeException("INVALID INPUT"); }
            }
        }
        if (terminals.isMatch(token)) tokens.add(terminals.createToken(token));
        else { throw new RuntimeException("INVALID INPUT"); }
        return tokens;
    }

}
