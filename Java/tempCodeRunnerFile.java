import java.util.*;

import java.util.Calendar;
import java.text.SimpleDateFormat;

public class tempCodeRunnerFile {
    public static String generateDatestamp(int years, int months, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, years);
        calendar.add(Calendar.MONTH, months);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }
    public static String getCurrentDatestamp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        return dateFormat.format(calendar.getTime());
    }

    public static void main(String[] args) {
        // Example: If someone purchases a 1-year membership
        String oneYearMembership = generateDatestamp(1, 0, 0);

        // Storing the membership expiration date (you can save it in SharedPreferences or a database)
        String membershipExpirationDate = oneYearMembership;

        // Example usage
        System.out.println("Membership expiration date for a 1-year membership: " + membershipExpirationDate);

        System.out.println("Current datestamp:"+getCurrentDatestamp());
    }
}
