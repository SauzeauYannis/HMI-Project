package modele;

// This class is an enumeration
public enum Level {

	COPPER,
	GOLD,
	PLATINUM;

	@Override
	public String toString() {
		return super.toString().toLowerCase();
	}
}