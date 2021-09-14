/*
 * Classe Gerador Grafico
 * Gera um Grafico de Linhas
*/

package gerentederede;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.Color;
import org.jfree.chart.plot.CategoryPlot;

public class GeradorGrafico {

    public GeradorGrafico() {
    }

    public BufferedImage gerarGraficoLinha(String tituloGrafico, String tituloEixoX,
                             String tituloEixoY, ArrayList arrayValores) throws Exception {
        BufferedImage buf = null;
        try {
            DefaultCategoryDataset defaultCategoryDataset = new DefaultCategoryDataset();
            Iterator iterator = arrayValores.iterator();
            while (iterator.hasNext()) {
                ModeloGraficoItem modelo = (ModeloGraficoItem) iterator.next();
                defaultCategoryDataset.addValue(modelo.getValor(),
                                     modelo.getTaxa(), modelo.getTime());
            }
            JFreeChart chart = ChartFactory.createLineChart(tituloGrafico, tituloEixoX,
                             tituloEixoY, defaultCategoryDataset, PlotOrientation.VERTICAL,
                             false, false, false);
            chart.setBorderVisible(true);
            chart.setBorderPaint(Color.black);
            CategoryPlot categoryplot = chart.getCategoryPlot();
            categoryplot.setDomainGridlinesVisible(true);
            buf = chart.createBufferedImage(800, 650);
        } catch (Exception e) {
            throw new Exception(e);
        }
        return buf;
    }
}