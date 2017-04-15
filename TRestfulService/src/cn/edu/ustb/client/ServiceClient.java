package cn.edu.ustb.client;

import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

import javax.print.attribute.standard.Media;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import com.sun.jersey.api.client.*;
import cn.edu.ustb.model.*;

import java.util.Iterator;
import java.util.List;
/**
 * 客户端，对服务进行测试
 * @author phantom
 *
 */
public class ServiceClient {
	//获得一个book信息
	public void getOneBook(WebResource r,String id){
		GenericType<JAXBElement<Book>> gene = new GenericType<JAXBElement<Book>>(){} ;
		JAXBElement<Book> jaxbBook = r.path(id).accept(MediaType.APPLICATION_XML).get(gene);
		Book book = jaxbBook.getValue();
		System.out.println(book.getName());
	}
	//获得所有的book信息
	public void getAllBooks(WebResource r){
		//获取整个字符串
		String xmlstr = r.accept(MediaType.APPLICATION_XML).get(String.class);
		//获得一些回复信息
		ClientResponse response = r.get(ClientResponse.class);
		System.out.println(response.getStatus());
		//获得整个书单
		GenericType<List<Book>> gene = new GenericType<List<Book>>(){};
		List<Book> books = r.accept(MediaType.APPLICATION_XML).get(gene);
		for (int i = 0; i < books.size(); i++) {
			System.out.println(books.get(i).getName());
		}	
	}
	//更新表单
	public void updateBook(WebResource r,Book book){
		ClientResponse response = r.path(book.getId()).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class,book);
		System.out.println(response.getStatus());
	}
	//新建表单
	public void postForm(WebResource r,String id,String name,String author,String category){
		Form form = new Form();
		form.add("id", id);
		form.add("name", name);
		form.add("author", author);
		form.add("category", category);
		ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,form);
		System.out.println(response.getEntity(String.class));
	}
	//删除表单
	public void deleteBook(WebResource r,String id){
		ClientResponse response = r.path(id).accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
		System.out.println(response.getStatus());
	}
	//测试服务
	public static void main(String[] args) {
		ServiceClient sc = new ServiceClient() ;
		Client c = Client.create() ;
		WebResource r = c.resource("http://127.0.0.1:8086/libary/books") ;
//		sc.deleteBook(r, "2243");
		Book book = new Book("2243", "大汉骄子", "lin", "history");
//		sc.postForm(r, "2243", "大汉天子", "dai", "history");
		sc.updateBook(r, book);
		
	}
	
}
