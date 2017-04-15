package cn.edu.ustb.connectionpool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import cn.edu.ustb.model.Book;


/*
 * ģ���߳�������ȥ�������
 * @author Liu
 * ����ѯ��佻���̣߳��߳������ӳػ�ȡһ�����ӣ���������ӺͲ������һ�𣬻�ò�ѯ���
 * 
 * */

public class ThreadConnection implements Runnable{
	
	private ImpConnPool pool;//���ӳ�
	private String query;//����
	public ArrayList<Book> result;//���
	PoolBean poolbean=new PoolBean();//���ӳز���
	public ThreadConnection(String query,PoolBean poolbean) {
		// TODO Auto-generated constructor stub
		this.query=query;
		this.poolbean=poolbean;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		//��ʼ��һ�����ӳ�
		ImpConnPool pool = null;
		try {
			pool = new ImpConnPool(poolbean);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		//�����ӳ��л�ȡһ������
		Connection con=pool.getConnection();
		//�������������в�ѯ
		//������ѯ ������
		try {
			PreparedStatement ps = con.prepareStatement(query);
			//���ز�ѯ���
			ResultSet rs = ps.executeQuery();
	        //�������
			while(rs.next()) {
	            //���� User ���� �и� name ��
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
		     System.out.println("˳����ѯ");
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //�ر�
        
       
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
