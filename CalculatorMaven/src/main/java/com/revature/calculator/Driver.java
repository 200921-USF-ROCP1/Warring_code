package com.revature.calculator;

import java.util.Scanner;

public class Driver {
	public static void main(String[] args) {
		CalculatorImplemented calc = new CalculatorImplemented();
		Scanner s = new Scanner(System.in);
		
		String input = null;
		
		while (true) {
			System.out.print("Enter equation or type \'q\' to end the program: ");
			input = s.nextLine();
			if (input.equals("q")) {
				break;
			}
			System.out.println(calc.parse(input));
		}
	}
}
