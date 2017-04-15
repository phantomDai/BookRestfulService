package cn.edu.ustb.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainForPool {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//配置一个连接池
				PoolBean poolbean=new PoolBean();
				poolbean.setDriverName("com.mysql.jdbc.Driver");
				poolbean.setUrl("jdbc:mysql://localhost:3306/Restful");
				poolbean.setUserName("root");
				poolbean.setPassword("218111");
				poolbean.setMinConnection(5);
				poolbean.setMaxConnection(20);
				poolbean.setMaxActiveConnection(10);
				poolbean.setPoolName("restful");
				poolbean.setLazyCheck(1000*30);
				poolbean.setPeriodCheck(1000*30);
				//
		String query="select * from military";//查询语句，输入
		ThreadConnection a=new ThreadConnection(query,poolbean);
		Thread t=new Thread(a);
		t.start();
		//返回值a.result
		System.out.println("一个线程运行完毕");
		
//		System.out.println("===============test=================");
//		//数据库连接
//		String username="root";
//		String pass="sql123";
//		String driver="com.mysql.jdbc.Driver";
//		String url="jdbc:mysql://localhost:3306/Restful";
//		
//		Connection conn=null;
//		
//			try {
//				Class.forName(driver);
//				conn=(Connection)DriverManager.getConnection(url,username,pass);
//
//			} catch (ClassNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//查询
//			String sql="select * from history";
//			try {
//				PreparedStatement pst=conn.prepareStatement(sql);
//				//List<String> list = new ArrayList<String>();
//				ResultSet rs=pst.executeQuery();
//				while(rs.next()){
//					//list.add(rs.getString("name"));
//					System.out.println("name:"+rs.getString("name"));
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			
//			
	}

}
