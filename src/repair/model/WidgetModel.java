package repair.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class WidgetModel {
    public static String getDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return "Date: " + date.format(dateFormatter);
    }

    public static String getTime() {
        LocalTime time = LocalTime.now();
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return "Time: " + time.format(timeFormatter);
    }

    public static int s = 0;
    public static int m = 0;
    public static int h = 0;

    public static String getStopwatch() {
        s++;
        if (s == 60) {
            m++;
            s = 0;
            if (m == 60) {
                h++;
                m = 0;
            }
        }
        return "Stopwatch: " + String.format("%02d:%02d:%02d\n", h, m, s);
    }

    public static String getOS()
    {
        String OS = null;
        if(OS == null) {
            OS = System.getProperty("os.name");
        }
        return "OS: " + OS;
    }

    public static String getLANIP() throws Exception {
        String LANIP = "";
        try
        {
            InetAddress localhost = InetAddress.getLocalHost();
            LANIP = localhost.getHostAddress().trim();
            return "LAN IP: " + LANIP;
        }
        catch (Exception e)
        {
            LANIP = "";
            return "No adapters";
        }
    }

    public static String getWANIP() {
        String WANIP = "";
        try {
            URL url_name = new URL("http://bot.whatismyipaddress.com");
            BufferedReader sc = new BufferedReader(new InputStreamReader(url_name.openStream()));
            WANIP = sc.readLine().trim();
            return "WAN IP: " + WANIP;
        }
        catch (Exception e) {
            WANIP = "";
            return "No internet";
        }
    }
}
