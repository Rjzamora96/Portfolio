package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controller {
    @FXML
    private LineChart<NumberAxis, NumberAxis> giraffe;
    @FXML
    private TextField function;
    @FXML
    private Text error;
    @FXML
    private NumberAxis xAxis;
    @FXML
    private NumberAxis yAxis;
    @FXML
    protected void handleGraphButtonAction(ActionEvent event)
    {
        DrawChart();
    }
    @FXML
    protected void handleClearButtonAction(ActionEvent event){ giraffe.getData().clear(); }
    @FXML
    protected void handleMinusButtonAction(ActionEvent event)
    {
        if(xAxis.getLowerBound() != -5) {
            xAxis.setLowerBound(xAxis.getLowerBound()+5);
            xAxis.setUpperBound(xAxis.getUpperBound()-5);
            xAxis.setTickUnit(xAxis.getTickUnit() - 1);
            yAxis.setLowerBound(yAxis.getLowerBound()+5);
            yAxis.setUpperBound(yAxis.getUpperBound()-5);
            yAxis.setTickUnit(yAxis.getTickUnit() - 1);
        }
    }
    @FXML
    protected void handlePlusButtonAction(ActionEvent event)
    {
        if(xAxis.getLowerBound() != -40) {
            xAxis.setLowerBound(xAxis.getLowerBound()-5);
            xAxis.setUpperBound(xAxis.getUpperBound()+5);
            xAxis.setTickUnit(xAxis.getTickUnit() + 1);
            yAxis.setLowerBound(yAxis.getLowerBound()-5);
            yAxis.setUpperBound(yAxis.getUpperBound()+5);
            yAxis.setTickUnit(yAxis.getTickUnit() + 1);
        }
    }
    public void DrawChart()
    {
        String input = function.getText();
        String inputCpy = function.getText();
        ArrayList coeffs = new ArrayList<Double>();
        ArrayList degrees = new ArrayList<Double>();
        double constant = 0;
        int numOfConstants = 0;
        int numOfMatches = 0;
        if(input != null) {
            Pattern checkExternal = Pattern.compile("[a-wA-Wy-zY-Z]+");
            Matcher matchExternal = checkExternal.matcher(input);
            if(matchExternal.find())
            {
                error.setVisible(true);
                return;
            }
            Pattern p = Pattern.compile("(-?\\s*\\b\\d+)?\\s*[xX]\\s*\\s*(\\^\\s*-?\\s*\\d+\\b)?");
            Matcher m = p.matcher(input);
            while (m.find())
            {
                numOfMatches++;
                inputCpy = inputCpy.replace(m.group(0), "");
                coeffs.add((m.group(1) != null) ? Double.parseDouble(m.group(1).replaceAll("\\s+", "")) : 1);
                String test = m.group(2);
                if(m.group(2) != null) test = m.group(2).replaceAll("\\^+","");
                degrees.add((m.group(2) != null) ? Double.parseDouble(test.replaceAll("\\s+", "")) : 1);
            }
            Pattern p2 = Pattern.compile("(-?\\s*\\b\\d*[^Xx]\\b)");
            Matcher m2 = p2.matcher(inputCpy);
            while(m2.find()) {
                if(m2.group(1) != null) constant += Double.parseDouble(m2.group(1).replaceAll("\\s+", ""));
                numOfConstants++;
            }
        }
        else
        {
            error.setVisible(true);
            return;
        }
        if(numOfMatches == 0 && numOfConstants == 0)
        {
            error.setVisible(true);
            return;
        }
        XYChart.Series series = new XYChart.Series<>();
        for(double x = -40; x <= 40; x+=0.1)
        {
            double y = 0;
            for(int i = 0; i < numOfMatches; i++)
            {
                y += (double)coeffs.get(i) * Math.pow(x, (double)degrees.get(i));
            }
            y += constant;
            series.getData().add(new XYChart.Data(x, y));
        }
        giraffe.getData().add(series);
        if(error.isVisible()) error.setVisible(false);
    }
}
