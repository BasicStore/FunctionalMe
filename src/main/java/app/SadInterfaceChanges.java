package app;

import java.util.function.Predicate;

public class SadInterfaceChanges implements MySadInterface {
	
	public SadInterfaceChanges() {
		testInterfaceConstructs();
	}
	
	
	private void testInterfaceConstructs() {
		System.out.println("\n" + findSomething("sad"));
		doMySadImplement("Perverting principles of OO and Java by adding implementation here - "
				+ "inheritance still applies as if it were a class method");
		doMySadImplement2("Perverting principles of OO and Java by adding implementation here - "
				+ "inheritance still applies as if it were a class method");
		
		System.out.println("Call a sad interface default method from here without overriding it,"
				+ " and let the sad interface method call a static method");
		        // no concept of inheritance with static methods
		        // ie.  any static method of the same name in this class cannot make any use of the super keyword
		doMySadImplement333();
		
		System.out.println("Now refer to this sad interface method in a lamda expression:  ");
		MySadInterface d2=(s)->{  
			return findSomething(s);
		};  
		
		System.out.println(d2.findSomething("=========>"));
	}
	
	
	public String findSomething(String sadStory) {
		return "something found: " + sadStory;
	}
	
	@Override
	public void doMySadImplement2(String str) {
		System.out.println("Overriding interface default method implementation");
	}

	
}
