package com.example.application.views.apexcharts;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.Chart;
import com.github.appreciated.apexcharts.config.TitleSubtitle;
import com.github.appreciated.apexcharts.config.XAxis;
import com.github.appreciated.apexcharts.config.YAxis;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Events;
import com.github.appreciated.apexcharts.config.chart.Toolbar;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.Zoom;
import com.github.appreciated.apexcharts.config.chart.builder.EventsBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.SelectionBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ToolbarBuilder;
import com.github.appreciated.apexcharts.config.chart.builder.ZoomBuilder;
import com.github.appreciated.apexcharts.config.chart.selection.builder.XaxisBuilder;
import com.github.appreciated.apexcharts.config.chart.toolbar.AutoSelected;
import com.github.appreciated.apexcharts.config.chart.toolbar.Tools;
import com.github.appreciated.apexcharts.config.chart.toolbar.builder.ToolsBuilder;
import com.github.appreciated.apexcharts.config.chart.zoom.ZoomType;
import com.github.appreciated.apexcharts.config.legend.Position;
import com.github.appreciated.apexcharts.config.subtitle.Align;
import com.github.appreciated.apexcharts.config.xaxis.TickPlacement;
import com.github.appreciated.apexcharts.config.xaxis.Title;
import com.github.appreciated.apexcharts.config.xaxis.XAxisType;
import com.github.appreciated.apexcharts.config.xaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Coordinate;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@PageTitle("ApexCharts")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.CHART_LINE_SOLID)
public class ApexChartsView extends VerticalLayout {

