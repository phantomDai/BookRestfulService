package cn.edu.ustb.impl;

import java.awt.image.DataBufferUShort;
import java.sql.SQLException;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBElement;
import cn.edu.ustb.model.*;
import cn.edu.ustb.dao.*;
import java.util.List;
import java.util.ArrayList;
/**
 * 该类是服务类，提供可以被调用的一些接口
 * @author phantom
 *
 */
@WebService(targetNamespace="http://www.ustb.edu.cn/sei/ws")
@Path(value="/libary/")
@Produces(MediaType.APPLICATION_XML)
public class BookRestfulService {
	
	@GET
	@Path(value="/books/{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Book getOneBook(@PathParam(value="id")String id) throws SQLException{
		Book book = new Book() ;
		//实例画一个BookDao类
		BookDao dao = new BookDao() ;
		
		book = dao.getOneBook(id);
		return book ;
	}
	
	@GET
	@Path(value="/books")
	@Produces(MediaType.APPLICATION_XML)
	public List<Book> getBooks() throws SQLException{
		List<Book> books = new ArrayList<Book>();
		BookDao dao = new BookDao() ;
		books = dao.getAllBooks();
		return books ;
	}
	
	@PUT
	@Path(value="/books/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBook(@PathParam(value="id")String id,JAXBElement<Book> jaxbBook) throws SQLException{
		Book book = jaxbBook.getValue();
		return putResponse(book) ;
	}
	
	private Response putResponse(Book b) throws SQLException{
		Response res = null ;
		BookDao dao = new BookDao() ;
		if (dao.getMapBooks().containsKey(b.getId())){
			dao.update(b);
			res = Response.noContent().build();
		}else{
			res = Response.ok().build();
		}
		return res ;
	}
	
	
	//新建一个book
	@POST
	@Path(value="/books")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_XML)
	public void newOneBook(@FormParam("id")String id,
						   @FormParam("name")String name,
						   @FormParam("author")String author,
						   @FormParam("category")String category
			)throws SQLException{
		Book book = new Book(id, name, author, category);
		BookDao dao = new BookDao() ;
		dao.addBook(book);
	}
	//删除一个book
	@DELETE
	@Path(value="/books/{id}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deleteBook(@PathParam(value="id")String id) throws SQLException{
		Response res;
		BookDao dao = new BookDao();
		if(dao.getMapBooks().containsKey(id)){
			dao.delete(id);
			return res = Response.ok().build();
		}else{
			return res = Response.noContent().build();
		}
	}

	
}
