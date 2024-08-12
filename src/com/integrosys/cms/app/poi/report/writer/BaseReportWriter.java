/**
 * 
 */
package com.integrosys.cms.app.poi.report.writer;



public abstract class BaseReportWriter //extends Thread 
{
	
	/**
	 * 
	 */
	public BaseReportWriter() {
		
	}
	
	/**
	 * 
	 * @param fileName
	 * @return
	 */
	private String renameToZip(String fileName) {

		String tempFileName="";
		if(fileName!=null&& !"".equals(fileName)){
			tempFileName= fileName.substring(0, fileName.length()-3);
			tempFileName=tempFileName.concat("zip");
		}
		return tempFileName;
	}

	public abstract void generate();
	
}
