package main.java.co.edu.uniandes.lym.tokenizer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainTokenizer {

    List<Token> tokens ;
    public MainTokenizer(){
        this.tokens = getTokens();
        
    }
    
    
    public List<Token> getTokens()
    {
            
        File file = new File("/Users/obedc/eclipse-workspace/Lym_P0/files/text.txt");
        TextReader text = new TextReader(file);
        List<TokenType> tokenTypes = new ArrayList<>();

        // Defining token types
        // keywords
        tokenTypes.add(new TokenType("PROGRAM_START_KEYWORD", Pattern.compile("PROG")));
        tokenTypes.add(new TokenType("PROGRAM_END_KEYWORD", Pattern.compile("GROP")));
        tokenTypes.add(new TokenType("PROCEDURE_START_KEYWORD", Pattern.compile("PROC")));
        tokenTypes.add(new TokenType("PROCEDURE_END_KEYWORD", Pattern.compile("CROP")));
        // Variable declaration
        tokenTypes.add(new TokenType("VAR_DECLARATION_KEYWORD", Pattern.compile("var")));
        // Control structures
        tokenTypes.add(new TokenType("IF_OPEN_KEYWORD", Pattern.compile("if")));
        tokenTypes.add(new TokenType("IF_END_KEYWORD", Pattern.compile("fi")));
        tokenTypes.add(new TokenType("ELSE_KEYWORD", Pattern.compile("else")));
        tokenTypes.add(new TokenType("WHILE_KEYWORD", Pattern.compile("while")));
        tokenTypes.add(new TokenType("DO_OPEN_KEYWORD", Pattern.compile("do")));
        tokenTypes.add(new TokenType("DO_END_KEYWORD", Pattern.compile("od")));
        tokenTypes.add(new TokenType("REPEAT_OPEN_KEYWORD", Pattern.compile("repeatTimes")));
        tokenTypes.add(new TokenType("REPEAT_END_KEYWORD", Pattern.compile("per")));
        // Commands
        tokenTypes.add(new TokenType("WALK_COMMAND_KEYWORD", Pattern.compile("walk")));
        tokenTypes.add(new TokenType("JUMP_COMMAND_KEYWORD", Pattern.compile("jump")));
        tokenTypes.add(new TokenType("JUMP_TO_COMMAND_KEYWORD", Pattern.compile("jumpTo")));
        tokenTypes.add(new TokenType("VEER_COMMAND_KEYWORD", Pattern.compile("veer")));
        tokenTypes.add(new TokenType("LOOK_COMMAND_KEYWORD", Pattern.compile("look")));
        tokenTypes.add(new TokenType("DROP_COMMAND_KEYWORD", Pattern.compile("drop")));
        tokenTypes.add(new TokenType("GRAB_COMMAND_KEYWORD", Pattern.compile("grab")));
        tokenTypes.add(new TokenType("GET_COMMAND_KEYWORD", Pattern.compile("get")));
        tokenTypes.add(new TokenType("FREE_COMMAND_KEYWORD", Pattern.compile("free")));
        tokenTypes.add(new TokenType("POP_COMMAND_KEYWORD", Pattern.compile("pop")));
        tokenTypes.add(new TokenType("PICK_COMMAND_KEYWORD", Pattern.compile("pick")));
        // Orientation and Guidance
        tokenTypes.add(new TokenType("D_KEYWORD", Pattern.compile("left")));
        tokenTypes.add(new TokenType("D_KEYWORD", Pattern.compile("right")));
        tokenTypes.add(new TokenType("D_KEYWORD", Pattern.compile("front")));
        tokenTypes.add(new TokenType("D_KEYWORD", Pattern.compile("back")));

        tokenTypes.add(new TokenType("O_KEYWORD", Pattern.compile("north")));
        tokenTypes.add(new TokenType("O_KEYWORD", Pattern.compile("south")));
        tokenTypes.add(new TokenType("O_KEYWORD", Pattern.compile("east")));
        tokenTypes.add(new TokenType("O_KEYWORD", Pattern.compile("west")));
        // Conditions and booleans
        tokenTypes.add(new TokenType("IS_FACING_CONDITION_KEYWORD", Pattern.compile("isfacing")));
        tokenTypes.add(new TokenType("IS_VALID_CONDITION_KEYWORD", Pattern.compile("isValid")));
        tokenTypes.add(new TokenType("CAN_WALK_CONDITION_KEYWORD", Pattern.compile("canWalk")));
        tokenTypes.add(new TokenType("NEGATION_KEYWORD", Pattern.compile("not")));
        tokenTypes.add(new TokenType("TRUE_KEYWORD", Pattern.compile("true")));
        tokenTypes.add(new TokenType("FALSE_KEYWORD", Pattern.compile("false")));

        // ID_TEXT has to be defined after the reserved words or keywords
        // Because the reserved words have priority, and I don't know how to negate regexes yet
        // The success of this depends on the operation of the Java Stream library, so it is prone to errors
        tokenTypes.add(new TokenType("ID", Pattern.compile("[a-zA-Z_]\\w*")));
        // Operators
        tokenTypes.add(new TokenType("PLUS_OPERATOR", Pattern.compile("\\+")));
        tokenTypes.add(new TokenType("SUB_OPERATOR", Pattern.compile("-")));
        tokenTypes.add(new TokenType("MUL_OPERATOR", Pattern.compile("\\*")));
        tokenTypes.add(new TokenType("DIV_OPERATOR", Pattern.compile("/")));
        tokenTypes.add(new TokenType("LESS_THAN_OPERATOR", Pattern.compile("<")));
        tokenTypes.add(new TokenType("GREATER_THAN_OPERATOR", Pattern.compile(">")));
        tokenTypes.add(new TokenType("LESSEQ_THAN_OPERATOR", Pattern.compile("<=")));
        tokenTypes.add(new TokenType("GREATEREQ_THAN_OPERATOR", Pattern.compile(">=")));
        tokenTypes.add(new TokenType("ASSIGNMENT_OPERATOR", Pattern.compile("=")));
        tokenTypes.add(new TokenType("NOT_EQUAL_OPERATOR", Pattern.compile("!=")));
        tokenTypes.add(new TokenType("EQUAL_OPERATOR", Pattern.compile("==")));
        // Order of precedence and parameters container
        tokenTypes.add(new TokenType("LEFT_PARENTHESIS", Pattern.compile("\\(")));
        tokenTypes.add(new TokenType("RIGHT_PARENTHESIS", Pattern.compile("\\)")));
        // Block delimiters
        tokenTypes.add(new TokenType("LEFT_CURLY_BRACKET", Pattern.compile("\\{")));
        tokenTypes.add(new TokenType("RIGHT_CURLY_BRACKET", Pattern.compile("}")));
        // Instruction separator
        tokenTypes.add(new TokenType("SEMICOLON", Pattern.compile(";")));
        // Probably parameter and variable separator
        tokenTypes.add(new TokenType("COMMA", Pattern.compile(",")));
        // Numbers
        tokenTypes.add(new TokenType("NUM", Pattern.compile("\\d+(\\.\\d+)?")));
        // Generic separators
        tokenTypes.add(new TokenType("SPACE", Pattern.compile(" +")));
        tokenTypes.add(new TokenType("LINE_BREAK", Pattern.compile("\\R")));
        tokenTypes.add(new TokenType("TAB", Pattern.compile("\\t")));

        Terminals terminals = new Terminals(tokenTypes);
        Tokenizer tokenizer = new Tokenizer(terminals);

        return tokenizer.tokenize(text);

    }

}