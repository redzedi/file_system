package file_system;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Test;

import oop.file_system.Directory;
import oop.file_system.FSItem;
import oop.file_system.File;

public class DirectoryTest {
	
	@Test
	public void nestedDirectoryTest1() {
		
		Directory rt = new Directory("/");
		
		Directory rt_b = new Directory("b");
		File rt_a = new File("a",2);
		File rt_c = new File("c",5);
		rt.addChild(rt_a);
		rt.addChild(rt_b);
		rt.addChild(rt_c);
		
		Directory rt_b2 = new Directory("b2");
		File rt_b1 = new File("b1",2);
		File rt_b3 = new File("b3",5);
		rt_b.addChild(rt_b1);
		rt_b.addChild(rt_b2);
		rt_b.addChild(rt_b3);
		
		
		File rt_b21 = new File("b21",2);
		File rt_b22 = new File("b22",5);
		rt_b2.addChild(rt_b21);
		rt_b2.addChild(rt_b22);
		
		assertEquals( Integer.valueOf(21),rt.getSize());
		
		ArrayList<String> flNames = new ArrayList<>();
		
		for(FSItem it:rt) {
			flNames.add(it.getName());
		}
		
		assertEquals(Arrays.asList("a", "b", "b1", "b2", "b21", "b22", "b3", "c"), flNames);
		
		System.out.println(flNames);
		
		
		
	}
	
	public static void main(String[] args) {
		Predicate<String> nameFilter = (s)->"suman".equals(s);
		Predicate<String> sizeFilter = (s)-> s!=null&&(s.length() == 5);
		
		System.out.println(sizeFilter.test("suman"));
		System.out.println(sizeFilter.test("abcde"));
		System.out.println(sizeFilter.and(nameFilter).test("suman"));
		System.out.println(sizeFilter.or(nameFilter).test("abcde"));
		
	}

}
