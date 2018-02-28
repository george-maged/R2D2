package main;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SearchAlgorithm {
	Strategy strategy;
	SearchProblem sp;
	Boolean visualize;
	int numberOFNodes;
	public SearchAlgorithm(SearchProblem sp, Strategy strategy, Boolean visualize) {
		this.strategy = strategy;
		this.sp = sp;
		this.visualize = visualize;
		this.numberOFNodes =0;
	}
	public void run() {
		switch(strategy){
		case DF: depthOrID(sp,2147483646 ); break;
		case ID: depthOrID(sp, 0); break;
		case BF: breadthFirst(sp); break;
		case UC: uniformCost(sp);break;
		case GR1: Greedy(sp,1);break;
		case GR2: Greedy(sp,2);break;
		case AS1: AStar(sp, 1);break;
		case AS2: AStar(sp, 2);break;
		default: break;
		}
	}
	public void breadthFirst(SearchProblem sp){
		Node root = sp.genRoot();
		Deque<Node> queue = new LinkedList<>();
		queue.addFirst(root);
		while(!queue.isEmpty()){
			Node n = queue.removeFirst();
			if(!sp.arrayContains(sp.states, n.state)) {//Check for repeated states
				sp.states.add(n.state);
			} else {
				continue;
			}
			ArrayList<Node>nodes =sp.expandNode(n);//Nodes to be added to the queue
			numberOFNodes++;
			for(int i = 0;i < nodes.size();i++){
				Node n1 = nodes.get(i);
				if(n1.state != null){
					queue.addLast(n1);//Add nodes to the end of the queue
				}
			}
			if(sp.goalTest(n)){
				System.out.println("Goal Reached!");
				System.out.println("Path cost: "+sp.pathCost(n));
				ArrayList<Node> operators = new ArrayList<>();//List of operators to reach the goal
				while(n.parentNode!=null) {
					operators.add(n);
					n = n.parentNode;
				}
				System.out.print("Sequence: ");
				for(int i = operators.size()-1; i>=0;i--) {
					System.out.print(operators.get(i).op+" ");
				}
				operators.add(n);
				System.out.println(" ");
				for(int i = operators.size()-1; i>=0;i--) {
					if(visualize) {
						sp.visualize(operators.get(i));
					}
				}
				System.out.println("Nodes expanded: "+numberOFNodes);
				return;
			}

		}
		if(queue.isEmpty()) {//Queue is empty and goal was never reached
			System.out.println("No possible solution");
			System.out.println("Nodes expanded: "+numberOFNodes);
		}

	}
	private void uniformCost(SearchProblem sp) {
		Node root = sp.genRoot();
		Comparator<Node> pathComparator = new Comparator<Node>(){

			@Override
			public int compare(Node n1, Node n2) {
				return (sp.pathCost(n1)- sp.pathCost(n2));
			}
		};
		Queue<Node> queue = new PriorityQueue<>(pathComparator);
		queue.add(root);
		while(!queue.isEmpty()) {
			Node n = queue.poll();
			if(!sp.arrayContains(sp.states, n.state)) {
				sp.states.add(n.state);
			} else {
				continue;
			}
			ArrayList<Node>nodes =sp.expandNode(n);
			numberOFNodes++;
			for(int i = nodes.size()-1;i >=0;i--){
				Node n1 = nodes.get(i);
				if(n1.state != null){
					queue.add(n1);
				}
			}

			if(sp.goalTest(n)){
				System.out.println("Goal Reached!");
				System.out.println("Path cost: "+sp.pathCost(n));
				ArrayList<Node> operators = new ArrayList<>();
				while(n.parentNode!=null) {
					operators.add(n);
					n = n.parentNode;
				}
				System.out.print("Sequence: ");
				for(int i = operators.size()-1; i>=0;i--) {
					System.out.print(operators.get(i).op+" ");
				}
				operators.add(n);
				System.out.println(" ");
				for(int i = operators.size()-1; i>=0;i--) {
					if(visualize) {
						sp.visualize(operators.get(i));
					}
				}
				System.out.println("Nodes expanded: "+numberOFNodes);
				return;
			}
		}
		if(queue.isEmpty()) {
			System.out.println("No possible solution");
			System.out.println("Nodes expanded: "+numberOFNodes);
		}
	}
	private void depthOrID(SearchProblem sp,int limit) {
		while(true){
			int maxDepth =0;
			Node root = sp.genRoot();
			Deque<Node> queue = new LinkedList<>();
			queue.addFirst(root);
			Node n = null;
			while(!queue.isEmpty()){
				n = queue.removeFirst();
				if(n.depth>maxDepth) {
					maxDepth = n.depth;
				}
				if(!sp.arrayContains(sp.states, n.state)&&n.depth<limit) {
					sp.states.add(n.state);
				} else {
					continue;
				}
				ArrayList<Node>nodes =sp.expandNode(n);
				numberOFNodes++;
				for(int i = nodes.size()-1;i>=0;i--){
					Node n1 = nodes.get(i);
					if(n1.state != null){
						queue.addFirst(n1);
					}
				}
				if(sp.goalTest(n)){
					System.out.println("Goal Reached!");
					System.out.println("Path cost: "+sp.pathCost(n));
					ArrayList<Node> operators = new ArrayList<>();
					while(n.parentNode!=null) {
						operators.add(n);
						n = n.parentNode;

					}
					System.out.print("Sequence: ");
					for(int i = operators.size()-1; i>=0;i--) {
						System.out.print(operators.get(i).op+" ");
					}
					operators.add(n);
					System.out.println(" ");
					for(int i = operators.size()-1; i>=0;i--) {
						if(visualize) {
							sp.visualize(operators.get(i));
						}
					}
					System.out.println("Nodes expanded: "+numberOFNodes);
					return;
				}
			}
			if(maxDepth<limit){
				System.out.println("No possible solution. Depth: "+n.depth+" Limit: "+limit);
				System.out.println("Nodes expanded: "+numberOFNodes);
				return;
			}
			limit++;
		}
	}
	private void Greedy(SearchProblem sp,int a){
		Node root = sp.genRoot();
		Comparator<Node> pathComparator = new Comparator<Node>(){

			@Override
			public int compare(Node n1, Node n2) {
				return (int) (sp.heuristic(n1, a)- sp.heuristic(n2, a));
			}
		};
		Queue<Node> queue = new PriorityQueue<>(pathComparator);
		queue.add(root);
		while(!queue.isEmpty()) {
			Node n = queue.poll();
			if(!sp.arrayContains(sp.states, n.state)) {
				sp.states.add(n.state);
			} else {
				continue;
			}
			ArrayList<Node>nodes =sp.expandNode(n);
			numberOFNodes++;
			for(int i = nodes.size()-1;i >=0;i--){
				Node n1 = nodes.get(i);
				if(n1.state != null){
					queue.add(n1);
				}
			}

			if(sp.goalTest(n)){
				System.out.println("Goal Reached!");
				System.out.println("Path cost: "+sp.pathCost(n));
				ArrayList<Node> operators = new ArrayList<>();
				while(n.parentNode!=null) {
					operators.add(n);
					n = n.parentNode;
				}
				System.out.print("Sequence: ");
				for(int i = operators.size()-1; i>=0;i--) {
					System.out.print(operators.get(i).op+" ");
				}
				operators.add(n);
				System.out.println(" ");
				for(int i = operators.size()-1; i>=0;i--) {
					if(visualize) {
						sp.visualize(operators.get(i));
					}
				}
				System.out.println("Nodes expanded: "+numberOFNodes);
				return;
			}
		}
		if(queue.isEmpty()) {
			System.out.println("No possible solution");
			System.out.println("Nodes expanded: "+numberOFNodes);
		}
	}
	private void AStar(SearchProblem sp, int a){
		Node root = sp.genRoot();
		Comparator<Node> pathComparator = new Comparator<Node>(){

			@Override
			public int compare(Node n1, Node n2) {
				return  (sp.heuristic(n1,a) + sp.pathCost(n1) - (sp.heuristic(n2, a) + sp.pathCost(n2)));
			}
		};
		Queue<Node> queue = new PriorityQueue<>(pathComparator);
		queue.add(root);
		while(!queue.isEmpty()) {
			Node n = queue.poll();
			if(!sp.arrayContains(sp.states, n.state)) {
				sp.states.add(n.state);
			} else {
				continue;
			}
			ArrayList<Node>nodes =sp.expandNode(n);
			numberOFNodes++;
			for(int i = nodes.size()-1;i >=0;i--){
				Node n1 = nodes.get(i);
				if(n1.state != null){
					queue.add(n1);
				}
			}

			if(sp.goalTest(n)){
				System.out.println("Goal Reached!");
				System.out.println("Path cost: "+sp.pathCost(n));
				ArrayList<Node> operators = new ArrayList<>();
				while(n.parentNode!=null) {
					operators.add(n);
					n = n.parentNode;
				}
				System.out.print("Sequence: ");
				for(int i = operators.size()-1; i>=0;i--) {
					System.out.print(operators.get(i).op+" ");
				}
				operators.add(n);
				System.out.println(" ");
				for(int i = operators.size()-1; i>=0;i--) {
					if(visualize) {
						sp.visualize(operators.get(i));
					}
				}
				System.out.println("Nodes expanded: "+numberOFNodes);
				return;
			}
		}
		if(queue.isEmpty()) {
			System.out.println("No possible solution");
			System.out.println("Nodes expanded: "+numberOFNodes);
		}
	}
}

