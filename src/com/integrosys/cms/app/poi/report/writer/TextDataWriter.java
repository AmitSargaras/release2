package com.integrosys.cms.app.poi.report.writer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.time.DateFormatUtils;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamDao;
import com.integrosys.cms.app.generalparam.bus.IGeneralParamEntry;
import com.integrosys.cms.app.poi.report.ReportDaoImpl;

public class TextDataWriter extends BaseReportWriter {

	private BufferedWriter bufferedWriter;
	private BufferedWriter bufferedWriterNew;
	private BufferedWriter bufferedWriterZip;

	private String delimiter;

	private String fileName;
	private String fileName2;
	private Boolean writeColumnHeading = false;
	private Integer rowNumber = 0;
	Map<String, Object> parameters = null;
	private List<String[]> dataList;

	public TextDataWriter() {

	}

	public TextDataWriter(String fileName) {
		try {
			this.fileName = fileName;
			bufferedWriter = new BufferedWriter(new FileWriter(fileName));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public TextDataWriter(String delimiter, String fileName) {
		this(fileName);
		setDelimiter(delimiter);
	}

	public TextDataWriter(String basePath, Map parameters, String fileName, List<String[]> dataList, String fileType) {
		try {
			this.parameters = parameters;
			this.fileName = fileName;
			this.dataList = dataList;
			bufferedWriter = new BufferedWriter(new FileWriter(basePath + "/" + fileName));
			parameters.put("basePath", basePath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public TextDataWriter(String basePath, Map parameters, String fileName,String fileName2,String fileName3, List<String[]> dataList, String fileType) {
		try {
			this.parameters = parameters;
			this.fileName = fileName;
			this.fileName2 = fileName2;
			this.dataList = dataList;
			bufferedWriter = new BufferedWriter(new FileWriter(basePath + "/" + fileName));
			bufferedWriterNew = new BufferedWriter(new FileWriter(basePath + "/" + fileName2));
			bufferedWriterZip = new BufferedWriter(new FileWriter(basePath + "/" + fileName3));
			parameters.put("basePath", basePath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public String getArrangedReport(Map parameters, List<String[]> dataList, String delimiter,
			BufferedWriter bufferedWriter2, String fixedLength, String secDelimiter) throws IOException {

		String report = "";

		String printHeaderColumnName = (String) parameters.get("printheadercolumnname");

		Integer[] columnWidths = (Integer[]) parameters.get("columnWidths");

		if ("Y".equals(printHeaderColumnName)) {
			String[] columnsMap = (String[]) parameters.get("columnsMap");
			int i = 0;
			for (String string : columnsMap) {

				if (columnWidths[i] - string.length() > 0) {
					report += string;
					for (int j = 0; j < (columnWidths[i] - string.length()); j++) {
						report += " ";
					}
				} else {
					report += string.substring(0, columnWidths[i]);
				}
				i++;
			}
			bufferedWriter.write(report);
			bufferedWriter.write("\r\n");
			// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			report = "";
		}

		if (dataList != null && !dataList.isEmpty()) {
			for (String[] string : dataList) {
				report = "";
				int i = 0;
				for (; i < string.length - 1; i++) {
					if (string[i] != null && !string[i].equals("")) {
						if (delimiter != null && !delimiter.equals("")) {
							if (i == string.length - 1)
								report += string[i].trim();
							else
								report += string[i].trim() + delimiter;
						} else {
							if (columnWidths[i] - string[i].length() > 0) {
								report += string[i];
								for (int j = 0; j < (columnWidths[i] - string[i].length()); j++) {
									report += " ";
								}
							} else {
								report += string[i].substring(0, columnWidths[i]);
							}

						}
					} else {
						if (delimiter == null || delimiter.equals("")) {
							for (int j = 0; j < columnWidths[i]; j++) {
								report += " ";
							}

						}
					}

				}
				report += string[i];
				bufferedWriter.write(report);
				bufferedWriter.write("\r\n");
				// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			}
		} else {
			bufferedWriter.write("\tNo Records Found\t");
			bufferedWriter.write("\r\n");
			// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
		}

		return report;
	}

	public String getArrangedReport(Map parameters, List<String[]> dataList, String delimiter,
			BufferedWriter bufferedWriter2, String secDelimiter) throws IOException {

		String report = "";
		String reportName = (String) parameters.get("reportname");
		String printHeaderColumnName = (String) parameters.get("printheadercolumnname");
		String additionalHeader = (String) parameters.get("additionalHeader");
		String additionalTrailer = (String) parameters.get("additionalTrailer");
		String securityType = (String) parameters.get("securityType");
		Integer[] columnWidths = (Integer[]) parameters.get("columnWidths");

		// Print Header
		String[] columnsHeader = (String[]) parameters.get("columnNameData");
		String[] columnNameValues = (String[]) parameters.get("columnNameValues");
		// Added By Prachit
		if (reportName.equals("CERSAI Charge Release report") || reportName.equals("CERSAI Batch Upload Report")) {
			if (reportName.equals("CERSAI Charge Release report")) {
				if (securityType.equals("IMMOVABLE")) {
					bufferedWriter.write("FH|ADSI|");
				} else {
					bufferedWriter.write("FH|MVIN|");
				}
			} else {
				if (securityType.equals("IMMOVABLE")) {
					bufferedWriter.write("FH|ADSI|");
				} else {
					bufferedWriter.write("FH|MVIN|");
				}
			}

			if (dataList != null && !dataList.isEmpty()) {
				bufferedWriter.write(dataList.size() + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
				bufferedWriter.write("\r\n");
				bufferedWriter.write("\r\n");
			} else {
				bufferedWriter.write("0|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
				bufferedWriter.write("\r\n");
				bufferedWriter.write("\r\n");
			}
		}
		// end

		if (columnsHeader != null && columnsHeader.length > 0) {
			for (int i = 0; i < columnsHeader.length; i++) {
				String data = columnsHeader[i] + ": " + columnNameValues[i];
				bufferedWriter.write(data);
				bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			}
			bufferedWriter.write("-----------------------------------------");
			bufferedWriter.write("\r\n");// bufferedWriter.newLine();
		}

		if (additionalHeader != null && !additionalHeader.isEmpty()) {
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(new Date(generalParamEntry.getParamValue()));
			bufferedWriter.write(additionalHeader + " " + CommonUtil.getCurrentDateForPosidex(currentDate));
			bufferedWriter.write("\r\n");
		}

		if ("Y".equals(printHeaderColumnName)) {
			String[] columnsMap = (String[]) parameters.get("columnsMap");
			int i = 0;
			for (String string : columnsMap) {
				if (delimiter != null && !delimiter.equals("")) {
					if (i == columnsMap.length - 1) {
						report += string.trim();
						report += secDelimiter;
					} else
						report += string.trim() + delimiter;
				} else {
					if (columnWidths[i] - string.length() > 0) {
						report += string;
						for (int j = 0; j < (columnWidths[i] - string.length()); j++) {
							report += " ";
						}
					} else {
						report += string.substring(0, columnWidths[i]);
					}
					report += "\t";
				}
				i++;
			}
			bufferedWriter.write(report);
			bufferedWriter.write("\r\n");
			// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			report = "";
		}

		if (dataList != null && !dataList.isEmpty()) {
			int size = dataList.size();
			int cnt = 0;
			int counter = 1;
			for (String[] string : dataList) {
				if (reportName.equals("CERSAI Charge Release report")) {
					bufferedWriter.write("RH|" + counter + "|");
				}
				report = "";
				for (int i = 0; i < string.length; i++) {
					cnt = i;
					if (string[i] != null && !string[i].equals("")) {

						if (delimiter != null && !delimiter.equals("")) {
							if (i == string.length - 1) {
								report += string[i].trim();
								report += secDelimiter;
							} else
								report += string[i].trim() + delimiter;
						} else {
							if (columnWidths[i] - string[i].length() > 0) {
								if (reportName.equals("CERSAI Charge Release report")) {
									if (cnt == 3) {
										report += "01|";
									}
								}
								if (string[i].equals("-")) {
									report += "";
								} else {
									report += string[i];
								}
								// report += string[i];
								for (int j = 0; j < (columnWidths[i] - string[i].length()); j++) {
									report += "|";
									break;
								}
							} else {
								report += string[i].substring(0, columnWidths[i]);
							}
							// report += "\t";
						}
					} else {
						if (delimiter != null && !delimiter.equals("")) {
							if (i == string.length - 1)
								report += string[i];
							else
								report += string[i] + delimiter;
						}

						if (delimiter == null || delimiter.equals("")) {
							for (int j = 0; j < columnWidths[i]; j++) {
								report += "|";
							}
							// report += "\t";
						}
					}

				}

				// report += "\n";
				bufferedWriter.write(report);
				if (counter != size) {
					bufferedWriter.write("\r\n");
				}
				// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
				counter++;
			}

			if (additionalTrailer != null && !additionalTrailer.isEmpty()) {
				bufferedWriter.write("\r\n");
				bufferedWriter.write(additionalTrailer + " " + size);
			}
		} else {
			bufferedWriter.write("No Records Found");
			bufferedWriter.write("\r\n");
			// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
		}

		// Print Footer
		String[] columnsFooter = (String[]) parameters.get("footerNameData");
		String[] footerNameValues = (String[]) parameters.get("footerNameValues");

		if (columnsFooter != null && columnsFooter.length > 0) {
			bufferedWriter.write("-----------------------------------------");
			bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			for (int i = 0; i < columnsFooter.length; i++) {
				String data = columnsFooter[i] + ": " + footerNameValues[i];
				bufferedWriter.write(data);
				bufferedWriter.write("\r\n");
				// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			}
		}

		return report;
	}

	// Added For Cersai Batch Upload Report
	public String getArrangedReport2(Map parameters, List<String[]> dataList, String delimiter,
			BufferedWriter bufferedWriter2,BufferedWriter bufferedWriter3, String secDelimiter) throws IOException {

		String report = "";
		String reportName = (String) parameters.get("reportname");
		String printHeaderColumnName = (String) parameters.get("printheadercolumnname");
		String additionalHeader = (String) parameters.get("additionalHeader");
		String additionalTrailer = (String) parameters.get("additionalTrailer");
		String securityType = (String) parameters.get("securityType");
		String bankMethod = (String) parameters.get("bankingMethod");
		String fromDate = (String) parameters.get("fromDate");
		String toDate = (String) parameters.get("toDate");
		Integer[] columnWidths = (Integer[]) parameters.get("columnWidths");
		
		// Print Header
		String[] columnsHeader = (String[]) parameters.get("columnNameData");
		String[] columnNameValues = (String[]) parameters.get("columnNameValues");
		
		if(reportName.equals("CERSAI Batch Upload Report")) {

		ReportDaoImpl reportImpl = new ReportDaoImpl();
		List<String[]> reportBorrower = new LinkedList<String[]>();
		List<String[]> reportThirdParty = new LinkedList<String[]>();
		List<String[]> reportSecurityInterest = new LinkedList<String[]>();
		List<String[]> reportProperty = new LinkedList<String[]>();
		List<String[]> reportDocuments = new LinkedList<String[]>();
		List<String[]> reportLoan = new LinkedList<String[]>();
		List<String[]> reportAsset = new LinkedList<String[]>();
		
		//System.out.println("Get reportBorrower record list.");
		reportBorrower = reportImpl.getBorrowerRecords(bankMethod,fromDate,toDate,securityType);
		//System.out.println("Get reportBorrower record list with size == "+reportBorrower.size());
		
		//System.out.println("Get reportThirdParty record list.");
		reportThirdParty = reportImpl.getThirdPartyRecords(bankMethod,fromDate,toDate,securityType);
		//System.out.println("Get reportThirdParty record list with size == "+reportThirdParty.size());
		
		//System.out.println("Get reportSecurityInterest record list.");
		reportSecurityInterest = reportImpl.getSecurityThirdInterestRecords(bankMethod,fromDate,toDate,securityType);
		//System.out.println("Get reportSecurityInterest record list with size == "+reportSecurityInterest.size());
		
		if (securityType.equals("IMMOVABLE")) {
			//System.out.println("Get reportProperty record list.");
			reportProperty = reportImpl.getPropertyRecords(bankMethod,fromDate,toDate,securityType);
			//System.out.println("Get reportProperty record list with size == "+reportSecurityInterest.size());
		}
		
		//System.out.println("Get reportDocuments record list.");
		reportDocuments = reportImpl.getDocumentRecords(bankMethod,fromDate,toDate,securityType);
		//System.out.println("Get reportDocuments record list with size == "+reportDocuments.size());
		
		//System.out.println("Get reportLoan record list.");
		reportLoan = reportImpl.getLoanRecords(bankMethod,fromDate,toDate,securityType);
		//System.out.println("Get reportLoan record list with size == "+reportLoan.size());
		
		if (securityType.equals("MOVABLE")) {
			//System.out.println("Get reportAsset record list.");
			reportAsset = reportImpl.getAssetRecords(bankMethod,fromDate,toDate,securityType);
			//System.out.println("Get reportAsset record list with size == "+reportAsset.size());
		}

		List<String> borrowList = new ArrayList<String>();
		List<String> thirdPartyList = new ArrayList<String>();
		List<String> securityInterestList = new ArrayList<String>();
		List<String> propertyList = new ArrayList<String>();
		List<String> documentsList = new ArrayList<String>();
		List<String> loanList = new ArrayList<String>();
		List<String> assetList = new ArrayList<String>();

		String st = "";
		// String[] str = new String[reportBorrower.size()];
		int count = 0;

		// All Records security id added in list
		if (reportBorrower != null && !reportBorrower.isEmpty()) {
			//System.out.println("reportBorrower record list is not empty =>in.");
			for (int i = 0; i < reportBorrower.size(); i++) {
				String[] str = (String[]) reportBorrower.get(i);
				st = str[1];
				borrowList.add(st);
			}
			//System.out.println("reportBorrower record list is not empty =>out.");
		}

		if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
			//System.out.println("reportThirdParty record list is not empty =>in.");
			for (int i = 0; i < reportThirdParty.size(); i++) {
				String[] str = (String[]) reportThirdParty.get(i);
				st = str[1];
				thirdPartyList.add(st);
			}
			//System.out.println("reportThirdParty record list is not empty =>out.");
		}

		if (reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
			//System.out.println("reportSecurityInterest record list is not empty =>in.");
			for (int i = 0; i < reportSecurityInterest.size(); i++) {
				String[] str = (String[]) reportSecurityInterest.get(i);
				st = str[1];
				securityInterestList.add(st);
			}
			//System.out.println("reportSecurityInterest record list is not empty =>out.");
		}

		if (securityType.equals("IMMOVABLE")) {
		if (reportProperty != null && !reportProperty.isEmpty()) {
			//System.out.println("reportProperty record list is not empty =>in.");
			for (int i = 0; i < reportProperty.size(); i++) {
				String[] str = (String[]) reportProperty.get(i);
				st = str[1];
				propertyList.add(st);
			}
			//System.out.println("reportProperty record list is not empty =>out.");
		}
		}
		if (reportDocuments != null && !reportDocuments.isEmpty()) {
			//System.out.println("reportDocuments record list is not empty =>in.");
			for (int i = 0; i < reportDocuments.size(); i++) {
				String[] str = (String[]) reportDocuments.get(i);
				st = str[1];
				documentsList.add(st);
			}
			//System.out.println("reportDocuments record list is not empty =>out.");
		}

		if (reportLoan != null && !reportLoan.isEmpty()) {
			//System.out.println("reportLoan record list is not empty =>in.");
			for (int i = 0; i < reportLoan.size(); i++) {
				String[] str = (String[]) reportLoan.get(i);
				st = str[1];
				loanList.add(st);
			}
			//System.out.println("reportLoan record list is not empty =>out.");
		}
		if (securityType.equals("MOVABLE")) {
		if (reportAsset != null && !reportAsset.isEmpty()) {
			//System.out.println("reportAsset record list is not empty =>out.");
			for (int i = 0; i < reportAsset.size(); i++) {
				String[] str = (String[]) reportAsset.get(i);
				st = str[1];
				assetList.add(st);
			}
			//System.out.println("reportAsset record list is not empty =>out.");
		}
		}

		// Unique Security id in Set.

		Set<String> borrowListSpecific = new HashSet<String>(borrowList);
		Set<String> thirdPartyListSpecific = new HashSet<String>(thirdPartyList);
		Set<String> securityInterestListSpecific = new HashSet<String>(securityInterestList);
		Set<String> propertyListSpecific = new HashSet<String>();
		if (securityType.equals("IMMOVABLE")) {
			propertyListSpecific = new HashSet<String>(propertyList);
		}
		Set<String> documentsListSpecific = new HashSet<String>(documentsList);
		Set<String> loanListSpecific = new HashSet<String>(loanList);
		Set<String> assetListSpecific = new HashSet<String>();
		if (securityType.equals("MOVABLE")) {
			assetListSpecific =new HashSet<String>(assetList);
		}
		// Unique Security Id added in List.

		if (borrowListSpecific != null && !borrowListSpecific.isEmpty()) {
			borrowList.clear();
			for (String borrow : borrowListSpecific) {
				borrowList.add(borrow);
			}
		}

		if (thirdPartyListSpecific != null && !thirdPartyListSpecific.isEmpty()) {
			thirdPartyList.clear();
			for (String thirdParty : thirdPartyListSpecific) {
				thirdPartyList.add(thirdParty);
			}
		}

		if (securityInterestListSpecific != null && !securityInterestListSpecific.isEmpty()) {
			securityInterestList.clear();
			for (String securityInterest : securityInterestListSpecific) {
				securityInterestList.add(securityInterest);
			}
		}
		
		if (securityType.equals("IMMOVABLE")) {
		if (propertyListSpecific != null && !propertyListSpecific.isEmpty()) {
			propertyList.clear();
			for (String property : propertyListSpecific) {
				propertyList.add(property);
			}
		}
		}

		if (documentsListSpecific != null && !documentsListSpecific.isEmpty()) {
			documentsList.clear();
			for (String documents : documentsListSpecific) {
				documentsList.add(documents);
			}
		}

		if (loanListSpecific != null && !loanListSpecific.isEmpty()) {
			loanList.clear();
			for (String loan : loanListSpecific) {
				loanList.add(loan);
			}
		}
		
		if (securityType.equals("MOVABLE")) {
		if (assetListSpecific != null && !assetListSpecific.isEmpty()) {
			assetList.clear();
			for (String asset : assetListSpecific) {
				assetList.add(asset);
			}
		}
		}
		// Each List Compared and Unique Security Id get from all 7 Lists
		if (!borrowList.isEmpty() && !thirdPartyList.isEmpty() && borrowList != null && thirdPartyList != null) {
			for (int k = 0; k < borrowList.size(); k++) {
				for (int j = 0; j < thirdPartyList.size(); j++) {
					if (borrowList.get(k).equals(thirdPartyList.get(j))) {
						thirdPartyList.remove(j);
					}
				}
			}
		}

		if (!borrowList.isEmpty() && !securityInterestList.isEmpty() && borrowList != null
				&& securityInterestList != null) {
			for (int k = 0; k < borrowList.size(); k++) {
				for (int j = 0; j < securityInterestList.size(); j++) {
					if (borrowList.get(k).equals(securityInterestList.get(j))) {
						securityInterestList.remove(j);
					}
				}
			}
		}

		if (securityType.equals("IMMOVABLE")) {
			if (!borrowList.isEmpty() && !propertyList.isEmpty() && borrowList != null && propertyList != null) {
				for (int k = 0; k < borrowList.size(); k++) {
					for (int j = 0; j < propertyList.size(); j++) {
						if (borrowList.get(k).equals(propertyList.get(j))) {
							propertyList.remove(j);
						}
					}
				}
			}
		} else {
			if (!borrowList.isEmpty() && !assetList.isEmpty() && borrowList != null && assetList != null) {
				for (int k = 0; k < borrowList.size(); k++) {
					for (int j = 0; j < assetList.size(); j++) {
						if (borrowList.get(k).equals(assetList.get(j))) {
							assetList.remove(j);
						}
					}
				}
			}
		}

		if (!borrowList.isEmpty() && !documentsList.isEmpty() && borrowList != null && documentsList != null) {
			for (int k = 0; k < borrowList.size(); k++) {
				for (int j = 0; j < documentsList.size(); j++) {
					if (borrowList.get(k).equals(documentsList.get(j))) {
						documentsList.remove(j);
					}
				}
			}
		}

		if (!borrowList.isEmpty() && !loanList.isEmpty() && borrowList != null && loanList != null) {
			for (int k = 0; k < borrowList.size(); k++) {
				for (int j = 0; j < loanList.size(); j++) {
					if (borrowList.get(k).equals(loanList.get(j))) {
						loanList.remove(j);
					}
				}
			}
		}

		if (!thirdPartyList.isEmpty() && !securityInterestList.isEmpty() && thirdPartyList != null
				&& securityInterestList != null) {
			for (int k = 0; k < thirdPartyList.size(); k++) {
				for (int j = 0; j < securityInterestList.size(); j++) {
					if (thirdPartyList.get(k).equals(securityInterestList.get(j))) {
						securityInterestList.remove(j);
					}
				}
			}
		}

		if (securityType.equals("IMMOVABLE")) {
			if (!thirdPartyList.isEmpty() && !propertyList.isEmpty() && thirdPartyList != null
					&& propertyList != null) {
				for (int k = 0; k < thirdPartyList.size(); k++) {
					for (int j = 0; j < propertyList.size(); j++) {
						if (thirdPartyList.get(k).equals(propertyList.get(j))) {
							propertyList.remove(j);
						}
					}
				}
			}
		} else {
			if (!thirdPartyList.isEmpty() && !assetList.isEmpty() && thirdPartyList != null && assetList != null) {
				for (int k = 0; k < thirdPartyList.size(); k++) {
					for (int j = 0; j < assetList.size(); j++) {
						if (thirdPartyList.get(k).equals(assetList.get(j))) {
							assetList.remove(j);
						}
					}
				}
			}
		}

		if (!thirdPartyList.isEmpty() && !documentsList.isEmpty() && thirdPartyList != null && documentsList != null) {
			for (int k = 0; k < thirdPartyList.size(); k++) {
				for (int j = 0; j < documentsList.size(); j++) {
					if (thirdPartyList.get(k).equals(documentsList.get(j))) {
						documentsList.remove(j);
					}
				}
			}
		}

		if (!thirdPartyList.isEmpty() && !loanList.isEmpty() && thirdPartyList != null && loanList != null) {
			for (int k = 0; k < thirdPartyList.size(); k++) {
				for (int j = 0; j < loanList.size(); j++) {
					if (thirdPartyList.get(k).equals(loanList.get(j))) {
						loanList.remove(j);
					}
				}
			}
		}

		if (securityType.equals("IMMOVABLE")) {
			if (!securityInterestList.isEmpty() && !propertyList.isEmpty() && securityInterestList != null
					&& propertyList != null) {
				for (int k = 0; k < securityInterestList.size(); k++) {
					for (int j = 0; j < propertyList.size(); j++) {
						if (securityInterestList.get(k).equals(propertyList.get(j))) {
							propertyList.remove(j);
						}
					}
				}
			}
		} else {
			if (!securityInterestList.isEmpty() && !assetList.isEmpty() && securityInterestList != null
					&& assetList != null) {
				for (int k = 0; k < securityInterestList.size(); k++) {
					for (int j = 0; j < assetList.size(); j++) {
						if (securityInterestList.get(k).equals(assetList.get(j))) {
							assetList.remove(j);
						}
					}
				}
			}
		}

		if (!securityInterestList.isEmpty() && !documentsList.isEmpty() && securityInterestList != null
				&& documentsList != null) {
			for (int k = 0; k < securityInterestList.size(); k++) {
				for (int j = 0; j < documentsList.size(); j++) {
					if (securityInterestList.get(k).equals(documentsList.get(j))) {
						documentsList.remove(j);
					}
				}
			}
		}

		if (!securityInterestList.isEmpty() && !loanList.isEmpty() && securityInterestList != null
				&& loanList != null) {
			for (int k = 0; k < securityInterestList.size(); k++) {
				for (int j = 0; j < loanList.size(); j++) {
					if (securityInterestList.get(k).equals(loanList.get(j))) {
						loanList.remove(j);
					}
				}
			}
		}

		if (securityType.equals("IMMOVABLE")) {
			if (!propertyList.isEmpty() && !documentsList.isEmpty() && propertyList != null && documentsList != null) {
				for (int k = 0; k < propertyList.size(); k++) {
					for (int j = 0; j < documentsList.size(); j++) {
						if (propertyList.get(k).equals(documentsList.get(j))) {
							documentsList.remove(j);
						}
					}
				}
			}

			if (!propertyList.isEmpty() && !loanList.isEmpty() && propertyList != null && loanList != null) {
				for (int k = 0; k < propertyList.size(); k++) {
					for (int j = 0; j < loanList.size(); j++) {
						if (propertyList.get(k).equals(loanList.get(j))) {
							loanList.remove(j);
						}
					}
				}
			}

		} else {
			if (!assetList.isEmpty() && !documentsList.isEmpty() && assetList != null && documentsList != null) {
				for (int k = 0; k < assetList.size(); k++) {
					for (int j = 0; j < documentsList.size(); j++) {
						if (assetList.get(k).equals(documentsList.get(j))) {
							documentsList.remove(j);
						}
					}
				}
			}

			if (!assetList.isEmpty() && !loanList.isEmpty() && assetList != null && loanList != null) {
				for (int k = 0; k < assetList.size(); k++) {
					for (int j = 0; j < loanList.size(); j++) {
						if (assetList.get(k).equals(loanList.get(j))) {
							loanList.remove(j);
						}
					}
				}
			}
		}

		if (!documentsList.isEmpty() && !loanList.isEmpty() && documentsList != null && loanList != null) {
			for (int k = 0; k < documentsList.size(); k++) {
				for (int j = 0; j < loanList.size(); j++) {
					if (documentsList.get(k).equals(loanList.get(j))) {
						loanList.remove(j);
					}
				}
			}
		}

		int numberOfTotalRecords = 0;
		if (securityType.equals("IMMOVABLE")) {
			numberOfTotalRecords = borrowList.size() + thirdPartyList.size() + securityInterestList.size()
					+ propertyList.size() + documentsList.size() + loanList.size();
			System.out.println("IMMOVABLE = numberOfTotalRecords = "+numberOfTotalRecords);
			System.out.println("borrowList.size() = "+borrowList.size()+" // thirdPartyList.size() = "+thirdPartyList.size()+" // securityInterestList.size() = "+securityInterestList.size()
			+" // propertyList.size() = "+propertyList.size()+" // documentsList.size() = "+documentsList.size()+" // loanList.size() = "+loanList.size());
		} else {
			numberOfTotalRecords = borrowList.size() + thirdPartyList.size() + securityInterestList.size()
					+ assetList.size() + documentsList.size() + loanList.size();
			
			System.out.println("MOVABLE = numberOfTotalRecords = "+numberOfTotalRecords);
			System.out.println("borrowList.size() = "+borrowList.size()+" // thirdPartyList.size() = "+thirdPartyList.size()+" // securityInterestList.size() = "+securityInterestList.size()
			+" // assetList.size() = "+assetList.size()+" // documentsList.size() = "+documentsList.size()+" // loanList.size() = "+loanList.size());
		}

		// Added By Prachit

		int totalNumberOfRecords = 0;
		if (securityType.equals("IMMOVABLE")) {
			bufferedWriter.write("FH|ADSI|");
		} else {
			bufferedWriter.write("FH|MVIN|");
		}
		if(numberOfTotalRecords > 1000) {
			totalNumberOfRecords = 1000;
			System.out.println(" totalNumberOfRecords for first file = "+totalNumberOfRecords);
		}else {
			totalNumberOfRecords = numberOfTotalRecords;
			System.out.println("numberOfTotalRecords as  totalNumberOfRecords for first file = "+totalNumberOfRecords);
		}
		bufferedWriter.write(totalNumberOfRecords + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
		bufferedWriter.write("\r\n");
		bufferedWriter.write("\r\n");
		
		if(numberOfTotalRecords > 1000) {
			if (securityType.equals("IMMOVABLE")) {
				bufferedWriterNew.write("FH|ADSI|");
			} else {
				bufferedWriterNew.write("FH|MVIN|");
			}
			numberOfTotalRecords = numberOfTotalRecords - 1000;
//			if(numberOfTotalRecords > 1000) {
//				numberOfTotalRecords = 1000;
//			}
			System.out.println("numberOfTotalRecords for second file = "+numberOfTotalRecords);
			
			bufferedWriterNew.write(numberOfTotalRecords + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
			bufferedWriterNew.write("\r\n");
			bufferedWriterNew.write("\r\n");
		}
		// end

		if (columnsHeader != null && columnsHeader.length > 0) {
			//System.out.println("columnsHeader is not null and length = "+columnsHeader.length);
			for (int i = 0; i < columnsHeader.length; i++) {
				String data = columnsHeader[i] + ": " + columnNameValues[i];
				bufferedWriter.write(data);
				bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			}
			bufferedWriter.write("-----------------------------------------");
			bufferedWriter.write("\r\n");// bufferedWriter.newLine();
		}

		if (additionalHeader != null && !additionalHeader.isEmpty()) {
			//System.out.println("additionalHeader is not null and length = "+additionalHeader.length());
			IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
			IGeneralParamEntry generalParamEntry = generalParamDao
					.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
			Calendar currentDate = Calendar.getInstance();
			currentDate.setTime(new Date(generalParamEntry.getParamValue()));
			bufferedWriter.write(additionalHeader + " " + CommonUtil.getCurrentDateForPosidex(currentDate));
			bufferedWriter.write("\r\n");
		}

		if ("Y".equals(printHeaderColumnName)) {
			//System.out.println("printHeaderColumnName is equals Y ");
			String[] columnsMap = (String[]) parameters.get("columnsMap");
			int i = 0;
			for (String string : columnsMap) {
				if (delimiter != null && !delimiter.equals("")) {
					if (i == columnsMap.length - 1) {
						report += string.trim();
						report += secDelimiter;
					} else
						report += string.trim() + delimiter;
				} else {
					if (columnWidths[i] - string.length() > 0) {
						report += string;
						for (int j = 0; j < (columnWidths[i] - string.length()); j++) {
							report += " ";
						}
					} else {
						report += string.substring(0, columnWidths[i]);
					}
					report += "\t";
				}
				i++;
			}
			
			bufferedWriter.write(report);
			bufferedWriter.write("\r\n");
			//System.out.println("printHeaderColumnName is equals Y => write data to file bufferedWriter");
			// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			report = "";
		}

		boolean flag = true;
		if (securityType.equals("IMMOVABLE")) {
			if ((reportBorrower == null || reportBorrower.isEmpty())
					&& (reportThirdParty == null || reportThirdParty.isEmpty())
					&& (reportSecurityInterest == null || reportSecurityInterest.isEmpty())
					&& (reportProperty == null || reportProperty.isEmpty())
					&& (reportDocuments == null || reportDocuments.isEmpty())
					&& (reportLoan == null || reportLoan.isEmpty())) {
				bufferedWriter.write("No Records Found");
				bufferedWriter.write("\r\n");
				flag = false;
			}
		} else {
			if ((reportBorrower == null || reportBorrower.isEmpty())
					&& (reportThirdParty == null || reportThirdParty.isEmpty())
					&& (reportSecurityInterest == null || reportSecurityInterest.isEmpty())
					&& (reportAsset == null || reportAsset.isEmpty())
					&& (reportDocuments == null || reportDocuments.isEmpty())
					&& (reportLoan == null || reportLoan.isEmpty())) {
				bufferedWriter.write("No Records Found");
				bufferedWriter.write("\r\n");
				flag = false;
			}
		}

		String surveyNo = "";
		String floorNo = "";
		String buildingNo = "";
		String buildingName = "";
		String plotSurveyhouseNo = "";
		String tpmPlotSurveyhouseNo = "";
		if (flag == true) {
			
			String[] str=null;
			String[] strThiParty=null;
			String[] strSecInt=null;
			String[] strDoc=null;
			String[] strLoan=null;
			String[] strAsset=null;
			String[] strProperty=null;
			
			if(reportBorrower != null && !reportBorrower.isEmpty()) {
				//System.out.println("(reportBorrower.get(0)).length "+(reportBorrower.get(0)).length);
				str = new String[(reportBorrower.get(0)).length];
			}
			if(reportThirdParty != null && !reportThirdParty.isEmpty()) {
				//System.out.println("(reportThirdParty.get(0)).length "+(reportThirdParty.get(0)).length);
				strThiParty = new String[(reportThirdParty.get(0)).length];
			}
			if(reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
				//System.out.println("(reportSecurityInterest.get(0)).length "+(reportSecurityInterest.get(0)).length);
				strSecInt = new String[(reportSecurityInterest.get(0)).length];
			}
			if(reportDocuments != null && !reportDocuments.isEmpty()) {
				//System.out.println("(reportDocuments.get(0)).length "+(reportDocuments.get(0)).length);
				strDoc = new String[(reportDocuments.get(0)).length];
			}
			if(reportLoan != null && !reportLoan.isEmpty()) {
				//System.out.println("(reportLoan.get(0)).length "+(reportLoan.get(0)).length);
				strLoan = new String[(reportLoan.get(0)).length];
			}
			if(reportAsset != null && !reportAsset.isEmpty()) {
				//System.out.println("(reportAsset.get(0)).length "+(reportAsset.get(0)).length);
				strAsset = new String[(reportAsset.get(0)).length];
			}
			if(reportProperty != null && !reportProperty.isEmpty()) {
				//System.out.println("(reportProperty.get(0)).length "+(reportProperty.get(0)).length);
				strProperty = new String[(reportProperty.get(0)).length];
			}
			
			
			String amount = "";
			String docDate = "";
			int recordCount = 0;
			for (int i = 0; i < 1; i++) {
				
//				BorrowList records 
				
				if (borrowList != null && !borrowList.isEmpty()) {
					System.out.println("Borrowwer list is not null or empty.");
					//System.out.println("Borrower List records Starts : ");
					
					for (int j = 0; j < borrowList.size(); j++) {
						recordCount++;
						//System.out.println("recordCount is == "+recordCount);
//						if(recordCount > 2000) {
//							break;
//						}
						count=0;
						for (int k = 0; k < reportBorrower.size(); k++) {
							str = reportBorrower.get(k);
							if (borrowList.get(j).equals(str[1])) {
								count++;
								amount = str[2];
							}
						}
						
						//reportProperty strProperty
						if(securityType.equals("IMMOVABLE")) {
						if (reportProperty != null && !reportProperty.isEmpty()) {
							for (int k = 0; k < reportProperty.size(); k++) {
								strProperty = reportProperty.get(k);
								if (borrowList.get(j).equals(strProperty[1])) {
									if(strProperty[15].equals("STOCKS AND RECEIVABLES")) {
										amount = strProperty[2];
									}
								}
							}
						}else {
							for (int k = 0; k < reportBorrower.size(); k++) {
								str = reportBorrower.get(k);
								if (borrowList.get(j).equals(str[1])) {
									amount = str[2];
								}
							}
						}
						}else if(securityType.equals("MOVABLE")){
							if (reportAsset != null && !reportAsset.isEmpty()) {
								for (int k = 0; k < reportAsset.size(); k++) {
									strAsset = reportAsset.get(k);
									if (borrowList.get(j).equals(strAsset[1])) {
										if(strAsset[4].equals("STOCKS AND RECEIVABLES")) {
											amount = strAsset[2];
										}
									}
								}
							}else {
								for (int k = 0; k < reportBorrower.size(); k++) {
									str = reportBorrower.get(k);
									if (borrowList.get(j).equals(str[1])) {
										amount = str[2];
									}
								}
							}
						}
						//reportAsset strAsset
						
//						for (int k = 0; k < reportBorrower.size(); k++) {
//							str = reportBorrower.get(k);
//							if (borrowList.get(j).equals(str[1])) {
//								docDate = str[15];
//							}
//						}
						
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								docDate = strLoan[6];
							}
						}
						if(docDate == null) {
							docDate = "";
						}
						
						
						report = "";
						report += "RH|" + recordCount + "|" + count + "|";
						count = 0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
							for (int k = 0; k < reportThirdParty.size(); k++) {
								strThiParty = reportThirdParty.get(k);
								if (borrowList.get(j).equals(strThiParty[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;

						if (reportDocuments != null && !reportDocuments.isEmpty()) {
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								if (borrowList.get(j).equals(strDoc[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								if (borrowList.get(j).equals(strLoan[1])) {
									count++;
								}
							}
						}
						if (amount == null) {
							amount = "";
						}
//						report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
						report += count + "|" + docDate + "|" + amount + "|"
								+ borrowList.get(j) + "|";
						if (securityType.equals("MOVABLE")) {
							report += "|";
						}
						
						
						if(recordCount <= 1000) {
							bufferedWriter.write(report);
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write(report);
							bufferedWriterNew.write("\r\n");
						}
						
						
						//Adding Records from List By security id
						count=0;
						for (int k = 0; k < reportBorrower.size(); k++) {
							str = reportBorrower.get(k);
							
							for(int a=0;a<str.length;a++) {
								if(str[a] == null) {
									str[a]="";
								}else {
									//System.out.println("Borrower Value Test for special character 1 == "+str[a]);
									str[a] = str[a].replaceAll("&","AND");
									//System.out.println("Borrower Value Test for special character 1 == "+str[a]);
									str[a] = str[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println("Borrower Value Test for special character 2 == "+str[a]);
//									str[a] = str[a].replaceAll("  "," ");
//									System.out.println("Borrower Value Test for special character 3 == "+str[a]);
									while(str[a].contains("  ")){
										 // System.out.println("text1=="+text1);
										str[a] = str[a].replaceAll("  "," ");
										 // System.out.println("text1=="+text1);
										}
								}
							}
							
							if (borrowList.get(j).equals(str[1])) {
								//System.out.println("Borrower List records adding to file.");
								count++;
								report="";
								report +="BOR|"+count+"|"; 
								if(str[0].equalsIgnoreCase("IND") || str[0].equalsIgnoreCase("COM") || str[0].equalsIgnoreCase("HUF") || str[0].equalsIgnoreCase("COS") || str[0].equalsIgnoreCase("LLP") || str[0].equalsIgnoreCase("PAF") || str[0].equalsIgnoreCase("PRF") || str[0].equalsIgnoreCase("TRS")) {
									report +=str[0]+"|";
								}else {
									report +="|";
								}
								if(str[0].equalsIgnoreCase("COM")) {
									report +=str[3]+"|"+str[4]+"|"+str[5]+"|";
								}else {
									report +="|||";
								}
								
								if(str[0].equalsIgnoreCase("IND")){
									report +="Mr.||"+str[3]+"||"+str[5]+"||";
								}else {
									report +="||||||";
								}
								
								if(str[0].equalsIgnoreCase("PRF")){
									report +=str[3]+"|"+str[5]+"|PRF_COM|"+str[3]+"|"+str[4]+"||||||";
								}else {
									report +="||||||||||";
								}
								
								if(str[0].equalsIgnoreCase("PAF")) {
									report +=str[3]+"|Ms.||"+str[6]+"|||"+str[5]+"|";
								}else {
									report +="|||||||";
								}
								
								if(str[0].equalsIgnoreCase("TRS")) {
									report +=str[3]+"|"+str[5]+"|Mr.||"+str[6]+"|||";
								}else {
									report +="|||||||";
								}
								
								if(str[0].equalsIgnoreCase("LLP")) {
									report +=str[3]+"|Mr.||"+str[6]+"|||"+str[5]+"|";
								}else {
									report +="|||||||";
								}
								
								if(str[0].equalsIgnoreCase("HUF")) {
									report +="Mr.||"+str[3]+"||"+str[5]+"||";
								}else {
									report +="||||||";
								}
								
								if(str[0].equalsIgnoreCase("COS")) {
									if (securityType.equals("IMMOVABLE")) {
										report +=str[6]+"|||"+str[5]+"|";
									}else {
										report +=str[3]+"|||"+str[5]+"|";
									}
								}else {
									report +="||||";
								}
								
//								if(str[7] == null && str[8] == null && str[9] == null) {
//									report +="";
//								}else {
//								if(str[7] != null) {
//									report +=str[7];
//								}
//								if(str[8] != null) {
//									report +=" "+str[8];
//								}
//								if(str[9] != null) {
//									report +=" "+str[9];
//								}
//								}
								
								if("".equals(str[7]) && "".equals(str[8]) && "".equals(str[9])) {
									report +="";
								}else {
//								if(!"".equals(str[7])) {
//									report +=str[7];
//								}
//								if(!"".equals(str[8])) {
//									report +=" "+str[8];
//								}
//								if(!"".equals(str[9])) {
//									report +=" "+str[9];
//								}
								
								plotSurveyhouseNo = str[7].trim()+" "+str[8].trim()+" "+str[9].trim();
								if(plotSurveyhouseNo.length() > 100) {
									plotSurveyhouseNo = plotSurveyhouseNo.substring(0, 100);
									report += plotSurveyhouseNo.trim(); 
								}else {
									report += plotSurveyhouseNo.trim(); 
								}
								
								}
								report +="|||||||"+str[10]+"|"+str[14]+"|"+str[12]+"|||";
								if("".equals(str[13]) || "BORROWER".equals(str[13])) {
									report +="Y|";
								}else {
									report +="N|";
								}
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from borrower list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
									//System.out.println("record write to first file from borrower list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from borrower list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
									//System.out.println("record write to second file from borrower list. recordCount ="+recordCount);
								}
								report = "";
							}
						}
						
						if(reportThirdParty != null && !reportThirdParty.isEmpty()) {
							count=0;
							System.out.println("reportThirdParty is not null or empty. ");
							//System.out.println("borrowerlist id checking with thirdparty list. ");
						for (int k = 0; k < reportThirdParty.size(); k++) {
							strThiParty = reportThirdParty.get(k);
							
							for(int a=0;a<strThiParty.length;a++) {
								if(strThiParty[a] == null) {
									strThiParty[a]="";
								}else {
									strThiParty[a] = strThiParty[a].replaceAll("&","AND");
									//System.out.println(strThiParty[a]);
									strThiParty[a] = strThiParty[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strThiParty[a]);
									//strThiParty[a] = strThiParty[a].replaceAll("  "," ");
									//System.out.println(strThiParty[a]);
									while(strThiParty[a].contains("  ")){
										strThiParty[a] = strThiParty[a].replaceAll("  "," ");
										}
								}
							}
							
							if (borrowList.get(j).equals(strThiParty[1])) {
								count++;
								report="";
								
								report +="TPM|"+count+"|";
								if(strThiParty[0].equalsIgnoreCase("IND") || strThiParty[0].equalsIgnoreCase("COM") || strThiParty[0].equalsIgnoreCase("HUF") || strThiParty[0].equalsIgnoreCase("COS") || strThiParty[0].equalsIgnoreCase("LLP") || strThiParty[0].equalsIgnoreCase("PAF") || strThiParty[0].equalsIgnoreCase("PRF") || strThiParty[0].equalsIgnoreCase("TRS")) {
									report +=strThiParty[0]+"|";
								}else {
									report +="|";
								}
								if(strThiParty[0].equalsIgnoreCase("COM")) {
									report +=strThiParty[3]+"|"+strThiParty[4]+"|"+strThiParty[9]+"|";
								}else {
									report +="|||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("IND")){
									report +="Mr.||"+strThiParty[3]+"||"+strThiParty[9]+"||";
								}else {
									report +="||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("PRF")){
									report +=strThiParty[3]+"|"+strThiParty[9]+"|PRF_COM|"+strThiParty[3]+"|"+strThiParty[4]+"||||||";
								}else {
									report +="||||||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("PAF")) {
									report +=strThiParty[3]+"|Mr.||"+strThiParty[3]+"|||"+strThiParty[9]+"|";
								}else {
									report +="|||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("TRS")) {
									report +=strThiParty[3]+"|"+strThiParty[9]+"|Mr.||"+strThiParty[3]+"|||";
								}else {
									report +="|||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("LLP")) {
									report +=strThiParty[3]+"|Mr.||"+strThiParty[3]+"|||"+strThiParty[9]+"|";
								}else {
									report +="|||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("HUF")) {
									report +="Mr.||"+strThiParty[3]+"||"+strThiParty[9]+"||";
								}else {
									report +="||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("COS")) {
									report +=strThiParty[3]+"|||"+strThiParty[9]+"|";
								}else {
									report +="||||";
								}
								
								tpmPlotSurveyhouseNo = strThiParty[5];
								if(tpmPlotSurveyhouseNo.length() > 100) {
									tpmPlotSurveyhouseNo = tpmPlotSurveyhouseNo.substring(0, 100);
									report += tpmPlotSurveyhouseNo.trim()+"|"; 
								}else {
									report += tpmPlotSurveyhouseNo.trim()+"|"; 
								}
								
								//report +=strThiParty[5]+"|";
								
								report +="||||||"+strThiParty[6]+"|"+strThiParty[10]+"|"+strThiParty[8]+"|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to second file from third party list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
									//System.out.println("record write to first file from third party list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from third party list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
									//System.out.println("record write to second file from third party list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
					}
						
						if(reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
							count=0;
							System.out.println("reportSecurityInterest is not null or empty. ");
							//System.out.println("borrowerlist id checking with reportSecurityInterest list. ");
						for (int k = 0; k < reportSecurityInterest.size(); k++) {
							strSecInt = reportSecurityInterest.get(k);
							
							for(int a=0;a<strSecInt.length;a++) {
								if(strSecInt[a] == null) {
									strSecInt[a]="";
								}else {
									strSecInt[a] = strSecInt[a].replaceAll("&","AND");
									//System.out.println(strSecInt[a]);
									strSecInt[a] = strSecInt[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strSecInt[a]);
									//strSecInt[a] = strSecInt[a].replaceAll("  "," ");
									//System.out.println(strSecInt[a]);
									while(strSecInt[a].contains("  ")){
										strSecInt[a] = strSecInt[a].replaceAll("  "," ");
										}
								}
							}
							
							if (borrowList.get(j).equals(strSecInt[1])) {
								report="";
								report += "CHG|0510005|"+strSecInt[0]+"||||||"+strSecInt[3]+"|"+strSecInt[6]+"|"+strSecInt[5]+"|";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from security interest list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
								//	System.out.println("record write to first file from security interest list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from security interest list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
								//	System.out.println("record write to second file from security interest list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if (securityType.equals("IMMOVABLE")) {
						if(reportProperty != null && !reportProperty.isEmpty()) {
							count=0;
							System.out.println("reportProperty is not null or empty. ");
						//	System.out.println("borrowerlist id checking with reportProperty list. ");
						for (int k = 0; k < reportProperty.size(); k++) {
							strProperty = reportProperty.get(k);
							
							for(int a=0;a<strProperty.length;a++) {
								if(strProperty[a] == null) {
									strProperty[a]="";
								}else {
									strProperty[a] = strProperty[a].replaceAll("&","AND");
									//System.out.println(strProperty[a]);
									strProperty[a] = strProperty[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strProperty[a]);
									//strProperty[a] = strProperty[a].replaceAll("  "," ");
									//System.out.println(strProperty[a]);
									while(strProperty[a].contains("  ")){
										strProperty[a] = strProperty[a].replaceAll("  "," ");
										}
								}
							}
							
							if (borrowList.get(j).equals(strProperty[1])) {
								report="";
								report += "PRO|01|";
								if(strProperty[0].equals("")) {
									report +="|";
								}else {
									if(strProperty[0].equalsIgnoreCase("Residential Plot")) {
										report +="01|";
									}
									else if(strProperty[0].equalsIgnoreCase("Industrial Plot")) {
										report +="02|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Plot")) {
										report +="03|";
									}
									else if(strProperty[0].equalsIgnoreCase("Residential Flat")) {
										report +="04|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Flat")) {
										report +="05|";
									}
									else if(strProperty[0].equalsIgnoreCase("Row House")) {
										report +="06|";
									}
									else if(strProperty[0].equalsIgnoreCase("Bungalow") || strProperty[0].equalsIgnoreCase("Villa") || strProperty[0].equalsIgnoreCase("Bungalow/Villa")) {
										report +="07|";
									}else {
										report +="|";
									}
								}
								String addrr1="";
								if(!strProperty[3].equals("")) {
								if(strProperty[3].length() > 20) {
									addrr1 = strProperty[3].substring(0, 20);
									report +=addrr1.trim()+"|";
								}else {
									report +=strProperty[3]+"|";
								}
								}else {
									report +=strProperty[3]+"|";
								}
								surveyNo = strProperty[6].trim()+" "+strProperty[7].trim()+" "+strProperty[8].trim();
								if(surveyNo.length() > 20) {
									surveyNo = surveyNo.substring(0, 20);
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}else {
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}
//								report +=strProperty[6]+" "+strProperty[7]+" "+strProperty[8]+"||"; // Survey_no and house_id from clims 
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//								}
//								if(strProperty[4] != null) {
//									report +=" "+strProperty[4];
//								}
								
								floorNo = strProperty[3].trim()+" "+strProperty[4].trim();
								if(floorNo.length() > 100) {
									floorNo = floorNo.substring(0, 100);
									report += floorNo.trim(); 
								}else {
									report += floorNo.trim(); 
								}
								
								report +="|";
								
//								if(strProperty[5] != null) {
//									report +=strProperty[5];
//									}
//								if(strProperty[6] != null) {
//									report +=" "+strProperty[6];
//								}
								
								buildingNo = strProperty[5].trim()+" "+strProperty[6].trim();
								if(buildingNo.length() > 100) {
									buildingNo = buildingNo.substring(0, 100);
									report += buildingNo.trim(); 
								}else {
									report += buildingNo.trim(); 
								}
								
								report +="|";
								
//								if(strProperty[7] != null) {
//									report +=strProperty[7];
//									}
//								if(strProperty[8] != null) {
//									report +=" "+strProperty[8];
//								}
								
								buildingName = strProperty[7].trim()+" "+strProperty[8].trim();
								if(buildingName.length() > 100) {
									buildingName = buildingName.substring(0, 100);
									report += buildingName.trim(); 
								}else {
									report += buildingName.trim(); 
								}
								
								report +="|"+strProperty[9]+"|";
								
								if(strProperty[10].equals("")) {
									report +="|||";
								}else {
									if(strProperty[10].equals("SQFT")) {
										report +="Square feet|||";
									}else if(strProperty[10].equals("SQM")) {
										report +="Square metre|||";
									}else if(strProperty[10].equals("ACRES")) {
										report +="Acre|||";
									}else if(strProperty[10].equals("HQT")) {
										report +="Hectare|||";
									}else if(strProperty[10].equals("GUNTHA")) {
										report +="Gunta|||";
									}else {
										report +="|||";
									}
								}
								
//								if(strProperty[3] != null) {
//									report +=strProperty[3];
//								}
//								if(strProperty[4] != null) {
//									report +=" "+strProperty[4];
//								}
								
								report += floorNo.trim(); //Locality
								
								report +="||||||"+strProperty[11]+"|"+strProperty[14]+"|"+strProperty[13]+"|||||||||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from property list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
							//		System.out.println("record write to first file from property list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from property list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
							//		System.out.println("record write to second file from property list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						}
						else {
							if(reportAsset != null && !reportAsset.isEmpty()) {
								count=0;
								System.out.println("reportProperty is not null or empty. ");
							//	System.out.println("borrowerlist id checking with reportProperty list. ");
							for (int k = 0; k < reportAsset.size(); k++) {
								strAsset = reportAsset.get(k);
								
								for(int a=0;a<strAsset.length;a++) {
									if(strAsset[a] == null) {
										strAsset[a]="";
									}else {
										strAsset[a] = strAsset[a].replaceAll("&","AND");
										//System.out.println(strAsset[a]);
										strAsset[a] = strAsset[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
										//System.out.println(strAsset[a]);
										//strAsset[a] = strAsset[a].replaceAll("  "," ");
										//System.out.println(strAsset[a]);
										while(strAsset[a].contains("  ")){
											strAsset[a] = strAsset[a].replaceAll("  "," ");
											}
									}
								}
								
								if (borrowList.get(j).equals(strAsset[1])) {
									report="";
									report +="PRO|";
									
									if(strAsset[3].equalsIgnoreCase("Movable")) {
										report +="02|";
									}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
										report +="03|";
									}else {
										report +="02|";
									}
									
									if(strAsset[0].equalsIgnoreCase("Others")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="5||";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="7||";
										}else {
											report +="4||";
										}
									}else if(strAsset[0].equalsIgnoreCase("Hypothecation")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="4||";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="6||";
										}else {
											report +="4||";
										}
									}else {
										report +="4||";
									}
									
									if(strAsset[0].equalsIgnoreCase("Vehicles")) {
										report +="1|";
									}
									else if(strAsset[0].equalsIgnoreCase("Inventory")) {
										report +="2|";
									}
									else if(strAsset[0].equalsIgnoreCase("Receivables")) {
										report +="4|";
									}
									else if(strAsset[0].equalsIgnoreCase("Equipment and Machinery")) {
										report +="5|";
									}
									else if(strAsset[0].equalsIgnoreCase("Others")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="7|";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="12|";
										}else {
											report +="|";
										}
									}
									else if(strAsset[0].equalsIgnoreCase("Copyright")) {
										report +="8|";
									}
									else if(strAsset[0].equalsIgnoreCase("Patent")) {
										report +="9|";
									}
									else if(strAsset[0].equalsIgnoreCase("Licenses")) {
										report +="10|";
									}
									else if(strAsset[0].equalsIgnoreCase("IPR")) {
										report +="11|";
									}else {
										report +="|";
									}
									
									report +="|||||||||Hypothecation Agreement||";
									if(recordCount <= 1000) {
										//System.out.println("Writing data to first file from asset list");
										bufferedWriter.write(report);
										bufferedWriter.write("\r\n");
								//		System.out.println("record write to first file from asset list. recordCount = "+recordCount);
									}else {
										//System.out.println("Writing data to second file from asset list");
										bufferedWriterNew.write(report);
										bufferedWriterNew.write("\r\n");
								//		System.out.println("record write to second file from asset list. recordCount = "+recordCount);
									}
									report = "";
								}
							}
							}
						}
						
						if(reportDocuments != null && !reportDocuments.isEmpty()) {
							count=0;
							System.out.println("reportProperty is not null or empty. ");
						//	System.out.println("borrowerlist id checking with reportProperty list. ");
						for (int k = 0; k < reportDocuments.size(); k++) {
							strDoc = reportDocuments.get(k);
							
							for(int a=0;a<strDoc.length;a++) {
								if(strDoc[a] == null) {
									strDoc[a]="";
								}else {
									strDoc[a] = strDoc[a].replaceAll("&","AND");
									//System.out.println(strDoc[a]);
									strDoc[a] = strDoc[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strDoc[a]);
									//strDoc[a] = strDoc[a].replaceAll("  "," ");
									//System.out.println(strDoc[a]);
									while(strDoc[a].contains("  ")){
										strDoc[a] = strDoc[a].replaceAll("  "," ");
										}
								}
							}
							
							if (borrowList.get(j).equals(strDoc[1])) {
								report="";
								count++;
								report +="DOC|"+count+"|Others|";
								
								if (securityType.equals("IMMOVABLE")) {
									report +="Mortgage Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[8]+"|"+strDoc[5]+"|";
									if(strDoc[7].equals("")) {
										report+=strDoc[6]+"|";
									}else {
										report+=strDoc[7]+"|";
									}
								}else {
									report +="Hypothecation Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[7]+"|"+strDoc[5]+"|"+strDoc[6]+"|";
								}
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from document list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from document list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from document list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
						//			System.out.println("record write to second file from document list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if(reportLoan != null && !reportLoan.isEmpty()) {
							count=0;
							System.out.println("reportLoan is not null or empty. ");
					//		System.out.println("borrowerlist id checking with reportLoan list. ");
						for (int k = 0; k < reportLoan.size(); k++) {
							strLoan = reportLoan.get(k);
							
							for(int a=0;a<strLoan.length;a++) {
								if(strLoan[a] == null) {
									strLoan[a]="";
								}else {
									strLoan[a] = strLoan[a].replaceAll("&","AND");
									//System.out.println(strLoan[a]);
									strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strLoan[a]);
									//strLoan[a] = strLoan[a].replaceAll("  "," ");
									//System.out.println(strLoan[a]);
									while(strLoan[a].contains("  ")){
										strLoan[a] = strLoan[a].replaceAll("  "," ");
										}
								}
							}
							
							if (borrowList.get(j).equals(strLoan[1])) {
								report="";
								count++;
								report +="LON|"+count+"|";
								if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
										|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
										|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
										|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
										|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
										|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
										|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
									report +=strLoan[0]+"|";
								}else {
									report +="|";
								}
								
								report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from loan list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
						//			System.out.println("record write to first file from loan list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from loan list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
						//			System.out.println("record write to second file from loan list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						if(recordCount <= 1000) {
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write("\r\n");
						}
					}
				}
				System.out.println("Borrower List Records Done with Adding To report.");
				
//				Third Party List records 
				
				if (thirdPartyList != null && !thirdPartyList.isEmpty()) {
					System.out.println("thirdPartyList Records starts. recordCount = "+recordCount);
					
					for (int j = 0; j < thirdPartyList.size(); j++) {
						recordCount++;
//						if(recordCount > 2000) {
//							break;
//						}
						count=0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
						for (int k = 0; k < reportThirdParty.size(); k++) {
							str = reportThirdParty.get(k);
							if (thirdPartyList.get(j).equals(str[1])) {
								count++;
								amount = str[2];
							}
						}
						}
						
						
						if(securityType.equals("IMMOVABLE")) {
							if (reportProperty != null && !reportProperty.isEmpty()) {
								for (int k = 0; k < reportProperty.size(); k++) {
									strProperty = reportProperty.get(k);
									if (thirdPartyList.get(j).equals(strProperty[1])) {
										if(strProperty[15].equals("STOCKS AND RECEIVABLES")) {
											amount = strProperty[2];
										}
									}
								}
							}else {
								if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
									for (int k = 0; k < reportThirdParty.size(); k++) {
										str = reportThirdParty.get(k);
										if (thirdPartyList.get(j).equals(str[1])) {
											amount = str[2];
										}
									}
									}
							}
							}else if(securityType.equals("MOVABLE")){
								if (reportAsset != null && !reportAsset.isEmpty()) {
									for (int k = 0; k < reportAsset.size(); k++) {
										strAsset = reportAsset.get(k);
										if (thirdPartyList.get(j).equals(strAsset[1])) {
											if(strAsset[4].equals("STOCKS AND RECEIVABLES")) {
												amount = strAsset[2];
											}
										}
									}
								}else {
									if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
										for (int k = 0; k < reportThirdParty.size(); k++) {
											str = reportThirdParty.get(k);
											if (thirdPartyList.get(j).equals(str[1])) {
												amount = str[2];
											}
										}
										}
								}
							}
						
//						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
//						for (int k = 0; k < reportThirdParty.size(); k++) {
//							str = reportThirdParty.get(k);
//							if (thirdPartyList.get(j).equals(str[1])) {
//								docDate = str[11];
//							}
//						}
//						}
						
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								docDate = strLoan[6];
							}
						}
						if(docDate == null) {
							docDate = "";
						}
						
						report = "";
						report += "RH|" + recordCount + "|" + count + "|";
						count = 0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
							for (int k = 0; k < reportThirdParty.size(); k++) {
								strThiParty = reportThirdParty.get(k);
								if (thirdPartyList.get(j).equals(strThiParty[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;

						if (reportDocuments != null && !reportDocuments.isEmpty()) {
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								if (thirdPartyList.get(j).equals(strDoc[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								if (thirdPartyList.get(j).equals(strLoan[1])) {
									count++;
								}
							}
						}
						if (amount == null) {
							amount = "";
						}
//						report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
								report += count + "|" + docDate + "|" + amount + "|"
								+ thirdPartyList.get(j) + "|";
						
						if (securityType.equals("MOVABLE")) {
							report += "|";
						}
						
						if(recordCount <= 1000) {
							//System.out.println("Adding RH to first file => thirtpartyList");
							bufferedWriter.write(report);
							bufferedWriter.write("\r\n");
					//		System.out.println("Added RH to first file => thirtpartyList");
						}else {
							//System.out.println("Adding RH to second file => thirtpartyList");
							bufferedWriterNew.write(report);
							bufferedWriterNew.write("\r\n");
				//			System.out.println("Added RH to second file => thirtpartyList");
						}
						
						
						//Adding Records from List By security id
						count=0;
						
						if(reportThirdParty != null && !reportThirdParty.isEmpty()) {
							count=0;
							System.out.println("reportThirdParty is not null or empty. ");
					//		System.out.println("reportThirdParty id checking with reportThirdParty list. ");
						for (int k = 0; k < reportThirdParty.size(); k++) {
							strThiParty = reportThirdParty.get(k);
							
							for(int a=0;a<strThiParty.length;a++) {
								if(strThiParty[a] == null) {
									strThiParty[a]="";
								}else {
									strThiParty[a] = strThiParty[a].replaceAll("&","AND");
									//System.out.println(strThiParty[a]);
									strThiParty[a] = strThiParty[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strThiParty[a]);
									//strThiParty[a] = strThiParty[a].replaceAll("  "," ");
									//System.out.println(strThiParty[a]);
									while(strThiParty[a].contains("  ")){
										strThiParty[a] = strThiParty[a].replaceAll("  "," ");
										}
								}
							}
							
							if (thirdPartyList.get(j).equals(strThiParty[1])) {
								count++;
								report="";
								
								report +="TPM|"+count+"|";
								if(strThiParty[0].equalsIgnoreCase("IND") || strThiParty[0].equalsIgnoreCase("COM") || strThiParty[0].equalsIgnoreCase("HUF") || strThiParty[0].equalsIgnoreCase("COS") || strThiParty[0].equalsIgnoreCase("LLP") || strThiParty[0].equalsIgnoreCase("PAF") || strThiParty[0].equalsIgnoreCase("PRF") || strThiParty[0].equalsIgnoreCase("TRS")) {
									report +=strThiParty[0]+"|";
								}else {
									report +="|";
								}
								if(strThiParty[0].equalsIgnoreCase("COM")) {
									report +=strThiParty[3]+"|"+strThiParty[4]+"|"+strThiParty[9]+"|";
								}else {
									report +="|||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("IND")){
									report +="Mr.||"+strThiParty[3]+"||"+strThiParty[9]+"||";
								}else {
									report +="||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("PRF")){
									report +=strThiParty[3]+"|"+strThiParty[9]+"|PRF_COM|"+strThiParty[3]+"|"+strThiParty[4]+"||||||";
								}else {
									report +="||||||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("PAF")) {
									report +=strThiParty[3]+"|Mr.||"+strThiParty[3]+"|||"+strThiParty[9]+"|";
								}else {
									report +="|||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("TRS")) {
									report +=strThiParty[3]+"|"+strThiParty[9]+"|Mr.||"+strThiParty[3]+"|||";
								}else {
									report +="|||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("LLP")) {
									report +=strThiParty[3]+"|Mr.||"+strThiParty[3]+"|||"+strThiParty[9]+"|";
								}else {
									report +="|||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("HUF")) {
									report +="Mr.||"+strThiParty[3]+"||"+strThiParty[9]+"||";
								}else {
									report +="||||||";
								}
								
								if(strThiParty[0].equalsIgnoreCase("COS")) {
									report +=strThiParty[3]+"|||"+strThiParty[9]+"|";
								}else {
									report +="||||";
								}
								
								tpmPlotSurveyhouseNo = strThiParty[5];
								if(tpmPlotSurveyhouseNo.length() > 100) {
									tpmPlotSurveyhouseNo = tpmPlotSurveyhouseNo.substring(0, 100);
									report += tpmPlotSurveyhouseNo.trim()+"|"; 
								}else {
									report += tpmPlotSurveyhouseNo.trim()+"|"; 
								}
								
//								report +=strThiParty[5]+"|";
								
								report +="||||||"+strThiParty[6]+"|"+strThiParty[10]+"|"+strThiParty[8]+"|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from thirdparty list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from thirdparty list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from thirdparty list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from thirdparty list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
					}
						
						if(reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
							count=0;
							System.out.println("reportSecurityInterest is not null or empty. ");
					//		System.out.println("reportThirdParty id checking with reportSecurityInterest list. ");
						for (int k = 0; k < reportSecurityInterest.size(); k++) {
							strSecInt = reportSecurityInterest.get(k);
							
							for(int a=0;a<strSecInt.length;a++) {
								if(strSecInt[a] == null) {
									strSecInt[a]="";
								}else {
									strSecInt[a] = strSecInt[a].replaceAll("&","AND");
									//System.out.println(strSecInt[a]);
									strSecInt[a] = strSecInt[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strSecInt[a]);
									//strSecInt[a] = strSecInt[a].replaceAll("  "," ");
									//System.out.println(strSecInt[a]);
									while(strSecInt[a].contains("  ")){
										strSecInt[a] = strSecInt[a].replaceAll("  "," ");
										}
								}
							}
							
							if (thirdPartyList.get(j).equals(strSecInt[1])) {
								report="";
								report += "CHG|0510005|"+strSecInt[0]+"||||||"+strSecInt[3]+"|"+strSecInt[6]+"|"+strSecInt[5]+"|";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from security interest list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from security interest list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from security interest list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from security interest list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if (securityType.equals("IMMOVABLE")) {
						if(reportProperty != null && !reportProperty.isEmpty()) {
							count=0;
							System.out.println("reportProperty is not null or empty. ");
					//		System.out.println("reportThirdParty id checking with reportProperty list. ");
						for (int k = 0; k < reportProperty.size(); k++) {
							strProperty = reportProperty.get(k);
							
							for(int a=0;a<strProperty.length;a++) {
								if(strProperty[a] == null) {
									strProperty[a]="";
								}else {
									strProperty[a] = strProperty[a].replaceAll("&","AND");
									//System.out.println(strProperty[a]);
									strProperty[a] = strProperty[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strProperty[a]);
									//strProperty[a] = strProperty[a].replaceAll("  "," ");
									//System.out.println(strProperty[a]);
									while(strProperty[a].contains("  ")){
										strProperty[a] = strProperty[a].replaceAll("  "," ");
										}
								}
							}
							
							if (thirdPartyList.get(j).equals(strProperty[1])) {
								report="";
								report += "PRO|01|";
								if(strProperty[0] == null) {
									report +="|";
								}else {
									if(strProperty[0].equalsIgnoreCase("Residential Plot")) {
										report +="01|";
									}
									else if(strProperty[0].equalsIgnoreCase("Industrial Plot")) {
										report +="02|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Plot")) {
										report +="03|";
									}
									else if(strProperty[0].equalsIgnoreCase("Residential Flat")) {
										report +="04|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Flat")) {
										report +="05|";
									}
									else if(strProperty[0].equalsIgnoreCase("Row House")) {
										report +="06|";
									}
									else if(strProperty[0].equalsIgnoreCase("Bungalow") || strProperty[0].equalsIgnoreCase("Villa") || strProperty[0].equalsIgnoreCase("Bungalow/Villa")) {
										report +="07|";
									}else {
										report +="|";
									}
								}
								String addrr1="";
								if(!strProperty[3].equals("")) {
								if(strProperty[3].length() > 20) {
									addrr1 = strProperty[3].substring(0, 20);
									report +=addrr1.trim()+"|";
								}else {
									report +=strProperty[3]+"|";
								}
								}else {
									report +=strProperty[3]+"|";
								}
								surveyNo = strProperty[6].trim()+" "+strProperty[7].trim()+" "+strProperty[8].trim();
								if(surveyNo.length() > 20) {
									surveyNo = surveyNo.substring(0, 20);
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}else {
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}
//								report +=strProperty[6]+" "+strProperty[7]+" "+strProperty[8]+"||"; // Survey_no and house_id from clims
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//								}
//								if(strProperty[4] != null) {
//									report +=" "+strProperty[4];
//								}
								
								floorNo = strProperty[3].trim()+" "+strProperty[4].trim();
								if(floorNo.length() > 100) {
									floorNo = floorNo.substring(0, 100);
									report += floorNo.trim(); 
								}else {
									report += floorNo.trim(); 
								}
								
								report +="|";
								
//								if(strProperty[5] != null) {
//								report +=strProperty[5];
//								}
//							if(strProperty[6] != null) {
//								report +=" "+strProperty[6];
//							}
							
							buildingNo = strProperty[5].trim()+" "+strProperty[6].trim();
							if(buildingNo.length() > 100) {
								buildingNo = buildingNo.substring(0, 100);
								report += buildingNo.trim(); 
							}else {
								report += buildingNo.trim(); 
							}
							
							report +="|";
							
//							if(strProperty[7] != null) {
//								report +=strProperty[7];
//								}
//							if(strProperty[8] != null) {
//								report +=" "+strProperty[8];
//							}
							
							buildingName = strProperty[7].trim()+" "+strProperty[8].trim();
							if(buildingName.length() > 100) {
								buildingName = buildingName.substring(0, 100);
								report += buildingName.trim(); 
							}else {
								report += buildingName.trim(); 
							}
								
							report +="|"+strProperty[9]+"|";
							
							if(strProperty[10].equals("")) {
								report +="|||";
							}else {
								if(strProperty[10].equals("SQFT")) {
									report +="Square feet|||";
								}else if(strProperty[10].equals("SQM")) {
									report +="Square metre|||";
								}else if(strProperty[10].equals("ACRES")) {
									report +="Acre|||";
								}else if(strProperty[10].equals("HQT")) {
									report +="Hectare|||";
								}else if(strProperty[10].equals("GUNTHA")) {
									report +="Gunta|||";
								}else {
									report +="|||";
								}
							}
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//							}
//							if(strProperty[4] != null) {
//								report +=" "+strProperty[4];
//							}
							
							report += floorNo.trim(); //Locality
								
								report +="||||||"+strProperty[11]+"|"+strProperty[14]+"|"+strProperty[13]+"|||||||||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from Property list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from Property list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from Property list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from Property list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						}
						else {
							if(reportAsset != null && !reportAsset.isEmpty()) {
								count=0;
								System.out.println("reportAsset is not null or empty. ");
						//		System.out.println("reportThirdParty id checking with reportAsset list. ");
							for (int k = 0; k < reportAsset.size(); k++) {
								strAsset = reportAsset.get(k);
								
								for(int a=0;a<strAsset.length;a++) {
									if(strAsset[a] == null) {
										strAsset[a]="";
									}else {
										strAsset[a] = strAsset[a].replaceAll("&","AND");
										//System.out.println(strAsset[a]);
										strAsset[a] = strAsset[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
										//System.out.println(strAsset[a]);
										//strAsset[a] = strAsset[a].replaceAll("  "," ");
										//System.out.println(strAsset[a]);
										while(strAsset[a].contains("  ")){
											strAsset[a] = strAsset[a].replaceAll("  "," ");
											}
									}
								}
								
								if (thirdPartyList.get(j).equals(strAsset[1])) {
									report="";
									report +="PRO|";
									
									if(strAsset[3].equalsIgnoreCase("Movable")) {
										report +="02|";
									}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
										report +="03|";
									}else {
										report +="02|";
									}
									
									if(strAsset[0].equalsIgnoreCase("Others")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="5||";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="7||";
										}else {
											report +="4||";
										}
									}else if(strAsset[0].equalsIgnoreCase("Hypothecation")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="4||";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="6||";
										}else {
											report +="4||";
										}
									}else {
										report +="4||";
									}
									
									if(strAsset[0].equalsIgnoreCase("Vehicles")) {
										report +="1|";
									}
									else if(strAsset[0].equalsIgnoreCase("Inventory")) {
										report +="2|";
									}
									else if(strAsset[0].equalsIgnoreCase("Receivables")) {
										report +="4|";
									}
									else if(strAsset[0].equalsIgnoreCase("Equipment and Machinery")) {
										report +="5|";
									}
									else if(strAsset[0].equalsIgnoreCase("Others")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="7|";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="12|";
										}else {
											report +="|";
										}
									}
									else if(strAsset[0].equalsIgnoreCase("Copyright")) {
										report +="8|";
									}
									else if(strAsset[0].equalsIgnoreCase("Patent")) {
										report +="9|";
									}
									else if(strAsset[0].equalsIgnoreCase("Licenses")) {
										report +="10|";
									}
									else if(strAsset[0].equalsIgnoreCase("IPR")) {
										report +="11|";
									}else {
										report +="|";
									}
									
									report +="|||||||||Hypothecation Agreement||";
									if(recordCount <= 1000) {
										//System.out.println("Writing data to first file from asset list");
										bufferedWriter.write(report);
										bufferedWriter.write("\r\n");
				//						System.out.println("record write to first file from asset list. recordCount = "+recordCount);
									}else {
										//System.out.println("Writing data to second file from asset list");
										bufferedWriterNew.write(report);
										bufferedWriterNew.write("\r\n");
						//				System.out.println("record write to second file from asset list. recordCount = "+recordCount);
									}
									report = "";
								}
							}
							}
						}
						
						if(reportDocuments != null && !reportDocuments.isEmpty()) {
							count=0;
							System.out.println("reportDocuments is not null or empty. ");
						//	System.out.println("reportThirdParty id checking with reportDocuments list. ");
						for (int k = 0; k < reportDocuments.size(); k++) {
							strDoc = reportDocuments.get(k);
							
							for(int a=0;a<strDoc.length;a++) {
								if(strDoc[a] == null) {
									strDoc[a]="";
								}else {
									strDoc[a] = strDoc[a].replaceAll("&","AND");
									//System.out.println(strDoc[a]);
									strDoc[a] = strDoc[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strDoc[a]);
									//strDoc[a] = strDoc[a].replaceAll("  "," ");
									//System.out.println(strDoc[a]);
									while(strDoc[a].contains("  ")){
										strDoc[a] = strDoc[a].replaceAll("  "," ");
										}
								}
							}
							
							if (thirdPartyList.get(j).equals(strDoc[1])) {
								report="";
								count++;
								report +="DOC|"+count+"|Others|";
								
								if (securityType.equals("IMMOVABLE")) {
									report +="Mortgage Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[8]+"|"+strDoc[5]+"|";
									if(strDoc[7].equals("")) {
										report+=strDoc[6]+"|";
									}else {
										report+=strDoc[7]+"|";
									}
								}else {
									report +="Hypothecation Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[7]+"|"+strDoc[5]+"|"+strDoc[6]+"|";
								}
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from document list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
						//			System.out.println("record write to first file from document list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from document list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
						//			System.out.println("record write to second file from document list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if(reportLoan != null && !reportLoan.isEmpty()) {
							count=0;
							System.out.println("reportLoan is not null or empty. ");
						//	System.out.println("reportThirdParty id checking with reportLoan list. ");
						for (int k = 0; k < reportLoan.size(); k++) {
							strLoan = reportLoan.get(k);
							
							for(int a=0;a<strLoan.length;a++) {
								if(strLoan[a] == null) {
									strLoan[a]="";
								}else {
									strLoan[a] = strLoan[a].replaceAll("&","AND");
									//System.out.println(strLoan[a]);
									strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strLoan[a]);
									//strLoan[a] = strLoan[a].replaceAll("  "," ");
									//System.out.println(strLoan[a]);
									while(strLoan[a].contains("  ")){
										strLoan[a] = strLoan[a].replaceAll("  "," ");
										}
								}
							}
							
							if (thirdPartyList.get(j).equals(strLoan[1])) {
								report="";
								count++;
								report +="LON|"+count+"|";
								if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
										|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
										|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
										|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
										|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
										|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
										|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
									report +=strLoan[0]+"|";
								}else {
									report +="|";
								}
								
								report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from loan list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
						//			System.out.println("record write to first file from loan list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from loan list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
						//			System.out.println("record write to second file from loan list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						if(recordCount <= 1000) {
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write("\r\n");
						}
					}
				}
				
				System.out.println("Third Party List Records Done with Adding To report.");
				
//				Security Interest List records 
				
				if (securityInterestList != null && !securityInterestList.isEmpty()) {
					System.out.println("securityInterestList Records starts. recordCount = "+recordCount);
					
					for (int j = 0; j < securityInterestList.size(); j++) {
						recordCount++;
//						if(recordCount > 2000) {
//							break;
//						}
						count=0;
						if (reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
						for (int k = 0; k < reportSecurityInterest.size(); k++) {
							str = reportSecurityInterest.get(k);
							if (securityInterestList.get(j).equals(str[1])) {
								count++;
								amount = str[2];
							}
						}
						}
						
						//strSecInt = reportSecurityInterest.get(k);
						if(securityType.equals("IMMOVABLE")) {
							if (reportProperty != null && !reportProperty.isEmpty()) {
								for (int k = 0; k < reportProperty.size(); k++) {
									strProperty = reportProperty.get(k);
									if (reportSecurityInterest.get(j).equals(strProperty[1])) {
										if(strProperty[15].equals("STOCKS AND RECEIVABLES")) {
											amount = strProperty[2];
										}
									}
								}
							}else {
								if (reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
									for (int k = 0; k < reportSecurityInterest.size(); k++) {
										str = reportSecurityInterest.get(k);
										if (securityInterestList.get(j).equals(str[1])) {
											amount = str[2];
										}
									}
									}
							}
							}else if(securityType.equals("MOVABLE")){
								if (reportAsset != null && !reportAsset.isEmpty()) {
									for (int k = 0; k < reportAsset.size(); k++) {
										strAsset = reportAsset.get(k);
										if (reportSecurityInterest.get(j).equals(strAsset[1])) {
											if(strAsset[4].equals("STOCKS AND RECEIVABLES")) {
												amount = strAsset[2];
											}
										}
									}
								}else {
									if (reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
										for (int k = 0; k < reportSecurityInterest.size(); k++) {
											str = reportSecurityInterest.get(k);
											if (securityInterestList.get(j).equals(str[1])) {
												amount = str[2];
											}
										}
										}
										
								}
							}
						
//						if (reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
//						for (int k = 0; k < reportSecurityInterest.size(); k++) {
//							str = reportSecurityInterest.get(k);
//							if (securityInterestList.get(j).equals(str[1])) {
//								docDate = str[7];
//							}
//						}
//						}
						
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								docDate = strLoan[6];
							}
						}
						if(docDate == null) {
							docDate = "";
						}
						
						report = "";
						report += "RH|" + recordCount + "|" + count + "|";
						count = 0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
							for (int k = 0; k < reportThirdParty.size(); k++) {
								strThiParty = reportThirdParty.get(k);
								if (securityInterestList.get(j).equals(strThiParty[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;

						if (reportDocuments != null && !reportDocuments.isEmpty()) {
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								if (securityInterestList.get(j).equals(strDoc[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								if (securityInterestList.get(j).equals(strLoan[1])) {
									count++;
								}
							}
						}
						if (amount == null) {
							amount = "";
						}
						report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
								+ securityInterestList.get(j) + "|";
						
						if (securityType.equals("MOVABLE")) {
							report += "|";
						}
						
						if(recordCount <= 1000) {
							//System.out.println("Adding RH To first file report. securityInterestList");
							bufferedWriter.write(report);
							bufferedWriter.write("\r\n");
				//			System.out.println("Added RH To first file report. securityInterestList");
						}else {
							//System.out.println("Adding RH To second file report. securityInterestList");
							bufferedWriterNew.write(report);
							bufferedWriterNew.write("\r\n");
					//		System.out.println("Added RH To second file report. securityInterestList");
						}
						
						
						//Adding Records from List By security id
						count=0;
						
						if(reportSecurityInterest != null && !reportSecurityInterest.isEmpty()) {
							count=0;
							System.out.println("reportSecurityInterest is not null or empty. ");
				//			System.out.println("reportSecurityInterest id checking with reportSecurityInterest list. ");
						for (int k = 0; k < reportSecurityInterest.size(); k++) {
							strSecInt = reportSecurityInterest.get(k);
							
							for(int a=0;a<strSecInt.length;a++) {
								if(strSecInt[a] == null) {
									strSecInt[a]="";
								}else {
									strSecInt[a] = strSecInt[a].replaceAll("&","AND");
									//System.out.println(strSecInt[a]);
									strSecInt[a] = strSecInt[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strSecInt[a]);
									//strSecInt[a] = strSecInt[a].replaceAll("  "," ");
									//System.out.println(strSecInt[a]);
									while(strSecInt[a].contains("  ")){
										strSecInt[a] = strSecInt[a].replaceAll("  "," ");
										}
								}
							}
							
							if (securityInterestList.get(j).equals(strSecInt[1])) {
								report="";
								report += "CHG|0510005|"+strSecInt[0]+"||||||"+strSecInt[3]+"|"+strSecInt[6]+"|"+strSecInt[5]+"|";
								if(recordCount <= 1000) {
								//	System.out.println("Writing data to first file from security InterestList list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from security InterestList list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from security InterestList list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from security InterestList list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if (securityType.equals("IMMOVABLE")) {
						if(reportProperty != null && !reportProperty.isEmpty()) {
							count=0;
							System.out.println("reportProperty is not null or empty. ");
					//		System.out.println("reportSecurityInterest id checking with reportProperty list. ");
						for (int k = 0; k < reportProperty.size(); k++) {
							strProperty = reportProperty.get(k);
							
							for(int a=0;a<strProperty.length;a++) {
								if(strProperty[a] == null) {
									strProperty[a]="";
								}else {
									strProperty[a] = strProperty[a].replaceAll("&","AND");
									//System.out.println(strProperty[a]);
									strProperty[a] = strProperty[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strProperty[a]);
									//strProperty[a] = strProperty[a].replaceAll("  "," ");
									//System.out.println(strProperty[a]);
									while(strProperty[a].contains("  ")){
										strProperty[a] = strProperty[a].replaceAll("  "," ");
										}
								}
							}
							
							if (securityInterestList.get(j).equals(strProperty[1])) {
								report="";
								report += "PRO|01|";
								if(strProperty[0] == null) {
									report +="|";
								}else {
									if(strProperty[0].equalsIgnoreCase("Residential Plot")) {
										report +="01|";
									}
									else if(strProperty[0].equalsIgnoreCase("Industrial Plot")) {
										report +="02|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Plot")) {
										report +="03|";
									}
									else if(strProperty[0].equalsIgnoreCase("Residential Flat")) {
										report +="04|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Flat")) {
										report +="05|";
									}
									else if(strProperty[0].equalsIgnoreCase("Row House")) {
										report +="06|";
									}
									else if(strProperty[0].equalsIgnoreCase("Bungalow") || strProperty[0].equalsIgnoreCase("Villa") || strProperty[0].equalsIgnoreCase("Bungalow/Villa")) {
										report +="07|";
									}else {
										report +="|";
									}
								}
								String addrr1="";
								if(!strProperty[3].equals("")) {
								if(strProperty[3].length() > 20) {
									addrr1 = strProperty[3].substring(0, 20);
									report +=addrr1.trim()+"|";
								}else {
									report +=strProperty[3]+"|";
								}
								}else {
									report +=strProperty[3]+"|";
								}
								surveyNo = strProperty[6].trim()+" "+strProperty[7].trim()+" "+strProperty[8].trim();
								if(surveyNo.length() > 20) {
									surveyNo = surveyNo.substring(0, 20);
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}else {
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}
//								report +=strProperty[6]+" "+strProperty[7]+" "+strProperty[8]+"||"; // Survey_no and house_id from clims
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//								}
//								if(strProperty[4] != null) {
//									report +=" "+strProperty[4];
//								}
								
								floorNo = strProperty[3].trim()+" "+strProperty[4].trim();
								if(floorNo.length() > 100) {
									floorNo = floorNo.substring(0, 100);
									report += floorNo.trim(); 
								}else {
									report += floorNo.trim(); 
								}
								
								report +="|";
								
//								if(strProperty[5] != null) {
//								report +=strProperty[5];
//								}
//							if(strProperty[6] != null) {
//								report +=" "+strProperty[6];
//							}
							
							buildingNo = strProperty[5].trim()+" "+strProperty[6].trim();
							if(buildingNo.length() > 100) {
								buildingNo = buildingNo.substring(0, 100);
								report += buildingNo.trim(); 
							}else {
								report += buildingNo.trim(); 
							}
							
							report +="|";
							
//							if(strProperty[7] != null) {
//								report +=strProperty[7];
//								}
//							if(strProperty[8] != null) {
//								report +=" "+strProperty[8];
//							}
							
							buildingName = strProperty[7].trim()+" "+strProperty[8].trim();
							if(buildingName.length() > 100) {
								buildingName = buildingName.substring(0, 100);
								report += buildingName.trim(); 
							}else {
								report += buildingName.trim(); 
							}
								
							report +="|"+strProperty[9]+"|";
							
							if(strProperty[10].equals("")) {
								report +="|||";
							}else {
								if(strProperty[10].equals("SQFT")) {
									report +="Square feet|||";
								}else if(strProperty[10].equals("SQM")) {
									report +="Square metre|||";
								}else if(strProperty[10].equals("ACRES")) {
									report +="Acre|||";
								}else if(strProperty[10].equals("HQT")) {
									report +="Hectare|||";
								}else if(strProperty[10].equals("GUNTHA")) {
									report +="Gunta|||";
								}else {
									report +="|||";
								}
							}
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//							}
//							if(strProperty[4] != null) {
//								report +=" "+strProperty[4];
//							}
							
							report += floorNo.trim(); //Locality
								
								report +="||||||"+strProperty[11]+"|"+strProperty[14]+"|"+strProperty[13]+"|||||||||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from property list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from property list. recordCount = "+recordCount);
								}else {
								//	System.out.println("Writing data to second file from property list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from property list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						}
						else {
							if(reportAsset != null && !reportAsset.isEmpty()) {
								count=0;
								System.out.println("reportAsset is not null or empty. ");
					//			System.out.println("reportSecurityInterest id checking with reportAsset list.");
							for (int k = 0; k < reportAsset.size(); k++) {
								strAsset = reportAsset.get(k);
								
								for(int a=0;a<strAsset.length;a++) {
									if(strAsset[a] == null) {
										strAsset[a]="";
									}else {
										strAsset[a] = strAsset[a].replaceAll("&","AND");
										//System.out.println(strAsset[a]);
										strAsset[a] = strAsset[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
										//System.out.println(strAsset[a]);
										//strAsset[a] = strAsset[a].replaceAll("  "," ");
										//System.out.println(strAsset[a]);
										while(strAsset[a].contains("  ")){
											strAsset[a] = strAsset[a].replaceAll("  "," ");
											}
										
									}
								}
								
								if (securityInterestList.get(j).equals(strAsset[1])) {
									report="";
									report +="PRO|";
									
									if(strAsset[3].equalsIgnoreCase("Movable")) {
										report +="02|";
									}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
										report +="03|";
									}else {
										report +="02|";
									}
									
									if(strAsset[0].equalsIgnoreCase("Others")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="5||";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="7||";
										}else {
											report +="4||";
										}
									}else if(strAsset[0].equalsIgnoreCase("Hypothecation")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="4||";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="6||";
										}else {
											report +="4||";
										}
									}else {
										report +="4||";
									}
									
									if(strAsset[0].equalsIgnoreCase("Vehicles")) {
										report +="1|";
									}
									else if(strAsset[0].equalsIgnoreCase("Inventory")) {
										report +="2|";
									}
									else if(strAsset[0].equalsIgnoreCase("Receivables")) {
										report +="4|";
									}
									else if(strAsset[0].equalsIgnoreCase("Equipment and Machinery")) {
										report +="5|";
									}
									else if(strAsset[0].equalsIgnoreCase("Others")) {
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="7|";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="12|";
										}else {
											report +="|";
										}
									}
									else if(strAsset[0].equalsIgnoreCase("Copyright")) {
										report +="8|";
									}
									else if(strAsset[0].equalsIgnoreCase("Patent")) {
										report +="9|";
									}
									else if(strAsset[0].equalsIgnoreCase("Licenses")) {
										report +="10|";
									}
									else if(strAsset[0].equalsIgnoreCase("IPR")) {
										report +="11|";
									}else {
										report +="|";
									}
									
									report +="|||||||||Hypothecation Agreement||";
									if(recordCount <= 1000) {
									//	System.out.println("Writing data to first file from asset list");
										bufferedWriter.write(report);
										bufferedWriter.write("\r\n");
						//				System.out.println("record write to first file from asset list. recordCount = "+recordCount);
									}else {
									//	System.out.println("Writing data to second file from asset list");
										bufferedWriterNew.write(report);
										bufferedWriterNew.write("\r\n");
						//				System.out.println("record write to second file from asset list. recordCount = "+recordCount);
									}
									report = "";
								}
							}
							}
						}
						
						if(reportDocuments != null && !reportDocuments.isEmpty()) {
							count=0;
							System.out.println("reportDocuments is not null or empty. ");
					//		System.out.println("reportSecurityInterest id checking with reportDocuments list.");
						for (int k = 0; k < reportDocuments.size(); k++) {
							strDoc = reportDocuments.get(k);
							
							for(int a=0;a<strDoc.length;a++) {
								if(strDoc[a] == null) {
									strDoc[a]="";
								}else {
									strDoc[a] = strDoc[a].replaceAll("&","AND");
									//System.out.println(strDoc[a]);
									strDoc[a] = strDoc[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strDoc[a]);
									//strDoc[a] = strDoc[a].replaceAll("  "," ");
									//System.out.println(strDoc[a]);
									while(strDoc[a].contains("  ")){
										strDoc[a] = strDoc[a].replaceAll("  "," ");
										}
									
								}
							}
							
							if (securityInterestList.get(j).equals(strDoc[1])) {
								report="";
								count++;
								report +="DOC|"+count+"|Others|";
								
								if (securityType.equals("IMMOVABLE")) {
									report +="Mortgage Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[8]+"|"+strDoc[5]+"|";
									if(strDoc[7].equals("")) {
										report+=strDoc[6]+"|";
									}else {
										report+=strDoc[7]+"|";
									}
								}else {
									report +="Hypothecation Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[7]+"|"+strDoc[5]+"|"+strDoc[6]+"|";
								}
								if(recordCount <= 1000) {
									//System.out.println("Writing data to second file from document list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
				//					System.out.println("record write to second file from document list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from document list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
				//					System.out.println("record write to second file from document list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if(reportLoan != null && !reportLoan.isEmpty()) {
							count=0;
							System.out.println("reportLoan is not null or empty. ");
				//			System.out.println("reportSecurityInterest id checking with reportLoan list.");
						for (int k = 0; k < reportLoan.size(); k++) {
							strLoan = reportLoan.get(k);
							
							for(int a=0;a<strLoan.length;a++) {
								if(strLoan[a] == null) {
									strLoan[a]="";
								}else {
									strLoan[a] = strLoan[a].replaceAll("&","AND");
									//System.out.println(strLoan[a]);
									strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strLoan[a]);
									//strLoan[a] = strLoan[a].replaceAll("  "," ");
									//System.out.println(strLoan[a]);
									while(strLoan[a].contains("  ")){
										strLoan[a] = strLoan[a].replaceAll("  "," ");
										}
								}
							}
							
							if (securityInterestList.get(j).equals(strLoan[1])) {
								report="";
								count++;
								report +="LON|"+count+"|";
								if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
										|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
										|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
										|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
										|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
										|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
										|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
									report +=strLoan[0]+"|";
								}else {
									report +="|";
								}
								
								report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from loan list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
				//					System.out.println("record write to first file from loan list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from loan list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from loan list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						if(recordCount <= 1000) {
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write("\r\n");
						}
					}
				}

				System.out.println("Security Interest List Records Done with Adding To report.");
				
				if (securityType.equals("IMMOVABLE")) {
					
//				Property List records 
					
				if (propertyList != null && !propertyList.isEmpty()) {
					System.out.println("propertyList Records starts. recordCount = "+recordCount);
					
					for (int j = 0; j < propertyList.size(); j++) {
						recordCount++;
//						if(recordCount > 2000) {
//							break;
//						}
						count=0;
						if (reportProperty != null && !reportProperty.isEmpty()) {
						for (int k = 0; k < reportProperty.size(); k++) {
							str = reportProperty.get(k);
							if (propertyList.get(j).equals(str[1])) {
								count++;
								amount = str[2];
							}
						}
						}
//						if (reportProperty != null && !reportProperty.isEmpty()) {
//							for (int k = 0; k < reportProperty.size(); k++) {
//								str = reportProperty.get(k);
//								if (propertyList.get(j).equals(str[1])) {
//									docDate = str[7];
//								}
//							}
//							}
						
						
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								docDate = strLoan[6];
							}
						}
						if(docDate == null) {
							docDate = "";
						}
						
						report = "";
						report += "RH|" + recordCount + "|" + count + "|";
						count = 0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
							for (int k = 0; k < reportThirdParty.size(); k++) {
								strThiParty = reportThirdParty.get(k);
								if (propertyList.get(j).equals(strThiParty[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;

						if (reportDocuments != null && !reportDocuments.isEmpty()) {
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								if (propertyList.get(j).equals(strDoc[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								if (propertyList.get(j).equals(strLoan[1])) {
									count++;
								}
							}
						}
						if (amount == null) {
							amount = "";
						}
//						report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
						report += count + "|" + docDate + "|" + amount + "|"
								+ propertyList.get(j) + "|";
						
						if (securityType.equals("MOVABLE")) {
							report += "|";
						}
						
						if(recordCount <= 1000) {
							//System.out.println("Adding RH To first file report .PropertyList");
							bufferedWriter.write(report);
							bufferedWriter.write("\r\n");
			//				System.out.println("Added RH To first file report .PropertyList");
						}else {
							//System.out.println("Adding RH To second file report .PropertyList");
							bufferedWriterNew.write(report);
							bufferedWriterNew.write("\r\n");
				//			System.out.println("Added RH To second file report .PropertyList");
						}
						
						
						//Adding Records from List By security id
						count=0;
						
						if(reportProperty != null && !reportProperty.isEmpty()) {
							count=0;
							System.out.println("reportProperty is not null or empty. ");
				//			System.out.println("propertyList id checking with reportProperty list.");
						for (int k = 0; k < reportProperty.size(); k++) {
							strProperty = reportProperty.get(k);
							
							for(int a=0;a<strProperty.length;a++) {
								if(strProperty[a] == null) {
									strProperty[a]="";
								}else {
									strProperty[a] = strProperty[a].replaceAll("&","AND");
									//System.out.println(strProperty[a]);
									strProperty[a] = strProperty[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strProperty[a]);
									//strProperty[a] = strProperty[a].replaceAll("  "," ");
									//System.out.println(strProperty[a]);
									while(strProperty[a].contains("  ")){
										strProperty[a] = strProperty[a].replaceAll("  "," ");
										}
								}
							}
							
							if (propertyList.get(j).equals(strProperty[1])) {
								report="";
								report += "PRO|01|";
								if(strProperty[0] == null) {
									report +="|";
								}else {
									if(strProperty[0].equalsIgnoreCase("Residential Plot")) {
										report +="01|";
									}
									else if(strProperty[0].equalsIgnoreCase("Industrial Plot")) {
										report +="02|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Plot")) {
										report +="03|";
									}
									else if(strProperty[0].equalsIgnoreCase("Residential Flat")) {
										report +="04|";
									}
									else if(strProperty[0].equalsIgnoreCase("Commercial Flat")) {
										report +="05|";
									}
									else if(strProperty[0].equalsIgnoreCase("Row House")) {
										report +="06|";
									}
									else if(strProperty[0].equalsIgnoreCase("Bungalow") || strProperty[0].equalsIgnoreCase("Villa") || strProperty[0].equalsIgnoreCase("Bungalow/Villa")) {
										report +="07|";
									}else {
										report +="|";
									}
								}
								String addrr1="";
								if(!strProperty[3].equals("")) {
								if(strProperty[3].length() > 20) {
									addrr1 = strProperty[3].substring(0, 20);
									report +=addrr1.trim()+"|";
								}else {
									report +=strProperty[3]+"|";
								}
								}else {
									report +=strProperty[3]+"|";
								}
								surveyNo = strProperty[6].trim()+" "+strProperty[7].trim()+" "+strProperty[8].trim();
								if(surveyNo.length() > 20) {
									surveyNo = surveyNo.substring(0, 20);
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}else {
									report += surveyNo.trim()+"|"+strProperty[5].trim()+"|"; // Survey_no and house_id from clims
								}
//								report +=strProperty[6]+" "+strProperty[7]+" "+strProperty[8]+"||"; // Survey_no and house_id from clims 
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//								}
//								if(strProperty[4] != null) {
//									report +=" "+strProperty[4];
//								}
								
								floorNo = strProperty[3].trim()+" "+strProperty[4].trim();
								if(floorNo.length() > 100) {
									floorNo = floorNo.substring(0, 100);
									report += floorNo.trim(); 
								}else {
									report += floorNo.trim(); 
								}
								
								report +="|";
								
//								if(strProperty[5] != null) {
//								report +=strProperty[5];
//								}
//							if(strProperty[6] != null) {
//								report +=" "+strProperty[6];
//							}
							
							buildingNo = strProperty[5].trim()+" "+strProperty[6].trim();
							if(buildingNo.length() > 100) {
								buildingNo = buildingNo.substring(0, 100);
								report += buildingNo.trim(); 
							}else {
								report += buildingNo.trim(); 
							}
							
							report +="|";
							
//							if(strProperty[7] != null) {
//								report +=strProperty[7];
//								}
//							if(strProperty[8] != null) {
//								report +=" "+strProperty[8];
//							}
							
							buildingName = strProperty[7].trim()+" "+strProperty[8].trim();
							if(buildingName.length() > 100) {
								buildingName = buildingName.substring(0, 100);
								report += buildingName.trim(); 
							}else {
								report += buildingName.trim(); 
							}
								
							report +="|"+strProperty[9]+"|";
							
							if(strProperty[10].equals("")) {
								report +="|||";
							}else {
								if(strProperty[10].equals("SQFT")) {
									report +="Square feet|||";
								}else if(strProperty[10].equals("SQM")) {
									report +="Square metre|||";
								}else if(strProperty[10].equals("ACRES")) {
									report +="Acre|||";
								}else if(strProperty[10].equals("HQT")) {
									report +="Hectare|||";
								}else if(strProperty[10].equals("GUNTHA")) {
									report +="Gunta|||";
								}else {
									report +="|||";
								}
							}
								
//								if(strProperty[3] != null) {
//								report +=strProperty[3];
//							}
//							if(strProperty[4] != null) {
//								report +=" "+strProperty[4];
//							}
							
							report += floorNo.trim(); //Locality
								
								report +="||||||"+strProperty[11]+"|"+strProperty[14]+"|"+strProperty[13]+"|||||||||";
								if(recordCount <= 1000) {
								//	System.out.println("Writing data to first file from property list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from property list. recordCount = "+recordCount);
								}else {
								//	System.out.println("Writing data to second file from property list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
					//				System.out.println("record write to second file from property list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						
						if(reportDocuments != null && !reportDocuments.isEmpty()) {
							count=0;
							System.out.println("reportDocuments is not null or empty. ");
					//		System.out.println("propertyList id checking with reportDocuments list.");
						for (int k = 0; k < reportDocuments.size(); k++) {
							strDoc = reportDocuments.get(k);
							
							for(int a=0;a<strDoc.length;a++) {
								if(strDoc[a] == null) {
									strDoc[a]="";
								}else {
									strDoc[a] = strDoc[a].replaceAll("&","AND");
									//System.out.println(strDoc[a]);
									strDoc[a] = strDoc[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strDoc[a]);
									//strDoc[a] = strDoc[a].replaceAll("  "," ");
									//System.out.println(strDoc[a]);
									while(strDoc[a].contains("  ")){
										strDoc[a] = strDoc[a].replaceAll("  "," ");
										}
								}
							}
							
							if (propertyList.get(j).equals(strDoc[1])) {
								report="";
								count++;
								report +="DOC|"+count+"|Others|";
								
								if (securityType.equals("IMMOVABLE")) {
									report +="Mortgage Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[8]+"|"+strDoc[5]+"|";
									if(strDoc[7].equals("")) {
										report+=strDoc[6]+"|";
									}else {
										report+=strDoc[7]+"|";
									}
								}else {
									report +="Hypothecation Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[7]+"|"+strDoc[5]+"|"+strDoc[6]+"|";
								}
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from document list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
									//System.out.println("record write to first file from document list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from document list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
									//System.out.println("record write to second file from document list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if(reportLoan != null && !reportLoan.isEmpty()) {
							count=0;
							System.out.println("reportLoan is not null or empty. ");
							//System.out.println("propertyList id checking with reportLoan list.");
						for (int k = 0; k < reportLoan.size(); k++) {
							strLoan = reportLoan.get(k);
							
							for(int a=0;a<strLoan.length;a++) {
								if(strLoan[a] == null) {
									strLoan[a]="";
								}else {
									strLoan[a] = strLoan[a].replaceAll("&","AND");
									//System.out.println(strLoan[a]);
									strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strLoan[a]);
									//strLoan[a] = strLoan[a].replaceAll("  "," ");
									//System.out.println(strLoan[a]);
									while(strLoan[a].contains("  ")){
										strLoan[a] = strLoan[a].replaceAll("  "," ");
										}
								}
							}
							
							if (propertyList.get(j).equals(strLoan[1])) {
								report="";
								count++;
								report +="LON|"+count+"|";
								if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
										|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
										|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
										|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
										|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
										|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
										|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
									report +=strLoan[0]+"|";
								}else {
									report +="|";
								}
								
								report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from loan list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
								//	System.out.println("record write to first file from loan list. recordCount = "+recordCount);
								}else {
								//	System.out.println("Writing data to second file from loan list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
								//	System.out.println("record write to second file from loan list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						if(recordCount <= 1000) {
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write("\r\n");
						}
					}
				}
				System.out.println("Property List Records Done with Adding To report.");
				}
				else {
					
//					Asset List records
					
					if (assetList != null && !assetList.isEmpty()) {
						System.out.println("assetlist Records starts. recordCount = "+recordCount);
						
						for (int j = 0; j < assetList.size(); j++) {
							recordCount++;
//							if(recordCount > 2000) {
//								break;
//							}
							count=0;
							if (reportAsset != null && !reportAsset.isEmpty()) {
							for (int k = 0; k < reportAsset.size(); k++) {
								str = reportAsset.get(k);
								if (assetList.get(j).equals(str[1])) {
									count++;
									amount = str[2];
								}
							}
							}
							
//							if (reportAsset != null && !reportAsset.isEmpty()) {
//							for (int k = 0; k < reportAsset.size(); k++) {
//								str = reportAsset.get(k);
//								if (assetList.get(j).equals(str[1])) {
//									docDate = str[4];
//								}
//							}
//							}
							
							if (reportLoan != null && !reportLoan.isEmpty()) {
								for (int k = 0; k < reportLoan.size(); k++) {
									strLoan = reportLoan.get(k);
									docDate = strLoan[6];
								}
							}
							if(docDate == null) {
								docDate = "";
							}
							
							report = "";
							report += "RH|" + recordCount + "|" + count + "|";
							count = 0;
							if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
								for (int k = 0; k < reportThirdParty.size(); k++) {
									strThiParty = reportThirdParty.get(k);
									if (assetList.get(j).equals(strThiParty[1])) {
										count++;
									}
								}
							}
							report += count + "|";
							count = 0;

							if (reportDocuments != null && !reportDocuments.isEmpty()) {
								for (int k = 0; k < reportDocuments.size(); k++) {
									strDoc = reportDocuments.get(k);
									if (assetList.get(j).equals(strDoc[1])) {
										count++;
									}
								}
							}
							report += count + "|";
							count = 0;
							if (reportLoan != null && !reportLoan.isEmpty()) {
								for (int k = 0; k < reportLoan.size(); k++) {
									strLoan = reportLoan.get(k);
									if (assetList.get(j).equals(strLoan[1])) {
										count++;
									}
								}
							}
							if (amount == null) {
								amount = "";
							}
//							report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
							report += count + "|" + docDate + "|" + amount + "|"
									+ assetList.get(j) + "|";
							
							if (securityType.equals("MOVABLE")) {
								report += "|";
							}
							
							if(recordCount <= 1000) {
								//System.out.println("Adding RH to first file report. AssetList");
								bufferedWriter.write(report);
								bufferedWriter.write("\r\n");
						//		System.out.println("Added RH to first file report. AssetList");
							}else {
								//System.out.println("Adding RH to second file report. AssetList");
								bufferedWriterNew.write(report);
								bufferedWriterNew.write("\r\n");
							//	System.out.println("Added RH to second file report. AssetList");
							}
							
							
							//Adding Records from List By security id
							count=0;
							
								if(reportAsset != null && !reportAsset.isEmpty()) {
									count=0;
									System.out.println("reportAsset is not null or empty. ");
								//	System.out.println("reportAsset id checking with reportAsset list.");
								for (int k = 0; k < reportAsset.size(); k++) {
									strAsset = reportAsset.get(k);
									
									for(int a=0;a<strAsset.length;a++) {
										if(strAsset[a] == null) {
											strAsset[a]="";
										}else {
											strAsset[a] = strAsset[a].replaceAll("&","AND");
											//System.out.println(strAsset[a]);
											strAsset[a] = strAsset[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
											//System.out.println(strAsset[a]);
											//strAsset[a] = strAsset[a].replaceAll("  "," ");
											//System.out.println(strAsset[a]);
											while(strAsset[a].contains("  ")){
												strAsset[a] = strAsset[a].replaceAll("  "," ");
												}
										}
									}
									
									if (assetList.get(j).equals(strAsset[1])) {
										report="";
										report +="PRO|";
										
										if(strAsset[3].equalsIgnoreCase("Movable")) {
											report +="02|";
										}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
											report +="03|";
										}else {
											report +="02|";
										}
										
										if(strAsset[0].equalsIgnoreCase("Others")) {
											if(strAsset[3].equalsIgnoreCase("Movable")) {
												report +="5||";
											}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
												report +="7||";
											}else {
												report +="4||";
											}
										}else if(strAsset[0].equalsIgnoreCase("Hypothecation")) {
											if(strAsset[3].equalsIgnoreCase("Movable")) {
												report +="4||";
											}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
												report +="6||";
											}else {
												report +="4||";
											}
										}else {
											report +="4||";
										}
										
										if(strAsset[0].equalsIgnoreCase("Vehicles")) {
											report +="1|";
										}
										else if(strAsset[0].equalsIgnoreCase("Inventory")) {
											report +="2|";
										}
										else if(strAsset[0].equalsIgnoreCase("Receivables")) {
											report +="4|";
										}
										else if(strAsset[0].equalsIgnoreCase("Equipment and Machinery")) {
											report +="5|";
										}
										else if(strAsset[0].equalsIgnoreCase("Others")) {
											if(strAsset[3].equalsIgnoreCase("Movable")) {
												report +="7|";
											}else if(strAsset[3].equalsIgnoreCase("Intangible")) {
												report +="12|";
											}else {
												report +="|";
											}
										}
										else if(strAsset[0].equalsIgnoreCase("Copyright")) {
											report +="8|";
										}
										else if(strAsset[0].equalsIgnoreCase("Patent")) {
											report +="9|";
										}
										else if(strAsset[0].equalsIgnoreCase("Licenses")) {
											report +="10|";
										}
										else if(strAsset[0].equalsIgnoreCase("IPR")) {
											report +="11|";
										}else {
											report +="|";
										}
										
										report +="|||||||||Hypothecation Agreement||";
										if(recordCount <= 1000) {
											//System.out.println("Writing data to first file from asset list");
											bufferedWriter.write(report);
											bufferedWriter.write("\r\n");
									//		System.out.println("record write to first file from asset list. recordCount = "+recordCount);
										}else {
											//System.out.println("Writing data to second file from asset list");
											bufferedWriterNew.write(report);
											bufferedWriterNew.write("\r\n");
										//	System.out.println("record write to second file from asset list. recordCount = "+recordCount);
										}
										report = "";
									}
								}
								}
							
							if(reportDocuments != null && !reportDocuments.isEmpty()) {
								count=0;
								System.out.println("reportDocuments is not null or empty. ");
						//		System.out.println("reportAsset id checking with reportDocuments list.");
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								
								for(int a=0;a<strDoc.length;a++) {
									if(strDoc[a] == null) {
										strDoc[a]="";
									}else {
										strDoc[a] = strDoc[a].replaceAll("&","AND");
										//System.out.println(strDoc[a]);
										strDoc[a] = strDoc[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
										//System.out.println(strDoc[a]);
										//strDoc[a] = strDoc[a].replaceAll("  "," ");
										//System.out.println(strDoc[a]);
										while(strDoc[a].contains("  ")){
											strDoc[a] = strDoc[a].replaceAll("  "," ");
											}
									}
								}
								
								if (assetList.get(j).equals(strDoc[1])) {
									report="";
									count++;
									report +="DOC|"+count+"|Others|";
									
									if (securityType.equals("IMMOVABLE")) {
										report +="Mortgage Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[8]+"|"+strDoc[5]+"|";
										if(strDoc[7].equals("")) {
											report+=strDoc[6]+"|";
										}else {
											report+=strDoc[7]+"|";
										}
									}else {
										report +="Hypothecation Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[7]+"|"+strDoc[5]+"|"+strDoc[6]+"|";
									}
									
									if(recordCount <= 1000) {
										//System.out.println("Writing data to first file from document list");
										bufferedWriter.write(report);
										bufferedWriter.write("\r\n");
							//			System.out.println("record write to first file from document list. recordCount = "+recordCount);
									}else {
										//System.out.println("Writing data to second file from document list");
										bufferedWriterNew.write(report);
										bufferedWriterNew.write("\r\n");
							//			System.out.println("record write to second file from document list. recordCount = "+recordCount);
									}
									report = "";
								}
							}
							}
							
							if(reportLoan != null && !reportLoan.isEmpty()) {
								count=0;
								System.out.println("reportLoan is not null or empty. ");
						//		System.out.println("reportAsset id checking with reportLoan list.");
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								
								for(int a=0;a<strLoan.length;a++) {
									if(strLoan[a] == null) {
										strLoan[a]="";
									}else {
										strLoan[a] = strLoan[a].replaceAll("&","AND");
										//System.out.println(strLoan[a]);
										strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
										//System.out.println(strLoan[a]);
										//strLoan[a] = strLoan[a].replaceAll("  "," ");
										//System.out.println(strLoan[a]);
										while(strLoan[a].contains("  ")){
											strLoan[a] = strLoan[a].replaceAll("  "," ");
											}
									}
								}
								
								if (assetList.get(j).equals(strLoan[1])) {
									report="";
									count++;
									report +="LON|"+count+"|";
									if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
											|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
											|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
											|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
											|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
											|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
											|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
										report +=strLoan[0]+"|";
									}else {
										report +="|";
									}
									
									report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
									if(recordCount <= 1000) {
										//System.out.println("Writing data to first file from loan list");
										bufferedWriter.write(report);
										bufferedWriter.write("\r\n");
							//			System.out.println("record write to first file from loan list. recordCount = "+recordCount);
									}else {
										//System.out.println("Writing data to second file from loan list");
										bufferedWriterNew.write(report);
										bufferedWriterNew.write("\r\n");
							//			System.out.println("record write to second file from loan list. recordCount = "+recordCount);
									}
									report = "";
								}
							}
							}
							if(recordCount <= 1000) {
								bufferedWriter.write("\r\n");
							}else {
								bufferedWriterNew.write("\r\n");
							}
						}
					}
					System.out.println("Asset List Records Done with Adding To report.");
				}
				
//				Documents List records
				
				if (documentsList != null && !documentsList.isEmpty()) {
					System.out.println("documentsList Records starts. recordCount = "+recordCount);
					
					for (int j = 0; j < documentsList.size(); j++) {
						recordCount++;
//						if(recordCount > 2000) {
//							break;
//						}
						count=0;
						if (reportDocuments != null && !reportDocuments.isEmpty()) {
						for (int k = 0; k < reportDocuments.size(); k++) {
							str = reportDocuments.get(k);
							if (documentsList.get(j).equals(str[1])) {
								count++;
								amount = str[2];
							}
						}
						}
						
//						if (securityType.equals("IMMOVABLE")) {
//						if (reportDocuments != null && !reportDocuments.isEmpty()) {
//						for (int k = 0; k < reportDocuments.size(); k++) {
//							str = reportDocuments.get(k);
//							if (documentsList.get(j).equals(str[1])) {
//								docDate = str[9];
////								docDate = str[6];
//							}
//						}
//						}
//						}else {
//							if (reportDocuments != null && !reportDocuments.isEmpty()) {
//								for (int k = 0; k < reportDocuments.size(); k++) {
//									str = reportDocuments.get(k);
//									if (documentsList.get(j).equals(str[1])) {
////										docDate = str[9];
//										docDate = str[6];
//									}
//								}
//								}
//						}
						
						
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								docDate = strLoan[6];
							}
						}
						if(docDate == null) {
							docDate = "";
						}
						
						report = "";
						report += "RH|" + recordCount + "|" + count + "|";
						count = 0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
							for (int k = 0; k < reportThirdParty.size(); k++) {
								strThiParty = reportThirdParty.get(k);
								if (documentsList.get(j).equals(strThiParty[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;

						if (reportDocuments != null && !reportDocuments.isEmpty()) {
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								if (documentsList.get(j).equals(strDoc[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								if (documentsList.get(j).equals(strLoan[1])) {
									count++;
								}
							}
						}
						if (amount == null) {
							amount = "";
						}
//						report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
								report += count + "|" + docDate + "|" + amount + "|"
								+ documentsList.get(j) + "|";
						
						if (securityType.equals("MOVABLE")) {
							report += "|";
						}
						
						if(recordCount <= 1000) {
							//System.out.println("Adding RH to first file. Documentlist");
							bufferedWriter.write(report);
							bufferedWriter.write("\r\n");
					//		System.out.println("Added RH to first file. Documentlist");
						}else {
							//System.out.println("Adding RH to second file. Documentlist");
							bufferedWriterNew.write(report);
							bufferedWriterNew.write("\r\n");
					//		System.out.println("Added RH to second file. Documentlist");
						}
						
						
						//Adding Records from List By security id
						count=0;
						
						if(reportDocuments != null && !reportDocuments.isEmpty()) {
							count=0;
							System.out.println("reportDocuments is not null or empty. ");
					//		System.out.println("reportDocuments id checking with reportDocuments list.");
						for (int k = 0; k < reportDocuments.size(); k++) {
							strDoc = reportDocuments.get(k);
							
							for(int a=0;a<strDoc.length;a++) {
								if(strDoc[a] == null) {
									strDoc[a]="";
								}else {
									strDoc[a] = strDoc[a].replaceAll("&","AND");
									//System.out.println(strDoc[a]);
									strDoc[a] = strDoc[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strDoc[a]);
									//strDoc[a] = strDoc[a].replaceAll("  "," ");
									//System.out.println(strDoc[a]);
									while(strDoc[a].contains("  ")){
										strDoc[a] = strDoc[a].replaceAll("  "," ");
										}
								}
							}
							
							if (documentsList.get(j).equals(strDoc[1])) {
								report="";
								count++;
								report +="DOC|"+count+"|Others|";
								
								if (securityType.equals("IMMOVABLE")) {
									report +="Mortgage Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[8]+"|"+strDoc[5]+"|";
									if(strDoc[7].equals("")) {
										report+=strDoc[6]+"|";
									}else {
										report+=strDoc[7]+"|";
									}
								}else {
									report +="Hypothecation Deed|1|"+strDoc[3]+"||"+strDoc[3]+"|"+strDoc[7]+"|"+strDoc[5]+"|"+strDoc[6]+"|";
								}
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from document list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
						//			System.out.println("record write to first file from document list. recordCount = "+recordCount);
								}else {
								//	System.out.println("Writing data to second file from document list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
						//			System.out.println("record write to second file from document list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						
						if(reportLoan != null && !reportLoan.isEmpty()) {
							count=0;
							System.out.println("reportLoan is not null or empty. ");
						//	System.out.println("reportDocuments id checking with reportLoan list.");
						for (int k = 0; k < reportLoan.size(); k++) {
							strLoan = reportLoan.get(k);
							
							for(int a=0;a<strLoan.length;a++) {
								if(strLoan[a] == null) {
									strLoan[a]="";
								}else {
									strLoan[a] = strLoan[a].replaceAll("&","AND");
									//System.out.println(strLoan[a]);
									strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strLoan[a]);
									//strLoan[a] = strLoan[a].replaceAll("  "," ");
									//System.out.println(strLoan[a]);
									while(strLoan[a].contains("  ")){
										strLoan[a] = strLoan[a].replaceAll("  "," ");
										}
								}
							}
							
							if (documentsList.get(j).equals(strLoan[1])) {
								report="";
								count++;
								report +="LON|"+count+"|";
								if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
										|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
										|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
										|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
										|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
										|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
										|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
									report +=strLoan[0]+"|";
								}else {
									report +="|";
								}
								
								report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from loan list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
						//			System.out.println("record write to first file from loan list. recordCount = "+recordCount);
								}else {
								//	System.out.println("Writing data to second file from loan list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
							//		System.out.println("record write to second file from loan list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						if(recordCount <= 1000) {
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write("\r\n");
						}
					}
				}
				System.out.println("Document List Records Done with Adding To report.");
//				Loan List records
				
				if (loanList != null && !loanList.isEmpty()) {
					
					System.out.println("loanList Records starts. recordCount = "+recordCount);
					
					for (int j = 0; j < loanList.size(); j++) {
						recordCount++;
//						if(recordCount > 2000) {
//							break;
//						}
						count=0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
						for (int k = 0; k < reportLoan.size(); k++) {
							str = reportLoan.get(k);
							if (loanList.get(j).equals(str[1])) {
								count++;
								amount = str[2];
							}
						}
						}
//						if (reportLoan != null && !reportLoan.isEmpty()) {
//						for (int k = 0; k < reportLoan.size(); k++) {
//							str = reportLoan.get(k);
//							if (loanList.get(j).equals(str[1])) {
//								docDate = str[6];
//							}
//						}
//						}
						
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								docDate = strLoan[6];
							}
						}
						if(docDate == null) {
							docDate = "";
						}
						
						report = "";
						report += "RH|" + recordCount + "|" + count + "|";
						count = 0;
						if (reportThirdParty != null && !reportThirdParty.isEmpty()) {
							for (int k = 0; k < reportThirdParty.size(); k++) {
								strThiParty = reportThirdParty.get(k);
								if (loanList.get(j).equals(strThiParty[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;

						if (reportDocuments != null && !reportDocuments.isEmpty()) {
							for (int k = 0; k < reportDocuments.size(); k++) {
								strDoc = reportDocuments.get(k);
								if (loanList.get(j).equals(strDoc[1])) {
									count++;
								}
							}
						}
						report += count + "|";
						count = 0;
						if (reportLoan != null && !reportLoan.isEmpty()) {
							for (int k = 0; k < reportLoan.size(); k++) {
								strLoan = reportLoan.get(k);
								if (loanList.get(j).equals(strLoan[1])) {
									count++;
								}
							}
						}
						if (amount == null) {
							amount = "";
						}
//						report += count + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|" + amount + "|"
						report += count + "|" + docDate + "|" + amount + "|"
								+ loanList.get(j) + "|";
						
						if (securityType.equals("MOVABLE")) {
							report += "|";
						}
						
						if(recordCount <= 1000) {
							//System.out.println("Adding RH to file first. LoanList");
							bufferedWriter.write(report);
							bufferedWriter.write("\r\n");
				//			System.out.println("Adding RH to file first. LoanList");
						}else {
							//System.out.println("Adding RH to file second. LoanList");
							bufferedWriterNew.write(report);
							bufferedWriterNew.write("\r\n");
				//			System.out.println("Adding RH to file second. LoanList");
						}
						
						
						//Adding Records from List By security id
						count=0;
						
						if(reportLoan != null && !reportLoan.isEmpty()) {
							count=0;
							System.out.println("reportLoan is not null or empty. ");
					//		System.out.println("reportLoan id checking with reportLoan list.");
						for (int k = 0; k < reportLoan.size(); k++) {
							strLoan = reportLoan.get(k);
							
							for(int a=0;a<strLoan.length;a++) {
								if(strLoan[a] == null) {
									strLoan[a]="";
								}else {
									strLoan[a] = strLoan[a].replaceAll("&","AND");
									//System.out.println(strLoan[a]);
									strLoan[a] = strLoan[a].replaceAll("[^a-zA-Z0-9- _.-]", "");
									//System.out.println(strLoan[a]);
									//strLoan[a] = strLoan[a].replaceAll("  "," ");
									//System.out.println(strLoan[a]);
									while(strLoan[a].contains("  ")){
										strLoan[a] = strLoan[a].replaceAll("  "," ");
										}
								}
							}
							
							if (loanList.get(j).equals(strLoan[1])) {
								report="";
								count++;
								report +="LON|"+count+"|";
								if(strLoan[0].equalsIgnoreCase("Demand Loan") || strLoan[0].equalsIgnoreCase("Term Loan") 
										|| strLoan[0].equalsIgnoreCase("LAP") || strLoan[0].equalsIgnoreCase("Cash Credit") 
										|| strLoan[0].equalsIgnoreCase("Overdraft") || strLoan[0].equalsIgnoreCase("LC") 
										|| strLoan[0].equalsIgnoreCase("BG") || strLoan[0].equalsIgnoreCase("Bills") 
										|| strLoan[0].equalsIgnoreCase("Derivatives") || strLoan[0].equalsIgnoreCase("Export Packing Credit") 
										|| strLoan[0].equalsIgnoreCase("Foreign Usance Bills Discounted") || strLoan[0].equalsIgnoreCase("Foreign Bills Purchased")
										|| strLoan[0].equalsIgnoreCase("Post Shipment Credit in Foreign Currency") || strLoan[0].equalsIgnoreCase("ECB")) {
									report +=strLoan[0]+"|";
								}else {
									report +="|";
								}
								
								report +=strLoan[3]+"_"+strLoan[4]+"|"+strLoan[5]+"|"+strLoan[6]+"|"+strLoan[7]+"|12|||";
								if(recordCount <= 1000) {
									//System.out.println("Writing data to first file from loan list");
									bufferedWriter.write(report);
									bufferedWriter.write("\r\n");
					//				System.out.println("record write to first file from loan list. recordCount = "+recordCount);
								}else {
									//System.out.println("Writing data to second file from loan list");
									bufferedWriterNew.write(report);
									bufferedWriterNew.write("\r\n");
							//		System.out.println("record write to second file from loan list. recordCount = "+recordCount);
								}
								report = "";
							}
						}
						}
						if(recordCount <= 1000) {
							bufferedWriter.write("\r\n");
						}else {
							bufferedWriterNew.write("\r\n");
						}
					}
				}
				
			}
		}
		}else {
			System.out.println("Cersai Charge Release Report records start in textDataWriter");
			// Added By Prachit
			int size1=0;
			if (dataList != null && !dataList.isEmpty()) {
				if(dataList.size() > 1000) {
					if (securityType.equals("IMMOVABLE")) {
						bufferedWriterNew.write("FH|ADSI|");
					} else if(securityType.equals("MOVABLE")) {
						bufferedWriterNew.write("FH|MVIN|");
					}else {
						bufferedWriterNew.write("FH|SATN|");
					}
					size1 = (dataList.size()) - 1000;
					if(size1 > 1000) {
						size1 = 1000;
					}
				if (dataList != null && !dataList.isEmpty()) {
					bufferedWriterNew.write(size1 + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
					bufferedWriterNew.write("\r\n");
					bufferedWriterNew.write("\r\n");
				} else {
					bufferedWriterNew.write("0|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
					bufferedWriterNew.write("\r\n");
					bufferedWriterNew.write("\r\n");
				}
				}
			}
			
					if (securityType.equals("IMMOVABLE")) {
						bufferedWriter.write("FH|ADSI|");
					} else if(securityType.equals("MOVABLE")) {
						bufferedWriter.write("FH|MVIN|");
					}else {
						bufferedWriter.write("FH|SATN|");
					}

				if (dataList != null && !dataList.isEmpty()) {
					
					if(dataList.size() > 1000) {
						size1 = 1000;
					}else {
						size1 = dataList.size();
					}
					bufferedWriter.write(size1 + "|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
					bufferedWriter.write("\r\n");
					bufferedWriter.write("\r\n");
				} else {
					bufferedWriter.write("0|" + DateFormatUtils.format(new Date(), "dd-MM-yyyy") + "|");
					bufferedWriter.write("\r\n");
					bufferedWriter.write("\r\n");
				}
			
			// end

			if (columnsHeader != null && columnsHeader.length > 0) {
				if(dataList.size() > 1000) {
					for (int i = 0; i < columnsHeader.length; i++) {
						String data = columnsHeader[i] + ": " + columnNameValues[i];
						bufferedWriterNew.write(data);
						bufferedWriterNew.write("\r\n");// bufferedWriter.newLine();
					}
					bufferedWriterNew.write("-----------------------------------------");
					bufferedWriterNew.write("\r\n");// bufferedWriter.newLine();
				}
				
				for (int i = 0; i < columnsHeader.length; i++) {
					String data = columnsHeader[i] + ": " + columnNameValues[i];
					bufferedWriter.write(data);
					bufferedWriter.write("\r\n");// bufferedWriter.newLine();
				}
				bufferedWriter.write("-----------------------------------------");
				bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			}

			if (additionalHeader != null && !additionalHeader.isEmpty()) {
				IGeneralParamDao generalParamDao = (IGeneralParamDao) BeanHouse.get("generalParamDao");
				IGeneralParamEntry generalParamEntry = generalParamDao
						.getGeneralParamEntryByParamCodeActual("APPLICATION_DATE");
				Calendar currentDate = Calendar.getInstance();
				currentDate.setTime(new Date(generalParamEntry.getParamValue()));
				if(dataList.size() > 1000) {
					bufferedWriterNew.write(additionalHeader + " " + CommonUtil.getCurrentDateForPosidex(currentDate));
					bufferedWriterNew.write("\r\n");
				}
				bufferedWriter.write(additionalHeader + " " + CommonUtil.getCurrentDateForPosidex(currentDate));
				bufferedWriter.write("\r\n");
			}

			if ("Y".equals(printHeaderColumnName)) {
				String[] columnsMap = (String[]) parameters.get("columnsMap");
				int i = 0;
				for (String string : columnsMap) {
					if (delimiter != null && !delimiter.equals("")) {
						if (i == columnsMap.length - 1) {
							report += string.trim();
							report += secDelimiter;
						} else
							report += string.trim() + delimiter;
					} else {
						if (columnWidths[i] - string.length() > 0) {
							report += string;
							for (int j = 0; j < (columnWidths[i] - string.length()); j++) {
								report += " ";
							}
						} else {
							report += string.substring(0, columnWidths[i]);
						}
						report += "\t";
					}
					i++;
				}
				
				if(dataList.size() > 1000) {
					bufferedWriterNew.write(report);
					bufferedWriterNew.write("\r\n");
				}
				bufferedWriter.write(report);
				bufferedWriter.write("\r\n");
				// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
				report = "";
			}

			if (dataList != null && !dataList.isEmpty()) {
				int size = dataList.size();
				int cnt = 0;
				int counter = 1;
				for (String[] string : dataList) {
					if(counter > 1000) {
						bufferedWriterNew.write("RH|" + counter + "|");
					}else {
						bufferedWriter.write("RH|" + counter + "|");
					}
					report = "";
					for (int i = 0; i < string.length; i++) {
						cnt = i;
						if (string[i] != null && !string[i].equals("")) {

							if (delimiter != null && !delimiter.equals("")) {
								if (i == string.length - 1) {
									report += string[i].trim();
									report += secDelimiter;
								} else
									report += string[i].trim() + delimiter;
							} else {
								if (columnWidths[i] - string[i].length() > 0) {
										if (cnt == 3) {
											report += "01|";
										}
									if (string[i].equals("-")) {
										report += "";
									} else {
										report += string[i];
									}
									// report += string[i];
									for (int j = 0; j < (columnWidths[i] - string[i].length()); j++) {
										report += "|";
										break;
									}
								} else {
									report += string[i].substring(0, columnWidths[i]);
								}
								// report += "\t";
							}
						} else {
							if (delimiter != null && !delimiter.equals("")) {
								if (i == string.length - 1)
									report += string[i];
								else
									report += string[i] + delimiter;
							}

							if (delimiter == null || delimiter.equals("")) {
								for (int j = 0; j < columnWidths[i]; j++) {
									report += "|";
								}
								// report += "\t";
							}
						}

					}

					// report += "\n";
					if(counter > 1000) {
						bufferedWriterNew.write(report);
						if (counter != size) {
							bufferedWriterNew.write("\r\n");
						}
					}else {
					bufferedWriter.write(report);
					if (counter != size) {
						bufferedWriter.write("\r\n");
					}
				}
					// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
					counter++;
				}

				if (additionalTrailer != null && !additionalTrailer.isEmpty()) {
					if(counter > 1000) {
						bufferedWriterNew.write("\r\n");
						bufferedWriterNew.write(additionalTrailer + " " + size);
					}else {
					bufferedWriter.write("\r\n");
					bufferedWriter.write(additionalTrailer + " " + size);
					}
				}
			} else {
				bufferedWriter.write("No Records Found");
				bufferedWriter.write("\r\n");
				// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			}
			System.out.println("Cersai Charge Release report Records Adding done in report");			
		}

		// Print Footer
		String[] columnsFooter = (String[]) parameters.get("footerNameData");
		String[] footerNameValues = (String[]) parameters.get("footerNameValues");

		if (columnsFooter != null && columnsFooter.length > 0) {
		//	System.out.println("columnsFooter in textdatawiter is not null and length more than zero");
			bufferedWriter.write("-----------------------------------------");
			bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			for (int i = 0; i < columnsFooter.length; i++) {
				String data = columnsFooter[i] + ": " + footerNameValues[i];
				if(dataList.size() > 1000) {
				//	System.out.println("datalist size more than 1000 . write in second file");
					bufferedWriterNew.write(data);
					bufferedWriterNew.write("\r\n");
			//		System.out.println("datalist size more than 1000 . write in second file done");
				}
			//	System.out.println("datalist size less than 1000 . write in first file");
				bufferedWriter.write(data);
				bufferedWriter.write("\r\n");
			//	System.out.println("datalist size less than 1000 . write in first file done");
				// bufferedWriter.write("\r\n");//bufferedWriter.newLine();
			}
		}
		return report;
	}

	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}

	public void writeFooter(Map<String, String> footer) throws WriteException {
		createLine(footer);
	}

	public void writeRow(Map<String, String> row) throws WriteException {
		createLine(row);
	}

	public void writeHeader(Map<String, String> header) throws WriteException {
		createLine(header);
	}

	private void createLine(Map cells) {
		try {
			StringBuilder builder = new StringBuilder();
			Iterator keys = cells.keySet().iterator();
			Set<String> set = cells.keySet();

			if (rowNumber == 0 && getWriteColumnHeading()) {

				Integer cNo = 0;
				for (String cellNo : set) {
					cNo++;
					if (delimiter != null && delimiter.length() > 0) {
						builder.append(cellNo + delimiter);
					} else
						builder.append(cellNo);
				}

				String temp = "";
				if (delimiter != null && delimiter.length() > 0) {
					temp = builder.toString();
					if (temp != null && temp.length() > 0)
						temp = temp.substring(0, temp.length() - 1);
				} else {
					temp = builder.toString();
				}
				bufferedWriter.write(temp);
				bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			}
			rowNumber = rowNumber + 1;

			// if(textData.toString().length() > 0)
			// textData.append("\n");
			builder = new StringBuilder();

			while (keys.hasNext()) {
				if (delimiter != null && delimiter.length() > 0) {
					builder.append(cells.get(keys.next()).toString() + "" + delimiter);
				} else {
					builder.append(cells.get(keys.next()).toString());
				}
			}
			String temp = "";
			// String finalLine = builder.toString();
			if (delimiter != null && delimiter.length() > 0) {
				temp = builder.toString();
				if (temp != null && temp.length() > 0)
					temp = temp.substring(0, temp.length() - 1);
			} else {
				temp = builder.toString();
			}
			bufferedWriter.write(temp);
			bufferedWriter.write("\r\n");// bufferedWriter.newLine();

		} catch (IOException e) {
			e.printStackTrace();
		}
		// textData.append(finalLine);

	}

	public byte[] getOutput() {
		byte[] test = new byte[1000];
		return test;
	}

	/*
	 * public String getBasePath() { return
	 * applicationContext.getProperty("report.generationpath"); }
	 */

	public String getUniqueTimeStamp(String applicationDate) {

		Long currentTimeMillis = System.currentTimeMillis();
		return applicationDate + "_" + currentTimeMillis;
	}

	public void close() {
		try {
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		generate();
	}

	public void generate() {
		try {
			//logs
			if (bufferedWriter != null) {

				String delimiter = (String) parameters.get("delimiter");
				String secDelimiter = (String) parameters.get("secDelimiter");
				secDelimiter = secDelimiter == null ? "" : secDelimiter;
				String fixedLength = (String) parameters.get("fixedLength");
				String reportName = (String) parameters.get("reportname");
				if (dataList != null) {
					
					System.out.println(" inside TextDataWriter => datalist = "+dataList);
					if ("Y".equals(fixedLength)) {
						
						
						getArrangedReport(parameters, dataList, delimiter, bufferedWriter, fixedLength, secDelimiter);
						
					} else {
						if (reportName.equals("CERSAI Batch Upload Report")) {
							
							getArrangedReport2(parameters, dataList, delimiter, bufferedWriter,bufferedWriterNew, secDelimiter);
							
						} 
						else if(reportName.equals("CERSAI Charge Release report")) {
							if(dataList.size() > 1000) {
								
								getArrangedReport2(parameters, dataList, delimiter, bufferedWriter,bufferedWriterNew, secDelimiter);
								
							}else {
								
								getArrangedReport(parameters, dataList, delimiter, bufferedWriter, secDelimiter);
								
							}
						}
						else {
							
							getArrangedReport(parameters, dataList, delimiter, bufferedWriter, secDelimiter);
							
						}
					}
				} else {
					
					getArrangedReportForNewFormat(delimiter, fixedLength);
					
				}

				List<List<String[]>> additionalDetails = (List<List<String[]>>) parameters.get("additionalDetails");
				List<String[]> addiColumnsMapList = (List<String[]>) parameters.get("addiColumnsMapList");
				List<Integer[]> addiColumnsWidthList = (List<Integer[]>) parameters.get("addiColumnsWidthList");
				if (additionalDetails != null && additionalDetails.size() > 0) {
					for (int i = 0; i < additionalDetails.size(); i++) {
						String[] columnsMap = addiColumnsMapList.get(i);
						List<String[]> addiRecordsArray = additionalDetails.get(i);
						parameters.remove("columnsMap");
						parameters.put("columnsMap", columnsMap);
						if (addiColumnsWidthList == null) {
							
							getArrangedReport(parameters, addiRecordsArray, delimiter, bufferedWriter, secDelimiter);
							
						} else {
							Integer[] columnWidths = addiColumnsWidthList.get(i);
							
							getArrangedReport(parameters, addiRecordsArray, delimiter, bufferedWriter, columnWidths,
									fixedLength, secDelimiter);
							
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("TextDataWriter => generate => Exception Occurs == =>  "+e);
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null) {
					bufferedWriter.close();
					System.out.println("bufferedWriter closed");
					// convertFileToZipAndWrite(fileName,parameters);
				}
				if (bufferedWriterNew != null) {
					bufferedWriterNew.close();
					System.out.println("bufferedWriterNew closed");
				}
				if (bufferedWriterZip != null) {
					bufferedWriterZip.close();
					System.out.println("bufferedWriterZip closed");
				}
			} catch (IOException e) {
				System.out.println("TextDataWriter => generate => Exception Occurs --- =>  "+e);
				e.printStackTrace();
			}

		}
	}

	private void getArrangedReportForNewFormat(String delimiter, String fixedLength) {
		System.out.println("Inside getArrangedReportForNewFormat method");
		try {
			List<Integer[]> singleRecordColoumnWidth = (List<Integer[]>) parameters.get("singlerecordcoloumnwidth");
			List<List<String[]>> recordsList = (List<List<String[]>>) parameters.get("recordsList");
			String printHeaderColumnName = (String) parameters.get("printheadercolumnname");
			if ("Y".equals(printHeaderColumnName)) {
				List<String[]> columnHeadings = (List<String[]>) parameters.get("columnheadingsrowwise");
				if (columnHeadings != null && !columnHeadings.isEmpty()) {
					for (int index = 0; index < columnHeadings.size(); index++) {
						String[] singleRowHeadings = columnHeadings.get(index);
						Integer[] columnWidths = singleRecordColoumnWidth.get(index);
						int counter = 0;
						String columnHeaderForReport = "";
						for (String columnHeading : singleRowHeadings) {
							if ("Y".equalsIgnoreCase(fixedLength)) {
								columnHeaderForReport += getDataForFixedLengthText(columnWidths, counter,
										columnHeading);
							} else {
								if (delimiter != null && !delimiter.equals("")) {
									columnHeaderForReport += getDataForDelimiterText(delimiter, singleRowHeadings,
											counter, columnHeading);
								} else {
									columnHeaderForReport += getDataForFixedLengthText(columnWidths, counter,
											columnHeading);
									columnHeaderForReport += "\t";
								}
							}
							counter++;
						}
					//	System.out.println("Adding to first file columnHeaderForReport");
						bufferedWriter.write(columnHeaderForReport);
						bufferedWriter.write("\r\n");// bufferedWriter.newLine();
					}
				}
			}
			bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			if (recordsList != null && !recordsList.isEmpty()) {
				for (List<String[]> oneRecord : recordsList) {
					for (int index = 0; index < oneRecord.size(); index++) {
						String[] singleRowData = oneRecord.get(index);
						Integer[] columnWidths = singleRecordColoumnWidth.get(index);

						int counter = 0;
						String dataForReport = "";
						for (String columnData : singleRowData) {

							if ("Y".equalsIgnoreCase(fixedLength)) {
								dataForReport += getDataForFixedLengthText(columnWidths, counter, columnData);
							} else {
								if (delimiter != null && !delimiter.equals("")) {
									dataForReport += getDataForDelimiterText(delimiter, singleRowData, counter,
											columnData);
								} else {
									dataForReport += getDataForFixedLengthText(columnWidths, counter, columnData);
									dataForReport += "\t";
								}
							}
							counter++;

						}
						bufferedWriter.write(dataForReport);
						bufferedWriter.write("\r\n");// bufferedWriter.newLine();
					}
				}
			} else {
				bufferedWriter.write("No Records Found");
				bufferedWriter.write("\r\n");// bufferedWriter.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (bufferedWriter != null) {
				try {
					bufferedWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	//	System.out.println("done with getArrangedReportForNewFormat method");
	}

	private String getDataForDelimiterText(String delimiter, String[] singleRowHeadings, int counter, String value) {
		System.out.println("Inside getDataForDelimiterText method");
		
		String data = "";
		if (value != null && !"".equalsIgnoreCase(value)) {
			value = value.trim();
		} else {
			value = "";
		}
		if (counter == singleRowHeadings.length - 1) {
			data += value;
		} else {
			data += value.trim() + delimiter;
		}
	//	System.out.println("done with getDataForDelimiterText method");
		return data;
	}

	private String getDataForFixedLengthText(Integer[] columnWidths, int counter, String value) {
		
	//	System.out.println("Inside getDataForFixedLengthText method");
		
		String columnHeader = "";

		if (value == null || "".equalsIgnoreCase(value)) {
			value = "";
		}
		int differenceLength = (columnWidths[counter] - value.length());
		if (differenceLength > 0) {
			columnHeader += value;
			for (int count = 0; count < differenceLength; count++) {
				columnHeader += " ";
			}
		} else {
			columnHeader += value.substring(0, columnWidths[counter]);
		}
	//	System.out.println("done with  getDataForFixedLengthText method");
		return columnHeader;
	}

	public void setWriteColumnHeading(Boolean writeColumnHeading) {
		this.writeColumnHeading = writeColumnHeading;
	}

	public Boolean getWriteColumnHeading() {
		return writeColumnHeading;
	}

	/**
	 * Arrange Data for additional
	 * 
	 * @param parameters
	 * @param dataList
	 * @param delimiter
	 * @param bufferedWriter2
	 * @param columnWidths
	 * @param fixedLength
	 * @return report data
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public String getArrangedReport(Map parameters, List<String[]> dataList, String delimiter,
			BufferedWriter bufferedWriter2, Integer[] columnWidths, String fixedLength, String secDelimiter)
			throws IOException {
		//System.out.println("Inside getArrangedReport method");
		parameters.put("columnWidths", columnWidths);
		if ("Y".equalsIgnoreCase(fixedLength)) {
		//	System.out.println("Inside getArrangedReport method => fixedLength is Y => going inside  getArrangedReport with 6 parameter");
			return getArrangedReport(parameters, dataList, delimiter, bufferedWriter2, fixedLength, secDelimiter);
		} else {
		//	System.out.println("Inside getArrangedReport method => fixedLength is not Y => going inside  getArrangedReport with 5 parameter");
			return getArrangedReport(parameters, dataList, delimiter, bufferedWriter2, secDelimiter);
		}
	}
}
