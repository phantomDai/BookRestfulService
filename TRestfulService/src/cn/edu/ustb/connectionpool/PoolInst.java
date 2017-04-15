package cn.edu.ustb.connectionpool;

import java.sql.SQLException;

/*
 * ����һ�����ݿ����ӳص�ʵ��
 * */
public class PoolInst {
	
	public ImpConnPool create(){
		//����
		PoolBean poolbean=new PoolBean();
		poolbean.setDriverName("com.mysql.jdbc.Driver");
		poolbean.setUrl("jdbc:mysql://localhost:3306/Restful");
		poolbean.setUserName("root");
		poolbean.setPassword("218111");
		poolbean.setMinConnection(1);
		poolbean.setMaxConnection(20);
		poolbean.setMaxActiveConnection(10);
		poolbean.setPoolName("restful");
		poolbean.setLazyCheck(1000*30);
		poolbean.setPeriodCheck(1000*30);
		poolbean.setDefaultConnection(3);
		//
		//��ʼ��һ�����ӳ�
		ImpConnPool pool = null;
		try {
			pool = new ImpConnPool(poolbean);
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		return pool;
	}
	
}
