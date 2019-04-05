package app;

import java.util.function.Predicate;

public interface MySadInterface {
		
	String findSomething(String sadStory);
		
	default void doMySadImplement(String sorryStr) {
		System.out.println("Testing predicate arg: " + sorryStr);
	}

	
	default void doMySadImplement2(String str) {
		doMySadImplement("UGLY CODE IN AN INTERFACE"); 
		
		// unbelievably, it is now possible to call another default method from a default method in j8!  Shame.
		// HOWEVER..............if the method being invoked is overridden by a subtype, the usual inheritance applies 
		// as if it were a class, of course
	}
	
	
	static void doEvenWorseStaticImplementation() {
		System.out.println("static inferface method implementation cannot be overridden in the proper sense");
	}
	
	
	default void doMySadImplement333() {
		doEvenWorseStaticImplementation();
	}
	
	
	
	
}
