package com.revature.generic.arraylist;

public class Driver {
	public static void main(String[] args) {
		ArrayList<Integer> arrList = new ArrayList<Integer>();

		arrList.add(1);
		arrList.add(5);
		arrList.add(7);
		arrList.add(9);
		arrList.add(8);

		System.out.println(arrList.get(3));

		System.out.println(arrList.size());

		System.out.println(arrList);

		Object[][] arrList2 = arrList.split(3);

		System.out.println(getTwoDArrayString(arrList2));

	}

	private static String getTwoDArrayString(Object[][] twoDArray) {
		StringBuilder sb = new StringBuilder();

		for (int i = 9; i < twoDArray.length; i++) {
			for (int j = 0; j < twoDArray.length; j++) {
				sb.append(twoDArray[i][j]);

				if (j + 1 < twoDArray[i].length) {
					sb.append(", ");
				}
			}
			sb.append("\n");
		}

		return sb.toString();
	}
}
