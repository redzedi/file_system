package oop.file_system;

import java.util.Iterator;
import java.util.function.Predicate;

public class FilteredFSItemIterator implements Iterator<FSItem> {
	
	private Iterator<FSItem> itr;
	private Predicate<FSItem> fltrFn;
	
	
	

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FSItem next() {
		// TODO Auto-generated method stub
		return null;
	}

}
