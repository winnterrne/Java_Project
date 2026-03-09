//import BUS.SanPham_BUS;
//import DTO.SanPham_DTO;
//import org.apache.poi.ss.usermodel.Cell;
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import javax.swing.*;
//
//public void ImportExcel() {
//    JFileChooser fileChooser = new JFileChooser();
//    fileChooser.setDialogTitle("Chọn file Excel để nhập dữ liệu");
//
//    // Chỉ cho phép chọn file Excel
//    fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xls", "xlsx"));
//
//    if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
//        File file = fileChooser.getSelectedFile();
//
//        try (FileInputStream fis = new FileInputStream(file);
//             Workbook workbook = new XSSFWorkbook(fis)) {
//
//            Sheet sheet = workbook.getSheetAt(0);
//            ArrayList<SanPham_DTO> listSanPhamMoi = new ArrayList<>();
//
//            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
//                Row row = sheet.getRow(i);
//                if (row == null) continue;
//
//                SanPham_DTO sp = new SanPham_DTO();
//
//                sp.setMaSP(getCellValue(row.getCell(0)));
//                sp.setTenSP(getCellValue(row.getCell(1)));
//                sp.setMaDM(getCellValue(row.getCell(2)));
//                sp.setMoTa(getCellValue(row.getCell(3)));
//
//
//                String giaBanStr = getCellValue(row.getCell(4));
//                sp.setGiaBan(giaBanStr.isEmpty() ? 0 : Double.parseDouble(giaBanStr));
//
//                sp.setDonVi(getCellValue(row.getCell(5)));
//
//
//                String soLuongStr = getCellValue(row.getCell(6));
//                sp.setSoLuongTon(soLuongStr.isEmpty() ? 0 : (int) Double.parseDouble(soLuongStr));
//
//
//
//                sp.setMaKhuyenMai(getCellValue(row.getCell(8)));
//                sp.setViTri(getCellValue(row.getCell(9)));
//
//                listSanPhamMoi.add(sp);
//            }
//
//            int soLuongThanhCong = 0;
//            for (SanPham_DTO sp : listSanPhamMoi) {
//
//                SanPham_BUS spBUS = new  SanPham_BUS();
//                if (spBUS.themSanPham(sp)) {
//                    soLuongThanhCong++;
//                }
//            }
//
//            JOptionPane.showMessageDialog(null,
//                    "Nhập thành công " + soLuongThanhCong + "/" + listSanPhamMoi.size() + " sản phẩm!",
//                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            JOptionPane.showMessageDialog(null, "Lỗi đọc file Excel hoặc sai định dạng dữ liệu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//}
//
//
//
//
