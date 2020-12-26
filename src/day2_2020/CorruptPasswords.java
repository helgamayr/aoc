package day2_2020;

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

public class CorruptPasswords {

	public static void main(String[] args) {

		Map<String, List<String>> passwords = retrievePasswordMap();

		// passwords.forEach((k,v)-> System.out.println(k + ": " + v.toString()));

		System.out.println(countValidPws(passwords, false));
		System.out.println(countValidPws(passwords, true));

	}

	public static long countValidPws(Map<String, List<String>> passwords, boolean isTobbogan) {

		long countedValidPws = 0;

		for (Map.Entry<String, List<String>> entry : passwords.entrySet()) {

			String key = entry.getKey();
			List<String> valueList = entry.getValue();

			char bst = key.charAt(key.length() - 1);
			int min = Integer.parseInt(key.substring(0, key.indexOf("-")));
			int max = Integer.parseInt(key.substring(key.indexOf("-") + 1, key.indexOf(" ")));

			if (isTobbogan) {
				countedValidPws += valueList.stream().mapToLong(s -> isValidTobboganPw(s, min, max, bst)).sum();
			} else {
				countedValidPws += valueList.stream().mapToLong(s -> isValidPw(s, min, max, bst)).sum();
			}

		}

		return countedValidPws;
	}

	public static long isValidPw(String s, int min, int max, char bst) {

		String reducedS = s.replace("" + bst, "");
		int foundChars = s.length() - reducedS.length();
		if (foundChars >= min && foundChars <= max) {
			// System.out.println(min + "-" + max + " " + bst + ": " + s);
			return 1L;
		} else {
			return 0L;
		}
	}

	public static long isValidTobboganPw(String s, int first, int second, char bst) {

		// bst position must either match first or second
		// bst is allowed on any other places except not on both first and second
		// s.chars().collect(Collectors.groupingBy());

		if (s.length() >= first) {
			if (s.toCharArray()[first - 1] == bst) {
				if (s.length() >= second) {
					if (s.toCharArray()[second - 1] != bst) {
						System.out.println("1 " + first + "-" + second + " " + bst + ": " + s);
						return 1L;
					}
				} else {
					return 1L;
				}
			} else {
				if (s.length() >= second) {
					if (s.toCharArray()[second - 1] == bst) {
						System.out.println("2 " + first + "-" + second + " " + bst + ": " + s);
						return 1L;
					} else {
						return 0L;
					}
				} else {
					return 0L;
				}

			}
		}

		return 0L;

	}

	public static Map<String, List<String>> retrievePasswordMap() {

		File passwordsFile = new File("password list.txt");
		Map<String, List<String>> passwords = null;

		try (FileReader fr = new FileReader(passwordsFile); BufferedReader br = new BufferedReader(fr)) {

			Function<String, String> classifier = s -> s.split(":")[0].trim();
			passwords = br.lines().collect(Collectors.groupingBy(classifier));

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Map<String, List<String>> passwordsCopy = new TreeMap<String, List<String>>();
		for (Map.Entry<String, List<String>> entry : passwords.entrySet()) {

			String key = entry.getKey();
			List<String> valueList = entry.getValue();
			List<String> newValueList = valueList.stream().map(s -> s.split(":")[1].trim())
					.collect(Collectors.toList());
			passwordsCopy.put(key, newValueList);

		}

		return passwordsCopy;
	}

}
