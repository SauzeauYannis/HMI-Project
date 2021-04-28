package gypsysCarnival.model.place.exit;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import gypsysCarnival.model.place.Ending;
import gypsysCarnival.model.place.Game;
import gypsysCarnival.model.place.Place;

import java.util.ArrayList;
import java.util.List;

public class Exit {

	private final Place place;
	private final BooleanProperty isLock;

	public Exit(Place place, boolean isLock) {
		this.place = place;
		this.isLock = new SimpleBooleanProperty(isLock);
	}

	public Place getPlace() {
		return place;
	}

	public BooleanProperty isLockProperty() {
		return isLock;
	}

	public boolean isLock() {
		return isLock.get();
	}

	public void unlock() {
		this.isLock.setValue(false);
		String place = this.place.getName();
		System.out.println("| " + place + " is now unlock.");
	}

	public static List<Exit> generateAllExits(List<Place> placeList) {
		List<Exit> exitList = new ArrayList<>();

		for (Place place: placeList) {
			exitList.add(new Exit(place,
					(place instanceof Game || place instanceof Ending)));
		}

		return exitList;
	}

}