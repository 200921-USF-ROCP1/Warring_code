package com.revature.app;

public class VowelCounter {
	import java.lang.*;
	import java.io.*;
	import java.util.*;

	public class Main
	{
	    public static void main(String[] args)
	    {
	        Scanner sc=new Scanner(System.in);

	        String sampleInput;
	        int result = 0;
	        sampleInput = sc.nextLine();
	        String[] vowelList = { "a", "e", "i", "o", "u" };
	        
	        //write your Logic
	        for (int i = 0, int j = 0; i < sampleInput.length(); i++) {
	            for (int j = 0; j < vowelList.length; j++) {
	                if (Character.toString(sampleInput.charAt(i)).equalsIgnoreCase(vowelList[j])) {
	                    result++;
	                }   
	            }
	           sampleInput.split(" ");
	        }


	        //OUTPUT [uncomment & modify if required]
	        System.out.println(result);
	    
	    }
	}
}
