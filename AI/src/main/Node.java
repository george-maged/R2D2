package main;

public abstract class Node {
	State state;
	Node parentNode;
	Operator op;
	int depth;
	int pathCost;
	
	public Node(State st, Node pn, Operator op, int d, int p ){
		this.state=st;
		this.parentNode=pn;
		this.op=op;
		this.depth=d;
		this.pathCost=p;
	}
}
