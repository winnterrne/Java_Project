package Utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ExportExcelHoaDon {

    public static void ExportExcelHoaDon(JTable table, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("DanhSachHoaDon");


        Row headerRow = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);

        for (int i = 0; i < table.getColumnCount(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(table.getColumnName(i));
            cell.setCellStyle(headerStyle);
        }


        for (int i = 0; i < table.getRowCount(); i++) {
            Row row = sheet.createRow(i + 1);
            for (int j = 0; j < table.getColumnCount(); j++) {
                Object value = table.getValueAt(i, j);
                Cell cell = row.createCell(j);

                if (value != null) {

                    if (j == 4) {
                        try {

                            NumberFormat nf = NumberFormat.getInstance();
                            String numberStr = value.toString().replace(" VNĐ", "").trim();
                            Number number = nf.parse(numberStr);
                            cell.setCellValue(number.doubleValue());
                        } catch (ParseException e) {
                            cell.setCellValue(value.toString());
                        }
                    } else {
                        cell.setCellValue(value.toString());
                    }
                }
            }
        }


        for (int i = 0; i < table.getColumnCount(); i++) {
            sheet.autoSizeColumn(i);
        }


        try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
            workbook.write(fileOut);
            JOptionPane.showMessageDialog(null, "Xuất Excel Hóa đơn thành công!");

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