package com.revature.patrick;

public class Patrick {
	//Data types
	
	//Primitive types or Value types
	boolean bool; // 1 bit (true / false)
	byte b; // 8 bits, 2^3
	char c; // 16 bits, 
	short s; // 16 bits, 2^4
	int i; // 32 bits, 2^5
	float f; // 32 bits
	long l; // 64 bits, 2^6
	double d; // 64 bits
	
	//Reference types contain a reference or ADDRESS
	// of something in the heap
	String str;
	Object o = new Object();
	
	// Access Modifiers:
	// public
	// default
	// protected
	// private
	
	// Public methods and variables can be accessed
	// from anywhere.
	public void myMethod() {
		// Instance method
		str.charAt(0); // 'H'
		
		// Static method
		String.valueOf(5); // "5"
	}
	
	// No access modifier = default access modifier:
	// Anything in the package can access
	// Also known as package-private
	void myDefaultMethod() { }
	
	// Protected means it can be accessed from inside the class
	// and any subclasses (aka child classes)
	protected void myProtectedMethod() { }
	
	// Private methods and variables can't be seen
	// from anywhere but inside the class
	private void myPrivateMethod() { }
	
	// Classes can only be public and default unless
	// declared inside of a class
	
	private class PrivateClass { }
	
	static class StaticClass { }
	
	// Constructors
	// No-params constructor
	public Patrick() {
		System.out.println("Inside Jacob constructor.");
	}
	
	// Parameterized constructor
	public Patrick(int i, char c, boolean bool) {
		this.i = i;
		this.c = c;
		this.bool = bool;
	}
	
	// Java favors the most specific scope
	// Class scope
	// Method scope
	
	// i here overshadows the class' i
	public void scopes(int i) {
		// Use "this: to get to class variables in that case
		this.i = i;
	}
	
	// Control statements
	public void control(boolean yes, int val) {
		if (yes) {
			// Do something if yes is true
		} else if (val == 2) {
			// Do something if val is 2
		} else {
			
		}
		
		// For-loops iterate some n number of times
		// Format is: (declaration; statement that must be true to keep going; what do do after each iteration)
		for (int i = 0; i < val; i++) {
			break;
		}
		
		for (int i = 0; ; i++) {
			//break immediately exists the loop
			break;
		}
		
		for ( ; yes ; ) {
			//Basically a while loop
			
			if (val == 1)
				// Skips the rest of the CURRENT ITERATION and gos to the next
				continue;
		}
		
		// ++i is precrement: increment the value, then resolve the statement
		// i++ is post-crement: resolve the statement, then increment the value
		for ( ; val++ < 5; ) { }
		for ( ; ++val < 5; ) { }
		
		while(yes) {
			// Do something repeatedly while yes is true
			break;
		}
		
		do {
			// Run once, and THEN run for as long as yes is true
		} while(yes);
	
		switch (val) {
		case 0:
			// Do someting if val is 0
			break;
		case 1:
			// Do something if val is 1
			break;
		case 2:
			// Do something if val is 2
			break;
		default:
			// If no other cases match, do this
			break;
		}
	}
}
