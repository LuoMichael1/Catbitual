import java.io.File;
import java.time.LocalDateTime;
import java.util.Scanner;

public class User {
    public static int coins = 0;
    private Scanner filesc;
    private String filepath = "Userdata/userdata.txt";

    public User() {


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
}
