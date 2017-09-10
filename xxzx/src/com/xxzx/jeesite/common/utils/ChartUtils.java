package com.xxzx.jeesite.common.utils;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.Chart;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryAxis3D;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.ui.TextAnchor;

public class ChartUtils {
	// 获取相关类
	// private static XTQXDao xtqxDao =
	// SpringContextHolder.getBean(XTQXDao.class);
	/* public JFreeChart getPieChart(String pieName,DefaultPieDataset dpd ) { */
	/*
	 * XTQXService xtqxService = new XTQXService();
	 * 
	 * List<XTQX> xList = new ArrayList<XTQX>();
	 * System.out.println("xlist数量"+xList.size()); xList =
	 * xtqxDao.findAllList(new XTQX());
	 */

	// DefaultPieDataset dpd = new DefaultPieDataset();
	/*
	 * XTQX xtqx = new XTQX(); for (int i = 0; i < xList.size(); i++) { xtqx =
	 * xList.get(i); dpd.setValue(xtqx.getQxmc(), i+1);
	 * 
	 * }
	 */
	/*
	 * JFreeChart pieChart = ChartFactory.createPieChart(pieName, dpd, true,
	 * false, false);
	 * 
	 * Font font = new Font("宋体", Font.BOLD, 16); TextTitle title = new
	 * TextTitle(pieName, font); pieChart.setTitle(title);
	 * 
	 * PiePlot plot = (PiePlot) pieChart.getPlot(); plot.setLabelGenerator(new
	 * StandardPieSectionLabelGenerator( "{0}所占比例：{1}({2})",
	 * NumberFormat.getNumberInstance(), new DecimalFormat("0.00%")));
	 * plot.setLabelFont(font); LegendTitle legendTitle = pieChart.getLegend();
	 * legendTitle.setItemFont(font);
	 * 
	 * return pieChart; }
	 */

	public String getPieChartUrl(String pieName, DefaultPieDataset dpd,
			HttpServletRequest request, HttpSession session) {
		/*
		 * ChartUtils chartUtils = new ChartUtils(); JFreeChart pieChart =
		 * chartUtils.getPieChart();
		 */
		JFreeChart pieChart = ChartFactory.createPieChart(pieName, dpd, true,
				false, false);

		Font font = new Font("宋体", Font.BOLD, 16);
		TextTitle title = new TextTitle(pieName, font);
		pieChart.setTitle(title);

		PiePlot plot = (PiePlot) pieChart.getPlot();
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:{1}({2})", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		plot.setLabelFont(font);
		plot.setBackgroundPaint(Color.white);
	
		//int jdqk =dpd.getValue().size();
	/*	if(dpd == null){
			plot.setOutlinePaint(Color.white);
		}else{
			plot.setOutlinePaint(Color.black);
		}*/
		//plot.setOutlinePaint(Color.white);
		
		plot.setShadowPaint(Color.white);
		//plot.setLabelOutlineStroke(Color.white);
		plot.setBaseSectionOutlinePaint(Color.white);
		LegendTitle legendTitle = pieChart.getLegend();
		legendTitle.setItemFont(font);
		String fileName = null;
		try {
			fileName = ServletUtilities.saveChartAsPNG(pieChart, 500, 400,
					session);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = request.getContextPath() + "/DisplayChart?filename="
				+ fileName;
		return url;

	}

	public String getCategoryUrl(String categoryName, String cName,
			String dName, DefaultCategoryDataset dataset,
			HttpServletRequest request, HttpSession session) {

		JFreeChart categoryChart = ChartFactory.createBarChart3D(categoryName,
				cName, dName, dataset, PlotOrientation.VERTICAL, true, true,
				false);
		Font font = new Font("宋体", Font.BOLD, 14);
		TextTitle title = new TextTitle(categoryName, font);
		categoryChart.setTitle(title);
		/*
		 * TextTitle title1 = new TextTitle(cName, font);
		 * categoryChart.set(title1);
		 */
		categoryChart.getLegend().setItemFont(font);

		CategoryPlot plot = (CategoryPlot) categoryChart.getCategoryPlot();
		// 设置网格背景颜色
		plot.setBackgroundPaint(Color.white);
		// 设置网格竖线颜色
		plot.setDomainGridlinePaint(Color.pink);
		// 设置网格横线颜色
		//plot.setRangeGridlinePaint(Color.black);
		//设置柱的透明度
		plot.setForegroundAlpha(0.9f);
		// 设置x,y轴字体
		CategoryAxis axis = plot.getDomainAxis();
		axis.setLabelFont(font);
		axis.setTickLabelFont(font);

		org.jfree.chart.axis.ValueAxis rAxis = plot.getRangeAxis();

		rAxis.setLabelFont(font);
		rAxis.setTickLabelFont(font);
		rAxis.setLowerBound(0);
		boolean isNoData = true; 
		int rowInt=dataset.getRowKeys().size();
		int columInt= dataset.getColumnKeys().size();
		for (int i = 0; i < rowInt; i++) {
			for (int j = 0; j < columInt; j++) {
				
				Number tmp = dataset.getValue(i, j);
				int tmpInt = tmp.intValue();
				if(tmpInt !=0){
					isNoData=false;
				}
			}
		}
		if (isNoData) {
			rAxis.setUpperBound(100);
		}
		// rAxis.setUpperBound(10et000);
		// rAxis.setUpperBound(100);
		// 设置原点
		/*
		 * NumberAxis vn = (NumberAxis)plot.getRangeAxis();
		 * vn.setAutoRangeIncludesZero(true);
		 * plot.setRangeAxis((org.jfree.chart.axis.ValueAxis)vn);
		 */
		// plot.setLabelFont(font);
		/*
		 * LegendTitle legendTitle = categoryChart.getLegend();
		 * legendTitle.setItemFont(font);
		 */

		// 显示每个柱的值，并修改该数值的字体属性
		BarRenderer3D renderer = new BarRenderer3D();
		renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());

		renderer.setBaseItemLabelsVisible(true);

		// 默认数字显示在柱子中，通过下两句可调整数字的显示
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		renderer.setItemLabelAnchorOffset(8D);

		renderer.setItemMargin(0.2);
		plot.setRenderer(renderer);

		String filename = null;
		try {
			filename = ServletUtilities.saveChartAsJPEG(categoryChart, 1000,
					300, session);
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		String graphUrl = request.getContextPath() + "/DisplayChart?filename="
				+ filename;

		return graphUrl;  

	}

	public JFreeChart getBarChart() {

		return null;

	}

}
