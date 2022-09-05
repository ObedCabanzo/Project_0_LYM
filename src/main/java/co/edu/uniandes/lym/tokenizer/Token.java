package main.java.co.edu.uniandes.lym.tokenizer;

public class Token {
    private final String value;
    private final TokenType type;

    public Token(String value, TokenType type){
        this.value = value;
        this.type = type;
    }
    
    public String getType() {
    	return this.type.getTypeName();
    }
    
    public String getValue(){
    	return this.value;
    }

    @Override
    public String toString(){
        return String.format("%s('%s')", type.getTypeName(), value);
    }
}
