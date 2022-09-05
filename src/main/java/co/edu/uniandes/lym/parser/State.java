package main.java.co.edu.uniandes.lym.parser;

import java.util.ArrayList;

public class State {
	
	private ArrayList<Rule> rules;

	public State() {
		this.rules = new ArrayList<Rule>();

	}
	public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public ArrayList<Rule> getRules(){
		return this.rules;
	}
	
}
