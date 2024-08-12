package com.integrosys.cms.ui.checklist.secreceipt;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.integrosys.base.techinfra.dbsupport.DBConnectionException;
import com.integrosys.base.techinfra.dbsupport.DBUtil;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.AbstractCommand;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.checklist.bus.ICheckListItem;
import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.app.customer.bus.ICMSCustomer;
import com.integrosys.cms.app.transaction.OBTrxContext;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.user.app.bus.ICommonUser;

public class SecurityReceiptImageTagUntagCommand extends AbstractCommand implements ICommonEventConstant{

	public String[][] getParameterDescriptor() {
		return (new String[][] { { "frame", "java.lang.String", SERVICE_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
				{ "checkList", "com.integrosys.cms.app.checklist.bus.ICheckList", SERVICE_SCOPE },
				{ "legalFirm", "java.lang.String", REQUEST_SCOPE },
				{ "selectedArrayMap", "java.util.HashMap", SERVICE_SCOPE },
				{ IGlobalConstant.USER, "com.integrosys.component.user.app.bus.ICommonUser", GLOBAL_SCOPE },
				{ IGlobalConstant.GLOBAL_CUSTOMER_OBJ, "com.integrosys.cms.app.customer.bus.ICMSCustomer", GLOBAL_SCOPE },
				{ "checkListItem", "com.integrosys.cms.app.checklist.bus.ICheckListItem", SERVICE_SCOPE }, 
				{"event", "java.lang.String", REQUEST_SCOPE},
				{ "prev_event", "java.lang.String", REQUEST_SCOPE }, 
				
				{ "fileNamed", "java.lang.String", REQUEST_SCOPE },					
				{ "imageIdes", "java.lang.String", REQUEST_SCOPE },
				{ "taggUntagg", "java.lang.String", REQUEST_SCOPE },
				{ "ddescs", "java.lang.String", REQUEST_SCOPE },					
				{ "dcodes", "java.lang.String", REQUEST_SCOPE },
				{"forms","com.integrosys.cms.ui.checklist.secreceipt.SecurityReceiptForm",SERVICE_SCOPE},
				{"theOBTrxContext", "com.integrosys.cms.app.transaction.OBTrxContext", FORM_SCOPE},
																
																
                { "isErrorEvent", "java.lang.String", REQUEST_SCOPE }, 
		});
	}
	
	public String[][] getResultDescriptor() {
		return (new String[][] {
				{"event", "java.lang.String", REQUEST_SCOPE},
				{"imageFileNameList", "java.util.List", REQUEST_SCOPE},
				{"imageFileNameList", "java.util.List", SERVICE_SCOPE},
				{ "fileNamed", "java.lang.String", REQUEST_SCOPE },					
				{ "imageIdes", "java.lang.String", REQUEST_SCOPE },
				{ "taggUntagg", "java.lang.String", REQUEST_SCOPE },
				{ "ddescs", "java.lang.String", REQUEST_SCOPE },					
				{ "dcodes", "java.lang.String", REQUEST_SCOPE },
				{ "index", "java.lang.String", REQUEST_SCOPE },
		});
	}

	
	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {
		HashMap returnMap = new HashMap();
		HashMap resultMap = new HashMap();
		DefaultLogger.debug(this, "Inside doExecute()");
		try {
			OBTrxContext ctx = (OBTrxContext) map.get("theOBTrxContext");
			SecurityReceiptForm form = (SecurityReceiptForm) map.get("forms");
			System.out.println("SecurityReceiptImageTagUntagCommand with form == "+form);
			String event = (String) map.get("event");
			String index = (String) map.get("index");
			System.out.println("index --- - - - -  === "+index);
			ICheckListItem checkListItem = (ICheckListItem) map.get("checkListItem");
			String checklistId=String.valueOf(checkListItem.getCheckListItemID());
			ICMSCustomer custOB = (ICMSCustomer) map.get(IGlobalConstant.GLOBAL_CUSTOMER_OBJ);
			String custIdNum = custOB.getCifId();
			ICommonUser custOB1 = (ICommonUser) map.get(IGlobalConstant.USER);		
			List imageFileNameList = new ArrayList(); 
			List imageFileNameListFromTemp = new ArrayList();
			if(event.equalsIgnoreCase("imagetaguntaglist")) {
				imageFileNameListFromTemp = (ArrayList) getImageTagUntagListFromTemp(custIdNum,checklistId);
				imageFileNameList = (ArrayList) getImageTagUntagList(checklistId,custIdNum,imageFileNameListFromTemp);
			}
			String documentCode = (String) map.get("dcodes");
			String documentDesc = (String) map.get("ddescs");
			System.out.println("Document Code == "+documentCode+" and Document Descriptioon == "+documentDesc);
			DefaultLogger.debug(this, "Inside doExecute() AND EVENT IS "+event);
			if(event.equals("securityreceipt_image_tag_untag_submit")) {
				
				String fileNamed = (String) map.get("fileNamed");
				String imageid = (String) map.get("imageIdes");
				String taggUntagg1 = (String) map.get("taggUntagg");
				String documentCode1 = (String) map.get("dcodes");
				String documentDesc1 = (String) map.get("ddescs");
				System.out.println("fileNamed === "+fileNamed);
				System.out.println("imageid === "+imageid);
				System.out.println("taggUntagg1 === "+taggUntagg1);
				System.out.println("event after image tag ==="+event);
				System.out.println("documentCode1 === "+documentCode1);
				System.out.println("documentDesc1 ==="+documentDesc1);
				resultMap.put("fileNamed", fileNamed);
				resultMap.put("imageIdes", imageid);
				resultMap.put("taggUntagg", taggUntagg1);
			}
			
			resultMap.put("event", map.get("event"));
			resultMap.put("index", index);
			resultMap.put("dcodes", documentCode);
			resultMap.put("ddescs", documentDesc);
			resultMap.put("imageFileNameList", imageFileNameList);
		}
		catch (Exception e) {
			DefaultLogger.debug(this, "got exception in doExecute" + e);
			e.printStackTrace();
			throw (new CommandProcessingException(e.getMessage()));
		}
		DefaultLogger.debug(this, "Going out of doExecute()");
		returnMap.put(ICommonEventConstant.COMMAND_RESULT_MAP, resultMap);
		return returnMap;
	}
	
	
private List getImageTagUntagList(String checklistId, String custIdNum,List imageFileNameListFromTemp) {
		List filenameOFimageList = new ArrayList();
		String securityImg = ICMSConstant.IMAGE_CATEGORY_SECURITY;
		String data="";
		String data2="";
		String data3="";
		String data4="";
		String data5="";
		String sql1="";
		String sql2="";
		String sql3="";
		String sql4="";
		String sql5="";
		//TreeMap map= new TreeMap();
		//List imageDataList = new ArrayList();
		String sql="SELECT IMG_FILENAME,IMG_ID,STATUS " + 
				"FROM CMS_UPLOADED_IMAGES " + 
				"WHERE IMG_ID IN " + 
				"  (SELECT image_id " + 
				"  FROM cms_image_tag_map " + 
				"  WHERE tag_id IN " + 
				"    (SELECT id " + 
				"    FROM cms_image_tag_details " + 
				"    WHERE DOC_DESC = " + 
				"      (SELECT TO_CHAR(doc_item_id) " + 
				"      FROM cms_checklist_item " + 
				"      WHERE DOC_ITEM_ID='"+checklistId+"' " + 
				"      ) " + 
				"    ) " + 
				"  ) AND CUST_ID ='"+custIdNum+"' AND category = 'SECURITY'  ";
//		String sql = "SELECT * FROM CMS_UPLOADED_IMAGES  WHERE CUST_ID = '"+custIdNum+"'  AND IMG_DEPRICATED='N' AND CATEGORY = 'SECURITY' ";
		
		System.out.println("SecurityReceiptImageTagUntagCommand.java => getImageTagUntagList()  Query sql == "+sql);
		DBUtil dbUtil=null;
		DBUtil dbUtil1=null;
		DBUtil dbUtil2=null;
		DBUtil dbUtil3=null;
		DBUtil dbUtil4=null;
		DBUtil dbUtil5=null;
		try {
			dbUtil=new DBUtil();
			 dbUtil1=new DBUtil();
//			 dbUtil2=new DBUtil();
//			 dbUtil3=new DBUtil();
//			 dbUtil4=new DBUtil();
//			 dbUtil5=new DBUtil();
			 ResultSet rs1=null;
			 ResultSet rs2=null;
			 ResultSet rs3=null;
			 ResultSet rs4=null;
			 ResultSet rs5=null;
			dbUtil.setSQL(sql);
			ResultSet rs = dbUtil.executeQuery();
			if(null!=rs){
				while(rs.next()){
					List imageDataList = new ArrayList();
					data=rs.getString("IMG_FILENAME");
					data2=rs.getString("IMG_ID");
					data4=rs.getString("STATUS");
					//sql1="SELECT distinct UNTAGGED_STATUS FROM cms_image_tag_map where image_id = '"+data2+"'";
					sql1 = "SELECT DISTINCT UNTAGGED_STATUS  " + 
							"				 FROM cms_image_tag_map  " + 
							"				 WHERE image_id= '"+data2+"' and tag_id IN  " + 
							"				  (SELECT id  " + 
							"				   FROM cms_image_tag_details  " + 
							"				  WHERE DOC_DESC =  " + 
							"				    (SELECT TO_CHAR(doc_item_id) " + 
							"				     FROM cms_checklist_item " + 
							"				     WHERE DOC_ITEM_ID='"+checklistId+"' " + 
							"				    )  " + 
							"				   )";
					System.out.println("SecurityReceiptImageTagUntagCommand image_id sql1 query=>"+sql1);
					dbUtil1.setSQL(sql1);
					rs1 = dbUtil1.executeQuery();
					while(rs1.next()){
					data3=rs1.getString("UNTAGGED_STATUS");
					}
					imageDataList.add(data);
					imageDataList.add(data2);
					imageDataList.add(data3);
					imageDataList.add(data4);
					
					if(!imageDataList.isEmpty()) {
						filenameOFimageList.add(imageDataList);
					}
				}
			}
			
			rs.close();
			if(rs1 != null) {
				rs1.close();
			}
			dbUtil.close();
			if(dbUtil1 != null) {
				dbUtil1.close();
			}
			
			dbUtil2=new DBUtil();
			
//			sql2 = "SELECT DISTINCT IMG_FILENAME,IMG_ID FROM CMS_TEMP_IMAGE_UPLOAD WHERE TYPE_OF_DOC = 'Security'";
//			sql2 = "SELECT DISTINCT IMG_FILENAME,IMG_ID FROM CMS_TEMP_IMAGE_UPLOAD WHERE CATEGORY = 'SECURITY'";
			sql2 = "SELECT image_id,UNTAGGED_STATUS " + 
					"  FROM cms_image_tag_map " + 
					"  WHERE tag_id IN " + 
					"    (SELECT id " + 
					"    FROM cms_image_tag_details " + 
					"    WHERE DOC_DESC = " + 
					"      (SELECT TO_CHAR(doc_item_id) " + 
					"      FROM cms_checklist_item " + 
					"      WHERE DOC_ITEM_ID='"+checklistId+"' " + 
					"      ) AND CUST_ID = '"+custIdNum+"' " + 
					"    )";
			System.out.println("Security Imag TagCommand sql2 query=>"+sql2);
			dbUtil2.setSQL(sql2);
			rs2 = dbUtil2.executeQuery();
			if(null!=rs2){
				while(rs2.next()){
					List imageDataList = new ArrayList();
					//data=rs2.getString("IMG_FILENAME");
					data2 = rs2.getString("image_id");
					data3 = rs2.getString("UNTAGGED_STATUS");
					data4 = "";
					data5 = "";
					
					sql5 = "SELECT IMG_ID FROM CMS_UPLOADED_IMAGES WHERE IMG_ID='"+data2+"'";
					dbUtil5=new DBUtil();
					dbUtil5.setSQL(sql5);
					rs5 = dbUtil5.executeQuery();
					while(rs5.next()){
						data5=rs5.getString("IMG_ID");
					}
					
					System.out.println("security Outside while IMG_ID data5="+data5);
					
					rs5.close();
					dbUtil5.close();
					
					if(data5 == null || "".equals(data5)) {
						
					sql3 = "SELECT IMG_FILENAME FROM CMS_TEMP_IMAGE_UPLOAD WHERE IMG_ID = '"+data2+"'";
					System.out.println("SecurityReceiptImageTagUntagCommand THIRD QUERY FOR IMAGE FILE NAME sql3 =>"+sql3);
					
					dbUtil3=new DBUtil();
					dbUtil3.setSQL(sql3);
					rs3 = dbUtil3.executeQuery();
					while(rs3.next()){
					data=rs3.getString("IMG_FILENAME");
					}
					if(data != null && !"".equals(data)) {					
					imageDataList.add(data);
					imageDataList.add(data2);
					imageDataList.add(data3);
					imageDataList.add(data4);
					}
					
					if(!imageDataList.isEmpty()) {
						filenameOFimageList.add(imageDataList);
					}
					rs3.close();
					dbUtil3.close();
					}
				}
			}
			for(int i=0;i<imageFileNameListFromTemp.size();i++) {
			sql4 = "SELECT DISTINCT IMG_FILENAME,IMG_ID,STATUS FROM CMS_TEMP_IMAGE_UPLOAD WHERE CATEGORY = 'SECURITY' and CUST_ID ='"+custIdNum+"' " + 
					"    AND IMG_ID = '"+imageFileNameListFromTemp.get(i)+"' ";
			System.out.println("Temp folder Query In Security sql4=>"+sql4);
			dbUtil4=new DBUtil();
			dbUtil4.setSQL(sql4);
			rs4 = dbUtil4.executeQuery();
			while(rs4.next()){
			List imageDataList = new ArrayList();
			data=rs4.getString("IMG_FILENAME");
			data2=rs4.getString("IMG_ID");
			data3="Y";
			data4=rs4.getString("STATUS");
			imageDataList.add(data);
			imageDataList.add(data2);
			imageDataList.add(data3);
			imageDataList.add(data4);
			if(!imageDataList.isEmpty()) {
				filenameOFimageList.add(imageDataList);
			}
			}
			rs4.close();
			dbUtil4.close();
			}
			
//			rs.close();
//			if(rs1 != null) {
//				rs1.close();
//			}
			
			String sqls = "SELECT IMG_FILENAME,IMG_ID,STATUS " + 
					"FROM CMS_UPLOADED_IMAGES " + 
					"WHERE IMG_DEPRICATED = 'N' AND CUST_ID = '"+custIdNum+"' AND CATEGORY = 'SECURITY' AND STATUS = '3' AND IMG_ID NOT IN " + 
					" (SELECT DISTINCT image_id " + 
					" FROM cms_image_tag_map)";
			System.out.println("SecurityReceiptImageTagUntagCommand => Quesry sqls=>"+sqls);
			data = "";
			data2 = "";
			data4 = "";
			data3 = "";
			
			DBUtil dbUtils=null;
			dbUtils=new DBUtil();
				 
				 ResultSet rss=null;
				dbUtils.setSQL(sqls);
				rss = dbUtils.executeQuery();
				if(null!=rss){
					while(rss.next()){
						List imageDataList = new ArrayList();
						data = rss.getString("IMG_FILENAME");
						data2=rss.getString("IMG_ID");
						data3=rss.getString("STATUS");
						data4="Y";
						System.out.println("SecurityReceiptImageTagUntagCommand.java => getImageTagUntagList() Query sqls from cms_upload_image only, inside while=>");
						System.out.println("IMG_FILENAMEs=>"+data+" IMG_IDs=>"+data2+" STATUSs=>"+data3);
						if(data != null && !"".equals(data)) {					
							imageDataList.add(data);
							imageDataList.add(data2);
							imageDataList.add(data3);
							imageDataList.add(data4);
							}
							System.out.println("SecurityReceiptImageTagUntagCommand only from table cms_uploaded_images  data="+data+" data2="+data2+" data3="+data3+" data4="+data4);
							
							if(!imageDataList.isEmpty()) {
								filenameOFimageList.add(imageDataList);
							}
					}
				}
				if(rss != null) {
					rss.close();
				}
				if(dbUtils != null) {
					dbUtils.close();
				}
			
			
			
			if(rs2 != null) {
				rs2.close();
			}
			if(rs3 != null) {
				rs3.close();
			}
			if(rs4 != null) {
				rs4.close();
			}
			if(rs5 != null) {
				rs5.close();
			}
//			if(!imageDataList.isEmpty()) {
//				filenameOFimageList.add(imageDataList);
//			}
		} catch (DBConnectionException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{ 
			try {
//				dbUtil.close();
//				if(dbUtil1 != null) {
//					dbUtil1.close();
//				}
				if(dbUtil2 != null) {
					dbUtil2.close();
				}
				if(dbUtil3 != null) {
					dbUtil3.close();
				}
				if(dbUtil4 != null) {
					dbUtil4.close();
				}
				if(dbUtil5 != null) {
					dbUtil5.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return filenameOFimageList;
	}




private List getImageTagUntagListFromTemp(String custIdNum,String checklistId) {
	List filenameOFimageList = new ArrayList();
	String securityImg = ICMSConstant.IMAGE_CATEGORY_SECURITY;
	String data="";
	String data1="";
	String data2="";
	String sql1 = "";
	String sql2="";
	boolean flag = true;
	List imageDataList = new ArrayList();
	DBUtil dbUtil=null;
	DBUtil dbUtil1=null;
	DBUtil dbUtil2=null;
	try {
	
	
	sql2 = "SELECT image_id " + 
			"  FROM cms_image_tag_map " + 
			"  WHERE tag_id IN " + 
			"    (SELECT id " + 
			"    FROM cms_image_tag_details " + 
			"    WHERE DOC_DESC = " + 
			"      (SELECT TO_CHAR(doc_item_id) " + 
			"      FROM cms_checklist_item " + 
			"      WHERE DOC_ITEM_ID='"+checklistId+"' " + 
			"      ) AND CUST_ID = '"+custIdNum+"' " + 
			"    )";
	
	
	
	ResultSet rs2=null;
	dbUtil2=new DBUtil();
	dbUtil2.setSQL(sql2);
	rs2 = dbUtil2.executeQuery();
	if(null!=rs2){
		while(rs2.next()){
			data2 = rs2.getString("image_id");
			imageDataList.add(data2);
		}
	}
	if(rs2 != null) {
		rs2.close();
	}
	
	String sql="SELECT DISTINCT IMG_ID FROM CMS_TEMP_IMAGE_UPLOAD WHERE CATEGORY = '"+securityImg+"' and CUST_ID ='"+custIdNum+"' ";
	System.out.println("SecurityReceiptImageTagUntagCommand.java => getImageTagUntagListFromTemp() Query sql1=>"+sql);
	
	
	
		 dbUtil=new DBUtil();
		 dbUtil1=new DBUtil();
		 ResultSet rs=null;
		 ResultSet rs1=null;
		 
		dbUtil.setSQL(sql);
		rs = dbUtil.executeQuery();
		if(null!=rs){
			while(rs.next()){
				data=rs.getString("IMG_ID");
				
				for(int i=0;i<imageDataList.size();i++) {
					if(data.equals(imageDataList.get(i))) {
						flag = false;
					}
				}
				if(flag==true) {
					filenameOFimageList.add(data);
				}
				flag = true;
//				sql1 = "SELECT IMAGE_ID FROM CMS_IMAGE_TAG_MAP WHERE IMAGE_ID = '"+data+"'";
//				dbUtil1.setSQL(sql1);
//				rs1 = dbUtil1.executeQuery();
//				if(null!=rs1){
//					while(rs1.next()){
//						data1=rs1.getString("IMAGE_ID");
////						if(data1 == null || "".equals(data1)) {
////							filenameOFimageList.add(data1);
////						}
//					}
//					if(data1 == null || "".equals(data1)) {
//						filenameOFimageList.add(data1);
//					}
//					}
//				filenameOFimageList.add(data);
			}
		}
		rs.close();
		if(rs1 != null) {
			rs1.close();
		}
	}catch (DBConnectionException e) {
		e.printStackTrace();
	} catch (SQLException e) {
		e.printStackTrace();
	}
	finally{ 
		try {
			dbUtil.close();
			if(dbUtil1 != null) {
				dbUtil1.close();
			}
			if(dbUtil2 != null) {
				dbUtil2.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	return filenameOFimageList;
}
	
	
}
