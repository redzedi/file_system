package oop.file_system;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class Directory implements FSItem, Iterable<FSItem> {
	
	private String name;
	
	private ArrayList<FSItem> children = new ArrayList<>();
	

	public Directory(String name) {
		super();
		this.name = name;
	}
	
	public void addChild(FSItem c) {
		children.add(c);
	}

	public List<FSItem> getChildren(){
		return Collections.unmodifiableList(children);
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getSize() {
		return children.stream().map(FSItem::getSize).reduce(Math::addExact).orElseThrow();
	}
	
	private class ItrItemHolder{
		final FSItem item;
		Iterator<ItrItemHolder> itr;
		
		private ItrItemHolder(FSItem item) {
			super();
			this.item = item;
		}
		
		
	}
	
	private Iterator<ItrItemHolder> directoryIterator(Predicate<FSItem> fltr){
		
		final ArrayDeque<ItrItemHolder> st = new ArrayDeque<>();
		for (int i =children.size()-1; i >= 0; i--) {
			if(fltr.test(children.get(i)))
			st.push(new ItrItemHolder(children.get(i)));
			
		}
		
		
		return new Iterator<ItrItemHolder>() {

			@Override
			public boolean hasNext() {
				return !st.isEmpty();
			}

			@Override
			public ItrItemHolder next() {
				
				if(st.isEmpty()) {
					return null;
				}
				
				ItrItemHolder nxt = st.peek();
				
				if(nxt.item instanceof Directory currDir) {
					
					if(nxt.itr == null) {
						nxt.itr = currDir.directoryIterator(fltr);
					}else {
						if(nxt.itr.hasNext()) {
							ItrItemHolder currDirNxt = nxt.itr.next();
							if(currDirNxt.item instanceof Directory currDirCurrDir) {
								st.push(currDirNxt);
							}
							nxt = currDirNxt;
						}else {
							//currDir iterator is finished
							st.pop();
							if(!st.isEmpty()) {
								ArrayDeque<ItrItemHolder> tmp = st;
								for(;!tmp.isEmpty()&& tmp.peek().itr!=null && !tmp.peek().itr.hasNext();tmp.pop());
								nxt = tmp==null?null:tmp.peek().itr == null? tmp.peek() : tmp.peek().itr.next();
								if(tmp.peek().itr == null) {
									tmp.pop();
								}
								
							}else {
								nxt = null;
							}
								
						}
						
					}
					
					
				}else {
					st.pop();
				}
				
				return nxt;
			}
			
		};
		
		
	
		
	}

	@Override
	public Iterator<FSItem> iterator() {
		
	return new Iterator<FSItem>() {
			
			final Iterator<ItrItemHolder> internalIterator = directoryIterator((fs)->true);
			

			@Override
			public boolean hasNext() {
				return internalIterator.hasNext();
			}

			@Override
			public FSItem next() {
			
				ItrItemHolder tmpNxt = internalIterator.next();
				
				return tmpNxt !=null ? tmpNxt.item:null;
			}
			
		};
		
	}

}
