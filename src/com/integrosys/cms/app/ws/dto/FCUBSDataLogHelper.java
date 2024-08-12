package com.integrosys.cms.app.ws.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

@Service
public class FCUBSDataLogHelper {

	/*@Autowired
	private IFCUBSDataLogDao FCUBSDataLogDao;

	public void setFCUBSDataLogDao(IFCUBSDataLogDao FCUBSDataLogDao) {
		this.FCUBSDataLogDao = FCUBSDataLogDao;
	}*/
	

	public void fcubsDataLoggingActivity(OBCustomerSysXRef obCustomerSysXRef,String fileName, Date requestDate) throws FCUBSDataLogException,Exception {

		try {
			System.out.println("FCUBSDataLogHelper.java => Inside fcubsDataLoggingActivity.");
			OBFCUBSDataLog fcubsObj = new OBFCUBSDataLog();
			 ILimitDAO dao = LimitDAOFactory.getDAO();
			fcubsObj = dao.fetchPartyDetails(obCustomerSysXRef,fcubsObj);
			try {
				fcubsObj = dao.fetchMakerCheckerDetails(obCustomerSysXRef,fcubsObj);
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			if(null!= fileName && !"".equalsIgnoreCase(fileName))
			{
				fcubsObj.setFileName(fileName);
			}
			
			if(null!= obCustomerSysXRef.getSourceRefNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getSourceRefNo()))
			{
				fcubsObj.setSourceRefNo(obCustomerSysXRef.getSourceRefNo());
			}
			
			if(null!= obCustomerSysXRef.getFacilitySystemID() && !"".equalsIgnoreCase(obCustomerSysXRef.getFacilitySystemID()))
			{
				fcubsObj.setLiabilityId(obCustomerSysXRef.getFacilitySystemID());
			}
			
			
			if(null!= obCustomerSysXRef.getLineNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getLineNo()))
			{
				fcubsObj.setLineNo(obCustomerSysXRef.getLineNo());
			}
			
			if(null!= obCustomerSysXRef.getSerialNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getSerialNo()))
			{
				fcubsObj.setSerialNo(obCustomerSysXRef.getSerialNo());
			}
			
			if(null!= obCustomerSysXRef.getLiabBranch() && !"".equalsIgnoreCase(obCustomerSysXRef.getLiabBranch())){
			String branchCode = fetchBranchCode(obCustomerSysXRef.getLiabBranch());
			if(null!= branchCode && !"".equalsIgnoreCase(branchCode))
			{
				fcubsObj.setLiabBranch(branchCode);
			}
			
			}
			if(null!= obCustomerSysXRef.getCurrency() && !"".equalsIgnoreCase(obCustomerSysXRef.getCurrency()))
			{
				fcubsObj.setCurrency(obCustomerSysXRef.getCurrency());
			}
			
			
			if(null!= obCustomerSysXRef.getReleasedAmount() && !"".equalsIgnoreCase(obCustomerSysXRef.getReleasedAmount()))
			{
				fcubsObj.setLimitAmount(Double.parseDouble(obCustomerSysXRef.getReleasedAmount()));
			}
			
			if(null!= obCustomerSysXRef.getLimitTenorDays() && !"".equalsIgnoreCase(obCustomerSysXRef.getLimitTenorDays()))
			{
				fcubsObj.setTenor(obCustomerSysXRef.getLimitTenorDays());
			}
			
			
			
				fcubsObj.setStatus(ICMSConstant.FCUBS_STATUS_PENDING);
			
			
			
			if(null!= obCustomerSysXRef.getAction() && !"".equalsIgnoreCase(obCustomerSysXRef.getAction()))
			{
				fcubsObj.setAction(obCustomerSysXRef.getAction());
			}
			
			if(null!= obCustomerSysXRef.getLimitStartDate() && !"".equals(obCustomerSysXRef.getLimitStartDate()))
			{
				fcubsObj.setStartDate(obCustomerSysXRef.getLimitStartDate());
			}
			
			
			if(null!= obCustomerSysXRef.getDateOfReset() && !"".equals(obCustomerSysXRef.getDateOfReset()))
			{
				fcubsObj.setExpiryDate(obCustomerSysXRef.getDateOfReset());
			}
			
			fcubsObj.setRequestDateTime(requestDate);
			
			if(null!= obCustomerSysXRef.getDayLightLimit() && !"".equals(obCustomerSysXRef.getDayLightLimit()))
			{
				fcubsObj.setDayLightLimit(obCustomerSysXRef.getDayLightLimit());
			}
			
			if(null!= obCustomerSysXRef.getIntradayLimitExpiryDate() && !"".equals(obCustomerSysXRef.getIntradayLimitExpiryDate()))
			{
				fcubsObj.setIntradayLimitExpiryDate(obCustomerSysXRef.getIntradayLimitExpiryDate());
			}
			
			if(null!= obCustomerSysXRef.getIntradayLimitFlag() && !"".equals(obCustomerSysXRef.getIntradayLimitFlag()))
			{
				fcubsObj.setIntradayLimitFlag(obCustomerSysXRef.getIntradayLimitFlag());
			}
			
			String stockDocMonth = obCustomerSysXRef.getStockDocMonth();
			String stockDocYear = obCustomerSysXRef.getStockDocYear();
			if(stockDocMonth == null) {
				stockDocMonth = "";
			}
			if(stockDocYear == null) {
				stockDocYear = "";
			}
			String stockDocMonthAndYear = stockDocMonth+"-"+stockDocYear;
			
			fcubsObj.setStockDocMonthAndYear(stockDocMonthAndYear);
			System.out.println("FCUBSDataLogHelper.java stockDocMonthAndYear=>"+stockDocMonthAndYear);
			
			
			IFCUBSDataLogDao fcubsDataLogDao = (IFCUBSDataLogDao)BeanHouse.get("FCUBSDataLogDao");
			fcubsDataLogDao.createFCUBSDataLog(fcubsObj);
			
		} catch (FCUBSDataLogException e) {
			e.printStackTrace();
			throw e;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
		
		
		

	}

	
	private String fetchBranchCode(String liabBranch) throws Exception {
		
		 DefaultLogger.debug(this,"FCUBSLimitFileUploadJob get BranchCode");
		 ILimitDAO dao = LimitDAOFactory.getDAO();
		 String fccBranchCode =dao.getBranchCodeFromId(liabBranch);
		 DefaultLogger.debug(this,"FCUBSLimitFileUploadJob after BranchCode"+fccBranchCode);
		 return fccBranchCode;
		}
	
	
	

}
