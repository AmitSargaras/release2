package com.integrosys.cms.app.ws.dto;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;

@Service
public class PSRDataLogHelper {

	/*@Autowired
	private IPSRDataLogDao psrDataLogDao;

	public void setPsrDataLogDao(IPSRDataLogDao psrDataLogDao) {
		this.psrDataLogDao = psrDataLogDao;
	}*/

	public void psrDataLoggingActivity(OBCustomerSysXRef obCustomerSysXRef, String fileName, Date requestDate)
			throws PSRDataLogException, Exception {
		
		System.out.println("In psrDataLoggingActivity().....");
		try {
			OBPSRDataLog psrObj = new OBPSRDataLog();
			ILimitDAO dao = LimitDAOFactory.getDAO();
			//to fetch party id and party name
			psrObj = dao.fetchPartyDetails(obCustomerSysXRef,psrObj);
			
			//to fetch maker checker details
			try {
				psrObj = dao.fetchMakerCheckerDetails(obCustomerSysXRef,psrObj);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
			if (null != fileName && !"".equalsIgnoreCase(fileName)) {
				psrObj.setFileName(fileName);
			}
			if (null != obCustomerSysXRef.getSourceRefNo()
					&& !"".equalsIgnoreCase(obCustomerSysXRef.getSourceRefNo())) {
				psrObj.setSourceRefNo(obCustomerSysXRef.getSourceRefNo());
			}

			//To Do:need to change with new field liability id
			if (null != obCustomerSysXRef.getFacilitySystemID()
					&& !"".equalsIgnoreCase(obCustomerSysXRef.getFacilitySystemID())) {
				psrObj.setLiabilityId(obCustomerSysXRef.getFacilitySystemID());
			}
			if (null != obCustomerSysXRef.getFacilitySystemID()
					&& !"".equalsIgnoreCase(obCustomerSysXRef.getFacilitySystemID())) {
				psrObj.setSystemId(obCustomerSysXRef.getFacilitySystemID());
			}
			if (null != obCustomerSysXRef.getLineNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getLineNo())) {
				psrObj.setLineNo(obCustomerSysXRef.getLineNo());
			}
			if (null != obCustomerSysXRef.getSerialNo() && !"".equalsIgnoreCase(obCustomerSysXRef.getSerialNo())) {
				psrObj.setSerialNo(obCustomerSysXRef.getSerialNo());
			}
			if (null != obCustomerSysXRef.getCurrency() && !"".equalsIgnoreCase(obCustomerSysXRef.getCurrency())) {
				psrObj.setCurrency(obCustomerSysXRef.getCurrency());
			}
			if (null != obCustomerSysXRef.getReleasedAmount()
					&& !"".equalsIgnoreCase(obCustomerSysXRef.getReleasedAmount())) {
				psrObj.setLimitAmount(Double.parseDouble(obCustomerSysXRef.getReleasedAmount()));
			}
			if (null != obCustomerSysXRef.getTenure()
					&& !"".equalsIgnoreCase(obCustomerSysXRef.getTenure())) {
				psrObj.setTenor(obCustomerSysXRef.getTenure());
			}

			//To Do:need to change with new field SellDownPeriod
			if (null != obCustomerSysXRef.getSellDownPeriod()
					&& !"".equalsIgnoreCase(obCustomerSysXRef.getSellDownPeriod())) {
				psrObj.setSellDownPeriod(obCustomerSysXRef.getSellDownPeriod());
			}
			if (null != obCustomerSysXRef.getReleaseDate() && !"".equals(obCustomerSysXRef.getReleaseDate())) {
				psrObj.setStartDate(obCustomerSysXRef.getReleaseDate());
			}
			if (null != obCustomerSysXRef.getDateOfReset() && !"".equals(obCustomerSysXRef.getDateOfReset())) {
				psrObj.setExpiryDate(obCustomerSysXRef.getDateOfReset());
			}
			if (null != obCustomerSysXRef.getAction() && !"".equalsIgnoreCase(obCustomerSysXRef.getAction())) {
				psrObj.setAction(obCustomerSysXRef.getAction());
			}
			psrObj.setStatus(ICMSConstant.PSR_STATUS_PENDING);
			psrObj.setRequestDateTime(requestDate);
			System.out.println("In psrDataLoggingActivity() before calling createPSRDataLog().....");
			IPSRDataLogDao psrDataLogDao = (IPSRDataLogDao) BeanHouse.get("PSRDataLogDao");
			psrDataLogDao.createPSRDataLog(psrObj);

		} catch (PSRDataLogException e) {
			e.printStackTrace();
			throw e;

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
}
