package report;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import properties.LoadFrameworkProp;
import businessFunction.ConvertCurrency;
import utilities.excelUtilis;

/**
 * Creates a customized
 */
public class TestReport {

	/**
	 * : Generates Report for Current Test Execution Run
	 * 
	 * @throws Exception
	 */

	@Test(groups = { "Results Report Generation" })
	public void testReport() throws Exception {

		ParsingTestResultXML result = new ParsingTestResultXML();
		AreaDataTable areaDataTable = new AreaDataTable();
		Object[][] datatable = areaDataTable.generateAreaDataTable(result);
		LoadFrameworkProp frameProp = new LoadFrameworkProp();
		Date dNow = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		SimpleDateFormat ft_filename = new SimpleDateFormat("ddMMyyyyhhmmss");
		excelUtilis.setExcelFile("D:\\Nuttan_Eclipse\\Syngenta_Report\\Excels\\Book2.xls", "TestData");
		PrintWriter printWriter = new PrintWriter(new FileWriter(
				frameProp.getHtmlReport() + "AutomationTestReport_" + ft_filename.format(dNow) + ".html"));
		printWriter.println(
				"<h2 align=\"center\"><font face=\"Segoe UI\"><u>Critical Flows Execution Automation Report WestPac-Newzealand "
						+ ft.format(dNow) + "</u></font></h2>");

		printWriter.println("<TABLE align=\"center\" ><font face=\"Segoe UI\"><TR><TD  width=\"50%\">");
		printWriter.println("<h4><u>COMPLETE TEST EXECUTION STATUS</u></h4>");
		printWriter.println("<table BORDER=2 align=\"center\">"
				+ "<tbody><tr><th>Total Tests</th><th>No.of Scripts Passed</th><th>No.of Scripts Skipped</th><th>No.of Scripts Failed</th><th>Total Time(sec) taken to Complete Test Run</th></tr><tr>	"
				+ "<th colspan=\"6\">Suite</th></tr><tr><td class=\"num\" align=\"center\">" + result.gettotalcount()
				+ "</td><td class=\"num\" align=\"center\">" + result.getpasscount()
				+ "</td><td class=\"num\" align=\"center\">" + result.getskipcount().toString() + "</td>"
				+ "<td class=\"num attn\" align=\"center\">" + result.getfailcount()
				+ "</td><td class=\"num\" align=\"center\">"
				+ Double.parseDouble(result.getduration().toString()) / 1000
				+ "</td></tr></tbody></table></body></html>");
		// Table with Test Status Count related w.r.t Browsers
		printWriter.println("</TD><TD>");
		printWriter.println("<h4><u>DETAILED TEST EXECUTION STATUS</u></h4>");
		printWriter.println(
				"<table border=2><tr><th>OS and Browser</th><th>Total No.of Test Scripts</th><th>No.of Scripts Passed</th><th>No.of Scripts Failed</th><th>No.of Scripts Skipped</th></tr>");
		for (int i = 0; i < result.getbrowserstatus().size(); i = i + 5) {
			printWriter.println("<tr><td align=\"center\">" + result.getbrowserstatus().get(i) + "<td align=\"center\">"
					+ result.getbrowserstatus().get(i + 1) + "<td align=\"center\">"
					+ result.getbrowserstatus().get(i + 2) + "<td align=\"center\">"
					+ result.getbrowserstatus().get(i + 3) + "<td align=\"center\">"
					+ result.getbrowserstatus().get(i + 4));
		}

		printWriter.println("</table><br>");
		printWriter.println("</TD></TR>");
		printWriter.println("<TR><TD>");
		printWriter
				.println("<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script>"
						+ "<script type=\"text/javascript\">"
						+ "google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});"
						+ "google.setOnLoadCallback(drawChart);" + "function drawChart() {"
						+ "var data = google.visualization.arrayToDataTable([");
		printWriter.println("['Area', 'Pass', 'Fail', 'Skip'],");
		for (int i = 0; i < datatable.length; i++) {
			printWriter.println("['" + datatable[i][0] + "'" + ", " + datatable[i][1] + ", " + datatable[i][2] + ", "
					+ datatable[i][3] + "]");
			if (!(i == (datatable.length - 1)))
				printWriter.print(",");
		}
		printWriter.println("]);"
				+ "var chart = new google.visualization.BarChart(document.getElementById('chart_div'));"
				+ "chart.draw(data, {width: 600, height: 900, min: 0, isStacked: true, orientation: 'vertical', colors:['green','red','yellow']});"
				+ "}" + "</script>" + "</head>" + "<body>" + "<div id=\"chart_div\"></div>" + "</body>" + "</html>");
		printWriter.println("</TD><TD>");
		printWriter.println(
				"<html><head><script type=\"text/javascript\" src=\"https://www.google.com/jsapi\"></script><script type=\"text/javascript\">google.load(\"visualization\", \"1\", {packages:[\"corechart\"]});google.setOnLoadCallback(drawChart);function drawChart() {var data = google.visualization.arrayToDataTable([['TestStatus', 'Count'],['Pass',"
						+ result.getpasscount().toString() + "],['Fail'," + result.getfailcount().toString()
						+ "],['Skipped'," + result.getskipcount().toString()
						+ "]]);var options = {title: 'Pie Chart', colors: ['green','red','yellow']};var chart = new google.visualization.PieChart(document.getElementById('piechart'));chart.draw(data, options);}</script></head><body align=\"centre\"><div align=\"center\"><div id=\"piechart\"  style=\"width: 600px; height: 500px; float:right \"></div></div></body></html>");
		printWriter.println("</TD></font></TABLE>");

		// Error Message
		if (Integer.parseInt(result.getfailcount().toString()) > 0
				|| Integer.parseInt(result.getskipcount().toString()) > 0
				|| Integer.parseInt(result.getpasscount().toString()) > 0) {
			printWriter.println("<h4><u>TEST EXECUTION DETAILS</u></h4>");
			printWriter.println(
					"<TABLE BORDER=1><font face=\"Segoe UI\"><TR><TH>TestName<TH>Test Description<TH>Status<TH>Duration(sec)<TH>ExpectedAccess<TH>ActualAccess<TH></TR></font>");
			// printWriter.println("<TABLE
			// BORDER=2><TR><TH>Browser</TH><TH>TestName<TH>Test
			// Description<TH>Status<TH>Error
			// Message<TH>Duration(sec)<TH>Screenshot</TR></font>");
			System.out.println(result.gettotalcount());
			for (int i = 0; i < Integer.parseInt(result.gettotalcount().toString()); i++) {
				if (result.getStatus().get(i).toString().equals("FAIL")
						|| result.getStatus().get(i).toString().equals("SKIP")
						|| result.getStatus().get(i).toString().equals("PASS")) {
					printWriter.println("<TR><TD>" + result.gettestmethodname().get(i) + "<TD>"
							+ result.getTestMethodDesc().get(i) + "<TD>" + result.getStatus().get(i) + "<TD>"
							+ Double.parseDouble((String) result.gettestrundduration().get(i)) / 1000 + "<TD>"
							+ result.getscreenShot().get(i) +"<TD>"+result.getaccess().get(i));
				}
			}
			printWriter.println("</TABLE></body>");
		}
		printWriter.close();
	}
}
