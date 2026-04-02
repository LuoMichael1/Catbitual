import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

// Using SQLite for storing daily check-ins

public class CalendarDB {
    private static final String DB_PATH = "Userdata/catbitual.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;
    private Connection conn;

    public CalendarDB() throws SQLException {
        connect();
        ensureTable();
    }

    private void connect() throws SQLException {
        conn = DriverManager.getConnection(URL);
    }

    private void ensureTable() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS checkins (date TEXT PRIMARY KEY)");
        }
    }

    public synchronized void markCheckIn(LocalDate date) throws SQLException {
        String sql = "INSERT OR REPLACE INTO checkins(date) VALUES(?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date.toString());
            ps.executeUpdate();
        }
    }

    public synchronized boolean isCheckedIn(LocalDate date) throws SQLException {
        String sql = "SELECT 1 FROM checkins WHERE date = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, date.toString());
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public synchronized Set<LocalDate> getAllCheckIns() throws SQLException {
        Set<LocalDate> set = new HashSet<>();
        String sql = "SELECT date FROM checkins";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                set.add(LocalDate.parse(rs.getString(1)));
            }
        }
        return set;
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
				conn.close();
			}
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
