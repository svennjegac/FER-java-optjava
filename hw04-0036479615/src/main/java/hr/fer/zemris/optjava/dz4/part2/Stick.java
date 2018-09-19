package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.List;

public class Stick {

	private int length;

	public Stick(int length) {
		this.length = length;
	}
	
	public int getLength() {
		return length;
	}
	
	public static List<Stick> copyStickList(List<Stick> stickList) {
		List<Stick> copyList = new ArrayList<>();
		
		stickList.forEach(stick -> copyList.add(stick));
		
		return copyList;
	}
	
	@Override
	public String toString() {
		return "" + length;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + length;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stick other = (Stick) obj;
		if (length != other.length)
			return false;
		return true;
	}
}
