package cn.edu.ustb.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

/*
 * ���ӳص�һ�������ʵ��,�������ݿ�����
 * @author Liu
 * */

public class ImpConnPool implements ConnectionPool {
	//�������ӳص�����
	private PoolBean poolBean;
	private boolean isActive=false;//���ӳػ״̬
	private int countConn=0;//�Ѵ�����������
	//��������
	private List<Connection> freeConn=new Vector<Connection>();
	//�����
	private List<Connection> activeConn=new Vector<Connection>();
	//���̺߳����Ӱ󶨣���֤������ͳһִ��
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
		
	//���캯��
	public ImpConnPool(PoolBean poolBean) throws SQLException {
		super();
		this.poolBean = poolBean;
		init();//��ʼ��һ�����ӳ�
		checkPool();
	}
	
	//��ʼ��
	private void init() throws SQLException {
		// TODO Auto-generated method stub
		try{
			Class.forName(poolBean.getDriverName());
			//��ʼ��������Ĭ����Ŀ�����ݿ�����
			for(int i=0;i<poolBean.getDefaultConnection();i++){
				Connection conn=createConn();
				if(conn!=null){
					freeConn.add(conn);
					countConn++;
				}
			}
			isActive=true;//�������ﵽĬ��ֵ�Ժ����ӳؾͱ�������
			System.out.println("�Ѿ���ʼ����"+countConn+"������");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//����һ���µ����ݿ�����
	private synchronized Connection createConn() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection conn=null;
		if(poolBean!=null){
			Class.forName(poolBean.getDriverName());
			conn=DriverManager.getConnection(poolBean.getUrl(),
					poolBean.getUserName(),poolBean.getPassword());
		}
		System.out.println("һ���������ѽ�����");
//		//��ѯ
//		String sql="select * from history";
//		try {
//			PreparedStatement pst=conn.prepareStatement(sql);
//			//List<String> list = new ArrayList<String>();
//			ResultSet rs=pst.executeQuery();
//			while(rs.next()){
//				//list.add(rs.getString("name"));
//				System.out.println("name:"+rs.getString("name"));
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("һ�����ӳɹ�����");
		return conn;
	}
	
	//�������,��������������޷���ȡ���������Ѿ��رգ������»�ȡ����
	@Override
	public synchronized Connection getConnection() {
		// TODO Auto-generated method stub
		Connection conn=null;
		try{
			//�ж��Ƿ񳬹����������
			if(countConn<poolBean.getMaxActiveConnection()){
				if(freeConn.size()>0){//������п������ӣ��ʹӿ���������ȡ
					conn=freeConn.get(0);
					System.out.println("�������ӻ����٣�"+freeConn.size());
					if(conn!=null){
						threadLocal.set(conn);//ȡ�����Ӿͼ����µ��߳���
						if(isValid(conn)){
							activeConn.add(conn);
						}
					}
					freeConn.remove(0);
				}else{//�Ѿ�û�п������ӣ��½�����
					System.out.println("�Ѿ�û�п���������");
						try {
							conn=createConn();
							if(isValid(conn)){
								//
								threadLocal.set(conn);
								activeConn.add(conn);
								countConn++;
							}
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
				}else{//�������������,��ȴ�֪���������Ϊֹ
					wait(this.poolBean.getConnTimeout());
					conn=getConnection();
				}
			
			System.out.println("�����ݿ����ӳ��л�ȡһ������");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	//��õ�ǰ������,�����Ѿ���ʼ����Ĭ�������л�ȡ����
	@Override
	public synchronized Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		//��Ĭ���߳�����ȡ
		Connection conn=threadLocal.get();//���ش��ֲ߳̾������ĵ�ǰ�̸߳����е�ֵ��
		if(!isValid(conn)){
			//��ǰ���Ӳ����ã����ٻ�ȡһ������
			conn=getConnection();
			System.out.println("threadlocal����û��connection,�������Ǿ������»��һ��");
		}
		//System.out.println("��ȡthreadlocal����ĵ�ǰ���ӵĸ���");
		return null;
	}
	
	//�ж������Ƿ����,����Ϊ�ջ��߹ر�״̬���򲻿���
	private boolean isValid(Connection conn) {
		// TODO Auto-generated method stub
		try{
			if(conn==null||conn.isClosed()){
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return true;
	}
	
	//�ͷ�����
	@Override
	public void releaseConnection(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		if(isValid(conn)&&!(freeConn.size()>=poolBean.getMaxConnection())){
			//�����õķǿ��������ͷ�,�ͷŵ����ӷŵ��������ӱ���
			freeConn.add(conn);
			activeConn.remove(conn);
			//countConn--;
			//����ɾ�����̵߳�ǰ�ֲ߳̾�������ֵ
			threadLocal.remove();
			System.out.println("�����Ѿ��ͷ�");
			// �������������ȴ����̣߳�ȥ������  
			//notifyAll();
		}
	}

	@Override
	public synchronized void destroy() throws SQLException {
		// TODO Auto-generated method stub
		for(Connection conn:freeConn){
				if(isValid(conn)){
					conn.close();
				}
		}
		for(Connection conn:activeConn){
			if(isValid(conn)){
				conn.close();
			}
		}
		isActive=false;
		countConn=0;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return isActive;
	}

	@Override
	public void checkPool() {
		// TODO Auto-generated method stub
		if(poolBean.isCheckPool()){
			//timer.schedule(new MyTask(), �ӳٿ�ʼ����ʱ��, �������);  
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("���ӳ����ƣ�"+poolBean.getPoolName());
					System.out.println("�ܵ���������"+countConn);
					System.out.println("���������"+activeConn.size());
					System.out.println("������������"+freeConn.size());
				}},
				poolBean.getLazyCheck(),
				poolBean.getPeriodCheck()
			);
		}
	}
	
	

}
