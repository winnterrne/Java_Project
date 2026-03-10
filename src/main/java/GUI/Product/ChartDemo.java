// package GUI.Product;
// import java.util.*;

// import org.knowm.xchart.*;
// import org.knowm.xchart.style.Styler.ChartTheme;
// import org.knowm.xchart.CategorySeries.CategorySeriesRenderStyle;

// public class ChartDemo {
//     public static void main(String[] args) throws Exception {
//         List<String> thang = Arrays.asList("Th1", "Th2", "Th3", "Th4", "Th5");
//         List<Double> doanhThuA = Arrays.asList(45.5, 78.0, 120.3, 95.7, 150.2);
//         List<Double> doanhThuB = Arrays.asList(30.0, 60.0, 90.0, 110.0, 130.0);

//         CategoryChart chart = new CategoryChartBuilder()
//                 .width(900)
//                 .height(600)
//                 .title("Thống kê Doanh Thu - Các kiểu khác nhau")
//                 .xAxisTitle("Tháng")
//                 .yAxisTitle("Triệu VND")
//                 .theme(ChartTheme.Matlab)
//                 .build();

//         chart.getStyler().setToolTipsEnabled(true);
//         chart.getStyler().setXAxisLabelRotation(45);

//         chart.addSeries("Sản phẩm A (Bar)", thang, doanhThuA)
//              .setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Bar);

//         chart.addSeries("Sản phẩm B (Line)", thang, doanhThuB)
//              .setChartCategorySeriesRenderStyle(CategorySeriesRenderStyle.Line);


//         new SwingWrapper<CategoryChart>(chart).displayChart();

//         System.out.println("Biểu đồ đa kiểu đã mở!");
//     }
// }