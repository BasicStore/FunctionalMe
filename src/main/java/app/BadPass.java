package app;

public class BadPass {

	public BadPass() {
		System.out.println("\n\nDemonstrating how java COPIES BY VALUE.................");
		demonstratePassAndCopyByValue();
		
		System.out.println("\n\nSwapping references also COPIES BY VALUE.................");
		runTrickyObjRefExample();

		// NOTE:
		// Java manipulates objects 'by reference,' but it passes / copies object references to methods 'by value.'" 
		// JAVA ALWAYS COPIES BY VALUE
		
		// POINT TO REMEMBER:
		// - when values are passed to a method, whether primitive or object reference, they are copies.
		// - just changing the value of the copy. When the thread of the called method's stack layer
		//     is terminated, the local references to the objects that may have been swapped are destroyed,
		//     so the caller reference will still point to the objects as they did before the call
		// - you can manipulate the value that a reference points to as long as you do not assign it to another reference,
		//   because you are still pointing to the same underlying object which you are updating.
		// - passing the top level reference to a method is enough, there is no need to be concerned with the
		//   references that might be encapsulated within a complex object
		testManipulateByPrimitivesAndReferences();
		
		demonstratePassByValueCategorically();
		
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
	
	
	private void demonstratePassByValueCategorically() {
		
		// when an object is created with the new operator, an instance is created and the variable holds the memory location
		Balloon red = new Balloon("Red"); // eg. memory reference 50
		Balloon blue = new Balloon("Blue"); // eg. memory reference 100
		
		// when we are calling swap() method, two new variables o1 and o2 are created pointing to 50 and 100 respectively.
		// After successfully swapping the new variables in swap(), once back in the original method the original
		// variables will still refer to the original memory locations (the garbage collector will destroy the swapped copies)
		swap(red, blue);
				
		System.out.println("red color="+red.getColor());
		System.out.println("blue color="+blue.getColor());
		System.out.println("Notice the colours are correct: ie. the swap has not worked.....");
		
		// in foo(), the ref 100 is passed through so the value is changed. 
		// But once the instance is changed to a different address, no manner of tinkling will stick 
		foo(blue);
		System.out.println("blue color="+blue.getColor());
		
		//
		System.out.println("\n\nPOT BLACK TEST................demonstrate how this should work..............");
		Balloon black = new Balloon("Black");
		Balloon pink = new Balloon("Pink");
		Balloon yellow = new Balloon("Yellow");
		
		// all should be pink
		black = pink;
		yellow = black;
		System.out.println("Black ref = " + black.getColor());
		System.out.println("Yellow ref = " + yellow.getColor());
		System.out.println("Both black and yellow references have been changed to point to the pink pocket");
		
		// HOWEVER!!!!!!! 
		
		
		// what happens if an object which itself contains an object reference is modified (WITH use of the new operator)?
		// EXPECTED: probably will not change passing rules to a method because the top level object reference is the relevant reference  
		Balloon black1 = new Balloon("Black");
		Balloon white1 = new Balloon("White");
		Balloon red1 = new Balloon("Red");
		Balloon green = new Balloon("Green", white1);
		Balloon brown = new Balloon("Brown", black1);
		Balloon white = new Balloon("White", red1);
		white.setNeighbour(green);
		green = white;
		brown = green;

		// what happens if an object which itself contains an object reference is modified
		// the changes take effect as expected
		System.out.println("Expect green, brown and white to all now point to Jimmy White, and in each case the neighbour is Mark Green");
		System.out.println("The green ball us pointing to the " + green.getColor() + " and is neighbour to the " + green.getNeighbour().getColor());
		System.out.println("The brown ball us pointing to the " + brown.getColor() + " and is neighbour to the " + brown.getNeighbour().getColor());
		System.out.println("The white ball us pointing to the " + white.getColor() + " and is neighbour to the " + white.getNeighbour().getColor());
		System.out.println("\nNow changing the neighbour of (the original) white, to black.............but the black was painted to pink!");
		white.setNeighbour(black);
		System.out.println("The green ball us pointing to the " + green.getColor() + " and is neighbour to the " + green.getNeighbour().getColor());
		System.out.println("The brown ball us pointing to the " + brown.getColor() + " and is neighbour to the " + brown.getNeighbour().getColor());
		System.out.println("The white ball us pointing to the " + white.getColor() + " and is neighbour to the " + white.getNeighbour().getColor());
		
		// what happens if an object which itself contains an object reference is passed by value up the stack
		System.out.println("\nExpect the yellow to be painted red and be neighbours to the cream billiard ball........");
		Balloon yellow2 = new Balloon("Yellow");
		deepFoo(yellow2);
		System.out.println("The original YELLOW is now " + yellow2.getColor() + " and is neighbour to the " + yellow2.getNeighbour().getColor());

		
		System.out.println("\nWhat happens if you change the nested reference values as above, but do so in a method call??????");
		// expect this not to work
		Balloon cream2 = new Balloon("Cream");
		Balloon black2 = new Balloon("Black");
		Balloon red2 = new Balloon("Red");
		Balloon brown2 = new Balloon("Brown", black2);
		Balloon white2 = new Balloon("White", black2);
		Balloon green2 = new Balloon("Green", white2);
		testUpdateUpStackLevel(brown2, white2, green2);
		// ????????
		System.out.println("\nThe green ball is still pointing to the green: " + green2.getColor() + 
				" --> and the brown is still neighbour to (an instance of) the black \n   "
				+ "[which has now been painted mushroom]: it is " + brown2.getNeighbour().getColor());
		/*
		The green ball is still pointing to the green: Green [ie not the white]  FINE 
		--> and the brown is still neighbour to (an instance of) the black 
        [which has now been painted mushroom]: it is MUSHROOM		
		 */
		
		Balloon chalkBlue = new Balloon("Blue", new Balloon("Red"));
		testComplexUpdateUpStackLevel(chalkBlue);
		System.out.println("\nThe blue ball is still pointing to the blue: " + chalkBlue.getColor() + 
				" and is now a neighbour to a painted mushroom: " + chalkBlue.getNeighbour().getColor());
		// NOTE: so pointing to the 
		
		Balloon chalkBlue2 = new Balloon("Blue", new Balloon("Red"));
		Balloon neighbour = new Balloon("Pink", new Balloon("Black"));
		testComplexUpdateUpStackLevel(chalkBlue2, neighbour);
		System.out.println("\nThe blue ball is still pointing to the blue: " + chalkBlue.getColor() + 
				" and is now a neighbour to the yellow: " + chalkBlue.getNeighbour().getColor());
	}
	
	
	
	// change the nested reference values as part of a swap operation, within this method with copied arguments
	// none of this should be reflected in the caller
	private void testUpdateUpStackLevel(Balloon brown, Balloon white, Balloon green) {
		green = white;
		brown = green;
		brown.getNeighbour().setColor("MUSHROOM");
	}
	
	
	
	// changing the lower level object within a call has updated it, because the neighbour's address has not changed
	private void testComplexUpdateUpStackLevel(Balloon chalkBlue) {
		chalkBlue.getNeighbour().setColor("yellow");
	}

	
	// changing the lower level object within a call has updated it, because the neighbour's address has not changed
	// however, if the new operator were to be used (unlike in this example), obviously it would be a different instance (or 'ball')   
	private void testComplexUpdateUpStackLevel(Balloon chalkBlue, Balloon neighbour) {
		chalkBlue.setNeighbour(neighbour);
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
		manipulateAnImmutableObjectReferenceWithTheNewOperator(prim);
		System.out.println("--> immutable reference after copy and update = " + ref);
		
		
		// NO< NO NO....!!!!!
		
		// any update to an mutable object reference is retained
		// note that the reference itself is copied by value
		Point p = new Point(47, 47);
		System.out.println("--> mutable reference before copy and update = p.x: " + p.x + " ==> p.y: " + p.y);
		manipulateAMutableObjectReferenceWithoutTheNewOperator(p);
		System.out.println("--> mutable reference after copy and update = p.x: " + p.x + " ==> p.y: " + p.y);
		
		System.out.println("....................that's settled");
	}
	
	private static void manipulateAPrimitive(int a) {
		a = a + 1;
		// with primitives, the copy is passed, so any change is reflected in the copied memory location, not the original,
		// so the caller does not notice any change in the passed values
	}
	
	private static void manipulateAnImmutableObjectReferenceWithTheNewOperator(Integer a) {
		a = new Integer(a + 1); // the new operator is pointing to a new address
		
		// this would work because variable 'A' is pointing to the same address as in the caller,
		// and a value associated with that is changed
		// HOWEVER, an Integer is immutable, so the new operator 
		//          And creating a new object is >>pointing the copied reference to a new location<<
		//          When the method returns, the copied reference is destroyed, and the newly created instance,
		//          at a new location, is also discarded.            
	}

	
	private static void manipulateAMutableObjectReferenceWithoutTheNewOperator(Point a) {
		a.x=147;
		a.y=147;
		
		// this works because variable 'A' is pointing to the same address as in the caller,
		// and a value associated with that is changed
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
	     	- JAVA PASSES A COPY OF THE PRIMITIVE VALUE OR OBJECT REFERENCE (POINTER) VALUE TO THE CALLED METHOD, ON THE NEXT STACKED MEMORY LAYER
	     	- JAVA CANNOT PASS BY REFERENCE

	       SOLUTION TO SWAPPING OBJECTS EFFECTIVELY:
	       ==> use instance variables to avoid having to make copies of the references	
	       ==> if it is the values that matter (not the specific instances), then some solution involving cloning to new objects would suffice  
	     */
	    
	}
		
	
	private void badSwap(int varX, int varY) {
		int origX = varX;
		varX = varY;
		varY = origX; 
	}
	
	
	
	// NOTE:
	// The args varX and varY are copies of the original reference!!!! 
	// so swapping the references so that they point to different locations is irrelevant.....
	// once the method returns the original references will still point o the original locations,
	// (containing values which have not changed)
	private void equallyBadSwap(Integer varX, Integer varY) {
		Integer origX = varX;
		varX = varY;
		varY = origX; 
	}
	
	
	public static void swap(Object o1, Object o2){ 
		Object temp = o1;
		o1=o2;
		o2=temp;
	} 
	
	
	private static void foo(Balloon balloon) { 
		balloon.setColor("Red"); // updates the value of the original reference 
		balloon = new Balloon("Green"); // now make balloon = to some other reference 
		balloon.setColor("Blue"); // no amount of tinkering to the new value in the new location 
		                          // will change the value of the original object in the original location
	}
	
	
	
	private static void deepFoo(Balloon balloon) { 
		balloon.setColor("Red"); // updates the value of the original reference 
		balloon.setNeighbour(new Balloon("Billiard Cream"));
		
		balloon = new Balloon("Green"); // now make balloon = to some other reference 
		balloon.setColor("Blue"); // no amount of tinkering to the new value in the new location 
		                          // will change the value of the original object in the original location
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
