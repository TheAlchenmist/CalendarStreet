import java.sql.*;
import java.util.Date;

import com.sothawo.mapjfx.Coordinate;

public class Database {
	
	public static void createNewDatabase() {
		String url = "jdbc:sqlite:";
		
		try(Connection conn = DriverManager.getConnection(url)){
			if (conn!=null) {
				DatabaseMetaData meta = conn.getMetaData();
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void connectToDatabase() {
		Connection conn = null;
		try {
			String url = "jdbc:sqlite:event.db";
			
			conn = DriverManager.getConnection(url);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			createNewDatabase();
		}finally {
			try {
				if(conn!=null){
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}
	
	public static void createNewTable() {
		String url = "jdbc:sqlite:event.db";
		
		String sql = "CREATE TABLE IF NOT EXISTS Events (\n"
				+ "id integer PRIMARY KEY AUTOINCREMENT, \n"
				+ "name text, \n"
				+ "Description text, \n"
				+ "StartTime blob, \n"
				+ "EndTime blob, \n"
				+ "Coordinates blob, \n"
				+ "Address text \n"
				+");";
		try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void insertEvent(Date startTime, Date endTime, String Name, String desc, String address, Coordinate location) {
		String url = "jdbc:sqlite:event.db";
		String sql = "INSERT INTO Events(name,Description,StartTime,EndTime,Coordinates,Address)"
				+" VALUES(?,?,?,?,?,?)";
		try (Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Name);
            pstmt.setString(2, desc);
            pstmt.setObject(3, startTime);
            pstmt.setObject(4, endTime);
            pstmt.setObject(5, location);
            pstmt.setString(6, address);
			pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public static void getEvent() {
		String sql = "SELECT name, Description, StartTime, EndTime, Coordinates, Address FROM Events";
		String url = "jdbc:sqlite:event.db";
		try(Connection conn = DriverManager.getConnection(url);
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
		}catch(SQLException e) {
			System.out.println(e.getMessage());;
		}
	}

		public static void updateEvent(int id, Date startTime, Date endTime, String name, String desc, String address, Coordinate location) {
			String url = "jdbc:sqlite:event.db";
			String sql = "UPDATE Events SET name=?, Description = ? ,"
					+ "StartTime = ?, EndTime = ?,"
					+ "Coordinates = ?,Address = ? WHERE id = ?";
			try (Connection conn = DriverManager.getConnection(url);
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setString(1, name);
	            pstmt.setString(2, desc);
	            pstmt.setObject(3, startTime);
	            pstmt.setObject(4, endTime);
	            pstmt.setObject(5, location);
	            pstmt.setString(6, address);
	            pstmt.setInt(7, id);
				pstmt.executeUpdate();
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		}
		
		public static void deleteEvent(int id) {
			String sql = "DELETE FROM Events WHERE id = ?";
			String url = "jdbc:sqlite:event.db";
			try(Connection conn = DriverManager.getConnection(url);
					PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.setInt(1, id);
				pstmt.executeUpdate();
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	
}
