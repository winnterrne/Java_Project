package Utils;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ExportHoaDon {


    public static void export(
            String maHD,
            String tenKH,
            String diaChiKH,
            String tenNV,
            String ngayTao,
            JTable table,
            double tongTienHang,
            String path) {

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);


            PDType0Font fontBold = PDType0Font.load(document, new File("C:/Windows/Fonts/arialbd.ttf"));
            PDType0Font fontNormal = PDType0Font.load(document, new File("C:/Windows/Fonts/arial.ttf"));

            PDPageContentStream content = new PDPageContentStream(document, page);
            DecimalFormat df = new DecimalFormat("#,###.##");




            content.beginText();
            content.setFont(fontBold, 18);
            content.newLineAtOffset(210, 800);
            content.showText("HÓA ĐƠN BÁN HÀNG");
            content.endText();


            LocalDate today = LocalDate.now();
            content.beginText();
            content.setFont(fontNormal, 11);
            content.newLineAtOffset(220, 780);
            content.showText("Ngày in: " + String.format("%02d/%02d/%d", today.getDayOfMonth(), today.getMonthValue(), today.getYear()));
            content.endText();


            content.beginText();
            content.setFont(fontBold, 12);
            content.setNonStrokingColor(Color.RED);
            content.newLineAtOffset(450, 780);
            content.showText("Số: " + maHD);
            content.setNonStrokingColor(Color.BLACK);
            content.endText();




            int startY = 740;
            int lineSpacing = 15;

            content.beginText();
            content.setFont(fontBold, 11);
            content.newLineAtOffset(50, startY);
            content.showText("Đơn vị bán hàng: ______________________");
            content.newLineAtOffset(0, -lineSpacing);
            content.setFont(fontNormal, 11);
            content.showText("Mã số thuế: xxxxxxxxYY");
            content.newLineAtOffset(0, -lineSpacing);
            content.showText("Địa chỉ: .........................");
            content.newLineAtOffset(0, -lineSpacing);
            content.endText();


            startY -= (lineSpacing * 3) + 10;
            content.moveTo(50, startY);
            content.lineTo(545, startY);
            content.stroke();




            startY -= 20;
            content.beginText();
            content.setFont(fontNormal, 11);
            content.newLineAtOffset(50, startY);
            content.showText("Họ tên người mua hàng: " + (tenKH != null ? tenKH : "......................................................."));
            content.newLineAtOffset(0, -lineSpacing);
            content.showText("Tên đơn vị: .........................................................................................................");
            content.newLineAtOffset(0, -lineSpacing);
            content.showText("Địa chỉ: " + (diaChiKH != null ? diaChiKH : "......................................................."));
            content.newLineAtOffset(0, -lineSpacing);
            content.endText();




            startY -= (lineSpacing * 4) + 30;
            float margin = 50;
            float yPosition = startY;
            float rowHeight = 25;
            float tableWidth = 495;


            float[] columnWidths = {40, 70, 165, 50, 80, 90};


            content.addRect(margin, yPosition - rowHeight, tableWidth, rowHeight);
            content.stroke();

            TableModel model = table.getModel();


            float xPosition = margin;
            String[] headers = {"STT", "Mã SP", "Tên Hàng Hóa", "SL", "Đơn Giá", "Thành Tiền"};
            for (int col = 0; col < headers.length; col++) {
                content.beginText();
                content.setFont(fontBold, 10);
                content.newLineAtOffset(xPosition + 5, yPosition - 16);
                content.showText(headers[col]);
                content.endText();


                if (col < headers.length - 1) {
                    content.moveTo(xPosition + columnWidths[col], yPosition);
                    content.lineTo(xPosition + columnWidths[col], yPosition - rowHeight);
                    content.stroke();
                }
                xPosition += columnWidths[col];
            }

            yPosition -= rowHeight;


            for (int row = 0; row < model.getRowCount(); row++) {
                content.addRect(margin, yPosition - rowHeight, tableWidth, rowHeight);
                content.stroke();

                xPosition = margin;

                for (int col = 0; col < headers.length; col++) {
                    String text = "";
                    if (col == 0) {
                        text = String.valueOf(row + 1);
                    } else {
                        Object cellValue = model.getValueAt(row, col - 1);
                        text = (cellValue != null) ? cellValue.toString() : "";
                    }

                    content.beginText();
                    content.setFont(fontNormal, 10);
                    content.newLineAtOffset(xPosition + 5, yPosition - 16);
                    content.showText(text);
                    content.endText();


                    if (col < headers.length - 1) {
                        content.moveTo(xPosition + columnWidths[col], yPosition);
                        content.lineTo(xPosition + columnWidths[col], yPosition - rowHeight);
                        content.stroke();
                    }
                    xPosition += columnWidths[col];
                }
                yPosition -= rowHeight;
            }




            yPosition -= 20;

            content.beginText();
            content.setFont(fontBold, 12);
            content.newLineAtOffset(margin, yPosition);
            content.showText("Tổng cộng tiền thanh toán:");
            content.newLineAtOffset(350, 0);
            content.showText(df.format(tongTienHang) + " VNĐ");
            content.endText();




            yPosition -= 40;
            xPosition = 50;

            content.beginText();
            content.setFont(fontBold, 11);

            content.newLineAtOffset(xPosition, yPosition);
            content.showText("Ngày tạo hóa đơn: " + (ngayTao != null ? ngayTao : ""));
            content.newLineAtOffset(0, -18);
            content.showText("Nhân viên bán hàng: " + (tenNV != null ? tenNV : ""));
            content.endText();

            content.close();
            document.save(path);


            File file = new File(path);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi xuất file PDF: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}