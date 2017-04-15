package cn.edu.ustb.db;
import java.sql.*;
import java.sql.Connection;
public class DBHelper {
		
	
	public static final String url = "jdbc:mysql://127.0.0.1:3306/Restful" ;
	public static final String user = "root" ;
	public static final String password = "218111" ;
	public Connection conn ;//数据库连接
	
	public DBHelper(){
		try {
			//加载驱动
			Class.forName("com.mysql.jdbc.Driver") ;
			//初始话数据库连接
			conn = DriverManager.getConnection(url,user,password) ;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void closeConn(){
		try {
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
}
