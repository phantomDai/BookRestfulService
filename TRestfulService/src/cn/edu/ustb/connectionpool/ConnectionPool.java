package cn.edu.ustb.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * 连接池及其操作的接口
 * @author
 * */

public interface ConnectionPool {
	//获得连接
	public Connection getConnection();
	//获得当前连接
	public Connection getCurrentConnection();
	//回收连接
	public void releaseConnection(Connection conn) throws SQLException;
	//销毁清空
	public void destroy() throws SQLException;
	//连接池是否处于活动状态
	public boolean isActive();
	//定时器，定时检查连接池
	public void checkPool();
}
 