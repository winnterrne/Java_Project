package Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import javax.swing.JFileChooser;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import DTO.SanPham_DTO;

public class SanPham_ExcelUtil {
    public static boolean exportToExcel(ArrayList<SanPham_DTO> listSanPham) {
        if (listSanPham == null || listSanPham.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(null, "Không có dữ liệu sản phẩm để xuất!", "Thông báo", javax.swing.JOptionPane.WARNING_MESSAGE);
            return false;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Lưu file Excel");
        fileChooser.setSelectedFile(new File("DanhSachSanPham.xlsx"));  // gợi ý tên file mặc định
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files (*.xlsx)", "xlsx"));

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection != JFileChooser.APPROVE_OPTION) {
            return false;
        }

        File fileToSave = fileChooser.getSelectedFile();
        String filePath = fileToSave.getAbsolutePath();

        if (!filePath.toLowerCase().endsWith(".xlsx")) {
            filePath += ".xlsx";
        }

        try (Workbook workbook = new XSSFWorkbook();
            FileOutputStream fileOut = new FileOutputStream(filePath)) {

            Sheet sheet = workbook.createSheet("Danh sách Sản phẩm");

            String[] headers = {"Mã SP", "Tên SP", "Mô tả", "Giá bán", "Đơn vị", "SL tồn", "Mã DM", "Mã KM", "Vị trí"};
            Row headerRow = sheet.createRow(0);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            Font font = workbook.createFont();
            font.setBold(true);
            headerStyle.setFont(font);

            for (int i = 0; i < headers.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            int rowNum = 1;
            for (SanPham_DTO sp : listSanPham) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(sp.getMaSP() != null ? sp.getMaSP() : "");
                row.createCell(1).setCellValue(sp.getTenSP() != null ? sp.getTenSP() : "");
                row.createCell(2).setCellValue(sp.getMoTa() != null ? sp.getMoTa() : "");
                row.createCell(3).setCellValue(sp.getGiaBan());
                row.createCell(4).setCellValue(sp.getDonVi());
                row.createCell(5).setCellValue(sp.getSoLuongTon());
                row.createCell(6).setCellValue(sp.getMaDM() != null ? sp.getMaDM() : "");
                row.createCell(7).setCellValue(sp.getMaKhuyenMai() != null ? sp.getMaKhuyenMai() : "");
                row.createCell(8).setCellValue(sp.getViTri() != null ? sp.getViTri() : "");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(fileOut);

            javax.swing.JOptionPane.showMessageDialog(null,
                    "Xuất file Excel thành công!\n" + filePath,
                    "Thành công", javax.swing.JOptionPane.INFORMATION_MESSAGE);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(null,
                    "Lỗi khi xuất file: " + e.getMessage(),
                    "Lỗi", javax.swing.JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
