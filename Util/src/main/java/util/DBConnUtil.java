package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBConnUtil {
	
	private static boolean isInit = false;
	public static void main(String[] args) throws SQLException {
		String url = "jdbc:oracle:thin:@11.8.131.59:1521:esbdb";
		String user = "esb120sit";
		String password = "esb120sit";
		Connection conn = DBConnUtil.getORAConnection(url, user, password);
		ResultSet excuteQuerySQL = DBConnUtil.excuteQuerySQL(conn, "select * from esb_service_control");
		//ResultSet excuteQuerySQL = DBConnUtil.excuteQuerySQL(conn, "select t.* from TEST t");
		List<List<String>> changeResultSetToList = DBConnUtil.changeResultSetToStrList(excuteQuerySQL);
		System.out.println("changeResultSetToList:" + changeResultSetToList);
		conn.close();
		
	}
	public static List<List<String>> changeResultSetToStrList(ResultSet rs){
		List<List<String>> result = new ArrayList<>();
		try {
			while(rs.next()) {
				List<String> list = new ArrayList<>();
				for (int i = 1;; i++) {
					try {
						String colValue = rs.getString(i);
						list.add(colValue);
					} catch (SQLException e) {
						break;
					}
				}
				result.add(list);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return result;
	}
	
	
	public static Connection getORAConnection(String url, String user, String password){
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
