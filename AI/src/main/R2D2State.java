package main;

import java.util.ArrayList;

public class R2D2State extends State{
	ArrayList<Rock> rocks;
	public R2D2State(int row, int col , ArrayList<Rock> rocks) {
		super(row,col);
		this.rocks=rocks;
	}
	
	

}
