package day6_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/*
 * As your flight approaches the regional airport where you'll switch 
 * to a much larger plane, customs declaration forms are distributed 
 * to the passengers.

The form asks a series of 26 yes-or-no questions marked a through z. 
All you need to do is identify the questions for which anyone in your 
group answers "yes". Since your group is just you, this doesn't take 
very long.

However, the person sitting next to you seems to be experiencing a 
language barrier and asks if you can help. For each of the people in 
their group, you write down the questions for which they answer "yes", 
one per line. For example:

abcx
abcy
abcz
In this group, there are 6 questions to which anyone answered "yes": 
a, b, c, x, y, and z. (Duplicate answers to the same question don't 
count extra; each question counts at most once.)

Another group asks for your help, then another, and eventually you've 
collected answers from every group on the plane (your puzzle input). 
Each group's answers are separated by a blank line, and within each 
group, each person's answers are on a single line. For example:

abc

a
b
c

ab
ac

a
a
a
a

b
This list represents answers from five groups:

The first group contains one person who answered "yes" to 3 questions: a, b, and c.
The second group contains three people; combined, they answered "yes" to 3 questions: a, b, and c.
The third group contains two people; combined, they answered "yes" to 3 questions: a, b, and c.
The fourth group contains four people; combined, they answered "yes" to only 1 question, a.
The last group contains one person who answered "yes" to only 1 question, b.
In this example, the sum of these counts is 3 + 3 + 3 + 1 + 1 = 11.

For each group, count the number of questions to which anyone answered "yes". What is the sum of those counts?
 */
/*
 * --- Part Two ---
As you finish the last group's customs declaration, you notice that you misread 
one word in the instructions:

You don't need to identify the questions to which anyone answered "yes"; you need 
to identify the questions to which everyone answered "yes"!

Using the same example as above:

abc

a
b
c

ab
ac

a
a
a
a

b
This list represents answers from five groups:

In the first group, everyone (all 1 person) answered "yes" to 3 questions: a, b, and c.
In the second group, there is no question to which everyone answered "yes".
In the third group, everyone answered yes to only 1 question, a. Since some people did 
not answer "yes" to b or c, they don't count.
In the fourth group, everyone answered yes to only 1 question, a.
In the fifth group, everyone (all 1 person) answered "yes" to 1 question, b.
In this example, the sum of these counts is 3 + 0 + 1 + 1 + 1 = 6.

For each group, count the number of questions to which everyone answered "yes". 
What is the sum of those counts?
 */
public class CustomsQuestionaire {

	public static void main(String[] args) {

		Map<Character, Set<Integer>> answersPerGroup = retrieveAnswersByGroup();

		answersPerGroup.forEach((k, v) -> System.out.println(k + ": " + v.toString()));

		int totalOfYesAnswers = countTotalYesAnswers(answersPerGroup);

		System.out.println(String.format("Total Answers: %d", totalOfYesAnswers));

		Map<Integer, ArrayList<Character>> concordantAnswersPerGroup = retrieveConcordantAnswersByGroup();

		concordantAnswersPerGroup.forEach((k, v) -> System.out.println(k + ": " + v.toString()));

		int totalConcordantYesAnswers = countTotalConcordantAnswers(concordantAnswersPerGroup);

		System.out.println(String.format("Total Concordant Answers: %d", totalConcordantYesAnswers));

	}

	public static Map<Character, Set<Integer>> retrieveAnswersByGroup() {

		File questFile = new File("yesanswers.txt");
		Map<Character, Set<Integer>> answersPerGroup = new TreeMap<Character, Set<Integer>>();
		String line = null;
		int group = 1;

		try (FileReader fr = new FileReader(questFile); BufferedReader br = new BufferedReader(fr)) {

			while (br.ready()) {
				line = br.readLine();
				if (line != null && line.trim().length() > 0) {
					char[] yesQuestions = line.toCharArray();
					Set<Integer> groups = null;
					for (char c : yesQuestions) {
						if (answersPerGroup.get(c) == null) {
							groups = new TreeSet<Integer>();
						} else {
							groups = answersPerGroup.get(c);
						}
						groups.add(group);
						answersPerGroup.put(c, groups);
					}

				} else {
					group += 1;
				}
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return answersPerGroup;
	}

	public static Map<Integer, ArrayList<Character>> retrieveConcordantAnswersByGroup() {

		File questFile = new File("yesanswers.txt");
		Map<Integer, ArrayList<Character>> answersPerGroup = new TreeMap<Integer, ArrayList<Character>>();
		ArrayList<Character> groupAnswers = null;
		String line = null;
		int group = 1;
		int numPersons = 0;

		try (FileReader fr = new FileReader(questFile); BufferedReader br = new BufferedReader(fr)) {

			Map<Character, Integer> countedAnswers = new TreeMap<Character, Integer>();

			while (br.ready()) {

				line = br.readLine();

				if (line != null && line.trim().length() > 0) {
					// count how many people answered yes to each question
					numPersons += 1;

					char[] yesQuestions = line.toCharArray();

					for (char c : yesQuestions) {

						if (countedAnswers.get(c) == null) {
							countedAnswers.put(c, 1);
						} else {
							Integer count = countedAnswers.get(c) + 1;
							System.out.println(count);
							countedAnswers.put(c, count);
						}
					}

				} else {
					groupAnswers = new ArrayList<Character>();
					if (countedAnswers.size() > 0) {
						for (Entry<Character, Integer> entry : countedAnswers.entrySet()) {
							Integer count = countedAnswers.get(entry.getKey());
							if (count == numPersons)
								groupAnswers.add(entry.getKey());
						}
						answersPerGroup.put(group, groupAnswers);
					}
					numPersons = 0;
					group += 1;
					countedAnswers = new TreeMap<Character, Integer>();
				}
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return answersPerGroup;
	}

	public static int countTotalYesAnswers(Map<Character, Set<Integer>> yesAnswersPerGroup) {

		int totalSum = 0;

		for (Set<Integer> valueSet : yesAnswersPerGroup.values()) {
			totalSum += valueSet.size();
		}

		return totalSum;
	}

	public static int countTotalConcordantAnswers(Map<Integer, ArrayList<Character>> yesAnswersPerGroup) {

		int totalSum = 0;

		for (ArrayList<Character> valueList : yesAnswersPerGroup.values()) {
			totalSum += valueList.size();
		}

		return totalSum;
	}
}
