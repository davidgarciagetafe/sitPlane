package sitPlane;

public class Plane {
int [][] mapPlane;
int rows;
int seatsPerRow;
public Plane(int rows, int seatsPerRow) {
	super();
	this.rows = rows;
	this.seatsPerRow = seatsPerRow;
	this.mapPlane = new int[rows][seatsPerRow];
}


}
