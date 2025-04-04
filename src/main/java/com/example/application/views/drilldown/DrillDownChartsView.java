package com.example.application.views.drilldown;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.builder.EventsBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
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
    private ApexCharts apexChart = new ApexCharts();
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

        createApexCharts(categories, salesData, title, true);

        backButton = new Button("Back", e ->resetChart());
        backButton.setVisible(false);

        add(backButton, apexChart);
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
                + "element.$server.drillDown(chartContext.w.globals.labels[config.dataPointIndex]);"
                + " }", id
        );
    }

    private ApexCharts createApexCharts(String[] categories, Double[] data, String title, Boolean horizontal) {
        String chartId = "my-chart";
        apexChart.getElement().setAttribute("id", chartId);

        apexChart.setChart(ChartBuilder.get()
                .withType(Type.BAR)
                .withEvents(EventsBuilder.get().withClick(getCategory())
                        .build())
                .build());

        apexChart.setPlotOptions(PlotOptionsBuilder.get()
                .withBar(BarBuilder.get()
                        .withHorizontal(horizontal)
                        .build())
                .build());

        apexChart.setXaxis(XAxisBuilder.get().withCategories(categories).withTickPlacement(TickPlacement.ON).build());
        apexChart.setSeries(new Series<>("Sales", data));
        apexChart.setTitle(TitleSubtitleBuilder.get().withText(title).build());

        apexChart.setDataLabels(DataLabelsBuilder.get().withEnabled(true).build());
        apexChart.setHeight(400, Unit.PIXELS);

        return apexChart;
    }

    @ClientCallable
    private void drillDown(String category) {
        System.out.println("Category: " + category);
        Double[] drillDownValues = drillDownData.get(category);
        String[] subCategoryNames = subCategories.get(category);
        System.out.println("SubCategories: " + Arrays.toString(subCategoryNames));

        if (drillDownValues != null &&subCategoryNames != null) {
            System.out.println("Drilling down");
            apexChart = createApexCharts(subCategoryNames, drillDownValues, category + " Sales", false);
            apexChart.updateConfig();
            backButton.setVisible(true);
        }

        System.out.println();
    }

    private void resetChart() {
        String[] categories = {"Electronics", "Clothing", "Groceries"};
        Double[] salesData = {5000.0, 3000.0, 7000.0};
        createApexCharts(categories, salesData, "Category Sales", true);
        apexChart.updateConfig();
        backButton.setVisible(false);
    }
}