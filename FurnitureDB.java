import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;

public class FurnitureDB {
    private static final String DB_PATH = "Userdata/furniture.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;
    private Connection conn;

    public static class FurnitureRecord {
        public int id;
        public String filepath;
        public int price;
        public String type;
        public int menuIndex;
        public boolean owned;
        public int x;
        public int y;
    }

    public FurnitureDB() throws SQLException {
        File userDataDir = new File("Userdata");
        if (!userDataDir.exists()) userDataDir.mkdirs();
        conn = DriverManager.getConnection(URL);
        ensureTable();
    }

    private void ensureTable() throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate("CREATE TABLE IF NOT EXISTS furniture (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "filepath TEXT UNIQUE, " +
                    "price INTEGER, " +
                    "type TEXT, " +
                    "menu_index INTEGER, " +
                    "owned INTEGER DEFAULT 0, " +
                    "x INTEGER DEFAULT -1, " +
                    "y INTEGER DEFAULT -1) ");
        }
    }

    public List<FurnitureRecord> getAllItems() throws SQLException {
        List<FurnitureRecord> list = new ArrayList<>();
        String sql = "SELECT id, filepath, price, type, menu_index, owned, x, y FROM furniture ORDER BY menu_index ASC";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                FurnitureRecord r = new FurnitureRecord();
                r.id = rs.getInt(1);
                r.filepath = rs.getString(2);
                r.price = rs.getInt(3);
                r.type = rs.getString(4);
                r.menuIndex = rs.getInt(5);
                r.owned = rs.getInt(6) != 0;
                r.x = rs.getInt(7);
                r.y = rs.getInt(8);
                list.add(r);
            }
        }
        return list;
    }

    public List<FurnitureRecord> getOwnedItems() throws SQLException {
        List<FurnitureRecord> list = new ArrayList<>();
        String sql = "SELECT id, filepath, price, type, menu_index, owned, x, y FROM furniture WHERE owned = 1 ORDER BY menu_index ASC";
        try (Statement st = conn.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                FurnitureRecord r = new FurnitureRecord();
                r.id = rs.getInt(1);
                r.filepath = rs.getString(2);
                r.price = rs.getInt(3);
                r.type = rs.getString(4);
                r.menuIndex = rs.getInt(5);
                r.owned = rs.getInt(6) != 0;
                r.x = rs.getInt(7);
                r.y = rs.getInt(8);
                list.add(r);
            }
        }
        return list;
    }

    public void ensureCatalogFromFile(File catalogFile) throws SQLException {
        if (!catalogFile.exists()) return;
        try (java.util.Scanner s = new java.util.Scanner(catalogFile)) {
            int idx = 0;
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) { idx++; continue; }
                // insert if missing
                if (!existsByFilepath(line)) {
                    insertItem(line, 100, "furniture", idx++);
                } else {
                    idx++;
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean existsByFilepath(String filepath) throws SQLException {
        String sql = "SELECT 1 FROM furniture WHERE filepath = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, filepath);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insertItem(String filepath, int price, String type, int menuIndex) throws SQLException {
        String sql = "INSERT OR IGNORE INTO furniture(filepath, price, type, menu_index) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, filepath);
            ps.setInt(2, price);
            ps.setString(3, type);
            ps.setInt(4, menuIndex);
            ps.executeUpdate();
        }
    }

    public void markOwned(int id) throws SQLException {
        String sql = "UPDATE furniture SET owned = 1 WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public void updateLocation(int id, int x, int y) throws SQLException {
        String sql = "UPDATE furniture SET x = ?, y = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, x);
            ps.setInt(2, y);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
    }

    public void close() {
        try { if (conn != null && !conn.isClosed()) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}
