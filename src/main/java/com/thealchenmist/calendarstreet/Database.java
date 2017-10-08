import java.sql.*;
import java.util.Date;

import com.sothawo.mapjfx.Coordinate;

public class Database {
	
	private Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:C://sqlite/db/test.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	
	public static void createNewDatabase() {
		String url = "jdbc:sqlite:";
		
		try(Connection conn = DriverManager.getConnection(url)){
			if (conn!=null) {
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("The driver name is " + meta.getDriverName());
				System.out.println("A new database has been created.");
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
			System.out.println("Connected");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			createNewDatabase(); //Double check this
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
				+ "name text PRIMARY KEY, \n"
				+ "Description text, \n"
				+ "StartTime blob NOT NULL, \n"
				+ "EndTime blob NOT NULL, \n"
				+ "Coordinates blob, \n"
				+ "Address text, \n"
				+");";
		try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	//PASS ONLY EVENTS OBJECT????!?!
	public void insertEvent(Date startTime, Date endTime, String name, String desc, String address, Coordinate location) {
		String sql = "INSERT INTO Events(name,Description,StartTime,EndTime,Coordinates,Address)"
				+" VALUES(name,desc,startTime,endTime,location,address)";
		try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate(); //didnt do set stuff see if this works
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
	public void getEvent() {
		String sql = "SELECT name, Description, StartTime, EndTime, Coordinates, Address FROM Events";
		
		try(Connection conn = this.connect();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)){
			while(rs.next()) {
				//for debugging
				 System.out.println(rs.getString("name"));
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());;
		}
	}
	
	//PASS ONLY EVENTS OBJECT????!?!
		public void updateEvent(Date startTime, Date endTime, String name, String desc, String address, Coordinate location) {
			String sql = "UPDATE Events SET name=name, Description = desc ,"
					+ "StartTime = startTime, EndTime = endTime,"
					+ "Coordinates = location,Address = address";
			try (Connection conn = this.connect();
	                PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.executeUpdate(); //didnt do set stuff see if this works
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		}
		
		public void deleteEvent(String name) {
			String sql = "DELETE FROM Events WHERE name = name";
			
			try(Connection conn = this.connect();
					PreparedStatement pstmt = conn.prepareStatement(sql)){
				pstmt.executeUpdate();
			}catch(SQLException e) {
				System.out.println(e.getMessage());
			}
		}
	
}
