package modele.character;

// This class is a subclass of Character
public class NPC extends Character {

	/***************
	 * Constructor *
	 ***************/

	// Constructor
	public NPC(String name) {
		super(name);
	}

	/**********
	 * Method *
	 **********/

	// To talk
	public void talk(String dialog) {
		System.out.println(this + dialog);
	}
}