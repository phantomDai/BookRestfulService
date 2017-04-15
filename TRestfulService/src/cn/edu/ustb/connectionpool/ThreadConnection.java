package cn.edu.ustb.connectionpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.ustb.model.Book;


/*
 * 模拟线程启动，去获得连接
 * @author Liu
 * 将查询语句交给线程，线程向连接池获取一个连接，用这个连接和插叙语句一起，获得查询结果
 * 
 * */

public class ThreadConnection implements Runnable{
	
	private ImpConnPool pool;//连接池
	private String query;//输入
	public ArrayList<Book> result;//输出
	PoolBean poolbean=new PoolBean();//连接池参数
	public ThreadConnection(String query,PoolBean poolbean) {
		// TODO Auto-generated constructor stub
		this.query=query;
		this.poolbean=poolbean;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//初始化一个连接池
		ImpConnPool pool = null;
		try {
			pool = new ImpConnPool(poolbean);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//从连接池中获取一个连接
		Connection con=pool.getConnection();
		//利用连接来进行查询
		//创建查询 “请求”
		try {
			PreparedStatement ps = con.prepareStatement(query);
			//返回查询结果
			ResultSet rs = ps.executeQuery();
	        //遍历结果
			while(rs.next()) {
	            //假如 User 表中 有个 name 列
//				String[] tmp={rs.getInt("id")+"",rs.getString("name")
//						,rs.getString("author")
//						,rs.getString("category")
//						,rs.getString("info")};
				Book book=new Book(rs.getInt("id")+"",rs.getString("name"),
						rs.getString("author"),rs.getString("category"));
				//result.add(book);
	            System.out.println("name >> "+rs.getString("Name"));
	        }
//			rs.close();
//			 ps.close();
		     pool.releaseConnection(con);
		     System.out.println("顺利查询");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //关闭
        
       
	}
	public Connection getConnection(){
		Connection conn=null;
		if(pool!=null&&pool.isActive()){
			conn=pool.getConnection();
		}
		
		return conn;
		
	}
	public Connection getCurrentConnection(){
		Connection conn=null;
		 if(pool != null && pool.isActive()){  
	            conn = pool.getCurrentConnection();  
	        }  
		return conn;
		
	}
		
}
