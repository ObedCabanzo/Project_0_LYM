package main.java.co.edu.uniandes.lym.parser;

public class Rule {
	private String input;
	private String output;
	private String condition;
	
	public Rule(String input, String condition ,String output) {
		this.input = input;
		this.output = output;
		this.condition = condition;
	}
	
	public Boolean validate(String currentInput, String currentCondition){
		if(currentCondition == this.condition && currentInput == this.input) {
			return true;
		}
		return false;
	}

	/**
	 * @return the input
	 */
	public String getInput() {
		return input;
	}

	/**
	 * @return the output
	 */
	public String getOutput() {
		return output;
	}


	/**
	 * @return the condition
	 */
	public String getCondition() {
		return condition;
	}

	
}
