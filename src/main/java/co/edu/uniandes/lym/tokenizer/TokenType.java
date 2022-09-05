package main.java.co.edu.uniandes.lym.tokenizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TokenType {
    private final String typeName;

    private final Matcher matcher;

    public TokenType(String typeName, Pattern typePattern){
        this.typeName = typeName;
        matcher = typePattern.matcher("");
    }

    public String getTypeName(){ return typeName; }

    public boolean isMatch(String string){
        matcher.reset(string);
        return matcher.matches();
    }

    public boolean isPartialMatch(String string){
        matcher.reset(string);
        if (matcher.matches()) return true;
        return matcher.hitEnd();
    }

}
