package cn.edu.ustb.db;
import java.sql.*;
import java.sql.Connection;
public class DBHelper {
		
	
	public static final String url = "jdbc:mysql://127.0.0.1:3306/Restful" ;
	public static final String user = "root" ;
	public static final String password = "218111" ;
	public Connection conn ;//���ݿ�����
	
	public DBHelper(){
		try {
			//��������
			Class.forName("com.mysql.jdbc.Driver") ;
			//��ʼ�����ݿ�����
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
