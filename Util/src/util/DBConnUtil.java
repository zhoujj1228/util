package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBConnUtil {
	
	private static boolean isInit = false;
	public static void main(String[] args) {
		DBConnUtil dbUtil = new DBConnUtil();
		String url = null;
		String user = null;
		String password = null;
		Connection conn = dbUtil.getConnection(url, user, password);
		
	}
	public static Connection getConnection(String url, String user, String password){
		Connection conn = null;
		try {
			if(!isInit){
				Class.forName("oracle.jdbc.OracleDriver");
				isInit = true;
			}
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void openTransaction(Connection conn) throws SQLException{
		conn.setAutoCommit(false);
	}
	
	public static boolean commitTransaction(Connection conn) throws SQLException{
		if(conn.getAutoCommit()){
			System.out.println("请先setAutoCommit为false");
			return false;
		}
		conn.commit();
		return true;
	}
	
	public static boolean rollbackTransaction(Connection conn) throws SQLException{
		if(conn.getAutoCommit()){
			System.out.println("请先setAutoCommit为false");
			return false;
		}
		conn.rollback();
		return true;
	}
	
	public static boolean excutePrepareSQL(Connection conn, String sql, List<String> argList) throws SQLException{
		PreparedStatement pt = conn.prepareStatement(sql);
		for(int i = 0; i < argList.size(); i++){
			pt.setString(i, argList.get(i));
		}
		return pt.execute();
	}
	
	public static boolean excuteSQL(Connection conn, String sql) throws SQLException{
		Statement st = conn.createStatement();
		return st.execute(sql);
	}
	
	public static ResultSet excuteQuerySQL(Connection conn, String sql) throws SQLException{
		Statement st = conn.createStatement();
		ResultSet rs = st.executeQuery(sql);
		/*while(rs.next()){
			rs.get
		}*/
		return rs;
	}
}
