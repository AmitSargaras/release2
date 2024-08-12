package com.integrosys.cms.ui.goodsMaster;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.util.LabelValueBean;

import com.integrosys.base.businfra.transaction.TransactionException;
import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.goodsMaster.bus.IGoodsMaster;
import com.integrosys.cms.app.goodsMaster.bus.OBGoodsMaster;
import com.integrosys.cms.app.collateral.bus.ICollateralDAO;
import com.integrosys.cms.app.geography.region.bus.IRegion;
import com.integrosys.cms.app.goodsMaster.bus.GoodsMasterException;
import com.integrosys.cms.app.goodsMaster.proxy.IGoodsMasterProxyManager;
import com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue;
import com.integrosys.cms.app.goodsMaster.trx.OBGoodsMasterTrxValue;
import com.integrosys.cms.app.systemBank.bus.SystemBankException;
import com.integrosys.cms.ui.manualinput.CommonUtil;

public class MakerReadGoodsMasterCmd extends AbstractCommand implements ICommonEventConstant{

	private IGoodsMasterProxyManager goodsMasterProxy;

	public IGoodsMasterProxyManager getGoodsMasterProxy() {
		return goodsMasterProxy;
	}
	public void setGoodsMasterProxy(IGoodsMasterProxyManager goodsMasterProxy) {
		this.goodsMasterProxy = goodsMasterProxy;
	}
	/**
	 * Default Constructor
	 */
	public MakerReadGoodsMasterCmd() {
	}
	
