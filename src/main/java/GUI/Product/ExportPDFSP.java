package GUI.Product;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.File;
import java.io.IOException;

public class ExportPDFSP {
    public static void exportTableToPDF(JTable table, String path, String title) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream content = new PDPageContentStream(document, page);

            PDType0Font font = PDType0Font.load(document, new File("C:/Windows/Fonts/arial.ttf"));

            // Tiêu đề
            content.beginText();
            content.setFont(font, 16);
            content.newLineAtOffset(200, 800);
            content.showText(title);
            content.endText();

            // Bảng
            TableModel model = table.getModel();
            float yPosition = 750;
            float rowHeight = 25;
            float margin = 50;

            int colCount = model.getColumnCount();
            float totalWidth = PDRectangle.A4.getWidth() - 2 * margin;
            float baseWidth = totalWidth / colCount;
            float[] columnWidths = new float[colCount];
            for (int i = 0; i < colCount; i++) {
                columnWidths[i] = baseWidth;
            }

            // In header
            float x = margin;
            for (int col = 0; col < colCount; col++) {
                content.beginText();
                content.setFont(font, 11);
                content.newLineAtOffset(x, yPosition);
                content.showText(model.getColumnName(col));
                content.endText();
                x += columnWidths[col];
            }

            yPosition -= rowHeight;

            // In dữ liệu
            for (int row = 0; row < model.getRowCount(); row++) {
                x = margin;
                for (int col = 0; col < colCount; col++) {
                    String text = model.getValueAt(row, col) != null ? model.getValueAt(row, col).toString() : "";

                    content.beginText();
                    content.setFont(font, 10);
                    content.newLineAtOffset(x, yPosition);
                    content.showText(text);
                    content.endText();

                    x += columnWidths[col];
                }
                yPosition -= rowHeight;

                if (yPosition < 50) {
                    content.close();
                    page = new PDPage(PDRectangle.A4);
                    document.addPage(page);
                    content = new PDPageContentStream(document, page);
                    yPosition = 750;
                }
            }

            content.close();
            document.save(path);

            JOptionPane.showMessageDialog(null, "Xuất PDF thành công!\nFile lưu tại: " + path);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi khi xuất PDF: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
}