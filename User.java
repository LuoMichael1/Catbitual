import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class User {
    public static int coins = 0;
    private Scanner filesc;
    private static final String filepath = "Userdata/userdata.txt";

    public User() {
        // make sure folder exists before trying to read
        File userFolder = new File("Userdata");
        if (!userFolder.exists()) {
            userFolder.mkdirs();
        }

        try {
            filesc = new Scanner(new File(filepath));
            
            while (filesc.hasNext()) {
                String line = filesc.nextLine();
                String[] data = new String[2];
                data = line.split(": ");

                coins = Integer.parseInt(data[1]);

                System.out.println("Fish: " + coins);
            }
        } catch (Exception e) {
            System.out.println("Could not read user file " + e);
        }
    }

    // Add or remove coins and save updated amount to file
    public static void addCoins(int amount) {
        coins += amount;
        if (coins < 0) {
            coins = 0;
        }
        saveCoins();
        // update any visible score menu
        Menu.refreshCoins();
    }

    // Attempt to spend coins and returns true if successful
    public static boolean spendCoins(int amount) {
        if (amount <= coins) {
            coins -= amount;
            saveCoins();
            Menu.refreshCoins();
            return true;
        }
        return false;
    }

    // Check whether the user has enough coins
    public static boolean canAfford(int amount) {
        return coins >= amount;
    }

    // save current coin count to disk
    public static void saveCoins() {
        try {
            File userDir = new File("Userdata");
            if (!userDir.exists()) userDir.mkdirs();
            java.io.FileWriter fw = new java.io.FileWriter(filepath);
            fw.write("Coins: " + coins);
            fw.close();
        } catch (Exception e) {
            System.out.println("Could not save user file " + e);
        }
    }
}
