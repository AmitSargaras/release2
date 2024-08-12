package com.integrosys.cms.batch.ubs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.integrosys.base.techinfra.dbsupport.SequenceManager;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.BatchJobException;
import com.integrosys.cms.batch.common.filereader.CSVReader;
import com.integrosys.cms.batch.common.filereader.ProcessDataFile;
import com.integrosys.cms.batch.factory.BatchJob;

public class UbscopyMain implements BatchJob {

	private IUbscopyDao dao;

	public void execute(Map context) throws BatchJobException {

		String uploadId=null;
		Set errMsg=null;
		int count=0;
		ProcessDataFile dataFile = new ProcessDataFile();
		IUbsErrDetLog obUbsErrDetLog[]=null;
		Locale local = Locale.US;
		try {
			uploadId=(new SequenceManager()).getSeqNum(ICMSConstant.SEQUENCE_LIMIT_UPLOAD, true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		/*set local*/
		CSVReader.setLocale(local);
		ArrayList resultList = dataFile.processFile(null, "UbsUpload");
		
		obUbsErrDetLog=new IUbsErrDetLog[dataFile.getErrorList().size()];
		for(int j=0;j<dataFile.getMaxCount();j++)
		{


			String [][]errorData = new String[50][2];
			errorData = (String[][])dataFile.getErrorList().get(new Integer(j));
			
			if(!(errorData==null)){
				errMsg=new HashSet();
				for(int k=0;k<=errorData.length-1;k++) {
					if(errorData[k][0]!=null) {
//						System.out.println(errorData[k][0]+" Value: " +errorData[k][1]);																	
						errMsg.add(errorData[k][0]);
						}
					}
				obUbsErrDetLog[count]=new OBUbsErrDetLog();
				obUbsErrDetLog[count].setPtId(uploadId);
				obUbsErrDetLog[count].setRecordNo(j+1+"");
				obUbsErrDetLog[count].setErrorMsg("Error in "+errMsg.toString());
				obUbsErrDetLog[count].setTime(new Date());
				count++;
			}
			
			
			

		}
//		System.out.println("ArrayList : " + resultList);
		dao.insertData(resultList,dataFile.getFileName(),uploadId,obUbsErrDetLog);


	}

	public IUbscopyDao getDao() {
		return dao;
	}

	public void setDao(IUbscopyDao dao) {
		this.dao = dao;
	}

}
