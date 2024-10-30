public class Vehicle {
	boolean car;
	String color;
	boolean horizontal;
	int pos;

	Vehicle(boolean car, String color, boolean horizontal, int row, int col){
		this.car = car;
		this.color = color;
		this.horizontal = horizontal;
		this.pos = (row-1) * 6 + (col-1);
	}
}