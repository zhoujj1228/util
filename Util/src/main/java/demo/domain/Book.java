package demo.domain;

public class Book {
	private int bookId;//���ID
	private String author;//����
	private String name;//����
	private int price;//���
	private Person authorData;
 
	public Person getAuthorData() {
		return authorData;
	}

	public void setAuthorData(Person authorData) {
		this.authorData = authorData;
	}

	public int getBookId() {
		return bookId;
	}
 
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
 
	public String getAuthor() {
		return author;
	}
 
	public void setAuthor(String author) {
		this.author = author;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public int getPrice() {
		return price;
	}
 
	public void setPrice(int price) {
		this.price = price;
	}
 
	@Override
	public String toString() {
		return "Book [bookId=" + bookId + ", author=" + author + ", name="
				+ name + ", price=" + price+ ", authorData=" + authorData.getBooks() + "]";
	}
 
}

