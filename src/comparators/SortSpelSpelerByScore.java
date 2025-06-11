package comparators;

import java.util.Comparator;

import domein.spelerSpel;


public class SortSpelSpelerByScore implements Comparator<spelerSpel> {
	@Override
	public int compare(spelerSpel o1, spelerSpel o2) {
		return Integer.compare(o1.getScore(), o2.getScore()) * -1;
	}

}

