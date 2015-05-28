package br.ufpe.cin.coinage.model;

public enum Store {

	UNKNOWN, STEAM, BUSCAPE;
	
	public static Store fromInt(int i) {
		switch(i) {
		case 0:
			return UNKNOWN;
			
		case 1:
			return STEAM;
			
		case 2:
			return BUSCAPE;
			
		default:
			return UNKNOWN;
		}
	}
	
}
