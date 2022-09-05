package main.java.co.edu.uniandes.lym.parser;

import java.util.ArrayList;

public class PrincipalState {

    private ArrayList<Road> roads;
    private ArrayList<Rule> rules;

    public PrincipalState(){
        this.roads = new ArrayList<Road>();
        this.rules = new ArrayList<Rule>();
    }

    public void addRule(Rule rule) {
		rules.add(rule);
	}
	
	public void addRoad(Road road) {
		roads.add(road);
	}
	
	public ArrayList<Rule> getRules(){
		return this.rules;
	}
	
	public ArrayList<Road> getRoads(){
		return this.roads;
	}

    
}
