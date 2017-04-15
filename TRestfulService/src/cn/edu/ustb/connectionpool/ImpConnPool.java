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
 * 连接池的一个具体的实现,管理数据库连接
 * @author Liu
 * */

public class ImpConnPool implements ConnectionPool {
	//配置连接池的属性
	private PoolBean poolBean;
	private boolean isActive=false;//连接池活动状态
	private int countConn=0;//已创建的连接数
	//空闲连接
	private List<Connection> freeConn=new Vector<Connection>();
	//活动连接
	private List<Connection> activeConn=new Vector<Connection>();
	//将线程和连接绑定，保证事物能统一执行
	private static ThreadLocal<Connection> threadLocal=new ThreadLocal<Connection>();
		
	//构造函数
	public ImpConnPool(PoolBean poolBean) throws SQLException {
		super();
		this.poolBean = poolBean;
		init();//初始化一个连接池
		checkPool();
	}
	
	//初始化
	private void init() throws SQLException {
		// TODO Auto-generated method stub
		try{
			Class.forName(poolBean.getDriverName());
			//初始化：创建默认数目的数据库连接
			for(int i=0;i<poolBean.getDefaultConnection();i++){
				Connection conn=createConn();
				if(conn!=null){
					freeConn.add(conn);
					countConn++;
				}
			}
			isActive=true;//连接数达到默认值以后，连接池就被激活了
			System.out.println("已经初始化了"+countConn+"个连接");
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	//创建一个新的数据库连接
	private synchronized Connection createConn() throws ClassNotFoundException, SQLException {
		// TODO Auto-generated method stub
		Connection conn=null;
		if(poolBean!=null){
			Class.forName(poolBean.getDriverName());
			conn=DriverManager.getConnection(poolBean.getUrl(),
					poolBean.getUserName(),poolBean.getPassword());
		}
		System.out.println("一个新连接已建立！");
//		//查询
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
//		System.out.println("一个连接成功建立");
		return conn;
	}
	
	//获得连接,如果空闲连接中无法获取或者连接已经关闭，就重新获取连接
	@Override
	public synchronized Connection getConnection() {
		// TODO Auto-generated method stub
		Connection conn=null;
		try{
			//判断是否超过最大活动连接数
			if(countConn<poolBean.getMaxActiveConnection()){
				if(freeConn.size()>0){//如果还有空闲连接，就从空闲连接里取
					conn=freeConn.get(0);
					System.out.println("空闲连接还多少："+freeConn.size());
					if(conn!=null){
						threadLocal.set(conn);//取到连接就加入新的线程里
						if(isValid(conn)){
							activeConn.add(conn);
						}
					}
					freeConn.remove(0);
				}else{//已经没有空闲连接，新建连接
					System.out.println("已经没有空闲连接了");
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
				}else{//超过最大活动连接数,则等待知道获得连接为止
					wait(this.poolBean.getConnTimeout());
					conn=getConnection();
				}
			
			System.out.println("从数据库连接池中获取一个连接");
		}catch(InterruptedException e){
			e.printStackTrace();
		}
		return conn;
	}
	
	//获得当前的连接,即从已经初始化的默认连接中获取连接
	@Override
	public synchronized Connection getCurrentConnection() {
		// TODO Auto-generated method stub
		//从默认线程里面取
		Connection conn=threadLocal.get();//返回此线程局部变量的当前线程副本中的值。
		if(!isValid(conn)){
			//当前连接不可用，便再获取一个连接
			conn=getConnection();
			System.out.println("threadlocal里面没有connection,所以我们决定重新获得一个");
		}
		//System.out.println("获取threadlocal里面的当前连接的副本");
		return null;
	}
	
	//判断连接是否可用,连接为空或者关闭状态，则不可用
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
	
	//释放连接
	@Override
	public void releaseConnection(Connection conn) throws SQLException {
		// TODO Auto-generated method stub
		if(isValid(conn)&&!(freeConn.size()>=poolBean.getMaxConnection())){
			//将可用的非空闲连接释放,释放的连接放到空闲连接表中
			freeConn.add(conn);
			activeConn.remove(conn);
			//countConn--;
			//方法删除该线程当前线程局部变量的值
			threadLocal.remove();
			System.out.println("连接已经释放");
			// 唤醒所有正待等待的线程，去抢连接  
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
			//timer.schedule(new MyTask(), 延迟开始检查的时间, 检查周期);  
			new Timer().schedule(new TimerTask(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					System.out.println("连接池名称："+poolBean.getPoolName());
					System.out.println("总的连接数："+countConn);
					System.out.println("活动连接数："+activeConn.size());
					System.out.println("空闲连接数："+freeConn.size());
				}},
				poolBean.getLazyCheck(),
				poolBean.getPeriodCheck()
			);
		}
	}
	
	

}
