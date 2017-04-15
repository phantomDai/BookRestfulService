package cn.edu.ustb.connectionpool;

import java.util.ArrayList;

import cn.edu.ustb.model.Book;

public class QueryManager {
	private ArrayList<String> sql=new ArrayList<String>();//�����洢�����ѯ����
	//public ArrayList<Book> books=new ArrayList<Book>();//���ص������鼮
	
	public QueryManager(ArrayList<String> sql) {
		super();
		this.sql = sql;
	}
	public ArrayList<Book> execute(){
		//����һ�����ӳ�
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
		//ִ��
		ArrayList<Book> booklist=null;
		//ArrayList<ArrayList<Book>> booklist=null;
		for(int i=0;i<sql.size();i++){
			String query=sql.get(i);
			ThreadConnection tc=new ThreadConnection(query,poolbean);//���߳�
			booklist.addAll(tc.result);
		}
		
		return booklist;
		
	}
	
}
