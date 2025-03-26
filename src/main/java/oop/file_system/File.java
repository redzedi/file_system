package oop.file_system;

public class File implements FSItem {
	
	private String name;
	private Integer size;
	
	
	

	public File(String name, Integer size) {
		super();
		this.name = name;
		this.size = size;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Integer getSize() {
		return size;
	}

}
