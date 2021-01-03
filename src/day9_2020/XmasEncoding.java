package day9_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import day8_2020.GameCommand;

/*
 * --- Day 9: Encoding Error ---
With your neighbor happily enjoying their video game, you turn your attention 
to an open data port on the little screen in the seat in front of you.

Though the port is non-standard, you manage to connect it to your computer through 
the clever use of several paperclips. Upon connection, the port outputs a 
series of numbers (your puzzle input).

The data appears to be encrypted with the eXchange-Masking Addition System (XMAS) which, 
conveniently for you, is an old cypher with an important weakness.

XMAS starts by transmitting a preamble of 25 numbers. After that, each number you receive 
should be the sum of any two of the 25 immediately previous numbers. The two numbers will 
have different values, and there might be more than one such pair.

For example, suppose your preamble consists of the numbers 1 through 25 in a random order. 
To be valid, the next number must be the sum of two of those numbers:

26 would be a valid next number, as it could be 1 plus 25 (or many 
other pairs, like 2 and 24).
49 would be a valid next number, as it is the sum of 24 and 25.
100 would not be valid; no two of the previous 25 numbers sum to 100.
50 would also not be valid; although 25 appears in the previous 25 numbers, 
the two numbers in the pair must be different.
Suppose the 26th number is 45, and the first number (no longer an option, 
as it is more than 25 numbers ago) was 20. Now, for the next number to be valid, 
there needs to be some pair of numbers among 1-19, 21-25, or 45 that add up to it:

26 would still be a valid next number, as 1 and 25 are still within 
the previous 25 numbers.
65 would not be valid, as no two of the available numbers sum to it.
64 and 66 would both be valid, as they are the result of 19+45 and 21+45 respectively.
Here is a larger example which only considers the previous 5 numbers 
(and has a preamble of length 5):

35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576
In this example, after the 5-number preamble, almost every number is the sum 
of two of the previous 5 numbers; the only number that does not follow this rule is 127.

The first step of attacking the weakness in the XMAS data is to find the 
first number in the list (after the preamble) which is not the sum of two of 
the 25 numbers before it. What is the first number that does not have this property?
 */
/*
 * The final step in breaking the XMAS encryption relies on the invalid number you 
 * just found: you must find a contiguous set of at least two numbers in your list 
 * which sum to the invalid number from step 1.

Again consider the above example:

35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576
In this list, adding up all of the numbers from 15 through 40 produces the invalid number from 
step 1, 127. (Of course, the contiguous set of numbers in your actual list might be much longer.)

To find the encryption weakness, add together the smallest and largest number in this contiguous 
range; in this example, these are 15 and 47, producing 62.

What is the encryption weakness in your XMAS-encrypted list of numbers?
 */
public class XmasEncoding {

	public static void main(String[] args) {

		// find weekness in xmas encoding -> the first number that does not follow the
		// encoding rule
		List<Long> encodinglines = retrieveEncoding();
		
		int idxFirstWrongLine = weeknessCheck(encodinglines);

		String retString = (idxFirstWrongLine == 0) ? "none" : ""+encodinglines.get(idxFirstWrongLine);
		System.out.println("encoding weekness found: " + retString);
		
		long minmax = weeknessCheckPart2(encodinglines, idxFirstWrongLine);
		
		System.out.println("minmax summe: " + minmax);
	}
	
	/***
	 * check encoding for weekness such as a number that does not follow 
	 * the rules
	 * 
	 * @param encodingLines
	 * @return 0 if no weekness found or the number that is wrong
	 */
	public static int weeknessCheck(List<Long> encodingLines) {

		long sum = 0L;
		boolean found = false;
		int wrongNumberIndex = 0;

		for (int i = 25; i < encodingLines.size(); i++) {
			wrongNumberIndex = i;
			
			// preambel is the last 25 numbers of the encodingLines
			List<Long> encodingPreambel = new ArrayList<Long>( encodingLines.subList(i-25, i));
			
			// number following the preambel is a sum of a pair of numbers in the preambel
			sum = encodingLines.get(i);

			// find the pair of summands by pairing each number in the preambel
			for (int k = 0; k < encodingPreambel.size(); k++) {
				
				long summand1 = encodingPreambel.get(k);

				// short cut if summand1 is already >= sum no other summand is possible
				if (summand1 >= sum) continue;
				
				List<Long> encodingPreambelSummands = new ArrayList<Long>( encodingPreambel);
				encodingPreambelSummands.remove(k);

				for (int z = 0; z < encodingPreambelSummands.size(); z++) {

					long summand2 = encodingPreambelSummands.get(z);
										
					if (summand1 + summand2 == sum) {
						found = true;
						break;
					}
				}
				if (found) {
					break;}
			}

			// for a correct encoding the pair of summands must be found
			if (!found) {
				break;
			} else {
				found = false;		
			}				
		}

		if (!found) {
			return wrongNumberIndex;
		} else
			return 0;
	}

	/***
	 * check encoding for weakness such as several subsequent numbers add up to the
	 * weakness found in part 1
	 * 
	 * @param encodingLines
	 * @param wrongNumberIndex - weakness number found in part 1
	 * @return the sum of the two numbers that are responsible for weakness found in part 1
	 */
	public static long weeknessCheckPart2(List<Long> encodingLines, int wrongNumberIndex) {
		
		long sum = encodingLines.get(wrongNumberIndex);		
		long minmax = 0;
		
		for (int i = 0; i < encodingLines.size(); i++) {
			List<Long> pn = null;
			for (int j = i+1; j < encodingLines.size(); j++) {
				pn = encodingLines.subList(i, j);
					if (pn.stream().reduce((long) 0,(a,b)-> a+b) == sum) {
						System.out.println(pn.stream().reduce((long) 0,(a,b)-> a+b));
						pn.stream().forEach( l -> System.out.print(l + " "));
						System.out.println();
						System.out.println(pn.stream().max(Long::compareTo).get() + " " + pn.stream().min(Long::compareTo).get());
						minmax = pn.stream().max(Long::compareTo).get() + pn.stream().min(Long::compareTo).get();
						break;
					};
			}
			System.out.println(pn.size());			
			if (minmax > 0) break;
		}
		
		return minmax;
		
	}
	
	public static List<Long> retrieveEncoding() {

		File file = new File("xmasencoding.txt");
		List<Long> encodinglines = new ArrayList<Long>();
		String line = null;

		try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {

			while (br.ready()) {
				line = br.readLine();
				if (line == null)
					continue;
				encodinglines.add(Long.parseLong(line));
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return encodinglines;
	}

}
