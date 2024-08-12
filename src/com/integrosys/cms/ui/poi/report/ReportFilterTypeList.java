package com.integrosys.cms.ui.poi.report;

import java.util.HashMap;


public class ReportFilterTypeList {

	private static HashMap<String,String> reportFilterList; 
	
	private static ReportFilterTypeList thisInstance;
	
	private ReportFilterTypeList() {}
	
	public synchronized static ReportFilterTypeList getInstance() {
		if (thisInstance == null) {
			thisInstance = new ReportFilterTypeList();
			thisInstance.getReportFilterList();
		}
		return thisInstance;
	}
	
	public HashMap<String,String> getReportFilterList() {

		if (reportFilterList == null) {
			reportFilterList = new HashMap<String,String>();
		
			reportFilterList.put("RPT0001","TYPE1");
			reportFilterList.put("RPT0002","TYPE1");
			reportFilterList.put("RPT0003","TYPE6");
			reportFilterList.put("RPT0004","TYPE5");
			reportFilterList.put("RPT0005","TYPE6");
			reportFilterList.put("RPT0006","TYPE5");
			reportFilterList.put("RPT0007","TYPE3");
			reportFilterList.put("RPT0008","TYPE1");
			reportFilterList.put("RPT0009","TYPE2");
			reportFilterList.put("RPT0010","TYPE2");
			reportFilterList.put("RPT0011","TYPE2");
			reportFilterList.put("RPT0012","TYPE2");
			reportFilterList.put("RPT0013_1","TYPE2");
			reportFilterList.put("RPT0013_2","TYPE2");
			reportFilterList.put("RPT0013_3","TYPE2");
			reportFilterList.put("RPT0013_4","TYPE2");
			reportFilterList.put("RPT0014","TYPE2");
			reportFilterList.put("RPT0015","TYPE2");
			reportFilterList.put("RPT0016","TYPE6");
			reportFilterList.put("RPT0017","TYPE2");
			reportFilterList.put("RPT0018","TYPE2");
			reportFilterList.put("RPT0019","TYPE5");
			reportFilterList.put("RPT0020","TYPE7");
//			reportFilterList.put("RPT0021","");
			reportFilterList.put("RPT0022","TYPE2");
			reportFilterList.put("RPT0023","TYPE2");
			reportFilterList.put("RPT0024","TYPE2");
//			reportFilterList.put("RPT0025","");
			reportFilterList.put("RPT0026","TYPE7");
			reportFilterList.put("RPT0027","TYPE3");
//			reportFilterList.put("RPT0028","");
			reportFilterList.put("RPT0029","TYPE3");
			reportFilterList.put("RPT0030","TYPE3");
			reportFilterList.put("RPT0031","TYPE3");
			reportFilterList.put("RPT0032","TYPE3");
			reportFilterList.put("RPT0033_1","TYPE7");
			reportFilterList.put("RPT0033_2","TYPE7");
			reportFilterList.put("RPT0033_3","TYPE7");
			reportFilterList.put("RPT0034","TYPE2");
			reportFilterList.put("RPT0035","TYPE3");
			reportFilterList.put("RPT0036","TYPE3");
			reportFilterList.put("RPT0037","TYPE8");
			reportFilterList.put("RPT0038","TYPE4");
			reportFilterList.put("RPT0039","TYPE8");
			reportFilterList.put("RPT0040","TYPE8");
			reportFilterList.put("RPT0041","TYPE8");
			reportFilterList.put("RPT0042","TYPE4");
//			reportFilterList.put("RPT0043","");
//			reportFilterList.put("RPT0044","");
//			reportFilterList.put("RPT0045","");
			reportFilterList.put("RPT0049","TYPE9");
			reportFilterList.put("RPT0050","TYPE10");
			reportFilterList.put("RPT0051","TYPE11");
			reportFilterList.put("RPT0046","TYPE9");
			reportFilterList.put("RPT0046","TYPE10");
			reportFilterList.put("RPT0056","TYPE3");
			reportFilterList.put("RPT0060","TYPE60");

			reportFilterList.put("RPT0061","TYPE61");
			reportFilterList.put("RPT0062","TYPE62");			
			reportFilterList.put("RPT0066","TYPE66");
			reportFilterList.put("RPT0067","TYPE67");
			reportFilterList.put("RPT0068","TYPE68");
			reportFilterList.put("RPT0073","TYPE73");
			reportFilterList.put("RPT0081","TYPE81");
			reportFilterList.put("RPT0091","TYPE3");
			reportFilterList.put("RPT0092","TYPE3");
			reportFilterList.put("RPT0100","TYPE3");
			reportFilterList.put("RPT0101","TYPE3");
		}

		return reportFilterList;
	}
}
