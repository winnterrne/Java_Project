package Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GeneratingID {
    public static String generatingID(String oldID) {
        // 1. Tách phần chữ (Tiền tố)
        String prefix = oldID.substring(0, 2);

        // 2. Tách phần số
        String numberPart = oldID.substring(2);

        // 3. Ép kiểu và cộng 1
        int nextNumber = Integer.parseInt(numberPart) + 1;

        // 4. Tự động đệm số 0 dựa trên độ dài của chuỗi số cũ
        // Ví dụ: Nếu numberPart là "004" (độ dài 3) -> formatString sẽ là "%03d"
        String formatString = "%0" + numberPart.length() + "d";

        // 5. Ghép chữ và phần số đã được định dạng chuẩn
        return prefix + String.format(formatString, nextNumber);
    }
}
