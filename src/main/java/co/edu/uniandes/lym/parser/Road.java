package main.java.co.edu.uniandes.lym.parser;

import java.util.ArrayList;
import java.util.HashMap;


public class Road {

    private String stateType; 
    private ArrayList<State> states;

    public Road(String stateType){

        this.stateType = stateType; 
        this.states = new ArrayList<>();
    }

    // walking the path from the first state to the last, throws error if the token could not travel all the way.
    public ArrayList<Object> walkState(int currentState,String currentInput, String currentCondition ) {
        
        
        ArrayList<Object> result = new ArrayList<Object>();


        String lastRuleOutput = "NOT DEFINED"; 

        State state = states.get(currentState);

        ArrayList<Rule> rules = state.getRules(); 
        Boolean inputValid = false;

        for (Rule rule : rules ) {

            if(rule.validate(currentInput, currentCondition)){

                inputValid = true;
                lastRuleOutput = rule.getOutput();

                print("Rule Found: " + rule.getInput() + "  -  "
                                                    + rule.getCondition() + "  -  " + rule.getOutput()  );
                
                break;
            }
        }

        result.add(0, inputValid) ;
        result.add(1, lastRuleOutput) ;
        result.add(2, rules.get(rules.size()-1)) ;
        result.add(3, rules.size()) ;


        return result;
    }

    public void addState(State state){
        this.states.add(state);

    }

    public void print(Object param){
        System.out.println(param);

    }

    // Return the number of states of the list "states"
    public int roadSize(){
        return this.states.size();   
    }

    // Return the number of states of the list "states"
    
    



    
}
