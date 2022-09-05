package main.java.co.edu.uniandes.lym.tokenizer;

import java.util.List;
import java.util.regex.Pattern;

public class Terminals {
    private final List<TokenType> tokenTypes;

    public Terminals(List<TokenType> tokenTypes){
        this.tokenTypes = tokenTypes;
    }

    public boolean isMatch(String string){
        return tokenTypes.stream().anyMatch(tp -> tp.isMatch(string));
    }

    public boolean isPartialMatch(String string){
        return tokenTypes.stream().anyMatch(tp -> tp.isPartialMatch(string));
    }

    public Token createToken(String tokenStr){
        TokenType tokenType = tokenTypes.stream().
                filter(tp -> tp.isMatch(tokenStr)).findAny().orElse(new TokenType(
                        "NON_TOKEN", Pattern.compile("(?!.*)")));
        return new Token(tokenStr, tokenType);
    }

}

