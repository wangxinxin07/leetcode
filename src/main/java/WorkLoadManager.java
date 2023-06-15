
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class WorkLoadManager {
	public static String dbtype;
	public static String logfile;
	public static String tpslog;
	public static int loop;
	public static int[] rates;
	public static int buffer;
	public static String savePoint;
	public static String URL;
	public static String userName;
	public static String password;
	public static int numberCount = 0;
	public static String tableName;
	public static String tablespace;
	public static int totalTrans;
	public static int tablenum;
	public static String sql;
	public static SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");

	public WorkLoadManager() {

	}

	public WorkLoadManager(String logfile, int loop, int[] rates, int buffer, String URL, String userName,
			String password) {
		WorkLoadManager.logfile = logfile;
		WorkLoadManager.loop = loop;
		WorkLoadManager.rates = rates;
		WorkLoadManager.buffer = buffer;
		WorkLoadManager.URL = URL;
		WorkLoadManager.userName = userName;
		WorkLoadManager.password = password;
	}

	public static class KeySequence {
		private int key = 0;
		private int currentKey = 0;

		public void init(int suffix) {
			try {

				if (dbtype.equalsIgnoreCase("DB2")) {
					Class.forName("com.ibm.db2.jcc.DB2Driver");
				} else if (dbtype.equalsIgnoreCase("Oracle")) {
					Class.forName("oracle.jdbc.driver.OracleDriver");
				} else if (dbtype.equalsIgnoreCase("MySQL")) {
					Class.forName("com.mysql.jdbc.Driver");
				} else if (dbtype.equalsIgnoreCase("Gauss")) {
					System.out.println("Gauss");
					Class.forName("com.huawei.gauss.jdbc.ZenithDriver");
				}else if (dbtype.equalsIgnoreCase("Gauss2")) {
					System.out.println("Gauss2");
					Class.forName("org.postgresql.Driver");
				}else if (dbtype.equalsIgnoreCase("Gauss5")) {
					System.out.println("Gauss5");
					Class.forName("org.opengauss.Driver");
				}else if (dbtype.equalsIgnoreCase("PG")) {
					System.out.println("PG");
					Class.forName("org.postgresql.Driver");
				}else if (dbtype.equalsIgnoreCase("GP")) {
					System.out.println("GreenPlum");
					Class.forName("com.pivotal.jdbc.GreenplumDriver");
				}else if (dbtype.equalsIgnoreCase("KB")) {
					System.out.println("Kingbase");
					Class.forName("com.kingbase8.Driver");
				}else if (dbtype.equalsIgnoreCase("KB")) {
					System.out.println("DM");
					Class.forName("dm.jdbc.driver.DmDriver");
				}else if (dbtype.equalsIgnoreCase("HIGHGO")) {
					System.out.println("HIGHGO");
					Class.forName("com.highgo.jdbc.Driver");
				}
				Connection conn = DriverManager.getConnection(URL, userName, password);
				System.out.println("init");
				Statement stmt = conn.createStatement();
				try {
					if (dbtype.equalsIgnoreCase("DB2")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 bigint, c3 varchar(100),c4 char(100), c5 timestamp) in " + tablespace);
						stmt.execute("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					} else if (dbtype.equalsIgnoreCase("Oracle")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 number, c3 varchar2(100),c4 char(100), c5 timestamp) tablespace "
								+ tablespace);
						stmt.execute("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					} else if (dbtype.equalsIgnoreCase("MySQL")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 int, c3 varchar(100),c4 char(100), c5 datetime(6))");
						stmt.execute("alter table " + tableName + " add primary key (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					} else if (dbtype.equalsIgnoreCase("Gauss")) {
						stmt.executeUpdate("create table " + tableName + "_" + suffix
								+ " (c1 int not null, c2 int, c3 varchar(100),c4 char(100), c5 date) tablespace " + tablespace);
						stmt.executeUpdate("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.executeUpdate("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					} else if (dbtype.equalsIgnoreCase("PG")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 int, c3 varchar(100),c4 char(100), c5 date)");
						stmt.execute("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					}else if (dbtype.equalsIgnoreCase("GP")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 int, c3 varchar(100),c4 char(100), c5 date,primary key(c1)) distributed by (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					}else if (dbtype.equalsIgnoreCase("KB")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 int, c3 varchar(100),c4 char(100), c5 date)");
						stmt.execute("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					} else if (dbtype.equalsIgnoreCase("DM")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 number, c3 varchar2(100),c4 char(100), c5 timestamp) tablespace "
								+ tablespace);
						stmt.execute("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					}else if (dbtype.equalsIgnoreCase("HIGHGO")) {
						stmt.execute("create table " + tableName + "_" + suffix
								+ " (c1 int, c2 int, c3 varchar(100),c4 char(100), c5 date)");
						stmt.execute("create index idx_1_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1)");
						stmt.execute("create index idx_2_" + tableName + "_" + suffix + " on " + tableName + "_"
								+ suffix + " (c1,c2,c3)");
					}
				} catch (Exception e) {

				}
				ResultSet rs = stmt.executeQuery("select count(*) from " + tableName + "_" + suffix);
				rs.next();
				this.currentKey = this.key = rs.getInt(1);
				System.out.println(this.key);
				rs.close();
				stmt.close();
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		public synchronized int nextKey() {
			this.key = key + 1;
			return this.key;
		}

		public synchronized int nextBufKey() {
			this.key = this.key + WorkLoadManager.buffer;
			return this.key;
		}

		public int currentKey() {
			return this.currentKey;
		}

		public int getKey() {
			Random r = new Random();
			return r.nextInt(this.currentKey + 1);
		}
	}

	public static class TimeMonitor {

		private long totalInstTime;
		private long totalUptTime;
		private long totalDelTime;
		private long totalQueryTime;

		public long getTotalInstTime() {
			return totalInstTime;
		}

		public long getTotalUptTime() {
			return totalUptTime;
		}

		public long getTotalDelTime() {
			return totalDelTime;
		}

		public long getTotalQueryTime() {
			return totalQueryTime;
		}

		public long getCurrentTime() {
			return System.currentTimeMillis();
		}

		public long getCurrentTimeNano() {
			return System.nanoTime();
		}

		public synchronized void calTotalInstTime(long time) {
			totalInstTime = totalInstTime + time;
		}

		public synchronized void calTotalUptTime(long time) {
			totalUptTime = totalUptTime + time;
		}

		public synchronized void calTotalDelTime(long time) {
			totalDelTime = totalDelTime + time;
		}

		public synchronized void calTotalQueryTime(long time) {
			totalQueryTime = totalQueryTime + time;
		}
	}

	public static class loadRunner extends Thread {
		private Connection conn = null;
		private Savepoint savepoint = null;
		private PreparedStatement stmt_inst = null;
		private PreparedStatement stmt_updt = null;
		private PreparedStatement stmt_del = null;
		private PreparedStatement stmt_sel = null;
		private KeySequence ks;
		private TimeMonitor tm;
		private int loop;
		private int suffix;

		public loadRunner(KeySequence ks, TimeMonitor tm, int loop, int suffix) {
			this.ks = ks;
			this.tm = tm;
			this.loop = loop;
			this.suffix = suffix;
		}

		public void run() {
			try {
				numberCount = numberCount + 1;
				try {
					System.out.println("Run");
					conn = DriverManager.getConnection(URL, userName, password);
				} catch (Exception e) {
					e.printStackTrace();
					this.sleep(5000);
				}
				if(savePoint!=null){
					conn.setAutoCommit(false);
				}else{
					conn.setAutoCommit(true);
				}
				stmt_inst = conn.prepareStatement("insert into " + tableName + "_" + suffix + "(c1,c2,c3,c4,c5) values (?,?,?,?,?)");
				stmt_updt = conn.prepareStatement("update " + tableName + "_" + suffix + " set c3=? where c1=?");
				stmt_del = conn.prepareStatement("delete from " + tableName + "_" + suffix + " where c1=?");
				if ("".equals(sql) || sql == null)
					stmt_sel = conn.prepareStatement("select * from " + tableName + "_" + suffix + " where c1 =?");
				else
					stmt_sel = conn.prepareStatement(sql);
				
				//Statement s = conn.createStatement();
				
				int i = 0;
				long totalInstTime = 0;
				long totalUptTime = 0;
				long totalDelTime = 0;
				long totalQueryTime = 0;
				int startkey = 0;
				int endkey = 0;
				String context = "";
				while (i < loop || loop == 0) {
					//Thread.sleep(1);
					long istart = 0;
					long iend = 0;
					long ustart = 0;
					long uend = 0;
					long dstart = 0;
					long dend = 0;
					long sstart = 0;
					long send = 0;
					long instTime = 0;
					long uptTime = 0;
					long delTime = 0;
					long queryTime = 0;
					int j0 = rates[0];
					int j1 = rates[1];
					int j2 = rates[2];
					int j3 = rates[3];
					while (j0 > 0 || j1 > 0 || j2 > 0 || j3 > 0) {
						savepoint = conn.setSavepoint();
						if (j0 > 0) {
							if (startkey == endkey) {
								endkey = ks.nextBufKey();
								startkey = endkey - buffer;
							}
							stmt_inst.setInt(1, startkey);
							stmt_inst.setInt(2, startkey);
							stmt_inst.setString(3,
									"bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb"
											+ startkey);
							stmt_inst.setString(4,
									"ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"
											+ startkey);
							stmt_inst.setTimestamp(5, new Timestamp(tm.getCurrentTime()));
							istart = tm.getCurrentTimeNano();
							stmt_inst.executeUpdate();
							totalTrans = totalTrans + 1;
							iend = tm.getCurrentTimeNano();
							instTime = (iend - istart) / 1000;
							totalInstTime = totalInstTime + instTime;
							startkey = startkey + 1;
							j0 = j0 - 1;
						}
						if (j1 > 0) {
							int ukey = ks.getKey();
							stmt_updt.setString(1,
									"modifiedasdfsdfvsdfsdfsdfwefsdfsdfsdfsdfcvfghjhjggvcsdfsdfdfgdg" + ukey);
							stmt_updt.setInt(2, ukey);
							ustart = tm.getCurrentTimeNano();
							stmt_updt.executeUpdate();
							totalTrans = totalTrans + 1;
							uend = tm.getCurrentTimeNano();
							uptTime = (uend - ustart) / 1000;
							totalUptTime = totalUptTime + uptTime;
							j1 = j1 - 1;
						}
						if (j2 > 0) {
							int dkey = ks.getKey();
							stmt_del.setInt(1, dkey);
							dstart = tm.getCurrentTimeNano();
							stmt_del.executeUpdate();
							totalTrans = totalTrans + 1;
							dend = tm.getCurrentTimeNano();
							delTime = (dend - dstart) / 1000;
							totalDelTime = totalDelTime + delTime;
							j2 = j2 - 1;
						}
						if (j3 > 0) {
							if ("".equals(sql) || sql == null) {
								int skey = ks.getKey();
								stmt_sel.setInt(1, skey);
								//stmt_sel.setInt(1, 1);
							}
							sstart = tm.getCurrentTimeNano();
							stmt_sel.executeQuery();
							//s.executeQuery("select * from test_1 where c1=1");
							totalTrans = totalTrans + 1;
							send = tm.getCurrentTimeNano();
							queryTime = (send - sstart) / 1000;
							totalQueryTime = totalQueryTime + queryTime;
							j3 = j3 - 1;
						}
						context = context + instTime + "\t" + uptTime + "\t" + delTime + "\t" + queryTime + "\n";
						instTime = -1;
						uptTime = -1;
						delTime = -1;
						queryTime = -1;
					}
					if (i % buffer == 0 || i == loop - 1) {
						if (!"".equals(logfile))
						//WorkLoadManager.appendMethod(logfile, context.substring(0, context.length() - 1));
						context = "";
					}
					i = i + 1;
					//随机rollback savepoint
					Random random = new Random();
					int randResult = random.nextInt(10);
					if (randResult < 5 && savepoint != null) {
						conn.rollback(savepoint);
					} else {
						conn.commit();
					}
				}
				tm.calTotalInstTime(totalInstTime);
				tm.calTotalUptTime(totalUptTime);
				tm.calTotalDelTime(totalDelTime);
				tm.calTotalQueryTime(totalQueryTime);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					this.sleep(10000);
				} catch (InterruptedException e1) {
				}
				this.run();
			} finally {
				try {
					stmt_updt.close();
					stmt_inst.close();
					stmt_del.close();
					stmt_sel.close();
					conn.close();
					numberCount = numberCount - 1;
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static synchronized void appendMethod(String fileName, String content) {
		try {
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content + "\r\n");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String formatDouble(double d) {
		DecimalFormat format = (DecimalFormat) NumberFormat.getInstance();
		format.applyPattern("0.0");
		return format.format(d);
	}

	public static void main(String[] args) throws Exception {
		Class.forName("org.opengauss.Driver");
		System.out.println("success");

		String configfile = "";
		if (args.length == 0) {
			System.out.print("Pls input config file location: ");
			InputStreamReader stdin = new InputStreamReader(System.in);
			BufferedReader bufin = new BufferedReader(stdin);
			configfile = bufin.readLine();
		}
		System.out.println("Start");
		Properties prop = new Properties();
		if (configfile == "")
			configfile = args[0];
		InputStream in = new FileInputStream(configfile);
		prop.load(in);
		dbtype = prop.getProperty("dbtype");
		logfile = prop.getProperty("log");
		tpslog = prop.getProperty("tpslog");
		int threadcount = (new Integer(prop.getProperty("thread"))).intValue();
		loop = (new Integer(prop.getProperty("loop"))).intValue();
		String ratesStr = prop.getProperty("rate");
		buffer = (new Integer(prop.getProperty("buffer"))).intValue();
		savePoint = prop.getProperty("savepoint");
		URL = prop.getProperty("url");
		userName = prop.getProperty("username");
		password = prop.getProperty("password");
		tableName = prop.getProperty("tablename");
		tablespace = prop.getProperty("tablespace");
		tablenum = (new Integer(prop.getProperty("tablenum"))).intValue();
		sql = prop.getProperty("sql");
		String[] ratesGrp = ratesStr.split(",");
		rates = new int[4];
		rates[0] = new Integer(ratesGrp[0]).intValue();
		rates[1] = new Integer(ratesGrp[1]).intValue();
		rates[2] = new Integer(ratesGrp[2]).intValue();
		rates[3] = new Integer(ratesGrp[3]).intValue();
		if (!"".equals(logfile)) {
			WorkLoadManager.appendMethod(logfile,
					"----------------------------------------------------------------");
			WorkLoadManager.appendMethod(logfile, "insert(us)\tupdate(us)\tdelete(us)\tquery(us)");
		}

		for (int i = 1; i <= tablenum; i++) {
			KeySequence ks = new KeySequence();
			ks.init(i);
			TimeMonitor tm = new TimeMonitor();
			for (int j = 0; j < threadcount / tablenum; j++) {
				new loadRunner(ks, tm, loop, i).start();
				Thread.sleep(100);
			}
		}
		String url2 = prop.getProperty("url2");
		if(url2!=null&& !"".equals(url2)){
			URL = url2;
			for (int i = 1; i <= tablenum; i++) {
				KeySequence ks = new KeySequence();
				// ks.init(i);
				TimeMonitor tm = new TimeMonitor();
				for (int j = 0; j < threadcount / tablenum; j++) {
					new loadRunner(ks, tm, loop, i).start();
					Thread.sleep(100);
				}
			}
		}

		Thread.sleep(1000);
		while (true) {
			if (numberCount != 0) {
				try {
					int totalTrans_pre = totalTrans;
					Thread.sleep(1000);
					int tps = totalTrans - totalTrans_pre;
					WorkLoadManager.appendMethod(tpslog,
							df.format(new Date()) + "\t" + new Integer(tps).toString());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		System.out.println("End");
	}
}
