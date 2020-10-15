package com.revature.calculator;

import java.lang.Math;
import java.util.Scanner;

public class CalculatorImplemented implements Calculator {
	
	/*
	 * Calculate should:
	 * 1. Take input - done
	 * 2. Parse the input (this is a part of the Calculator interface)
	 * 3. If it is valid input, output the result
	 * 4. If it is invalid, output an error
	 * 
	 * Because it is in an infinite loop, you only need 
	 * to do those four steps.
	 */
	

//		System.out.println(calc.add(1, 2));
//		System.out.println(calc.subtract(3,2));
//		System.out.println(calc.multiply(5,6));
//		System.out.println(calc.divide(6,3));
//		System.out.println(calc.exponent(2,2));
//		System.out.println(calc.fibonacci(5));
		
//		System.out.println(calc.parse("1 + 1"));
//		System.out.println(calc.parse("4 - 2"));
//		System.out.println(calc.parse("1 * 2"));
//		System.out.println(calc.parse("4 / 2"));
//		System.out.println(calc.parse("2 ^ 1"));
//		System.out.println(calc.parse("3 fib"));

	public int add(int a, int b) {
			return a + b;
	}

	public int subtract(int a, int b) {
		return a - b;
	}

	public int multiply(int a, int b) {
		return a * b;
	}

	public int divide(int a, int b) {
		return a / b;
	}

	public int exponent(int x, int e) {
		/* Alternative method of doing this
		 * int num = 1;
		 * for (int i = 0; i < e; i++) {
		 * 	num *= x;
		 * }
		 * return x;
		 */
		return (int) Math.pow(x, e);
	}

	public int fibonacci(int i) {
		int a = 0;
		int b = 1;
		boolean c = true;
		int position;
		
		switch (i) {
		case (1):
			return 0;
		case (2):
			return 1;
		default:
			for (position = 2; position < i; position++) {
				if (c) {
					a = a + b;
					c = false;
				} else {
					b = a + b;
					c = true;
				}
			}
			if (c) {
				return b;
			} else {
				return a;
			}
		}
	}

	// Parse a String into parameters and an operation
		// eg, given "5 + 2", return add(5, 2)
		// Look into String.parse()
	public int parse(String s) {
		String errorMsg = "Invalid Input - returning -1";
		int errorVal = -1;
		
		int result;
		String[] p = s.split(" ");
		
		if (p.length < 2 || p.length > 3) {
			System.out.println(errorMsg);
			return errorVal;
		}
		
		try {
			Integer.parseInt(p[0]);
		} catch (Exception e) {
			System.out.println(errorMsg);
			return errorVal;
		}
		
		switch (p[1]) {
		case ("+"):
			return add(Integer.parseInt(p[0]), Integer.parseInt(p[2]));
		case ("-"):
			return subtract(Integer.parseInt(p[0]), Integer.parseInt(p[2]));
		case ("*"):
			return multiply(Integer.parseInt(p[0]), Integer.parseInt(p[2]));
		case ("/"):
			return divide(Integer.parseInt(p[0]), Integer.parseInt(p[2]));
		case ("^"):
			return exponent(Integer.parseInt(p[0]), Integer.parseInt(p[2]));
		case ("fib"):
			return fibonacci(Integer.parseInt(p[0]));
		default:
			System.out.println("Invalid Input - returning -1");
			return -1;
		}
	}

}
