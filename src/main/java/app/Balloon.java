package app;

public class Balloon {
	// DEMONSTRATE THAT JAVA COPIES BY VALUE....ONLY
	
	/*
	 DEFINITIONS:
	 Pass by Value: The method parameter values are copied to 
                    another variable and then the copied object is 
                    passed, that's why it's called pass by value.

     Pass by Reference: An alias or reference (pointer) to the actual 
                    parameter is passed to the method, that's 
                    why it's called pass by reference
        
    Java is pass by value ONLY                
	C++ and Pascal can be either pass by reference or pass by value                    
                    
	 */

	private String color;
	
	private Balloon neighbour;
	

	public Balloon(){}
	
	public Balloon(String c){
		this.color=c;
	}
	
	public Balloon(String c, Balloon neighbour){
		this.color=c;
		this.neighbour = neighbour;
	}

	
	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Balloon getNeighbour() {
		return neighbour;
	}

	public void setNeighbour(Balloon neighbour) {
		this.neighbour = neighbour;
	}
	
}
