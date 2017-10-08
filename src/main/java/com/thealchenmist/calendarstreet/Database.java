import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sothawo.mapjfx.Coordinate;

public class Database {

    static String url = "jdbc:sqlite:event.db";

    public static void createNewDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                conn.getMetaData();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connectToDatabase() {
        try (Connection conn = DriverManager.getConnection(url)) { }
        catch (SQLException e) {
            System.out.println(e.getMessage());
            createNewDatabase();
        } 
    }

    public static void createNewTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Events (\n" +
                     "id integer PRIMARY KEY AUTOINCREMENT, \n" +
                     "Name VARCHAR(255), \n" +
                     "Description VARCHAR(255), \n" +
                     "StartTime DATETIME, \n" +
                     "EndTime DATETIME, \n" +
                     "Latitude DOUBLE PRECISION \n" +
                     "Longitude DOUBLE PRECISION \n" +
                     "Address VARCHAR(255)\n" +
                     ");";
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static int insertEvent(Date startTime, Date endTime, String Name, String desc, String address,
            Coordinate location) {
        String sql = "INSERT INTO Events(Name, Description, StartTime, EndTime, Latitude, Longitude, Address)"
                + " VALUES(?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, Name);
            pstmt.setString(2, desc);
            pstmt.setDate(3, new java.sql.Date(startTime.getTime()));
            pstmt.setDate(4, new java.sql.Date(endTime.getTime()));
            pstmt.setDouble(5, location.getLatitude());
            pstmt.setDouble(6, location.getLongitude());
            pstmt.setString(7, address);
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return 0;
    }

    public static List<Event> getEvents() {
        String sql = "SELECT Name, Description, StartTime, EndTime, Latitude, Longitude, Address FROM Events";

        List<Event> results = new ArrayList<Event>();
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                results.add(new Event(rs.getDate("StartTime"),
                                      rs.getDate("EndTime"),
                                      rs.getString("Name"),
                                      rs.getString("Description"),
                                      rs.getString("Address"),
                                      new Coordinate(rs.getDouble("Latitude"),
                                                     rs.getDouble("Longitude"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public static void updateEvent(int id, Date startTime, Date endTime, String name, String desc, String address,
            Coordinate location) {
        String sql = "UPDATE Events SET Name=?, Description = ?," +
                     "StartTime = ?, EndTime = ?," +
                     "Latitude = ?, Longitude = ?, Address = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, desc);
            pstmt.setDate(3, new java.sql.Date(startTime.getTime()));
            pstmt.setDate(4, new java.sql.Date(endTime.getTime()));
            pstmt.setDouble(5, location.getLatitude());
            pstmt.setDouble(6, location.getLongitude());
            pstmt.setString(7, address);;
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteEvent(int id) {
        String sql = "DELETE FROM Events WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
