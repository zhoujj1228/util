package demo.domain;

import java.util.ArrayList;
import java.util.List;

public class Person {
	String name;
	String id;
	List<String> books = new ArrayList<>();
	
	public List<String> getBooks() {
		return books;
	}
	public void setBooks(List<String> books) {
		this.books = books;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
