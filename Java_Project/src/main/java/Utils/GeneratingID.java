package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeneratingID {
    public static String generatingID(String oldID) {

        String prefix = oldID.substring(0, 2);


        String numberPart = oldID.substring(2);


        int nextNumber = Integer.parseInt(numberPart) + 1;



        String formatString = "%0" + numberPart.length() + "d";


        return prefix + String.format(formatString, nextNumber);
    }
}