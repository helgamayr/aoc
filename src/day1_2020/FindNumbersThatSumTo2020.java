package day1_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FindNumbersThatSumTo2020 {
	
	public static void main(String[] args) {
		
		ArrayList<Integer> numbers = retrieveNumbers();
		System.out.println(numbers.size());	 
		
		int[] twoNumbers = findTwoNumbers(numbers,2020,-1);
		System.out.println(twoNumbers.length);	
		
		System.out.println(twoNumbers[0] * twoNumbers[1]);
		
		int[] threeNumbers = findThreeNumbers(numbers,2020);
		
		System.out.println(threeNumbers.length);	
		
		System.out.println(threeNumbers[0] * threeNumbers[1] * threeNumbers[2]);
	}
	
	public static ArrayList<Integer> retrieveNumbers() {
		
		File numbersFile = new File("numbers.txt");
		ArrayList<Integer> numbers = new ArrayList<Integer>();
		
		try(FileReader fr = new FileReader(numbersFile);
				BufferedReader br = new BufferedReader(fr)){
			while(br.ready()) {
				numbers.add(new Integer(br.readLine()));
			}
					
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		}catch (IOException ioe) {
			ioe.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return numbers;
	}

	public static int[] findTwoNumbers(ArrayList<Integer> numbers, int matchSum, int exclude) {
		
		int numberOne;
		int numberTwo;
		int sum;
		
		for (int i = 0; i < numbers.size(); i++) {
			
			if (i == exclude)  { continue;}
			
			for (int k = i+1; k < numbers.size(); k++) {
				
				sum = numbers.get(i) + numbers.get(k);
				
				if ( sum == matchSum) {
					return new int[] {numbers.get(i), numbers.get(k)};
				}
			}
		}
				
		
		return new int[] {};
	}
	
	public static int[] findThreeNumbers(ArrayList<Integer> numbers, int matchSum) {
		
		int numberOne;
		int sum;
		int[] twoNumbers;
		
		for (int i = 0; i < numbers.size(); i++) {
				
				twoNumbers = findTwoNumbers(numbers, matchSum-numbers.get(i), i);
				
				if (twoNumbers.length != 0) {
				
				sum = numbers.get(i) + twoNumbers[0] + twoNumbers[1];
				
				if ( sum == matchSum) {
					return new int[] {numbers.get(i), twoNumbers[0] , twoNumbers[1]};
				}
				}
			
		};
				
		
		return new int[] {};
	}
	
}
