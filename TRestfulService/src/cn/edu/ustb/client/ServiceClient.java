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
 * �ͻ��ˣ��Է�����в���
 * @author phantom
 *
 */
public class ServiceClient {
	//���һ��book��Ϣ
	public void getOneBook(WebResource r,String id){
		GenericType<JAXBElement<Book>> gene = new GenericType<JAXBElement<Book>>(){} ;
		JAXBElement<Book> jaxbBook = r.path(id).accept(MediaType.APPLICATION_XML).get(gene);
		Book book = jaxbBook.getValue();
		System.out.println(book.getName());
	}
	//������е�book��Ϣ
	public void getAllBooks(WebResource r){
		//��ȡ�����ַ���
		String xmlstr = r.accept(MediaType.APPLICATION_XML).get(String.class);
		//���һЩ�ظ���Ϣ
		ClientResponse response = r.get(ClientResponse.class);
		System.out.println(response.getStatus());
		//��������鵥
		GenericType<List<Book>> gene = new GenericType<List<Book>>(){};
		List<Book> books = r.accept(MediaType.APPLICATION_XML).get(gene);
		for (int i = 0; i < books.size(); i++) {
			System.out.println(books.get(i).getName());
		}	
	}
	//���±�
	public void updateBook(WebResource r,Book book){
		ClientResponse response = r.path(book.getId()).accept(MediaType.APPLICATION_JSON).put(ClientResponse.class,book);
		System.out.println(response.getStatus());
	}
	//�½���
	public void postForm(WebResource r,String id,String name,String author,String category){
		Form form = new Form();
		form.add("id", id);
		form.add("name", name);
		form.add("author", author);
		form.add("category", category);
		ClientResponse response = r.type(MediaType.APPLICATION_FORM_URLENCODED).post(ClientResponse.class,form);
		System.out.println(response.getEntity(String.class));
	}
	//ɾ����
	public void deleteBook(WebResource r,String id){
		ClientResponse response = r.path(id).accept(MediaType.APPLICATION_XML).delete(ClientResponse.class);
		System.out.println(response.getStatus());
	}
	//���Է���
	public static void main(String[] args) {
		ServiceClient sc = new ServiceClient() ;
		Client c = Client.create() ;
		WebResource r = c.resource("http://127.0.0.1:8086/libary/books") ;
//		sc.deleteBook(r, "2243");
		Book book = new Book("2243", "�󺺽���", "lin", "history");
//		sc.postForm(r, "2243", "������", "dai", "history");
		sc.updateBook(r, book);
		
	}
	
}
