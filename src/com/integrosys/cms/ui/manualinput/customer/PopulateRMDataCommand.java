package com.integrosys.cms.ui.manualinput.customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.struts.action.ActionMessage;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.AccessDeniedException;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.common.util.CommonUtil;
import com.integrosys.cms.app.common.util.SQLParameter;
import com.integrosys.cms.app.custgrpi.bus.IGroupSubLimit;
import com.integrosys.cms.app.custgrpi.bus.OBGroupSubLimit;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;

public class PopulateRMDataCommand extends AbstractCommand{

	@Override
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			{ "rmCode", "java.lang.String", REQUEST_SCOPE },
			{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE }
		});
	}

	@Override
	public String[][] getResultDescriptor() {
		return (new String[][] {	
//			{ "OBCMSCustomer", "com.integrosys.cms.app.customer.bus.ICMSCustomer", SERVICE_SCOPE },
//			{ "event", "java.lang.String", SERVICE_SCOPE },
			{ "relationshipMgr", "java.lang.String", REQUEST_SCOPE },
			{ "rmRegion", "java.lang.String", REQUEST_SCOPE }
		});
	}

	
	
	@Override
	public HashMap doExecute(HashMap map)
			throws CommandValidationException, CommandProcessingException, AccessDeniedException {
		HashMap resultMap = new HashMap();
		HashMap returnMap = new HashMap();
		HashMap exceptionMap = new HashMap();
		String rmCode = (String) map.get("rmCode");
		
		List data =  fetchRMData(rmCode);
		
		String relationshipMgr = "-";
		String rmRegion = "-";

		if(!data.isEmpty()) {
			relationshipMgr = (String) data.get(0);
			rmRegion = (String)data.get(1);
		}else {
			exceptionMap.put("relationshipMgrEmpCodeError", new ActionMessage("error.string.disable.emp.code"));
		}
		
		resultMap.put("relationshipMgr", relationshipMgr);
		resultMap.put("rmRegion", rmRegion);
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
		return returnMap;
	}	
	
	
	private List fetchRMData(String rmEmpCode){
		List data = new ArrayList();	
		
		String sql="select RM_MGR_NAME,(select reg.REGION_NAME from cms_region reg where reg.id= REGION) as region from CMS_RELATIONSHIP_MGR where RM_MGR_CODE = '"+rmEmpCode+"' and STATUS = 'ACTIVE'";
		
	        SQLParameter params = SQLParameter.getInstance();
	        DBUtil dbUtil = null;
	        ResultSet rs = null;

	        try {
	            dbUtil = new DBUtil();
	            dbUtil.setSQL(sql);
	            rs = dbUtil.executeQuery();
	           
	            while (rs.next()) {	               
	               data.add(rs.getString("RM_MGR_NAME"));
	               data.add(rs.getString("REGION"));
	            }
	        } catch (SQLException ex) {
	            throw new SearchDAOException("SQLException in StagingCustGrpIdentifierDAO", ex);
	        } catch (Exception ex) {
	            throw new SearchDAOException("Exception in StagingCustGrpIdentifierDAO", ex);
	        }finally{
	        	try {
					dbUtil.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        }
		return data;
	}
	
	
	
}
