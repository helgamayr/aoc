package day4_2020;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

/*
 * ecl:gry - eye color
 * pid:860033327 - passport id
 * eyr:2020 - expiration year
 * hcl:#fffffd - hair color
 * byr:1937 - birth year
 * iyr:2017 - issue year
 * cid:147 - country id
 * hgt:183cm - height
 */
public class Passport {

	private String[] fields = new String[] { "byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid", "cid" };
	private int _byr; // (Birth Year)
	private int _iyr; // (Issue Year)
	private int _eyr; // (Expiration Year)
	private String _hgt; // (Height) in cm
	private String _hcl; // (Hair Color) in hex
	private String _ecl; // (Eye Color) in color code
	private String _pid; // (Passport ID)
	private String _cid; // (Country ID)
	private boolean valid;

	public Passport() {
	}

	// public Passport(int byr, int iyr, int eyr,
//			int hgt, long hcl, String ecl,
//			long pid, int cid){
//		 _byr = byr;
//	     _iyr = iyr; 
//	     _eyr = eyr;
//	     _hgt = hgt; 
//	     _hcl = hcl;
//	     _ecl = ecl; 
//	     _pid = pid; 
//	     _cid = cid; 
//	     valid = true;
//	}

	public int get_byr() {
		return _byr;
	}

	public void set_byr(int _byr) {
		this._byr = _byr;
	}

	public int get_iyr() {
		return _iyr;
	}

	public void set_iyr(int _iyr) {
		this._iyr = _iyr;
	}

	public int get_eyr() {
		return _eyr;
	}

	public void set_eyr(int _eyr) {
		this._eyr = _eyr;
	}

	public String get_hgt() {
		return _hgt;
	}

	public void set_hgt(String _hgt) {
		this._hgt = _hgt;
	}

	public String get_hcl() {
		return _hcl;
	}

	public void set_hcl(String _hcl) {
		this._hcl = _hcl;
	}

	public String get_ecl() {
		return _ecl;
	}

	public void set_ecl(String _ecl) {
		this._ecl = _ecl;
	}

	public String get_pid() {
		return _pid;
	}

	public void set_pid(String _pid) {
		this._pid = _pid;
	}

	public String get_cid() {
		return _cid;
	}

