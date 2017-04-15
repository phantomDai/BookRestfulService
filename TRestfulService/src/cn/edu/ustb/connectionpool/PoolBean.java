package cn.edu.ustb.connectionpool;

/*
 * 连接池的属性，默认值
 * 可以允许外部进行配置
 * @author Liu
 * */

public class PoolBean {
	//数据库属性
	private String driverName;
	private String url;
	private String userName;
	private String password;
	//连接池属性
	private String poolName;//连接池的名字
	private int minConnection=1;
	private int maxConnection=10;//最大连接数
	private int defaultConnection=5;//初始化默认连接数
	private long reConnTime=1000;//重新获得连接的频率
	private long connTimeout;//连接超时的时间
	private int maxActiveConnection;//最大当前连接数
	private boolean isCurrentConnection=true;//是否获得当前连接，默认是true
	private boolean isCheckPool=true;//是否定时检查连接池
	//private long lazyCheck=1000*60*60;//延迟多长时间后开始检查
	private long lazyCheck=1000;//延迟多长时间后开始检查
	private long periodCheck=1000;//检查频率
	private String query;//查询语句
	//构造函数
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
