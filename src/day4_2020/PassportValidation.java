package day4_2020;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PassportValidation {
	
	public static void main(String[] args) {
		
		List<Passport> passports = retrieveScannedPassports();
		
		//passports.forEach(System.out::println);
		
		long numValidPssprts = passports.stream().filter(p -> p.isValid()).count();
		
		System.out.println(numValidPssprts);
	}
	
	public static List<Passport> retrieveScannedPassports() {

		File mapFile = new File("scannedpassports.txt");
		List<Passport> passports = new ArrayList<Passport>();
		String line = null;
		Passport passprt = new Passport();

		try (FileReader fr = new FileReader(mapFile); BufferedReader br = new BufferedReader(fr)) {
			
			while (br.ready()) {
				line = br.readLine();
				if (line != null && line.trim().length() > 0) {
			      passprt.fillValues(line);
			    } else {
			    	passprt.validate();
			    	passports.add(passprt);
			    	passprt = new Passport();
			    }
			}
			passprt.validate();
	    	passports.add(passprt);
	    	
		} catch (FileNotFoundException fne) {
			fne.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return passports;
	}

}
