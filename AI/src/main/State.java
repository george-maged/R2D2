package main;

public abstract class State {
	int agentRow;
	int agentCol;
	public State(int agentRow, int agentCol) {
		this.agentRow=agentRow;
		this.agentCol=agentCol;
	}
	public static void main(String[]args) {
		float i = 0;
		while(true) {
			i+= 1000000000;
			System.out.println(i);
		}
	}
}
