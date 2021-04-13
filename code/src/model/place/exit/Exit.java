package model.place.exit;

import model.place.Ending;
import model.place.Game;
import model.place.Place;

import java.util.ArrayList;
import java.util.List;

public class Exit {

	private final Place place;
	private boolean isLock;

	public Exit(Place place, boolean isLock) {
		this.place = place;
		this.isLock = isLock;
	}

	public Place getPlace() {
		return place;
	}

	public boolean isLock() {
		return isLock;
	}

	public void unlock() {
		isLock = false;
		String place = this.place.getName();
		System.out.println("| " + place + " is now unlock.\n" +
				"| Type \"go " + place.split(" ")[0].toLowerCase() + "\" to get inside!");
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