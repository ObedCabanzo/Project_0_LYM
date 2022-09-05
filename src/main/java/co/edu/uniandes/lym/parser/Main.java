package main.java.co.edu.uniandes.lym.parser;

import java.util.ArrayList;
import java.util.List;

import main.java.co.edu.uniandes.lym.tokenizer.*;


public class Main {
	
	
	public static void main(String[] args) {
		
        MainTokenizer tokenizer = new MainTokenizer() ;


		//Procesed tokens
        List<Token> tokens =  tokenizer.getTokens();
        
		//Parser validation
		Boolean procesed = false;
	
		
		
		//Principal State 
		PrincipalState principalState  = new PrincipalState();
		ArrayList<Rule> principalRules = principalState.getRules();
		ArrayList<Road> principalRoads = principalState.getRoads();
		
		
		//Estado 0: Inmediatamente despues del PROG
		
		principalState.addRule(createRule("PROGRAM_START_KEYWORD", null, "Q"));
		principalState.addRule(createRule("VAR_DECLARATION_KEYWORD", "Q", "V"));
		principalState.addRule(createRule("PROCEDURE_START_KEYWORD", "Q", "P_DEFIN"));
		principalState.addRule(createRule("PROCEDURE_END_KEYWORD", "PROC_END", "Q"));
		principalState.addRule(createRule("PROCEDURE_END_KEYWORD", "Q", "Q"));


		principalState.addRule(createRule("LEFT_CURLY_BRACKET", "BLOCK_Q", "BLOCK_Q"));
		principalState.addRule(createRule("RIGHT_CURLY_BRACKET", "BLOCK_Q", "Q"));
		principalState.addRule(createRule("RIGHT_CURLY_BRACKET", "Q", "Q"));


		principalState.addRule(createRule("LEFT_CURLY_BRACKET", "PROC_START", "BLOCK_Q"));
		principalState.addRule(createRule("LEFT_CURLY_BRACKET", "Q", "BLOCK_Q"));

		// Reglas dentro de un proc 

		principalState.addRule(createRule("VAR_DECLARATION_KEYWORD", "BLOCK_Q", "BLOCK_V"));
		principalState.addRule(createRule("ID", "BLOCK_Q", "POSIBLE_VAL"));
		

		
		//ROAD'S DEFINITION
		
		
	   //-----------------------------------------------------------------------------------------------------
	   
	   // Variable state definition
	    State variableStateDefinition = new State();

		//Temporal variable
		variableStateDefinition.addRule(createRule("ID", "BLOCK_V", "BLOCK_ID"));
		variableStateDefinition.addRule(createRule("COMMA", "BLOCK_ID", "BLOCK_V"));
		variableStateDefinition.addRule(createRule("SEMICOLON", "BLOCK_ID", "BLOCK_Q"));

		//Global Variable
		variableStateDefinition.addRule(createRule("ID", "V", "ID"));
		variableStateDefinition.addRule(createRule("COMMA", "ID", "V"));
		variableStateDefinition.addRule(createRule("SEMICOLON", "ID", "Q"));

		//Variable assignment
		variableStateDefinition.addRule(createRule("ASSIGNMENT_OPERATOR", "POSIBLE_VAL", "VALUE_Q"));
		variableStateDefinition.addRule(createRule("NUM", "VALUE_Q", "BLOCK_FINV"));
		variableStateDefinition.addRule(createRule("ID", "VALUE_Q", "BLOCK_FINV"));
		variableStateDefinition.addRule(createRule("SEMICOLON", "BLOCK_FINV", "BLOCK_Q"));
		variableStateDefinition.addRule(createRule("RIGHT_CURLY_BRACKET", "BLOCK_FINV", "Q"));
		variableStateDefinition.addRule(createRule("LEFT_CURLY_BRACKET", "BLOCK_FINV", "BLOCK_Q"));




		
		//Variable Road
		Road variableRoad = new Road("VARIABLE_ROAD" );
		variableRoad.addState(variableStateDefinition);

	   //-----------------------------------------------------------------------------------------------------
		

		//Procedure state definition
		State procedureStateDefinition = new State();

		procedureStateDefinition.addRule(createRule("ID", "P_DEFIN", "PS"));
		procedureStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "PS", "PC"));
		procedureStateDefinition.addRule(createRule("ID", "PC", "C"));
		procedureStateDefinition.addRule(createRule("COMMA", "C", "PC"));
		procedureStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "C", "PROC_START"));
		procedureStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "PC", "PROC_START"));

		//Procedure call

		procedureStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "POSIBLE_VAL", "PARAM"));
		procedureStateDefinition.addRule(createRule("ID", "PARAM", "MIDDLE"));
		procedureStateDefinition.addRule(createRule("NUM", "PARAM", "MIDDLE"));
		procedureStateDefinition.addRule(createRule("COMMA", "MIDDLE", "PARAM"));
		procedureStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "MIDDLE", "FIN_CALL"));
		procedureStateDefinition.addRule(createRule("SEMICOLON", "FIN_CALL", "BLOCK_Q"));
		procedureStateDefinition.addRule(createRule("RIGHT_CURLY_BRACKET", "FIN_CALL", "BLOCK_Q"));







		
		//Procedure Road
		Road procedureRoad = new Road("PROCEDURE_ROAD" );
		procedureRoad.addState(procedureStateDefinition);

		//Block state definition

		//Command rules ------------------------------------------------------------------------------------------------------

		State blockStateDefinition = new State();

		//

		blockStateDefinition.addRule(createRule("PROCEDURE_END_KEYWORD", "BLOCK_Q", "Q"));
	
		blockStateDefinition.addRule(createRule("LEFT_CURLY_BRACKET", "BLOCK_Q", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("RIGHT_CURLY_BRACKET", "BLOCK_Q", "BLOCK_Q"));
		

		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(", "WALK_PARAM"));
		blockStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "BLOCK_)", "BLOCK_WQ"));


		// walk command
		blockStateDefinition.addRule(createRule("WALK_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_("));


		blockStateDefinition.addRule(createRule("SEMICOLON", "BLOCK_WQ", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("NUM", "WALK_PARAM", "BLOCK_)"));
		blockStateDefinition.addRule(createRule("ID", "WALK_PARAM", "BLOCK_)"));


		blockStateDefinition.addRule(createRule("D_KEYWORD", "WALK_PARAM", "D_KEYWORD"));
		blockStateDefinition.addRule(createRule("COMMA", "D_KEYWORD", "DC_KEYWORD"));
		blockStateDefinition.addRule(createRule("NUM", "DC_KEYWORD", "BLOCK_)"));
		blockStateDefinition.addRule(createRule("ID", "DC_KEYWORD", "BLOCK_)"));

		blockStateDefinition.addRule(createRule("O_KEYWORD", "WALK_PARAM", "O_KEYWORD"));
		blockStateDefinition.addRule(createRule("COMMA", "O_KEYWORD", "OC_KEYWORD"));
		blockStateDefinition.addRule(createRule("NUM", "OC_KEYWORD", "BLOCK_)"));
		blockStateDefinition.addRule(createRule("ID", "OC_KEYWORD", "BLOCK_)"));

		blockStateDefinition.addRule(createRule("RIGHT_CURLY_BRACKET", "BLOCK_Q", "PROC_END"));
		blockStateDefinition.addRule(createRule("RIGHT_CURLY_BRACKET", "BLOCK_WQ", "PROC_END"));


		// Jump command
		blockStateDefinition.addRule(createRule("JUMP_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(J"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(J", "NUM_ID_PARAM"));

		//Jump to command

		blockStateDefinition.addRule(createRule("JUMP_TO_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(JT"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(JT", "JMP_TO_PARAM"));

		blockStateDefinition.addRule(createRule("NUM", "JMP_TO_PARAM", "COMMA"));
		blockStateDefinition.addRule(createRule("ID", "JMP_TO_PARAM", "COMMA"));
		blockStateDefinition.addRule(createRule("COMMA", "COMMA", "FINAL_PARAM"));
		blockStateDefinition.addRule(createRule("NUM", "FINAL_PARAM", "BLOCK_)"));
		blockStateDefinition.addRule(createRule("ID", "FINAL_PARAM", "BLOCK_)"));

		// drop command
		blockStateDefinition.addRule(createRule("DROP_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(D"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(D", "NUM_ID_PARAM"));
	
		// free command
		blockStateDefinition.addRule(createRule("FREE_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(F"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(F", "NUM_ID_PARAM"));


		// veer command
		blockStateDefinition.addRule(createRule("VEER_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(V"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(V", "VEER_PARAM"));
		blockStateDefinition.addRule(createRule("D_KEYWORD", "VEER_PARAM", "BLOCK_)"));
		
		// look command
		blockStateDefinition.addRule(createRule("LOOK_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(L"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(L", "LOOK_PARAM"));
		blockStateDefinition.addRule(createRule("O_KEYWORD", "LOOK_PARAM", "BLOCK_)"));
 
		// grab command
		blockStateDefinition.addRule(createRule("GRAB_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(G"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(G", "NUM_ID_PARAM"));

		// get command	
		blockStateDefinition.addRule(createRule("GET_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(GET"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(GET", "NUM_ID_PARAM"));

		// pop command	
		blockStateDefinition.addRule(createRule("POP_COMMAND_KEYWORD", "BLOCK_Q", "BLOCK_(POP"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(POP", "NUM_ID_PARAM"));

		
		// ----------------------------------- Param rule (Num or Id) --------------------------------------
		blockStateDefinition.addRule(createRule("NUM", "NUM_ID_PARAM", "BLOCK_)"));
		blockStateDefinition.addRule(createRule("ID", "NUM_ID_PARAM", "BLOCK_)"));
		// -------------------------------------------------------------------------------------------------



		//Conditional rules ------------------------------------------------------------------------------------------------------

		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(COND", "CONDITIONAL_PROC"));

		//IsFacing rules
		blockStateDefinition.addRule(createRule("IS_FACING_CONDITION_KEYWORD", "CONDITIONAL_PROC", "BLOCK_(COND"));
		blockStateDefinition.addRule(createRule("O_KEYWORD", "BLOCK_(COND", "BLOCK_)COND"));
		blockStateDefinition.addRule(createRule("RIGHT_ARENTHESIS", "BLOCK_)COND", "CONDF"));

		//IsValid rules
		blockStateDefinition.addRule(createRule("IS_VALID_CONDITION_KEYWORD", "CONDITIONAL_PROC", "BLOCK_(C_VAL"));
		blockStateDefinition.addRule(createRule("O_KEYWORD", "BLOCK_(C_VAL", "BLOCK_)COND"));
		blockStateDefinition.addRule(createRule("RIGHT_ARENTHESIS", "BLOCK_)COND", "CONDF"));
		
		//canWalk rules
		blockStateDefinition.addRule(createRule("CAN_WALK_CONDITION_KEYWORD", "CONDITIONAL_PROC", "BLOCK_(C_CW"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "BLOCK_(C_CW", "PARAM_CW"));
		blockStateDefinition.addRule(createRule("O_KEYWORD", "PARAM_CW", "CW_COMMA"));
		blockStateDefinition.addRule(createRule("ID", "PARAM_CW", "CW_COMMA"));

		blockStateDefinition.addRule(createRule("COMMA", "CW_COMMA", "CW_PARAM"));
		blockStateDefinition.addRule(createRule("ID", "CW_PARAM", "BLOCK_)COND"));
		blockStateDefinition.addRule(createRule("NUM", "CW_PARAM", "BLOCK_)COND"));

		
		blockStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "BLOCK_)COND", "CONDF"));

		//NOT

		blockStateDefinition.addRule(createRule("NEGATION_KEYWORD", "CONDITIONAL_PROC", "(NOT_PARAM"));
		blockStateDefinition.addRule(createRule("LEFT_PARENTHESIS", "(NOT_PARAM", "CONDITIONAL_PROC"));
		blockStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "BLOCK_)COND", "BLOCK_Q"));

		//------------------------------------------------CONDITIONAL_PROCS---------------------------------------------
		//WHILE
		blockStateDefinition.addRule(createRule("WHILE_KEYWORD", "BLOCK_Q", "BLOCK_(COND"));
		blockStateDefinition.addRule(createRule("RIGHT_PARENTHESIS", "CONDF", "VAL"));
		blockStateDefinition.addRule(createRule("DO_OPEN_KEYWORD", "VAL", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("DO_END_KEYWORD", "PROC_END", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("SEMICOLON", "BLOCK_Q", "BLOCK_Q"));

		//IF
		blockStateDefinition.addRule(createRule("IF_OPEN_KEYWORD", "BLOCK_Q", "BLOCK_(COND"));
		blockStateDefinition.addRule(createRule("IF_END_KEYWORD", "PROC_END", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("SEMICOLON", "BLOCK_Q", "BLOCK_Q"));
		principalState.addRule(createRule("LEFT_CURLY_BRACKET", "VAL", "BLOCK_Q"));

		//IF_ELSE
		
		blockStateDefinition.addRule(createRule("ELSE_KEYWORD", "PROC_END", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("SEMICOLON", "PROC_END", "BLOCK_Q"));

		//REPEAT_TIMES
		
		blockStateDefinition.addRule(createRule("REPEAT_OPEN_KEYWORD", "BLOCK_Q", "VALUE_R"));
		blockStateDefinition.addRule(createRule("NUM", "VALUE_R", "BLOCK_Q"));
		blockStateDefinition.addRule(createRule("REPEAT_END_KEYWORD", "PROC_END", "BLOCK_Q"));
		

		//Block Road
		Road blockRoad = new Road("BLOCK_ROAD" );
		blockRoad.addState(blockStateDefinition);

	   //-----------------------------------------------------------------------------------------------------


		//Roads insertion
		
		principalState.addRoad(blockRoad);
		principalState.addRoad(variableRoad);
		principalState.addRoad(procedureRoad);
		
	   //-----------------------------------------------------------------------------------------------------

	   int tokenCounter = 0;
	   String currentInput = tokens.get(0).getType();
	   
	   String currentCondition = null;
	   //PARSER EJECUTION
	   Boolean inputValid = false;

		


		//Si valida que esta por esa ruta, la sigue
		//Si no busca mas rutas
		//Si no se encontro la ruta a seguir, se acaba y el input es invalido

		
		while(tokenCounter < tokens.size() )
		{

			

			if(currentInput == "PROGRAM_END_KEYWORD" && currentCondition == "Q"){

				inputValid = true;
				procesed = true;
				break;
			}

			//Validar si esta por alguna de las reglas principales

			try {

				while(currentInput.equals("SPACE")|| currentInput.equals("LINE_BREAK") || currentInput.equals("TAB") ){
					tokenCounter ++;
					currentInput = tokens.get(tokenCounter).getType();
					
				}

				

				Boolean principalRuleFound = false;

				for (int i = 0; i < principalRules.size(); i++) {
					
					
				

					Rule rule = principalRules.get(i);

					
					while(currentInput.equals("SPACE")|| currentInput.equals("LINE_BREAK") || currentInput.equals("TAB") ){
						tokenCounter ++;
						currentInput = tokens.get(tokenCounter).getType();
					}


					if(rule.validate(currentInput, currentCondition)){
						

						print("\n---------------------------------------------------------------------------------------");
						print("Principal rule found: " + currentInput  + "  -  " + currentCondition + " - " + rule.getOutput() );
						print("----------------------------------------------------------------------------------------\n");

						principalRuleFound = true;

						tokenCounter++;
						currentInput = tokens.get(tokenCounter).getType();
						currentCondition = rule.getOutput();
						i =-1;

						while(currentInput.equals("SPACE")|| currentInput.equals("LINE_BREAK") || currentInput.equals("TAB") ){
							tokenCounter ++;
							currentInput = tokens.get(tokenCounter).getType();
							
						}

					}

				}

				if(!principalRuleFound){
					throw new Exception("\nPrincipal rule not found. \n");
				}

			} catch (Exception e) {
				print("\n" + e.getMessage());
				break;
			}


			//Buscar el camino a seguir


			for (Road road : principalRoads) {

				while(currentInput.equals("SPACE")|| currentInput.equals("LINE_BREAK") || currentInput.equals("TAB") )
				{
					tokenCounter ++;
					currentInput = tokens.get(tokenCounter).getType();
				}

				int stateNumber = 0;
				ArrayList<Object> walkRoad = road.walkState(stateNumber, currentInput, currentCondition);

				//Busca el road de la condición
				

				if(walkRoad.get(0).equals(true)){
					
					tokenCounter ++;
					
					currentInput = tokens.get(tokenCounter).getType();
					currentCondition = walkRoad.get(1).toString();  
					
					while(currentInput.equals("SPACE")|| currentInput.equals("LINE_BREAK") || currentInput.equals("TAB") )
					{
						tokenCounter ++;
						currentInput = tokens.get(tokenCounter).getType();
					}
					//Encontro road, y lo debe seguir

					

					while(true){

						if( stateNumber < road.roadSize()){

							
						
							ArrayList<Object> currentWalk = road.walkState(stateNumber, currentInput, currentCondition);
							
							if(currentWalk.get(0).equals(true)){

								tokenCounter ++; 
								currentInput = tokens.get(tokenCounter).getType();
								currentCondition = currentWalk.get(1).toString(); 
							}

							if(currentWalk.get(0).equals(false)){
								break;
							}
							
							while(currentInput.equals("SPACE")|| currentInput.equals("LINE_BREAK") || currentInput.equals("TAB") )
							{
								tokenCounter ++;
								currentInput = tokens.get(tokenCounter).getType();
							}

							for (Rule rule : principalRules) {
								if(rule.validate(currentInput, currentCondition)){

								}
								
							}
							
						}



						


					}
				}
			}

		}

		

		if (!inputValid){
				
			//  throw new Exception("\nFailed in stage: " + stateType + "(" + currentState + ")"  + "\nThe input " + currentInput + "has not managed to cross the path: INVALID TEXT.\n"
			//                      + "Rule: " + lastRuleInput + "  -   " +
			//                       lastRuleCondition +  "  -   " +
			//                       lastRuleOutput);

			print("\nCurrent Input: " + currentInput);
			print("\nCurrent condition: " + currentCondition);

			print("\nParser completed: Invalid Text \n");
			
		}
	
	

	if(procesed){
		print("\nParser completed: Valid Text\n");
	}

		
		//---------------------------------------------------------------------------------------------------------------
	
		
		
		
		

		
	}
	
	public static Rule createRule(String input, String condition ,String output) {
		return new Rule(input, condition, output);
	}
	
	public static State createState() {
		return new State();
	}
	
	public static void print(Object param) {
		System.out.println(param);
	}
	
	public static void printRule(Rule rule) {
		System.out.println(rule.getInput() + " - " + rule.getCondition() + " - " + rule.getOutput());
	}

	

}
