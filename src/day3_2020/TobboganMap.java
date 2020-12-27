package day3_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TobboganMap {

	/**
	 * Due to the local geology, trees in this area only grow on exact integer
	 * coordinates in a grid. You make a map (your puzzle input) of the open squares
	 * (.) and trees (#) you can see. These aren't the only trees, though; due to
	 * something you read about once involving arboreal genetics and biome
	 * stability, the same pattern repeats to the right many times. You start on the
	 * open square (.) in the top-left corner and need to reach the bottom (below
	 * the bottom-most row on your map). The toboggan can only follow a few specific
	 * slopes (you opted for a cheaper model that prefers rational numbers); start
	 * by counting all the trees you would encounter for the slope right 3, down 1.
	 * From your starting position at the top-left, check the position that is right
	 * 3 and down 1. Then, check the position that is right 3 and down 1 from there,
	 * and so on until you go past the bottom of the map. The locations you'd check
	 * in the above example are marked here with O where there was an open square
	 * and X where there was a tree.
	 * 
	 * The TASK: Starting at the top-left corner of your map and following a slope
	 * of right 3 and down 1, how many trees would you encounter?
	 * 
	 * The grid consists of 323 lines and 31 columns that repeat itself on the right
	 * 
	 * y-Position on each line = 1 + (lineNo-1)*3 // starting point is first dot ->
	 * 1 + ; first line has no stop -> lineNo-1
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		/*
		 *     Right 1, down 1.
		 *     Right 3, down 1. (This is the slope you already checked.)
		 *     Right 5, down 1.
		 *     Right 7, down 1.
		 *     Right 1, down 2.
		 */
		int leftHop = 1;
		int downHop = 1;
		long productOfTrees = 0;
		
		char[][] tobboganMap = retrieveTobboganMap();

		int numTrees = earnTreesOnYourTobboganMap(tobboganMap, leftHop, downHop);

		System.out.println("slope 1: " + numTrees);
		
		productOfTrees = numTrees;

		leftHop = 3;
		downHop = 1;
		numTrees = earnTreesOnYourTobboganMap(tobboganMap, leftHop, downHop);

		System.out.println("slope 2: " + numTrees);
		
		productOfTrees *= numTrees;

		leftHop = 5;
		downHop = 1;
		numTrees = earnTreesOnYourTobboganMap(tobboganMap, leftHop, downHop);

		System.out.println("slope 3: " + numTrees);
		
		productOfTrees *= numTrees;

		leftHop = 7;
		downHop = 1;

		numTrees = earnTreesOnYourTobboganMap(tobboganMap, leftHop, downHop);

		System.out.println("slope 4: " + numTrees);
		
		productOfTrees *= numTrees;

		leftHop = 1;
		downHop = 2;
		numTrees = earnTreesOnYourTobboganMap(tobboganMap, leftHop, downHop);

		System.out.println("slope 5: " + numTrees);
		
		productOfTrees *= numTrees;
		
		System.out.println("result: " + productOfTrees);
	}

	public static int earnTreesOnYourTobboganMap(char[][] tobboganMap, int leftHop, int downHop) {

		// char space = '.';
		char tree = '#';
		int curLine = 0, curCol = 0;
		int numTrees = 0;
		int numGrids = 1;

		//System.out.println(tobboganMap.length);

		while (curLine < tobboganMap.length) {
			curCol += leftHop;
			curLine += downHop;

			if (curLine >= tobboganMap.length)
				break;

			if (curCol >= tobboganMap[curLine].length) {
				curCol = curCol - tobboganMap[curLine].length;
			}

			// System.out.println(curLine + " " + curCol);

			if (tobboganMap[curLine][curCol] == tree) {
				numTrees += 1;
			}
		}
		;

		return numTrees;
	}

	public static char[][] retrieveTobboganMap() {

		File mapFile = new File("tobboganmap.txt");
		char[][] tobboganMap = null;
		long numLines = 0;
		long lineLength = 0;
		Object[] lines = null;

		try (FileReader fr = new FileReader(mapFile); BufferedReader br = new BufferedReader(fr)) {

			lines = br.lines().toArray();
			numLines = lines.length;
			lineLength = lines[0].toString().length();
			tobboganMap = new char[(int) numLines][(int) lineLength];

			for (int line = 0; line < numLines; line++) {
				for (int pos = 0; pos < lineLength; pos++) {
					tobboganMap[line][pos] = lines[line].toString().toCharArray()[pos];
					if (line < 4 && pos < 5) {
						// System.out.print(tobboganMap[line][pos]);
					}
				}
				// System.out.println();
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tobboganMap;
	}

}
