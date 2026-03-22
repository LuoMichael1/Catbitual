import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class User {
    public static int coins = 0;
    private Scanner filesc;
    private static final String filepath = "Userdata/userdata.txt";

    public static boolean musicEnabled = true;
    public static boolean sfxEnabled = true;

    // cat state persistence
    public static double catFood = 100;
    public static double catWater = 100;
    public static long lastDecayTime = 0;  // milliseconds
    public static final long FOOD_WATER_DECAY_MS = 15 * 60 * 1000; // 15 minutes per unit decrease


    public User() {
        // make sure folder exists before trying to read
        File userFolder = new File("Userdata");
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }

        try {
            filesc = new Scanner(new File(filepath));
            
            // only read the first line which contains the coin
            if (filesc.hasNextLine()) {
                String line = filesc.nextLine();
                coins = Integer.parseInt(line.split(": ")[1]);
            }
            
            // now read the rest of the file which contains the user settings      
            if (filesc.hasNextLine()) {      
                String line = filesc.nextLine();
                musicEnabled = Boolean.parseBoolean(line.split(": ")[1]);
            }
            if (filesc.hasNextLine()) {      
                String line = filesc.nextLine();
                sfxEnabled = Boolean.parseBoolean(line.split(": ")[1]);
            }
            // optional cat persistence lines
            if (filesc.hasNextLine()) {
                String line = filesc.nextLine();
                if (line.startsWith("Food:")) {
                    catFood = Double.parseDouble(line.split(": ")[1]);
                }
            }
            if (filesc.hasNextLine()) {
                String line = filesc.nextLine();
                if (line.startsWith("Water:")) {
                    catWater = Double.parseDouble(line.split(": ")[1]);
                }
            }
            if (filesc.hasNextLine()) {      
                String line = filesc.nextLine();
                if (line.startsWith("LastDecay:")) {
                    lastDecayTime = Long.parseLong(line.split(": ")[1]);
                }
            }
            
        

        } catch (Exception e) {
            System.out.println("Could not read user file " + e);
        }
        
        // compute offline decay based on last saved timestamp
        long now = System.currentTimeMillis();
        if (lastDecayTime == 0) {
            // first launch, initialize
            lastDecayTime = now;
        } else {
            long elapsed = now - lastDecayTime;
            int decrease = (int)(elapsed / FOOD_WATER_DECAY_MS);
            if (decrease > 0) {
                catFood -= decrease;
                catWater -= decrease;
                if (catFood < 0) catFood = 0;
                if (catWater < 0) catWater = 0;
                // advance lastDecayTime forward by whole steps
                lastDecayTime += (long)decrease * FOOD_WATER_DECAY_MS;
            }
        }
        // ensure file contains all fields after potential change
        saveAll();
    }

    // Add or remove coins and save updated amount to file
    public static void addCoins(int amount) {
        coins += amount;
        if (coins < 0) {
            coins = 0;
        }
        saveAll();
        // update any visible score menu
        Menu.refreshCoins();
    }

    // Attempt to spend coins and returns true if successful
    public static boolean spendCoins(int amount) {
        if (amount <= coins) {
            coins -= amount;
            saveAll();
            Menu.refreshCoins();
            return true;
        }
        return false;
    }

    // Check whether the user has enough coins
    public static boolean canAfford(int amount) {
        return coins >= amount;
    }

    // write everything to the userdata file
    private static void saveAll() {
        try {
            File userDir = new File("Userdata");
            if (!userDir.exists()) userDir.mkdirs();
            FileWriter fw = new FileWriter(filepath);
            fw.write("Coins: " + coins + "\n");
            fw.write("Music: " + musicEnabled + "\n");
            fw.write("SFX: " + sfxEnabled + "\n");
            fw.write("Food: " + catFood + "\n");
            fw.write("Water: " + catWater + "\n");
            fw.write("LastDecay: " + lastDecayTime + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Could not save user file " + e);
        }
    }

    // save settings to disk (coins already persisted by saveAll)
    public static void saveSettings() {
        saveAll();
    }


    // helpers for cat tracking
    public static void setCatFood(double value) {
        catFood = Math.max(0, Math.min(100, value));
        lastDecayTime = System.currentTimeMillis();
        saveAll();
        Menu.refreshCoins();
    }

    public static void setCatWater(double value) {
        catWater = Math.max(0, Math.min(100, value));
        lastDecayTime = System.currentTimeMillis();
        saveAll();
        Menu.refreshCoins();
    }
}
