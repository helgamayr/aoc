package day7_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Timer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * --- Day 7: Handy Haversacks ---
You land at the regional airport in time for your next flight. In fact, 
it looks like you'll even have time to grab some food: all flights are 
currently delayed due to issues in luggage processing.

Due to recent aviation regulations, many rules (your puzzle input) are 
being enforced about bags and their contents; bags must be color-coded 
and must contain specific quantities of other color-coded bags. Apparently, 
nobody responsible for these regulations considered how long they would 
take to enforce!

For example, consider the following rules:

light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.
These rules specify the required contents for 9 bag types. In this example, 
every faded blue bag is empty, every vibrant plum bag contains 11 bags 
(5 faded blue and 6 dotted black), and so on.

You have a shiny gold bag. If you wanted to carry it in at least one other 
bag, how many different bag colors would be valid for the outermost bag? 
(In other words: how many colors can, eventually, contain at least 
one shiny gold bag?)

In the above rules, the following options would be available to you:

A bright white bag, which can hold your shiny gold bag directly.
A muted yellow bag, which can hold your shiny gold bag directly, plus some other bags.
A dark orange bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
A light red bag, which can hold bright white and muted yellow bags, either of which could then hold your shiny gold bag.
So, in this example, the number of bag colors that can eventually contain at least one shiny gold bag is 4.

How many bag colors can eventually contain at least one shiny gold bag? 
(The list of rules is quite long; make sure you get all of it.)

L�sung ist 235 !!!
 */

/*
 * It's getting pretty expensive to fly these days - not because of 
 * ticket prices, but because of the ridiculous number of bags you need to buy!

Consider again your shiny gold bag and the rules from the above example:

faded blue bags contain 0 other bags.
dotted black bags contain 0 other bags.
vibrant plum bags contain 11 other bags: 5 faded blue bags and 6 dotted black bags.
dark olive bags contain 7 other bags: 3 faded blue bags and 4 dotted black bags.
So, a single shiny gold bag must contain 1 dark olive bag (and the 7 bags within it) 
plus 2 vibrant plum bags (and the 11 bags within each of those): 1 + 1*7 + 2 + 2*11 = 32 bags!

Of course, the actual rules have a small chance of going several levels deeper than 
this example; be sure to count all of the bags, even if the nesting becomes topologically impractical!

Here's another example:

shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.
In this example, a single shiny gold bag must contain 126 other bags.

How many individual bags are required inside your single shiny gold bag?
 */

public class HandyHaversacks {

	public static void main(String[] args) {

		Map<String, Set<String>> handbagRules = retrieveHandbagRules();

		// handbagRules.forEach((k, v) -> System.out.println(k + " " + v));

		String innerbagName = "shiny gold";

		long numBags = 0;
		Set<String> bags = new TreeSet<>();
		bags.add(innerbagName);

		bags.addAll(recursiveLoop(bags, handbagRules));
		bags.remove(innerbagName);
		numBags = bags.size();

		System.out.println(String.format("%d number of bags can hold a %s bag", numBags, innerbagName));

		Map<String, Map<String, Integer>> handbagRulesPart2 = retrieveHandbagRulesPart2();

		// handbagRulesPart2.forEach((k,v)-> System.out.println(k + " " + v));

		long result = recursiveLoopPart2(handbagRulesPart2, innerbagName);

		// 119 too low, 276 too low, 134901 too low, 
		System.out.println(result);  //158493
	}

	public static int fakulty(int n) {

		if (n > 0) {
			int f = n * fakulty(n - 1);
			return f;
		}
		return 1;
	}

	public static long recursiveLoopPart2(Map<String, Map<String, Integer>> handbagRules, String bagName) {

		Map<String, Integer> innerbags = handbagRules.get(bagName);

		if (innerbags == null)
			return 0;

		//System.out.println(innerbags.size());

		int sumNumBags = 0;

		for (Entry<String, Integer> entry : innerbags.entrySet()) {
			//System.out.println(entry.getValue() + " + " + entry.getValue() + " * " + sumNumBags);
			sumNumBags += entry.getValue()
					+ entry.getValue() * recursiveLoopPart2(handbagRules, entry.getKey());
		}

		return sumNumBags;
	}

	public static HashSet<String> recursiveLoop(Set<String> bags, Map<String, Set<String>> handbagRules) {

		HashSet<String> uniqueBags = new HashSet<>();

		for (String s : bags) {
			// count handbags that hold the given s-handbag

			HashSet<String> temp = handbagRules.entrySet().stream().filter(entrySet -> {
				if (entrySet.getValue() != null)
					return entrySet.getValue().contains(s);
				else
					return false;
			}).map(Map.Entry::getKey).collect(Collectors.toCollection(HashSet::new));

			if (temp == null)
				continue;
			if (temp.size() == 0)
				continue;

			uniqueBags.addAll(temp);
			uniqueBags.addAll(recursiveLoop(temp, handbagRules));

		}

		return uniqueBags;
	}

	public static Map<String, Map<String, Integer>> retrieveHandbagRulesPart2() {

		File questFile = new File("HandbagRules.txt");
		Map<String, Map<String, Integer>> handbagRules = new TreeMap<String, Map<String, Integer>>();

		String line = null;

		try (FileReader fr = new FileReader(questFile); BufferedReader br = new BufferedReader(fr)) {

			while (br.ready()) {
				line = br.readLine();
				line = line.replace("bags", "").replace("bag", "").replace(".", "").replace("contain no other", "")
						.trim();

				if (!line.contains("contain"))
					continue;

				String bagname = line.split("contain")[0].trim();
				String[] bagcontents = null;
				Map<String, Integer> contentMap = new TreeMap<String, Integer>();

				try {
					String contentString = line.split("contain")[1].trim();
					// System.out.println(contentString);
					if (contentString.contains(",")) {
						bagcontents = line.split("contain")[1].trim().split(",");
					} else {
						bagcontents = new String[] { contentString.trim() };
					}
					for (String s : bagcontents) {
						contentMap.put(s.substring(2).trim(), Integer.parseInt(s.substring(0, 2).trim()));
					}
					handbagRules.put(bagname, contentMap);

				} catch (Exception e) {

					System.out.println(e.getMessage() + ": " + bagname + " " + bagcontents + " " + contentMap);
				}

			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return handbagRules;
	}

	public static Map<String, Set<String>> retrieveHandbagRules() {

		File questFile = new File("HandbagRules.txt");
		Map<String, Set<String>> handbagRules = new TreeMap<String, Set<String>>();

		String line = null;

		try (FileReader fr = new FileReader(questFile); BufferedReader br = new BufferedReader(fr)) {

			while (br.ready()) {
				line = br.readLine();
				line = line.replace("bags", "").replace("bag", "").replace(".", "").replace("no other", "").trim();

				String bagname = line.split("contain")[0].trim();
				String[] bagcontents = null;
				Set<String> contentSet = new TreeSet<String>();
				try {
					if (line.split("contain").length == 2) {
						bagcontents = line.split("contain")[1].trim().split(",");

						for (String s : bagcontents) {
							contentSet.add(s.substring(2).trim());
						}
						handbagRules.put(bagname, contentSet);
					}

				} catch (Exception e) {

					System.out.println(bagname + " " + bagcontents + " " + contentSet);
				}

			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return handbagRules;
	}

}