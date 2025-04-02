package com.example.application.views.drilldown;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.States;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.EventsBuilder;
import com.github.appreciated.apexcharts.config.xaxis.TickPlacement;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@PageTitle("DrillCharts")
@Route("drill-charts")
@Menu(order = 2, icon = LineAwesomeIconUrl.REACT)
public class DrillDownChartsView extends VerticalLayout {
    private ApexCharts apexChart;
    private final Button backButton;

    private final Map<String, Double[]> drillDownData = new HashMap<>();
    private final Map<String, String[]> subCategories = new HashMap<>();

    public DrillDownChartsView() {
        String[] categories = {"Electronics", "Clothing", "Groceries"};
        Double[] salesData = {5000.0, 3000.0, 7000.0};
        String title = "Category Sales";

        drillDownData.put("Electronics", new Double[]{1500.0, 2000.0, 1500.0});
        drillDownData.put("Clothing", new Double[]{1000.0, 800.0, 1200.0});
        drillDownData.put("Groceries", new Double[]{2000.0, 3000.0, 2000.0});

        subCategories.put("Electronics", new String[]{"Mobiles", "Laptops", "TVs"});
        subCategories.put("Clothing", new String[]{"Shirts", "Jeans", "Jackets"});
        subCategories.put("Groceries", new String[]{"Vegetables", "Snacks", "Beverages"});

        apexChart = createApexCharts(categories, salesData, title);

        backButton = new Button("Back", e ->resetChart());
        backButton.setVisible(false);

        add(apexChart, backButton);
    }

    private String getCategory() {
        final String id = getId().orElseGet(() -> {
           final String newId = getClass().getSimpleName();
           setId(newId);
           return newId;
        });
        return String.format("function(event, chartContext, config, globals) { "
                + "var element = document.getElementById(\"%s\"); "
                + "console.log(chartContext.w);"
                + "element.$server.drillDown(chartContext.w.globals.categoryLabels[config.dataPointIndex]);"
                + " }", id
        );
    }

    private ApexCharts createApexCharts(String[] categories, Double[] data, String title) {
        ApexCharts chart = new ApexCharts();

        String chartId = "my-chart";
        chart.getElement().setAttribute("id", chartId);

        chart.setChart(ChartBuilder.get()
                .withType(Type.BAR)
//                        .withEvents(EventsBuilder.get().withClick("function(event, chartContext, seriesIndex) {" +
//                                "const category = chartContext.w.config.xaxis.categories[seriesIndex.dataPointIndex];" +
//                                "console.log('Clicked category: ', category);" +
//                                "var element = document.getElementById('my-chart'); " +
//                                "element.$server.drillDown(category);" +
//                                "}").build())
                        .withEvents(EventsBuilder.get().withClick(getCategory()).build())
                .build());

        chart.setStates(new States());
        chart.setXaxis(XAxisBuilder.get().withCategories(categories).withTickPlacement(TickPlacement.ON).build());
        chart.setSeries(new Series<>("Sales", data));
        chart.setTitle(TitleSubtitleBuilder.get().withText(title).build());

        chart.setDataLabels(DataLabelsBuilder.get().withEnabled(true).build());
        chart.setHeight(400, Unit.PIXELS);

        return chart;
    }

    @ClientCallable
    private void drillDown(String category) {
        System.out.println("Category: " + category);
        Double[] drillDownValues = drillDownData.get(category);
        String[] subCategoryNames = subCategories.get(category);
        System.out.println("SubCategories: " + Arrays.toString(subCategoryNames));

        if (drillDownValues != null &&subCategoryNames != null) {
            remove(apexChart);
            System.out.println("Drilling down");
            apexChart = createApexCharts(subCategoryNames, drillDownValues, category + " Sales");
            backButton.setVisible(true);
            add(apexChart);
        }

        System.out.println();
    }

    private void resetChart() {
        remove(apexChart);
        String[] categories = {"Electronics", "Clothing", "Groceries"};
        Double[] salesData = {5000.0, 3000.0, 7000.0};
        apexChart = createApexCharts(categories, salesData, "Category Sales");
        backButton.setVisible(false);
        add(apexChart);
    }
}