    public ApexChartsView() {
        Zoom zoom = new Zoom();
        zoom.setEnabled(true);
        zoom.setType(ZoomType.XY);
//        zoom.setAutoScaleYaxis(true);

        ApexCharts lineChart = new ApexCharts();
        Chart chart = new Chart();
        chart.setType(Type.LINE);

        XAxis xaxis = new XAxis();
        xaxis.setTitle(TitleBuilder.get().withText("Months").build());
        List<String> categories = Arrays.asList("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep");
        xaxis.setCategories(categories);
//        System.out.println(xaxis.getPosition());

        YAxis yaxis = new YAxis();
        yaxis.setTitle(com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder.get().withText("Sales in Million").build());

        lineChart.setXaxis(xaxis);
        lineChart.setSeries(new Series<>("Sales", 10, 41, 35, 51, 49, 62, 69, 91, 148));
        lineChart.setYaxis(new YAxis[]{yaxis});

        Events events = new Events();
        events.setMarkerClick("function(event, chartContext, seriesIndex) {" +
                "const category = chartContext.w.config.series;" +
                "console.log('Clicked category: ', category);" +
                "}");

        chart.setEvents(events);

        lineChart.setChart(chart);
        lineChart.setHeight(400, Unit.PIXELS);

        TitleSubtitle lineTitle = new TitleSubtitle();
        lineTitle.setText("Apex Line Chart");
        lineTitle.setAlign(Align.CENTER);
        lineChart.setTitle(lineTitle);


        ApexCharts barChart = new ApexCharts();

        barChart.setChart(ChartBuilder.get().withType(Type.BAR)
                .withEvents(EventsBuilder.get().withClick("function(event, chartContext, seriesIndex) {" +
                        "const category = seriesIndex.dataPointIndex;" +
                        "console.log('Clicked category: ', category);" +
                        "}").build())
                .withZoom(zoom)
                .build());

        barChart.setXaxis(XAxisBuilder.get().withCategories("South Korea", "Canada", "United Kingdom", "Netherlands", "United States", "China")
                .withTickPlacement(TickPlacement.ON)
                .build());
        barChart.setSeries(new Series<>("Population", 400, 430, 448, 470, 1100, 1500));
        barChart.setDataLabels(DataLabelsBuilder.get().withEnabled(true).build());

        barChart.setHeight(400, Unit.PIXELS);

        TitleSubtitle barTitle = new TitleSubtitle();
        barTitle.setText("Apex Bar Chart");
        barTitle.setAlign(Align.CENTER);
        barChart.setTitle(barTitle);


        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(lineChart, barChart);
        horizontalLayout.setWidthFull();

        Toolbar toolbar = new Toolbar();
        Tools tools = new Tools();
//        tools.setZoom("Zoom");
//        tools.setZoomin("Zoom In");
//        tools.setZoomout("Zoom Out");

        toolbar.setTools(tools);
        toolbar.setShow(true);
        toolbar.setAutoSelected(AutoSelected.PAN);



        String[] countries = {
                "Japan", "Canada", "United States", "China", "Germany", "France", "Italy", "Ukraine", "United Kingdom", "Egypt",
                "Nigeria", "Turkey", "Australia", "Brazil", "India", "Mexico", "Russia", "Spain", "Argentina", "South Korea"
                , "Afghanistan", "Ivory Coast", "Azerbaijan", "Angola", "Andorra", "Armenia", "Bahrain", "Barbados", "Belgium", "Bhutan",
                "Finland", "Cyprus", "Austria", "Brunei", "Colombia", "Chile", "Romania", "Gabon", "Iceland", "Indonesia"
        };
        ApexCharts barChart2 = new ApexCharts();
        barChart2.setChart(ChartBuilder.get().withType(Type.BAR)
                        .withEvents(EventsBuilder.get().withClick("function(event, chartContext, seriesIndex) {" +
                                "const category = chartContext.w.config.xaxis.categories[seriesIndex.dataPointIndex];" +
                                "console.log('Country clicked: ', category);" +
                                "}").build())
                .withZoom(zoom)
                        .withSelection(SelectionBuilder.get().withEnabled(true).withXaxis(XaxisBuilder.get().build()).build())
                                .withToolbar(toolbar)
                .build());
        barChart2.setXaxis(XAxisBuilder.get()
                .withType(XAxisType.CATEGORIES)
//                .withTickPlacement(TickPlacement.ON)
                .withCategories(countries)
                .build());

        Random random = new Random();
        Integer[] populationData = new Integer[countries.length];
        Integer[] seniorCitizens = new Integer[countries.length];

        for (int i = 0; i < countries.length; i++) {
            populationData[i] = random.nextInt(2000);
            seniorCitizens[i] = random.nextInt(1200);
            if(seniorCitizens[i] > populationData[i]) {
                int temp = seniorCitizens[i];
                seniorCitizens[i] = populationData[i];
                populationData[i] = temp;
            }
        }

        barChart2.setSeries(new Series<>("Population", populationData), new Series<>("Senior Citizens", seniorCitizens));
        barChart2.setLegend(LegendBuilder.get().withPosition(Position.TOP).build());
        barChart2.setTitle(TitleSubtitleBuilder.get().withText("Population & Senior citizens of "+countries.length+" countries").build());
        barChart2.setDataLabels(DataLabelsBuilder.get().withEnabled(false).build());

        barChart2.setHeight(500, Unit.PIXELS);
//      barChart2.setWidth(50, Unit.PERCENTAGE);

        ApexCharts lineChart2 = new ApexCharts();
        lineChart2.setChart(ChartBuilder.get().withType(Type.LINE).build());

        List<String> years = Arrays.asList(
                "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010"
                ,"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"
                ,"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"
                ,"2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"
        );
        lineChart2.setXaxis(XAxisBuilder.get().withCategories(years).build());

        Integer[] rainfallInMeter = new Integer[years.size()];
        Integer[] harvestInMeter = new Integer[years.size()];

        for (int i = 0; i < years.size(); i++) {
            rainfallInMeter[i] = random.nextInt(160);
            harvestInMeter[i] = random.nextInt(100);
            if(harvestInMeter[i] > rainfallInMeter[i]) {
                int temp = harvestInMeter[i];
                harvestInMeter[i] = rainfallInMeter[i];
                rainfallInMeter[i] = temp;
            }
        }
        lineChart2.setSeries(new Series<>("Rainfall(Meters)", rainfallInMeter),
                new Series<>("Rainwater Harvesting(Meters)", harvestInMeter));

        lineChart2.setHeight(500, Unit.PIXELS);
        lineChart2.setTitle(TitleSubtitleBuilder.get().withText("Rainfall & Rainwater Harvesting in "+years.size()+" yrs").withAlign(Align.LEFT).build());
        lineChart2.setWidth(90, Unit.PERCENTAGE);


        ApexCharts candleStickChart = new ApexCharts();
        candleStickChart.setChart(ChartBuilder.get().withType(Type.CANDLESTICK).build());

        candleStickChart.setSeries(new Series<>(
                new Coordinate<>(getISOString(1538778600000L), new BigDecimal("6629.81"), new BigDecimal("6650.5"), new BigDecimal("6623.04"), new BigDecimal("6633.33")),
                new Coordinate<>(getISOString(1538780400000L), new BigDecimal("6632.01"), new BigDecimal("6643.59"), new BigDecimal("6620"), new BigDecimal("6630.11")),
                new Coordinate<>(getISOString(1538782200000L), new BigDecimal("6630.71"), new BigDecimal("6648.95"), new BigDecimal("6623.34"), new BigDecimal("6635.65")),
                new Coordinate<>(getISOString(1538784000000L), new BigDecimal("6635.65"), new BigDecimal("6651"), new BigDecimal("6629.67"), new BigDecimal("6638.24")),
                new Coordinate<>(getISOString(1538785800000L), new BigDecimal("6638.24"), new BigDecimal("6640"), new BigDecimal("6620"), new BigDecimal("6624.47")),
                new Coordinate<>(getISOString(1538787600000L), new BigDecimal("6624.53"), new BigDecimal("6636.03"), new BigDecimal("6621.68"), new BigDecimal("6624.31")),
                new Coordinate<>(getISOString(1538789400000L), new BigDecimal("6624.61"), new BigDecimal("6632.2"), new BigDecimal("6617"), new BigDecimal("6626.02")),
                new Coordinate<>(getISOString(1538791200000L), new BigDecimal("6627"), new BigDecimal("6627.62"), new BigDecimal("6584.22"), new BigDecimal("6603.02")),
                new Coordinate<>(getISOString(1538793000000L), new BigDecimal("6605"), new BigDecimal("6608.03"), new BigDecimal("6598.95"), new BigDecimal("6604.01")),
                new Coordinate<>(getISOString(1538794800000L), new BigDecimal("6604.5"), new BigDecimal("6614.4"), new BigDecimal("6602.26"), new BigDecimal("6608.02")),
                new Coordinate<>(getISOString(1538796600000L), new BigDecimal("6608.02"), new BigDecimal("6610.68"), new BigDecimal("6601.99"), new BigDecimal("6608.91")),
                new Coordinate<>(getISOString(1538798400000L), new BigDecimal("6608.91"), new BigDecimal("6618.99"), new BigDecimal("6608.01"), new BigDecimal("6612")),
                new Coordinate<>(getISOString(1538800200000L), new BigDecimal("6612"), new BigDecimal("6615.13"), new BigDecimal("6605.09"), new BigDecimal("6612")),
                new Coordinate<>(getISOString(1538802000000L), new BigDecimal("6612"), new BigDecimal("6624.12"), new BigDecimal("6608.43"), new BigDecimal("6622.95")),
                new Coordinate<>(getISOString(1538803800000L), new BigDecimal("6623.91"), new BigDecimal("6623.91"), new BigDecimal("6615"), new BigDecimal("6615.67")),
                new Coordinate<>(getISOString(1538805600000L), new BigDecimal("6618.69"), new BigDecimal("6618.74"), new BigDecimal("6610"), new BigDecimal("6610.4")),
                new Coordinate<>(getISOString(1538807400000L), new BigDecimal("6611"), new BigDecimal("6622.78"), new BigDecimal("6610.4"), new BigDecimal("6614.9")),
                new Coordinate<>(getISOString(1538809200000L), new BigDecimal("6614.9"), new BigDecimal("6626.2"), new BigDecimal("6613.33"), new BigDecimal("6623.45")),
                new Coordinate<>(getISOString(1538811000000L), new BigDecimal("6623.48"), new BigDecimal("6627"), new BigDecimal("6618.38"), new BigDecimal("6620.35")),
                new Coordinate<>(getISOString(1538812800000L), new BigDecimal("6619.43"), new BigDecimal("6620.35"), new BigDecimal("6610.05"), new BigDecimal("6615.53"))
        ));
        candleStickChart.setXaxis(XAxisBuilder.get().withType(XAxisType.DATETIME).build());

        candleStickChart.setHeight(500, Unit.PIXELS);
        candleStickChart.setWidth(90, Unit.PERCENTAGE);

        setAlignItems(Alignment.CENTER);
        add(horizontalLayout, barChart2, lineChart2, candleStickChart);
    }

    public String getISOString(long l) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}