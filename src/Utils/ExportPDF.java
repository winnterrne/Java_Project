package Utils;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ExportPDF {
    public static void export(
            String maPN,
            String tenNCC,
            String ngayTao,
            String tongTien,
            JTable table,
            String path) {

        try (PDDocument document = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content =
                    new PDPageContentStream(document, page);

            // Load font hỗ trợ tiếng Việt
            PDType0Font font = PDType0Font.load(
                    document,
                    new File("C:/Windows/Fonts/arial.ttf")
            );

            content.beginText();
            content.setFont(font, 16);
            content.newLineAtOffset(200, 800);
            content.showText("CHI TIẾT PHIẾU NHẬP");
            content.endText();

            content.beginText();
            content.setFont(font, 12);
            content.newLineAtOffset(50, 760);

            content.showText("Mã phiếu: " + maPN);
            content.newLineAtOffset(0, -20);
            content.showText("Nhà cung cấp: " + tenNCC);
            content.newLineAtOffset(0, -20);
            content.showText("Ngày tạo: " + ngayTao);
            content.newLineAtOffset(0, -20);
            content.showText("Tổng tiền: " + tongTien);
            content.endText();

            // In bảng
            TableModel model = table.getModel();

            float yPosition = 650;
            float rowHeight = 20;
            float margin = 50;


            float[] columnWidths = {80, 80, 200, 100, 100, 150};

// ===== In Header =====
            float xPosition = margin;

            for (int col = 0; col < model.getColumnCount(); col++) {

                content.beginText();
                content.setFont(font, 10);
                content.newLineAtOffset(xPosition, yPosition);
                content.showText(model.getColumnName(col));
                content.endText();

                xPosition += columnWidths[col];
            }

            yPosition -= rowHeight;

// ===== In Data =====
            for (int row = 0; row < model.getRowCount(); row++) {

                xPosition = margin;

                for (int col = 0; col < model.getColumnCount(); col++) {

                    String text = model.getValueAt(row, col).toString();

                    content.beginText();
                    content.setFont(font, 10);
                    content.newLineAtOffset(xPosition, yPosition);
                    content.showText(text);
                    content.endText();

                    xPosition += columnWidths[col];
                }

                yPosition -= rowHeight;
            }

            content.close();
            document.save(path);

            File file = new File(path);

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}