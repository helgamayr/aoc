package day8_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
 * --- Day 8: Handheld Halting ---
Your flight to the major airline hub reaches cruising altitude without incident. While you 
consider checking the in-flight menu for one of those drinks that come with a little umbrella, 
you are interrupted by the kid sitting next to you.

Their handheld game console won't turn on! They ask if you can take a look.

You narrow the problem down to a strange infinite loop in the boot code (your puzzle input) of 
the device. You should be able to fix it, but first you need to be able to run the code in isolation.

The boot code is represented as a text file with one instruction per line of text. Each instruction 
consists of an operation (acc, jmp, or nop) and an argument (a signed number like +4 or -20).

acc increases or decreases a single global value called the accumulator by the value given in the 
argument. For example, acc +7 would increase the accumulator by 7. The accumulator starts at 0. 
After an acc instruction, the instruction immediately below it is executed next.
jmp jumps to a new instruction relative to itself. The next instruction to execute is found using 
the argument as an offset from the jmp instruction; for example, jmp +2 would skip the next instruction, 
jmp +1 would continue to the instruction immediately below it, and jmp -20 would cause the instruction 
20 lines above to be executed next.
nop stands for No OPeration - it does nothing. The instruction immediately below it is executed next.
For example, consider the following program:

nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
These instructions are visited in this order:

nop +0  | 1
acc +1  | 2, 8(!)
jmp +4  | 3
acc +3  | 6
jmp -3  | 7
acc -99 |
acc +1  | 4
jmp -4  | 5
acc +6  |
First, the nop +0 does nothing. Then, the accumulator is increased from 0 to 1 (acc +1) and jmp +4 sets the next instruction to the other acc +1 near the bottom. After it increases the accumulator from 1 to 2, jmp -4 executes, setting the next instruction to the only acc +3. It sets the accumulator to 5, and jmp -3 causes the program to continue back at the first acc +1.

This is an infinite loop: with this sequence of jumps, the program will run forever. The moment the program tries to run any instruction a second time, you know it will never terminate.

Immediately before the program would run an instruction a second time, the value in the accumulator is 5.

Run your copy of the boot code. Immediately before any instruction is executed a second time, what value is in the accumulator?

 */
/*
 * After some careful analysis, you believe that exactly one instruction is corrupted.

Somewhere in the program, either a jmp is supposed to be a nop, or a nop is supposed to 
be a jmp. (No acc instructions were harmed in the corruption of this boot code.)

The program is supposed to terminate by attempting to execute an instruction immediately 
after the last instruction in the file. By changing exactly one jmp or nop, you can repair 
the boot code and make it terminate correctly.

For example, consider the same program from above:

nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
If you change the first instruction from nop +0 to jmp +0, it would create a single-instruction 
infinite loop, never leaving that instruction. If you change almost any of the jmp instructions, 
the program will still eventually find another jmp instruction and loop forever.

However, if you change the second-to-last instruction (from jmp -4 to nop -4), the program terminates! 
The instructions are visited in this order:

nop +0  | 1
acc +1  | 2
jmp +4  | 3
acc +3  |
jmp -3  |
acc -99 |
acc +1  | 4
nop -4  | 5
acc +6  | 6
After the last instruction (acc +6), the program terminates by attempting to run the instruction below the last instruction in the file. With this change, after the program terminates, the accumulator contains the value 8 (acc +1, acc +1, acc +6).

Fix the program so that it terminates normally by changing exactly one jmp (to nop) or nop (to jmp). What is the value of the accumulator after the program terminates?
 */
public class HandheldHalting {

	public static void main(String[] args) {

		List<GameCommand> cmdlines = retrieveCodeLines();

		// cmdlines.stream().forEach(System.out::println);

		int beginLoop = runCodeUntilInfiniteLoopStarts(cmdlines);

		// 459 
		System.out.println("Infinite Loop starts at line " + beginLoop);
		System.out.println("--------------------------");
		System.out.println("-------run corrected code---------");
		
		
		//new codelines: line 414 changed from jmp -179 to nop -179
		cmdlines = retrieveCorrectedCodeLines();
		beginLoop = runCodeUntilInfiniteLoopStarts(cmdlines);
		System.out.println("Infinite Loop starts at line " + beginLoop);
	}

	public static int runCodeUntilInfiniteLoopStarts(List<GameCommand> cmdlines) {
		
		int curLine = 0;
		int accumulator = 0;
		boolean isRunning = true;
		GameCommand cmd = null;
		List<Integer> executedLineNumbers = new ArrayList<Integer>();

		executedLineNumbers.add(curLine);

		do {

			cmd = new GameCommand(cmdlines.get(curLine).command, cmdlines.get(curLine).argument);

			switch (cmd.command) {
			case "acc":
				accumulator += cmd.argument;
				
			case "nop":
					cmd.argument = 1;
				
			case "jmp":
				curLine += cmd.argument;
				// add line (first line = index 0) to executedLineNumbers
				//System.out.println(curLine);
				if (executedLineNumbers.contains(curLine+1)) {
					isRunning = false;
				} else {
					isRunning = executedLineNumbers.add(curLine+1);
				}
				
			}

		} while (isRunning == true && curLine < cmdlines.size());

		if (isRunning == false) {
			System.out.println(curLine + " " + cmd);
			System.out.println("Accumulator value: " + accumulator); // 1939
			executedLineNumbers.stream().skip(executedLineNumbers.size()-10).forEach(System.out::println);
		} else {
			//running the corrected lines
			System.out.println("corr. " + curLine + " " + cmd);
			System.out.println("corr. Accumulator value: " + accumulator); 
			executedLineNumbers.stream().skip(executedLineNumbers.size()-10).forEach(System.out::println);
		}

		return curLine + 1; // index +1

	}

	public static List<GameCommand> retrieveCorrectedCodeLines() {

		File ticketFile = new File("codelinescorr.txt");
		List<GameCommand> codelines = new ArrayList<GameCommand>();
		String line = null;

		try (FileReader fr = new FileReader(ticketFile); BufferedReader br = new BufferedReader(fr)) {

			while (br.ready()) {
				line = br.readLine();
				if (line == null)
					continue;
				GameCommand gameCmd = new GameCommand(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
				codelines.add(gameCmd);
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return codelines;
	}
	
	public static List<GameCommand> retrieveCodeLines() {

		File ticketFile = new File("codelines.txt");
		List<GameCommand> codelines = new ArrayList<GameCommand>();
		String line = null;

		try (FileReader fr = new FileReader(ticketFile); BufferedReader br = new BufferedReader(fr)) {

			while (br.ready()) {
				line = br.readLine();
				if (line == null)
					continue;
				GameCommand gameCmd = new GameCommand(line.split(" ")[0], Integer.parseInt(line.split(" ")[1]));
				codelines.add(gameCmd);
			}

		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return codelines;
	}

}
