import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class adbwifi {

    public static void main(String[] args) {
        ArrayList addresses = new ArrayList();

        try {
            String e = Inet4Address.getLocalHost().getHostAddress();
            String subnet = e.substring(0, e.lastIndexOf(46));
            System.out.println("Scanning for devices on the class C subnet " + subnet + ".1 - " + subnet + ".254");
            System.out.println("Select an option as they come up\n");

            for(int scanner = 0; scanner < 254; ++scanner) {
                (new Thread(() -> {
                    String host = subnet + "." + scanner;

                    try {
                        new Socket(host, 5555);
                        System.out.println(addresses.size() + 1 + ") " + host);
                        addresses.add(host);
                    } catch (Exception var5) {
                        ;
                    }

                })).start();
            }

            Scanner var12 = new Scanner(System.in);
            int index = var12.nextInt() - 1;

            try {
                String e1 = (String)addresses.get(index);
                Process p = Runtime.getRuntime().exec("adb connect " + e1);
                p.waitFor();
                BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line;
                while((line = reader.readLine()) != null) {
                    System.out.println(line + "\n");
                }

                System.exit(0);
            } catch (IndexOutOfBoundsException var10) {
                System.out.println("Invalid option");
            }
        } catch (Exception var11) {
            System.out.println("Failed to acquire device IP Address");
            var11.printStackTrace();
        }

    }
}
