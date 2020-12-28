package day5_2020;

public class Seat implements Comparable<Seat>{
	
	private String seatCode;
	private String rowCode;
	public int seat;
	public int row;
	public int id;
	
	public Seat(String rowCode, String seatCode){
		this.rowCode = rowCode;
		this.seatCode = seatCode;
		
		this.DecodeRowCode();
		this.DecodeSeatCode();
		
		id = row * 8 + seat;
	}
	
	public void DecodeRowCode() {
		
		String binary = this.rowCode.replace("F","0");
		binary = binary.replace("B", "1");
		
		row = Integer.parseInt(binary, 2);
	}
	
	public void DecodeSeatCode() {
		
		String binary = this.seatCode.replace("L","0");
		binary = binary.replace("R", "1");
		
		seat = Integer.parseInt(binary, 2);
	}
	
	@Override
	public String toString() {
		String seatString = String.format("Seat-ID: %d -- %d / %d", id, row, seat);
		return seatString;
	}

	@Override
	public int compareTo(Seat o) {
		return this.id - o.id;
	}

}
