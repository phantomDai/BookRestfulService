package cn.edu.ustb.connectionpool;

/*
 * ���ӳص����ԣ�Ĭ��ֵ
 * ���������ⲿ��������
 * @author Liu
 * */

public class PoolBean {
	//���ݿ�����
	private String driverName;
	private String url;
	private String userName;
	private String password;
	//���ӳ�����
	private String poolName;//���ӳص�����
	private int minConnection=1;
	private int maxConnection=10;//���������
	private int defaultConnection=5;//��ʼ��Ĭ��������
	private long reConnTime=1000;//���»�����ӵ�Ƶ��
	private long connTimeout;//���ӳ�ʱ��ʱ��
	private int maxActiveConnection;//���ǰ������
	private boolean isCurrentConnection=true;//�Ƿ��õ�ǰ���ӣ�Ĭ����true
	private boolean isCheckPool=true;//�Ƿ�ʱ������ӳ�
	//private long lazyCheck=1000*60*60;//�ӳٶ೤ʱ���ʼ���
	private long lazyCheck=1000;//�ӳٶ೤ʱ���ʼ���
	private long periodCheck=1000;//���Ƶ��
	private String query;//��ѯ���
	//���캯��
	public PoolBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PoolBean(String driverName, String url, String userName,
			String password, String poolName) {
		super();
		this.driverName = driverName;
		this.url = url;
		this.userName = userName;
		this.password = password;
		this.poolName = poolName;
	}
	//getters&setters
	public String getDriverName() {
		return driverName;
	}
	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPoolName() {
		return poolName;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	public int getMinConnection() {
		return minConnection;
	}
	public void setMinConnection(int minConnection) {
		this.minConnection = minConnection;
	}
	public int getMaxConnection() {
		return maxConnection;
	}
	public void setMaxConnection(int maxConnection) {
		this.maxConnection = maxConnection;
	}
	public int getDefaultConnection() {
		return defaultConnection;
	}
	public void setDefaultConnection(int defaultConnection) {
		this.defaultConnection = defaultConnection;
	}
	public long getReConnTime() {
		return reConnTime;
	}
	public void setReConnTime(long reConnTime) {
		this.reConnTime = reConnTime;
	}
	public long getConnTimeout() {
		return connTimeout;
	}
	public void setConnTimeout(long connTimeout) {
		this.connTimeout = connTimeout;
	}
	public int getMaxActiveConnection() {
		return maxActiveConnection;
	}
	public void setMaxActiveConnection(int maxActiveConnection) {
		this.maxActiveConnection = maxActiveConnection;
	}
	public boolean isCurrentConnection() {
		return isCurrentConnection;
	}
	public void setCurrentConnection(boolean isCurrentConnection) {
		this.isCurrentConnection = isCurrentConnection;
	}
	public boolean isCheckPool() {
		return isCheckPool;
	}
	public void setCheckPool(boolean isCheckPool) {
		this.isCheckPool = isCheckPool;
	}
	public long getLazyCheck() {
		return lazyCheck;
	}
	public void setLazyCheck(long lazyCheck) {
		this.lazyCheck = lazyCheck;
	}
	public long getPeriodCheck() {
		return periodCheck;
	}
	public void setPeriodCheck(long periodCheck) {
		this.periodCheck = periodCheck;
	}
	public void setQuery(String query) {
		// TODO Auto-generated method stub
		this.query=query;
	}
	public String getQuery(){
		return query;
	}
}
