package app;

public class BadPass {

	public BadPass() {
		System.out.println("\n\nDemonstrating how java COPIES BY VALUE.................");
		demonstratePassAndCopyByValue();
		
		System.out.println("\n\nSwapping references also COPIES BY VALUE.................");
		runTrickyObjRefExample();

		// NOTE:
		// Java manipulates objects 'by reference,' but it passes / copies object references to methods 'by value.'" 
		// As a result, you cannot write a standard swap method to swap objects
		
		// The same applied for manipulation when there is copying involved to a different stack
		testManipulateByPrimitivesAndReferences();
		
		// prove fly weight pattern is implementation is used to copy strings
		proveJvmCreatesStringsWithTheFlyweightPattern();		
	}
	
	
	private void proveJvmCreatesStringsWithTheFlyweightPattern() {
		String a = "DOLLY-DOODLE";
		String b = "DOLLY-DOODLE";
		String c = "DOLLY-DOODLE-";
		if (a == b && a != c) {
			System.out.println("\n\nJava Strings are implemented using the flyweight pattern to save on resources");
		}
	}
	
	
	private void testManipulateByPrimitivesAndReferences() {
		System.out.println("\n\nDemonstrating how Java manipulates values differently from copying them............");
		
		// any update to a primitive copy is lost
		int prim = 47;
		System.out.println("--> primitive before copy and update = " + prim);
		manipulateAPrimitive(prim);
		System.out.println("--> primitive after copy and update = " + prim);
		
		// any update to an immutable object reference is lost
		Integer ref = new Integer(47);
		System.out.println("--> immutable reference before copy and update = " + ref);
		manipulateAnImmutableObjectReference(prim);
		System.out.println("--> immutable reference after copy and update = " + ref);
		
		// any update to an mutable object reference is retained
		// note that the reference itself is copied by value
		Point p = new Point(47, 47);
		System.out.println("--> mutable reference before copy and update = p.x: " + p.x + " ==> p.y: " + p.y);
		manipulateAMutableObjectReference(p);
		System.out.println("--> mutable reference after copy and update = p.x: " + p.x + " ==> p.y: " + p.y);
		
		System.out.println("....................that's settled");
	}
		
	private static void manipulateAPrimitive(int a) {
		a = a + 1;
	}
	
	private static void manipulateAnImmutableObjectReference(Integer a) {
		a = new Integer(a + 1);
	}

	private static void manipulateAMutableObjectReference(Point a) {
		a.x=147;
		a.y=147;
	}

	
	
	
	private void demonstratePassAndCopyByValue() {
		System.out.println("Swapping will not work on primitives:");
		int varX = 4; 
		int varY = 5;		// Java manipulates objects 'by reference,' but it passes object references to methods 'by value.'" 
		// As a result, you cannot write a standard swap method to swap objects

		System.out.println("BEFORE SWAP:");
		System.out.println("--> varX = " + varX);
		System.out.println("--> varY = " + varY);
		badSwap(varX, varY);
		System.out.println("AFTER SWAP:");
		System.out.println("--> varX = " + varX);
		System.out.println("--> varY = " + varY);
		
		System.out.println("No more luck copying with references:");
		System.out.println("JAVA PASSES OBJECTS BY VALUE TOO.......");
		Integer varIntX = 4; 
		Integer varIntY = 5;
		System.out.println("BEFORE SWAP:");
		System.out.println("--> varIntX = " + varIntX);
		System.out.println("--> varIntY = " + varIntY);
		equallyBadSwap(varIntX, varIntY);
		System.out.println("AFTER SWAP:");
		System.out.println("--> varIntX = " + varIntX);
		System.out.println("--> varIntY = " + varIntY);
		
		// obviously, when copying object variables, only the reference is copied.......
	}
	
	
    public void tricky(Point arg1, Point arg2)
	{
	    arg1.x = 100;
	    arg1.y = 100;
	    Point temp = arg1;
	    arg1 = arg2;
	    arg2 = temp;
	}
    
    
    
	public void runTrickyObjRefExample()
	{
	    Point pnt1 = new Point(0,0);
	    Point pnt2 = new Point(0,0);
	    System.out.println("X: " + pnt1.x + " Y: " +pnt1.y); 
	    System.out.println("X: " + pnt2.x + " Y: " +pnt2.y);
	    System.out.println(" ");
	    tricky(pnt1,pnt2);
	    System.out.println("X: " + pnt1.x + " Y:" + pnt1.y); 
	    System.out.println("X: " + pnt2.x + " Y: " +pnt2.y); 
	    
	    // THE RESULT AFTER OBJ REF SWAP:
	    /*
	        - pnt1 ==> x=100 and y=100
	        - pnt2 ==> 
	     
	     ...but the 'incorrect' intention would have been to set p1=100 & p2=0, then swap so p1=0 & p2 = 100.....
	        ....but actually the objects have not been swapped, and the actual result is the reverse 
	       
	       **** Java copies and passes the REFERENCE by VALUE, not the object **** 
	            .....hence pnt1 / arg1 is set as expected, but the value change within the swap is lost outside the method
	        
	       Goal is to swap the references NOT the copies

	       SOLUTION:
	       ==> use instance variables to avoid having to copy them at all (which can't be done in any case)	
	       ==> create new objects (cloned as appropriate) and return them 
	     */
	    
	}
		
	
	private void badSwap(int varX, int varY) {
		int origX = varX;
		varX = varY;
		varY = origX; 
	}
	
	
	private void equallyBadSwap(Integer varX, Integer varY) {
		Integer origX = varX;
		varX = varY;
		varY = origX; 
	}
	
	
	public class Point {
		int x;
		int y;
		
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	
}
