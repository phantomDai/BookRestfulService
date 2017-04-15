package cn.edu.ustb.dao;
import cn.edu.ustb.model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Map;
import java.util.HashMap;

import cn.edu.ustb.connectionpool.ImpConnPool;
import cn.edu.ustb.connectionpool.PoolBean;
import cn.edu.ustb.connectionpool.PoolInst;
import cn.edu.ustb.db.*;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
/**
 * 该类实现了数据库的接口API
 * @author phantom
 *
 */
public class BookDao {
	//创建一个数据库连接池
	PoolInst pi=new PoolInst();
	ImpConnPool pool=pi.create();
	//添加书
	public void addBook(Book b) throws SQLException{
		String sql = "insert into " + b.getCategory() + "(id,name,author,category) values (?,?,?,?)" ;
		//拿到数据库的连接
		Connection cn=pool.getConnection();
		
		//预编译
		PreparedStatement psmt = (PreparedStatement) cn.prepareStatement(sql) ; 
		//传参数
		psmt.setString(1, b.getId());
		psmt.setString(2, b.getName());
		psmt.setString(3, b.getAuthor());
		psmt.setString(4, b.getCategory());
		//编译sql语句
		psmt.execute();
		pool.releaseConnection(cn);
		System.out.println("您新添了一本书");
	}
	
	//更新书
	public void update(Book b) throws SQLException{
		
		String sql = "update " + b.getCategory() + " set id=?,name=?,author=?,category=? where id=?" ;
		//拿到数据库的连接
		Connection cn=pool.getConnection();
		PreparedStatement psmt = (PreparedStatement) cn.prepareStatement(sql) ; 
		psmt.setString(1, b.getId());
		psmt.setString(2, b.getName());
		psmt.setString(3, b.getAuthor());
		psmt.setString(4, b.getCategory());
		psmt.setString(5, b.getId());
		psmt.execute() ;
		pool.releaseConnection(cn);
	}
	
	//删除书籍
	public void delete (String id) throws SQLException{
		//在三个表单中查询所有的id
		String sql1 = "delete from history where id=?" ;
		String sql2 = "delete from military where id=?" ;
		String sql3 = "delete from spaceflight where id=?" ;
		Connection cn1=pool.getConnection();
		Connection cn2=pool.getCurrentConnection();
		Connection cn3=pool.getCurrentConnection();
		PreparedStatement psmt1 = (PreparedStatement) cn1.prepareStatement(sql1) ; 
		PreparedStatement psmt2 = (PreparedStatement) cn2.prepareStatement(sql2) ; 
		PreparedStatement psmt3 = (PreparedStatement) cn1.prepareStatement(sql3) ; 
		
		psmt1.setString(1,id) ;
		psmt1.execute();
		psmt2.setString(1,id) ;
		psmt2.execute();
		psmt3.setString(1,id) ;
		psmt3.execute();
		pool.releaseConnection(cn1);
		pool.releaseConnection(cn2);
		pool.releaseConnection(cn3);
	}
	
	//查询单个书目
	public Book getOneBook(String id) throws SQLException{
		//DBHelper db = new DBHelper() ;
		Connection cn=pool.getConnection();
		//ID设置为唯一，不再让它自增然后在三个表单中查找
		String sql = "select * from military where id=" + id + " union select * from history where id=" + id + " union select * from spaceflight where id=" + id ;
		//PreparedStatement psmt = (PreparedStatement) db.conn.prepareStatement(sql) ;
		PreparedStatement psmt = (PreparedStatement) cn.prepareStatement(sql) ; 
		ResultSet rs = psmt.executeQuery() ;
		pool.releaseConnection(cn);
		Book book = null;
		while (rs.next()){
			String bid = rs.getString("id") ;
			String bname = rs.getString("name") ;
			String bauthor = rs.getString("author") ;
			String bcategory = rs.getString("category") ;
			book = new Book(bid, bname, bauthor, bcategory) ;
		}
		//System.out.println("您正在查询"+book.getName()+"的信息");
		return book ;
		
	}
	
	//查询所有的书籍
	public List<Book> getAllBooks() throws SQLException{
		List<Book> books = new ArrayList<Book>();
		//DBHelper db = new DBHelper() ;
		Connection cn=pool.getConnection();
		String sql1 = "select * from history union select * from spaceflight union select * from military";
		//Statement stm1 = db.conn.createStatement();
		Statement stm1 = cn.createStatement(); 
		//h
		
		ResultSet rs1 = stm1.executeQuery(sql1) ;
		pool.releaseConnection(cn);
		Book book = null ;
		while(rs1.next()){
			String bid = rs1.getString("id") ;
			String bname = rs1.getString("name") ;
			String bauthor = rs1.getString("author") ;
			String bcategory = rs1.getString("category") ;
			book = new Book(bid, bname, bauthor, bcategory) ;
			books.add(book) ;
		}
		//System.out.println("为您展示所有书籍");
		return books ;
	}
	
	//获得所有书籍的一个map
	public Map<String,Book> getMapBooks() throws SQLException{
		Map<String,Book> books = new HashMap<String,Book>();
		List<Book> bookList = getAllBooks() ;
		for (int i = 0; i < bookList.size(); i++) {
			books.put(bookList.get(i).getId(), bookList.get(i)) ;
			//System.out.println(bookList.get(i).getId());
			//System.out.println(bookList.get(i));
		}
		return books ;
		
	}
	
	public static void main(String[] args) throws SQLException {
		BookDao dao = new BookDao() ;
		Book b=new Book();
		b.setId("10");
		b.setName("钢铁是怎样炼成的");
		b.setAuthor("保尔.柯察金");
		b.setCategory("history");
		dao.addBook(b);
//		Book book = new Book("2250", "宇宙的美", "lin", "spaceflight");
//		dao.addBook(book);
//		Book b = new Book() ;
//		b = dao.getOneBook("2249") ;
//		System.out.println(b.getAuthor());
//		List<Book> books = dao.getAllBooks(); 
//		for (int i = 0; i < books.size(); i++) {
//			System.out.println(books.get(i).getId());
//		}
		if(dao.getAllBooks()!=null){
			List<Book> books=dao.getAllBooks();
			for(int i=0;i<books.size();i++){
				Book book=books.get(i);
				System.out.println("书名："+book.getName());
			}	
		}
		if(dao.getMapBooks().containsKey("2243")){
			System.out.println("have one 2243");
		}else{
			System.out.println("no 2243");
		}	
	}
}
