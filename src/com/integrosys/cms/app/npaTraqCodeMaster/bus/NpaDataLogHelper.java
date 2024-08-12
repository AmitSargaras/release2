package com.integrosys.cms.app.npaTraqCodeMaster.bus;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.OBCustomerSysXRef;
import com.integrosys.cms.app.leiDateValidation.bus.ILeiDateValidationDao;
import com.integrosys.cms.app.limit.bus.ILimitDAO;
import com.integrosys.cms.app.limit.bus.LimitDAOFactory;
import com.integrosys.cms.app.ws.dto.OBFCUBSDataLog;

@Service
public class NpaDataLogHelper {

	/*@Autowired
	private INpaDataLogDao npaDataLogDao;*/
	/*INpaDataLogDao npaDataLogDao = (INpaDataLogDao) BeanHouse.get("npaDataLogDao");*/


	/*public void setNpaDataLogDao(INpaDataLogDao npaDataLogDao) {
		this.npaDataLogDao = npaDataLogDao;
	}*/

	public void npaDataLoggingActivity(OBNpaProvisionJob obj,String fileName, Date requestDate) throws NpaDataLogException,Exception {

		try {
			OBNpaDataLog npaObj = new OBNpaDataLog();
			
			if(null!= fileName && !"".equalsIgnoreCase(fileName))
				npaObj.setFileName(fileName);
			
			if(null!= obj.getSystem() && !"".equalsIgnoreCase(obj.getSystem()))
				npaObj.setSystem(obj.getSystem());
			
			if(null!= obj.getPartyID() && !"".equalsIgnoreCase(obj.getPartyID()))
				npaObj.setPartyID(obj.getPartyID());
			
			if(null!= obj.getCollateralType() && !"".equalsIgnoreCase(obj.getCollateralType()))
				npaObj.setCollateralType(obj.getCollateralType());
			
			if(null!= obj.getStartDate() && !"".equalsIgnoreCase(obj.getStartDate()))
				npaObj.setStartDate(obj.getStartDate());
			
			if(null!= obj.getMaturityDate() && !"".equalsIgnoreCase(obj.getMaturityDate()))
				npaObj.setMaturityDate(obj.getMaturityDate());
			
			if(null!= obj.getReportingDate() && !"".equals(obj.getReportingDate()))
				npaObj.setReportingDate(obj.getReportingDate());
			
			if(null!= obj.getValuationDate() && !"".equals(obj.getValuationDate()))
				npaObj.setValuationDate(obj.getValuationDate());
			
			if(null!= obj.getValuationAmount()&& !"".equals(obj.getValuationAmount()))
				npaObj.setValuationAmount(obj.getValuationAmount());
			
			if(null!= obj.getOriginalValue()&& !"".equals(obj.getOriginalValue()))
				npaObj.setOriginalValue(obj.getOriginalValue());
			
			npaObj.setStatus(ICMSConstant.NPA_UPLOAD_STATUS);
			
			INpaDataLogDao npaDataLogDao = (INpaDataLogDao)BeanHouse.get("npaDataLogDao");
			npaDataLogDao.createNpaDataLog(npaObj);
			
		} catch (NpaDataLogException e) {
			e.printStackTrace();
			throw e;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
