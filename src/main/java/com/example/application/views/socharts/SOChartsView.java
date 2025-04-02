package com.example.application.views.socharts;

import com.storedobject.chart.*;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import java.util.Random;

@PageTitle("SOCharts")
@Route("so-charts")
@Menu(order = 1, icon = LineAwesomeIconUrl.FILE)
public class SOChartsView extends VerticalLayout {
    public SOChartsView() {
        SOChart soChart = new SOChart();
        soChart.setSize("100%", "400px");

        CategoryData xValues = new CategoryData("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep");
        Data yValues = new Data(10, 41, 35, 51, 49, 62, 69, 91, 148);

        xValues.setName("X Values");
        yValues.setName("Random Values");

        LineChart lineChart = new LineChart(xValues, yValues);
        lineChart.setName("Sales");

        XAxis xAxis = new XAxis(DataType.CATEGORY);
        YAxis yAxis = new YAxis(DataType.NUMBER);
        RectangularCoordinate rc = new RectangularCoordinate(xAxis, yAxis);
        lineChart.plotOn(rc);

        soChart.add(lineChart, new Title("Sample Line Chart"));

        SOChart soChart2 = new SOChart();
        soChart2.setSize("100%", "400px");

        CategoryData labels = new CategoryData("Japan", "Canada", "UK", "Nauru", "USA", "China");
        Data data = new Data(400, 430, 448, 470, 1100, 1500);

        BarChart barChart = new BarChart(labels, data);
        barChart.setName("Population");
        RectangularCoordinate rc2 = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        barChart.plotOn(rc2);

        Toolbox toolbox = new Toolbox();
        toolbox.addButton(new Toolbox.Download(), new Toolbox.Zoom());

        soChart2.add(toolbox, barChart, new Title("Sample Bar Chart"));

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.add(soChart, soChart2);

        SOChart soChart3 = new SOChart();
        soChart3.setSize("100%", "500px");

        CategoryData labels2 = new CategoryData(
                "Japan", "Canada", "United States", "China", "Germany", "France", "Italy", "Ukraine", "United Kingdom", "Egypt",
                "Nigeria", "Turkey", "Australia", "Brazil", "India", "Mexico", "Russia", "Spain", "Argentina", "South Korea"
                ,"Afghanistan", "Ivory Coast", "Azerbaijan", "Angola", "Andorra", "Armenia", "Bahrain", "Barbados", "Belgium", "Bhutan",
                "Finland", "Cyprus", "Austria", "Brunei", "Colombia", "Chile", "Romania", "Gabon", "Iceland", "Indonesia"
        );
        Random random = new Random();
        Data population = new Data();
        Data seniorCitizens = new Data();

        for (int i = 0; i < labels2.size(); i++) {
            population.add(random.nextInt(2000));
            seniorCitizens.add(random.nextInt(1200));
            Number totalCitizens = population.get(i);
            Number totalSeniorCitizens = seniorCitizens.get(i);
            if(totalSeniorCitizens.intValue() > totalCitizens.intValue()){
                population.set(i, totalSeniorCitizens);
                seniorCitizens.set(i, totalCitizens);
            }
        }


        BarChart barChart2 = new BarChart(labels2, population);
        barChart2.setName("Population");
        RectangularCoordinate rcBarChart2 = new RectangularCoordinate(new XAxis(DataType.CATEGORY), new YAxis(DataType.NUMBER));
        barChart2.plotOn(rcBarChart2);

        BarChart barChart3 = new BarChart(labels2, seniorCitizens);
        barChart3.setName("Senior Citizens");
        barChart3.plotOn(rcBarChart2);

        soChart3.add(new Title("Population & Senior Citizens of " + labels2.size() +" Countries"));
        soChart3.add(barChart2, barChart3);

        SOChart soChart4 = new SOChart();
        soChart4.setSize("100%", "500px");

        CategoryData years = new CategoryData(
                "2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009", "2010"
                ,"2011", "2012", "2013", "2014", "2015", "2016", "2017", "2018", "2019", "2020"
                ,"2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030"
                ,"2031", "2032", "2033", "2034", "2035", "2036", "2037", "2038", "2039", "2040"
        );
        Data rainfall = new Data();
        Data harvest = new Data();

        for (int i = 0; i < years.size(); i++) {
            rainfall.add(random.nextInt(160));
            harvest.add(random.nextInt(100));
            Number raining = rainfall.get(i);
            Number harvesting = harvest.get(i);
            if(harvesting.intValue() > raining.intValue()) {
                rainfall.set(i, harvesting);
                harvest.set(i, raining);
            }
        }

        LineChart lineChart2 = new LineChart(years, rainfall);
        lineChart2.setName("Rainfall(Meters)");
        RectangularCoordinate rcLineChart2 = new RectangularCoordinate(xAxis, yAxis);
        lineChart2.plotOn(rcLineChart2);

        LineChart lineChart3 = new LineChart(years, harvest);
        lineChart3.setName("Rainwater Harvesting(Meters)");
        lineChart3.plotOn(rcLineChart2);

        soChart4.add(new Title("Rainfall & Rainwater Harvesting in "+years.size()+" yrs"));
        soChart4.add(toolbox, lineChart2, lineChart3);

        setAlignItems(Alignment.CENTER);
        add(horizontalLayout, soChart3, soChart4);
    }
}
