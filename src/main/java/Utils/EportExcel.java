package Utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class EportExcel {
    public static void exportTableToExcel(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachPhieuNhap");

        Row headerRow = sheet.createRow(0);
        for(int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
        }

        for(int i = 0; i < table.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for(int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getValueAt(i, j);
                Cell cell = row.createCell(j);
                if(value != null) {
                    if(j == 5) {
                        try {
                            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
                            String numberStr = table.getValueAt(i, 5).toString();
                            Number number = nf.parse(numberStr);
                            double giaNhap = number.doubleValue();
                            cell.setCellValue(giaNhap);
                        } catch (NumberFormatException e) {
                            cell.setCellValue(value.toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }

        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(null, "Xuất Excel thành công!");
            File file = new File(filePath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất Excel!");
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
