package cn.edu.ustb.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * bean ¿‡
 * @author phantom
 *
 */
@XmlRootElement
public class Book {
	private String id ;
	private String name ;
	private String author ;
	private String category ;
	
	public Book(){}
	public Book(String id,String name,String author,String category){
		setAuthor(author);
		setCategory(category);
		setId(id);
		setName(name);
	}
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
