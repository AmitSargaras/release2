package com.integrosys.cms.ui.collateral.assetbased.assetgencharge;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.springframework.jdbc.core.RowMapper;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.collateral.bus.ICollateral;
import com.integrosys.cms.app.collateral.trx.ICollateralTrxValue;
import com.integrosys.cms.app.component.bus.ComponentException;
import com.integrosys.cms.app.component.bus.OBComponent;
import com.integrosys.cms.app.component.trx.IComponentTrxValue;
import com.integrosys.cms.app.component.trx.OBComponentTrxValue;
import com.integrosys.cms.app.customer.bus.OBSystem;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.collateral.CollateralHelper;
import com.integrosys.cms.ui.collateral.IInsuranceGCDao;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import static com.integrosys.cms.ui.collateral.CollateralConstant.SESSION_TOTAL_INSURANCE_POLICY_AMT;

public class CreateInsuranceGCCommand extends AbstractCommand {
	private DBUtil dbUtil;
	public String[][] getParameterDescriptor() {
		return (new String[][] { { "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.USER_TEAM, "com.integrosys.component.bizstructure.app.bus.ITeam", GLOBAL_SCOPE },
				{ com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY, "java.util.Locale", GLOBAL_SCOPE },
				{ "insuranceGCObj", "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", FORM_SCOPE },
				{ "insuranceGCObj", "com.integrosys.cms.ui.collateral.assetbased.assetgencharge.OBInsuranceGC", REQUEST_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				 {"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
				 { "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "serviceColObj", "com.integrosys.cms.app.collateral.trx.ICollateralTrxValue", SERVICE_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "componentList", "java.util.List", SERVICE_SCOPE },
		});
	}

	/**
	 * Defines an two dimensional array with the result list to be expected as a
	 * result from the doExecute method using a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getResultDescriptor() {
		return (new String[][] { 
				{ "from_page", "java.lang.String", SERVICE_SCOPE },
				{ "tab", "java.lang.String", SERVICE_SCOPE },
				{ "fundedShare", "java.lang.String", SERVICE_SCOPE },
				{ "isEditable", "java.lang.String", SERVICE_SCOPE },
				{ "releasableAmount", "java.math.BigDecimal", SERVICE_SCOPE },
				{ "calculatedDP", "java.lang.String", ICommonEventConstant.REQUEST_SCOPE },
				{ "remarks", "java.lang.String", REQUEST_SCOPE },
				{ "dueDate", "java.lang.String", SERVICE_SCOPE },
				{ "displayList",  "java.util.List", SERVICE_SCOPE },
				{ "dueDateList",  "java.util.List", SERVICE_SCOPE},
				{ "filterLocationList",  "java.util.List", SERVICE_SCOPE },
				{ "insuranceList",  "java.util.List", SERVICE_SCOPE },
				{ "event", "java.lang.String", REQUEST_SCOPE },
				{ "alertRequired",  "java.util.List", SERVICE_SCOPE },
				{ "isStockDetailsAdded",  "java.util.List", SERVICE_SCOPE },
				{ SESSION_TOTAL_INSURANCE_POLICY_AMT, String.class.getName() , SERVICE_SCOPE },
				
				});
	}
	
	 public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
			HashMap returnMap = new HashMap();
			HashMap resultMap = new HashMap();
			List insuranceList=(List)map.get("insuranceList");
			List componentList=(List)map.get("componentList");
			String event=(String)map.get("event");
			Locale locale = (Locale) map.get(com.integrosys.base.uiinfra.common.Constants.GLOBAL_LOCALE_KEY);
			OBInsuranceGC insurance = (OBInsuranceGC) map.get("insuranceGCObj");
			ICollateralTrxValue itrxValue = (ICollateralTrxValue) map.get("serviceColObj");
			IInsuranceGCJdbc  insuranceGcJdbc = (IInsuranceGCJdbc) BeanHouse.get("insuranceGcJdbc");
			List insList = insuranceGcJdbc.getAllInsuranceForSec(itrxValue.getReferenceID());
			HashMap exceptionMap = new HashMap();
			if(insList!=null && insList.size()!=0)
			{	
			for(int i = 0;i<insList.size();i++)
			{OBInsuranceGC insActual = (OBInsuranceGC)insList.get(i);
			if(null!=insActual.getInsurancePolicyNo() && null!=insActual.getInsuranceCompany()){
				if(insActual.getInsurancePolicyNo().equals(insurance.getInsurancePolicyNo()) && insActual.getInsuranceCompany().equals(insurance.getInsuranceCompany()))
				{	
					exceptionMap.put("duplicateInsuranceError", new ActionMessage("error.string.insurance.id"));
					resultMap.put("request.ITrxValue", itrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
				}
				
			}
			}
			}
			for(int i = 0;i<insuranceList.size();i++)
			{
				OBInsuranceGC insActual = (OBInsuranceGC)insuranceList.get(i);
				
				if(null!=insActual.getInsurancePolicyNo() && null!=insActual.getInsuranceCompany()){
				if(insActual.getInsurancePolicyNo().equals(insurance.getInsurancePolicyNo()) && insActual.getInsuranceCompany().equals(insurance.getInsuranceCompany()))
						{
					exceptionMap.put("duplicateInsuranceError", new ActionMessage("error.string.insurance.id"));
					resultMap.put("request.ITrxValue", itrxValue);
					returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
					returnMap.put(ICommonEventConstant.COMMAND_EXCEPTION_MAP, exceptionMap);
					return returnMap;
						}
			}
			}
			try {
				if(!event.equalsIgnoreCase("maker_cancle_create_insurance")){
					
					if(insurance.getInsuranceCode()==null||insurance.getInsuranceCode().equals("")){
					
					insurance.setInsuranceCode(getInsCodeSeq());
					}
//					if(insurance.getInsuranceRequired().equals("All")){
					
					//Uma Khot::Insurance Deferral maintainance	
					if("All".equals(insurance.getInsuranceRequired())){
						StringBuffer add=new StringBuffer();
						for(int i=0;i<componentList.size();i++){
							LabelValueBean newBean= new LabelValueBean();
							newBean=(LabelValueBean)componentList.get(i);
							String component=newBean.getValue();
							add.append(component).append("-");
						}
						insurance.setSelectComponent(add.toString());
					}
										
						insuranceList.add(insurance);				
						resultMap.put("insuranceList", insuranceList);
						
						ICollateral stagingCollateral = itrxValue.getStagingCollateral();
						CollateralHelper.updateSecurityCoverageDetails(stagingCollateral,insuranceList);
						itrxValue.setStagingCollateral(stagingCollateral);
						resultMap.put("serviceColObj", itrxValue);
						
						resultMap.put("event", event);
						resultMap.put("calculatedDP", map.get("calculatedDP"));
						resultMap.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(stagingCollateral,insuranceList));					
				}
				
				else{
					resultMap.put("insuranceList", insuranceList);
					
					ICollateral stagingCollateral = itrxValue.getStagingCollateral();
					CollateralHelper.updateSecurityCoverageDetails(stagingCollateral,insuranceList);
					itrxValue.setStagingCollateral(stagingCollateral);
					resultMap.put("serviceColObj", itrxValue);
					
					resultMap.put("event", event);
					resultMap.put("calculatedDP", map.get("calculatedDP"));
					resultMap.put(SESSION_TOTAL_INSURANCE_POLICY_AMT,CollateralHelper.getTotalInsurancePolicyAmount(stagingCollateral,insuranceList));
				}
				
				
			}catch (Exception ex) {
				DefaultLogger.debug(this, "got exception in doExecute" + ex);
				ex.printStackTrace();
				throw (new CommandProcessingException(ex.getMessage()));
			}
			
			returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
			return returnMap;
	    }
	 
	 public boolean valueCheck(OBInsuranceGC insurance){
		 boolean value=false;
		 if(insurance.getInsuranceCode()==null||insurance.getInsuranceCode().equals("")){
			 return value;
		 }
		
		return true;
	 }
	 
		public String getInsCodeSeq() {
			
			String insCode="";
			String sql = "SELECT INSURANCEGC_SEQ.NEXTVAL as code FROM dual";
			ResultSet rs;
			try {
				DBUtil dbUtil =new DBUtil();
				dbUtil.setSQL(sql);
				rs = dbUtil.executeQuery();
				// skip initial rows as specified by the startIndex.
				while ( rs.next()) {
					insCode=rs.getString("code");
					NumberFormat numberFormat = new DecimalFormat("0000000");
					 insCode = numberFormat.format(Long.parseLong(insCode));
					 insCode = "IGC" + insCode;
				}
				rs.close();
				dbUtil.close();
			return insCode;
		}
		catch (SQLException ex) {
			throw new SearchDAOException("SQLException in searchDocumentItemList", ex);
		}
		catch (Exception ex) {
			throw new SearchDAOException("Exception in searchDocumentItemList", ex);
		}
		}
		
		


}
