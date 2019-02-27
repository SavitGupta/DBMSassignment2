import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class ConstructGraph extends ApplicationFrame
{
	public ConstructGraph(final String title)
	{
		super(title);
		final XYSeries series = new XYSeries("Throughput on given number of threads");
		series.add(1, 50);
		series.add(2, 60);
		series.add(3, 70);
		series.add(4, 80);
		series.add(5, 90);
		final XYSeriesCollection data = new XYSeriesCollection(series);
		final JFreeChart chart = ChartFactory.createXYLineChart("Throughput Vs. Threads", "Threads", "Throughput", data, PlotOrientation.VERTICAL, true, true, false);
		final ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(750, 450));
		setContentPane(chartPanel);
	}
	
	public static void main(final String[] args)
	{
		final ConstructGraph demo = new ConstructGraph("Throughput Vs. Threads");
		demo.pack();
		RefineryUtilities.centerFrameOnScreen(demo);
		demo.setVisible(true);
	}
}