	/**
	 * Defines an two dimensional array with the parameter list to be passed to
	 * the doExecute method by a HashMap syntax for the array is
	 * (HashMapkey,classname,scope) The scope may be request,form or service
	 * 
	 * @return the two dimensional String array
	 */
	public String[][] getParameterDescriptor() {
		return (new String[][] { 
			 {"goodsCode", "java.lang.String", REQUEST_SCOPE},
			 {"goodsName", "java.lang.String", REQUEST_SCOPE},
			 {"restrictionType", "java.lang.String", REQUEST_SCOPE},
			 {"id", "java.lang.String", REQUEST_SCOPE},
			 { "startIndex", "java.lang.String", REQUEST_SCOPE },
			 { "TrxId", "java.lang.String", REQUEST_SCOPE },
			 {"event", "java.lang.String", REQUEST_SCOPE},
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
				{ "goodsMasterObj", "com.integrosys.cms.app.goodstMaster.bus.OBGoodsMaster", FORM_SCOPE },
				{"IgoodsMasterTrxValue", "com.integrosys.cms.app.goodsMaster.trx.IGoodsMasterTrxValue", SERVICE_SCOPE},
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "restrictionTypeList", "java.util.List", SERVICE_SCOPE },
				{ "goodsParentCodeList", "java.util.List", SERVICE_SCOPE },
				{ "goodsParentCode", "java.lang.String", SERVICE_SCOPE },
				{ "restrictionType", "java.lang.String", SERVICE_SCOPE }
				
		});
	}
	
	/**
	 * This method does the Business operations with the HashMap and put the
	 * results back into the HashMap.Here creation for Company Borrower is done.
	 * 
	 * @param map is of type HashMap
	 * @return HashMap with the Result
	 */
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException,SystemBankException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		try {
			IGoodsMaster goodsMaster;
			IGoodsMasterTrxValue trxValue=null;
			String id=(String) (map.get("id"));
			String goodsCode=(String) (map.get("goodsCode"));
			String goodsName=(String) (map.get("goodsName"));
			String restrictionType=(String) (map.get("restrictionType"));
			String startIdx = (String) map.get("startIndex");
			String event = (String) map.get("event");
			ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
			List restrictionTypeList = (List) collateralDAO.getRestrictionTypeList();
			if("checker_view_goods_master".equals(event)) {
				trxValue = (OBGoodsMasterTrxValue) getGoodsMasterProxy().getGoodsMasterTrxValue((goodsCode));
			}else {
				trxValue = (OBGoodsMasterTrxValue) getGoodsMasterProxy().getGoodsMasterTrxValue((id));
			}
//			if("checker_view_goods_master".equals(event)) {
//				goodsMaster = (OBGoodsMaster) trxValue.getStagingGoodsMaster();
//			}else {
				goodsMaster = (OBGoodsMaster) trxValue.getGoodsMaster();
//			}
			DefaultLogger.debug(this, "startIdx: " + startIdx);
			if((trxValue.getStatus().equals("PENDING_CREATE"))||(trxValue.getStatus().equals("PENDING_UPDATE"))||(trxValue.getStatus().equals("PENDING_DELETE"))||trxValue.getStatus().equals("REJECTED")||trxValue.getStatus().equals("DRAFT"))
			{
				resultMap.put("wip", "wip");
			}
			
			if("checker_view_goods_master".equals(event)) {
				String parentCode = goodsMaster.getGoodsParentCode();
				resultMap.put("goodsParentCode", parentCode);
				goodsMaster.setGoodsParentCode(parentCode);
				
				String restType = goodsMaster.getRestrictionType();
				goodsMaster.setRestrictionType(restType);
				
				resultMap.put("IgoodsMasterTrxValue", trxValue);
				resultMap.put("goodsMasterObj", goodsMaster);
				resultMap.put("event", event);
				resultMap.put("startIndex",startIdx);
			}else {
				String parentCode = getGoodsParentCode(id);
				resultMap.put("goodsParentCode", parentCode);
				
				goodsMaster.setGoodsParentCode(parentCode);
				List goodsRestrictionType = getRestrictionType(id);
				
				String restType = (String) goodsRestrictionType.get(0);
				goodsMaster.setRestrictionType(restType);
				OBGoodsMasterTrxValue goodsMasterTrxValue = new OBGoodsMasterTrxValue();
				resultMap.put("goodsParentCodeList", getGoodsParentCodeList());
				DefaultLogger.debug(this, "goodsParentCodeList" + getGoodsParentCodeList());
				resultMap.put("restrictionType",  getRestrictionType(id));
				DefaultLogger.debug(this, "RestrictionTypeList ===>" + restrictionTypeList);
				resultMap.put("restrictionTypeList",  restrictionTypeList);
				resultMap.put("IgoodsMasterTrxValue", trxValue);
				resultMap.put("goodsMasterObj", goodsMaster);
				resultMap.put("event", event);
				resultMap.put("startIndex",startIdx);
			}
			
		} catch (GoodsMasterException e) {
		
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		} catch (Exception e) {
            DefaultLogger.debug(this, "got exception in doExecute" + e);
            e.printStackTrace();
            throw (new CommandProcessingException(e.getMessage()));
        }

		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	private List getGoodsParentCodeList() {
		 List lbValList = new ArrayList();
			try {
				DefaultLogger.debug(this, "inside getGoodsParentCodeList() method");
				ICollateralDAO collateralDAO = (ICollateralDAO)BeanHouse.get("collateralDao");
				List idList = (List) collateralDAO.getGoodsParentCodeList();
				for (int i = 0; i < idList.size(); i++) {
					//IGoodsMaster region = (IGoodsMaster)idList.get(i);
						//String id = region.getGoodsCode();
						//String val = region.getGoodsName();
					String id =(String) idList.get(i);
					String val = (String) idList.get(i);
						LabelValueBean lvBean = new LabelValueBean(val, id);
						lbValList.add(id);
				}
			} catch (Exception ex) {
			}
			//return CommonUtil.sortDropdown(lbValList);
			return lbValList;
		}
	
	public String getGoodsParentCode(String id) {
		String parentCode = "";
		 DBUtil dbUtil = null;
		try{
			DefaultLogger.debug(this, " getGoodsParentCode() started.");
			dbUtil = new DBUtil();			   
			
			String query1="SELECT GOODS_PARENT_CODE " + 
					"FROM CMS_GOODS_MASTER D WHERE ID ='"+id+"'";
			System.out.println("xrefIdList()=> sql query=>"+query1);
			ResultSet rs=null;
			 
			dbUtil.setSQL(query1);
			 rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					parentCode = rs.getString("GOODS_PARENT_CODE");
				//xrefIdList.add(xrefId);
				}
			}
			rs.close();
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}catch (Exception e) {
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, " getXrefIdList() completed.");
		return parentCode;
	}
	
	public ArrayList getRestrictionType(String id) {
		 ArrayList xrefIdList = new ArrayList();
		 DBUtil dbUtil = null;
		try{
			DefaultLogger.debug(this, " getRestrictionType() started.");
			dbUtil = new DBUtil();			   
			
			/*String query1="SELECT ENTRY_NAME " + 
					"FROM COMMON_CODE_CATEGORY_ENTRY J " + 
					"WHERE J.CATEGORY_CODE = 'RESTRICTED_TYPE' " ;
			System.out.println("xrefIdList()=> sql query=>"+query1);*/
			String query1="SELECT RESTRICTION_TYPE " + 
					"FROM CMS_GOODS_MASTER D WHERE ID ='"+id+"'";
			System.out.println("xrefIdList()=> sql query=>"+query1);
			ResultSet rs=null;
			 
			dbUtil.setSQL(query1);
			 rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
				String xrefId = rs.getString("RESTRICTION_TYPE");
				xrefIdList.add(xrefId);
				}
			}
			rs.close();
		}
		catch(SQLException e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		catch(Exception e){
			DefaultLogger.debug(this,e.getMessage());
			e.printStackTrace();
		}
		finally{
			try {
				if (dbUtil != null) {
					dbUtil.close();
				}
			}catch (Exception e) {
				DefaultLogger.debug(this,e.getMessage());
				e.printStackTrace();
			}
		}
		DefaultLogger.debug(this, " getXrefIdList() completed.");
		return xrefIdList;
	}
}