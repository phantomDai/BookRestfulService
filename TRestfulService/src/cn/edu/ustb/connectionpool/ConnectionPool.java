package cn.edu.ustb.connectionpool;

import java.sql.Connection;
import java.sql.SQLException;

/*
 * ���ӳؼ�������Ľӿ�
 * @author
 * */

public interface ConnectionPool {
	//�������
	public Connection getConnection();
	//��õ�ǰ����
	public Connection getCurrentConnection();
	//��������
	public void releaseConnection(Connection conn) throws SQLException;
	//�������
	public void destroy() throws SQLException;
	//���ӳ��Ƿ��ڻ״̬
	public boolean isActive();
	//��ʱ������ʱ������ӳ�
	public void checkPool();
}
 