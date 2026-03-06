import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PetStoreDB {
    private static final String DB_PATH = "Userdata/catbitual.db";
    private static final String URL = "jdbc:sqlite:" + DB_PATH;
    private Connection conn;
    private int[] prices = {80,100,150,200};
    
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

    public PetStoreDB() throws SQLException {
        conn = DriverManager.getConnection(URL);
        ensureTable();
    }

    private void ensureTable() throws SQLException {
        try  {
            Statement st = conn.createStatement();
            // Create furniture_types table
            st.executeUpdate("CREATE TABLE IF NOT EXISTS furniture_types (id INTEGER PRIMARY KEY AUTOINCREMENT, type_name TEXT UNIQUE NOT NULL)");
            
            // Create furniture table with foreign key to types
            st.executeUpdate("CREATE TABLE IF NOT EXISTS furniture (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "filepath TEXT UNIQUE, " +
                "price INTEGER, " +
                "type_id INTEGER, " +
                "menu_index INTEGER, " +
                "owned INTEGER DEFAULT 0, " +
                "x INTEGER DEFAULT -1, " +
                "y INTEGER DEFAULT -1, " +
                "FOREIGN KEY(type_id) REFERENCES furniture_types(id))");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        // Initialize furniture types if not already present
        initializeFurnitureTypes();
    }
    
    private void initializeFurnitureTypes() throws SQLException {
        String[] types = {"carpet", "door", "foodstand", "plants", "table1", "table2", "trashbin", "walldeco", "window"};
        String sql = "INSERT OR IGNORE INTO furniture_types(type_name) VALUES(?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            for (String type : types) {
                ps.setString(1, type);
                ps.executeUpdate();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FurnitureRecord> getAllItems() throws SQLException {
        ArrayList<FurnitureRecord> list = new ArrayList<FurnitureRecord>();
        String sql = "SELECT f.id, f.filepath, f.price, ft.type_name, f.menu_index, f.owned, f.x, f.y FROM furniture f LEFT JOIN furniture_types ft ON f.type_id = ft.id ORDER BY f.menu_index ASC";
        try {
            Statement st = conn.createStatement(); 
            ResultSet rs = st.executeQuery(sql);

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
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<FurnitureRecord> getOwnedItems() throws SQLException {
        ArrayList<FurnitureRecord> list = new ArrayList<FurnitureRecord>();
        String sql = "SELECT f.id, f.filepath, f.price, ft.type_name, f.menu_index, f.owned, f.x, f.y FROM furniture f LEFT JOIN furniture_types ft ON f.type_id = ft.id WHERE f.owned = 1 ORDER BY f.menu_index ASC";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

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
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void ensureCatalogFromFile(File catalogFile) throws SQLException {
        int counter = 0;
        if (!catalogFile.exists()) 
            return;
        try {
            Scanner s = new Scanner(catalogFile);
            int idx = 0;
            
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) { 
                    idx++; continue; 
                }
                // extract type from filepath like "Carpet/image.png" -> "carpet"
                String type = extractTypeFromPath(line);
                // insert if missing
                if (!existsByFilepath(line)) {
                    insertItem(line, prices[counter%4], type, idx++);
                    counter++;
                } 
                else {
                    idx++;
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    private String extractTypeFromPath(String filepath) {
        // Extract type from path like "Carpet/image.png" -> "carpet"
        String[] parts = filepath.split("/");
        if (parts.length > 0) {
            return parts[0].toLowerCase();
        }
        return "unknown";
    }

    private boolean existsByFilepath(String filepath) throws SQLException {
        String sql = "SELECT 1 FROM furniture WHERE filepath = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, filepath);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertItem(String filepath, int price, String type, int menuIndex) throws SQLException {
        // Look up type_id from type name
        int typeId = getOrCreateTypeId(type);
        String sql = "INSERT OR IGNORE INTO furniture(filepath, price, type_id, menu_index) VALUES(?,?,?,?)";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, filepath);
            ps.setInt(2, price);
            ps.setInt(3, typeId);
            ps.setInt(4, menuIndex);
            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
    private int getOrCreateTypeId(String typeName) throws SQLException {
        String selectSql = "SELECT id FROM furniture_types WHERE type_name = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(selectSql);

            ps.setString(1, typeName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // If type doesn't exist, create it
        String insertSql = "INSERT INTO furniture_types(type_name) VALUES(?)";
        try  {
            PreparedStatement ps = conn.prepareStatement(insertSql);
            ps.setString(1, typeName);
            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        // Now retrieve the ID
        try (PreparedStatement ps = conn.prepareStatement(selectSql)) {
            ps.setString(1, typeName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("Failed to create or retrieve type: " + typeName);
    }

    public void markOwned(int id) throws SQLException {
        String sql = "UPDATE furniture SET owned = 1 WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateLocation(int id, int x, int y) throws SQLException {
        String sql = "UPDATE furniture SET x = ?, y = ? WHERE id = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, x);
            ps.setInt(2, y);
            ps.setInt(3, id);
            ps.executeUpdate();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public void close() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close(); 
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
