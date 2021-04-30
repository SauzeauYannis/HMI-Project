package gypsysCarnival.model.character;

// This class is an abstract class
public abstract class Character{

	/******************************
	 * Attributes and constructor *
	 ******************************/

	/// Attributes ///
	private final String name;

	/// Constructor ///
	public Character(String name){
		this.name = name;
	}


	/***********
	 * Methods *
	 ***********/

	// Getter
	public String getName() {
		return this.name;
	}

	// Override toString
	@Override
	public String toString() {
		return "[" + this.name + "] : ";
	}
}