	public void set_cid(String _cid) {
		this._cid = _cid;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	@Override
	public String toString() {
		String passportString = "";

		for (int i = 0; i < fields.length; i++) {
			switch (fields[i]) {
			case "byr":
				passportString += "byr:" + get_byr() + " ";
				break;
			case "iyr":
				passportString += "iyr:" + get_iyr() + " ";
				break;
			case "eyr":
				passportString += "eyr:" + get_eyr() + " ";
				break;
			case "hgt":
				passportString += "hgt:" + get_hgt() + " ";
				break;
			case "hcl":
				passportString += "hcl:" + get_hcl() + " ";
				break;
			case "ecl":
				passportString += "ecl:" + get_ecl() + " ";
				break;
			case "pid":
				passportString += "pid:" + get_pid() + " ";
				break;
			case "cid":
				passportString += "cid:" + get_cid() + " ";
				break;
			}
		}
		return passportString + " isValid:" + isValid();
	}

	public void fillValues(String record) {
		String[] values = record.split(" ");

		// System.out.println(values.length);

		Map<String, String> keyValuePairs = new TreeMap<String, String>();

		for (int i = 0; i < values.length; i++) {
			// System.out.println(values[i].split(":")[0] + " " + values[i].split(":")[1]);
			keyValuePairs.put(values[i].split(":")[0], values[i].split(":")[1]);
		}

		for (Entry<String, String> entry : keyValuePairs.entrySet()) {
			switch (entry.getKey()) {
			case "byr":
				set_byr(Integer.parseInt(entry.getValue()));
				break;
			case "iyr":
				set_iyr(Integer.parseInt(entry.getValue()));
				break;
			case "eyr":
				set_eyr(Integer.parseInt(entry.getValue()));
				break;
			case "hgt":
				set_hgt(entry.getValue());
				break;
			case "hcl":
				set_hcl(entry.getValue());
				break;
			case "ecl":
				set_ecl(entry.getValue());
				break;
			case "pid":
				set_pid(entry.getValue());
				break;
			case "cid":
				set_cid(entry.getValue());
				break;
			}
			// System.out.println(this.toString());
		}

	}

	public void validate() {
		/*
		 * Validation Rules You can continue to ignore the cid field, but each other
		 * field has strict rules about what values are valid for automatic validation:
		 * byr (Birth Year) - four digits; at least 1920 and at most 2002.
		 * 
		 * iyr (Issue Year) - four digits; at least 2010 and at most 2020.
		 * 
		 * eyr (Expiration Year) - four digits; at least 2020 and at most 2030.
		 * 
		 * hgt (Height) - a number followed by either cm or in:
		 * 
		 * If cm, the number must be at least 150 and at most 193.
		 * 
		 * If in, the number must be at least 59 and at most 76.
		 * 
		 * hcl (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
		 * 
		 * ecl (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
		 * 
		 * pid (Passport ID) - a nine-digit number, including leading zeroes.
		 * 
		 * cid (Country ID) - ignored, missing or not.
		 */
		int numValidEntries = 0;

		for (int i = 0; i < fields.length; i++) {
			switch (fields[i]) {
			case "byr":
				if (get_byr() != 0 && get_byr() >= 1920 && get_byr() <= 2002)
					numValidEntries += 1;
				break;
			case "iyr":
				if (get_iyr() != 0 && get_iyr() >= 2010 && get_iyr() <= 2020)
					numValidEntries += 1;
				break;
			case "eyr":
				if (get_eyr() != 0 && get_eyr() >= 2020 && get_eyr() <= 2030)
					numValidEntries += 1;
				break;
			case "hgt":
				//System.out.println(get_hgt().substring(0, get_hgt().length() - 2));
				if (get_hgt() != null) {
					if (get_hgt().endsWith("cm") || get_hgt().endsWith("in")) {
						if (get_hgt().endsWith("cm")
								&& Integer.parseInt(get_hgt().substring(0, get_hgt().length() - 2)) >= 150
								&& Integer.parseInt(get_hgt().substring(0, get_hgt().length() - 2)) <= 193)
							numValidEntries += 1;
						if (get_hgt().endsWith("in")
								&& Integer.parseInt(get_hgt().substring(0, get_hgt().length() - 2)) >= 59
								&& Integer.parseInt(get_hgt().substring(0, get_hgt().length() - 2)) <= 76)
							numValidEntries += 1;
					}
				}
				break;
			case "hcl":
				if (get_hcl() != null) {
					if (get_hcl().length() == 7 && get_hcl().startsWith("#")) {
						try {
							Long.parseLong(get_hcl().substring(1), 16);
							numValidEntries += 1;
						} catch (Exception e) {
						}
					}
				}

				break;
			case "ecl":
				if (get_ecl() != null) {
					if (get_ecl().length() == 3) {

						switch (get_ecl()) {
						case "amb":
						case "blu":
						case "brn":
						case "grn":
						case "gry":
						case "hzl":
						case "oth":
							numValidEntries += 1;
						default:
							break;
						}
					}
				}
				break;
			case "pid":
				if (get_pid() != null) {
					if (get_pid().length() == 9)

						try {
							Long.parseLong(get_pid());
							numValidEntries += 1;
						} catch (Exception e) {
						}
				}

				break;
			case "cid":
				if (get_cid() != null)
					numValidEntries += 1;
				break;
			}
		}

		if (numValidEntries < 7)
			setValid(false);
		if (numValidEntries == 8)
			setValid(true);
		if (numValidEntries == 7 && get_cid() == null)
			setValid(true);
		if (numValidEntries == 7 && get_cid() != null)
			setValid(false);

		System.out.println(numValidEntries + " " + this);
	}
}
