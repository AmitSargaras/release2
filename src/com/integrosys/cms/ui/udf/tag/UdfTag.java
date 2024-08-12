package com.integrosys.cms.ui.udf.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.integrosys.base.techinfra.context.BeanHouse;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.customer.bus.ICustomerSysXRef;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf;
import com.integrosys.cms.app.customer.bus.ILimitXRefUdf2;
import com.integrosys.cms.app.limit.bus.ILimitProfile;
import com.integrosys.cms.app.limit.bus.ILimitProfileUdf;
import com.integrosys.cms.app.udf.bus.IUdf;
import com.integrosys.cms.app.udf.bus.IUdfDao;
import com.integrosys.cms.app.udf.bus.UDFComparator;
import com.integrosys.cms.app.udf.bus.UDFConstants;
import com.integrosys.cms.app.udf.bus.UdfDaoImpl;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.cms.ui.manualinput.aa.AADetailForm;
import com.integrosys.cms.ui.manualinput.customer.ManualInputCustomerInfoForm;
import com.integrosys.cms.ui.manualinput.limit.XRefDetailForm;
import com.integrosys.cms.ui.udf.IUserExtendable;

public class UdfTag extends TagSupport {

	String module;
	String action;
	String refId;
	String refId2;
	String oldUdf;
	
	public void setAction(String action) {
		this.action = action;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
	public void setRefId2(String refId2) {
		this.refId2 = refId2;
	}
	public void setOldUdf(String oldUdf) {
		this.oldUdf = oldUdf;
	}
	
	public int doEndTag() throws JspException {
		
		//IUdfDao udfDao = (UdfDaoImpl) BeanHouse.get("udfDao");
		IUdfDao udfDao = (IUdfDao) BeanHouse.get("udfDao");

		 
		
		String udf_template_line1 = PropertyManager.getValue("udf_template_line1");
		String udf_template_line2 = PropertyManager.getValue("udf_template_line2");
		String udf_template_line3 = PropertyManager.getValue("udf_template_line3");
		String udf_template_line4 = PropertyManager.getValue("udf_template_line4");
		String udf_template_line5 = PropertyManager.getValue("udf_template_line5");
		
		String udf_template_line_hiddenUDF = PropertyManager.getValue("udf_template_line_hiddenUDF");
		
		List udfOldList = new ArrayList();;
		if("add".equalsIgnoreCase(action)) {
		udfOldList = udfDao.getUdfByMandatory("3");
		}else {
			udfOldList = udfDao.getUdfByMandatory("3");
		}
		
		
		List udfOldList_2 = new ArrayList();;
		if("add".equalsIgnoreCase(action)) {
		udfOldList_2 = udfDao.getUdfByMandatory("4");
		}else {
			udfOldList_2 = udfDao.getUdfByMandatory("4");
		}
		
		ICustomerSysXRef sysXRef = null;
		if(null!=oldUdf) {
			sysXRef = (ICustomerSysXRef)pageContext.getRequest().getAttribute("ICustomerSysXRef");
			udf_template_line3 =PropertyManager.getValue("udf_template_line3_1");
			List udfList1 = new ArrayList();
			List udfListNonMandatory = new ArrayList();
			IUdf udf1;
			
			if("add".equalsIgnoreCase(action)) {
				udfListNonMandatory = udfDao.getUdfByNonMandatory("3");
			}else {
//				udfListNonMandatory = udfDao.getUdfByMandatoryView("3");	
				udfListNonMandatory = udfDao.getUdfByNonMandatory("3");
			}
			
			String[] items = oldUdf.split(",");
			for(int i=0;i<udfListNonMandatory.size();i++){
				udf1 = (IUdf) udfListNonMandatory.get(i);
				for(int j=0;j<items.length;j++)
				if(items[j].equals(Long.toString(udf1.getSequence())))
					udfList1.add(udfListNonMandatory.get(i));
			}
			udfOldList.addAll(udfList1);
		}
		
		if (this.module.equalsIgnoreCase(UDFConstants.MODULEDESC_PARTY)) {
			this.module = Long.toString(UDFConstants.MODULEID_PARTY);
		}
		if (this.module.equalsIgnoreCase(UDFConstants.MODULEDESC_CAM)) {
			this.module = Long.toString(UDFConstants.MODULEID_CAM);
		}
		//Start Santosh CR-UBS LIMIT 
		if (this.module.equalsIgnoreCase(UDFConstants.MODULEDESC_LIMIT)) {
			this.module = Long.toString(UDFConstants.MODULEID_LIMIT);
		}
		
		if (this.module.equalsIgnoreCase(UDFConstants.MODULEDESC_LIMIT_2)) {
			this.module = Long.toString(UDFConstants.MODULEID_LIMIT_2);
		}
		//List udfList = udfDao.getUdfByModuleIdAndStatus(this.module, UDFConstants.STATUS_FREEZED);
		
		List udfList = new ArrayList();
		List udfList_2 = new ArrayList();
		if("add".equalsIgnoreCase(action)) {
			udfList=udfDao.getUdfByModuleIdAndStatus(this.module, "ACTIVE");
			udfList_2=udfDao.getUdfByModuleIdAndStatus("4", "ACTIVE");
		}else {
			udfList=udfDao.getUdfByModuleIdAndStatus(this.module, "ACTIVE");
			udfList_2=udfDao.getUdfByModuleIdAndStatus("4", "ACTIVE");
		}
		
	
		if ( module.equalsIgnoreCase("3")) {
			if("add".equalsIgnoreCase(action)) {
				 udfList = udfDao.getUdfByMandatory("3");
				 udfList_2 = udfDao.getUdfByMandatory("4");
			}else {
//				 udfList = udfDao.getUdfByMandatoryView("3");
				udfList = udfDao.getUdfByMandatory("3");
				 udfList_2 = udfDao.getUdfByMandatory("4");
			}
			
		}
		List udfList1 = new ArrayList();
		List udfListNonMandatory = new ArrayList();
		IUdf udf1;
		if(null!=refId){
			//List items = Arrays.asList(refId.split(","));
			
			if("add".equalsIgnoreCase(action)) {
			udfListNonMandatory = udfDao.getUdfByNonMandatory("3");
			}
			else {
//				udfListNonMandatory = udfDao.getUdfByNonMandatoryView("3");	
				udfListNonMandatory = udfDao.getUdfByNonMandatory("3");
			}
			String[] items = refId.split(",");
			for(int i=0;i<udfListNonMandatory.size();i++){
				udf1 = (IUdf) udfListNonMandatory.get(i);
				for(int j=0;j<items.length;j++)
				if(items[j].equals(Long.toString(udf1.getSequence())))
					udfList1.add(udfListNonMandatory.get(i));
			}
			udfList.addAll(udfList1);
		}
		
		List udfList1_2 = new ArrayList();
		List udfListNonMandatory_2 = new ArrayList();
	
		if(null!=refId2){
			//List items = Arrays.asList(refId.split(","));
			
			if("add".equalsIgnoreCase(action)) {
			udfListNonMandatory_2 = udfDao.getUdfByNonMandatory("4");
			}
			else {
//				udfListNonMandatory = udfDao.getUdfByNonMandatoryView("3");	
				udfListNonMandatory_2 = udfDao.getUdfByNonMandatory("4");
			}
			String[] items = refId2.split(",");
			for(int i=0;i<udfListNonMandatory_2.size();i++){
				udf1 = (IUdf) udfListNonMandatory_2.get(i);
				for(int j=0;j<items.length;j++)
				if(items[j].equals(Long.toString(udf1.getSequence())))
					udfList1_2.add(udfListNonMandatory_2.get(i));
			}
			udfList_2.addAll(udfList1_2);
		}
		
		
		
		//End santosh
		IUserExtendable form = null ;
		if ( module.equalsIgnoreCase("1")) {
			ManualInputCustomerInfoForm form1 = (ManualInputCustomerInfoForm)pageContext.getRequest().getAttribute("ManualInputCustomerInfoForm");
			if (form1 != null)
				form = (IUserExtendable)form1;
		}
		if (module.equalsIgnoreCase("cam") || module.equalsIgnoreCase("2")) {
			AADetailForm form1 = (AADetailForm)pageContext.getRequest().getAttribute("AADetailForm");
			if (form1 != null){
				form = (IUserExtendable)form1;
			}
			//Start: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
			else{
				ILimitProfile limitProfileOB = (ILimitProfile) pageContext.getRequest().getAttribute("limitprofileOb");
				if(null!=limitProfileOB)
					form1=udfDao.setUdfDataForCam(limitProfileOB);
				
				if (form1 != null){
					form = (IUserExtendable)form1;
				}
			}
			//End: Uma Khot: Phase 3 CR:Customer details(summary Detail with CAM)
		}
		//added by santosh for ubs limit upload CR
	//	if ( module.equalsIgnoreCase("3") || module.equalsIgnoreCase("4")) {
			if ( module.equalsIgnoreCase("3") ) {
			XRefDetailForm form1 = (XRefDetailForm)pageContext.getRequest().getAttribute("XRefDetailForm");
			if (form1 != null)
				form = (IUserExtendable)form1;
			
			if(null==refId){
				refId="NULL";
			}
		}
		//End santosh for ubs limit upload CR
		JspWriter out = pageContext.getOut();
		try {
			StringBuffer page = new StringBuffer();
			page.append(udf_template_line1+udf_template_line2+udf_template_line3);
			if (udfList == null || udfList.size() == 0) {
				
				out.println("<br><b><i>No User Defined Fields Defined!</b></i>");
			}
			else {
				int size = udfList.size();
				Collections.sort(udfList, new UDFComparator());
				IUdf udf;
				String rowClass;
				String udfmandatory = null;
				for (int i=0; i<size; i++) {
					udf = (IUdf) udfList.get(i);
					udfmandatory = (udf.getMandatory() != null && udf.getMandatory().equalsIgnoreCase("on")) ? "<font color='RED'>*</font>" : "";
					rowClass = (i%2==0)?"even":"odd";
					if(null!=oldUdf) {
						IUdf udfOld=null;
						boolean flag=false;
						for(int j=0;j<udfOldList.size();j++) {
							udfOld = (IUdf) udfOldList.get(j);
							if(udfOld.getSequence()==udf.getSequence()) {
								flag=true;
								if(getValue(form, udfOld.getSequence()).equals(getOldValue(sysXRef, udf.getSequence()))) {
									page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udfOld.getFieldName()+" "+udfmandatory+"</td><td>"+getHTMLForField(udfOld, udfOld.getSequence(), form)+"</td><td>-</td></tr>");
								}else {
									page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udf.getFieldName()+" "+udfmandatory+"</td><td>"+getHTMLForOldField_View(udfOld, udfOld.getSequence(), sysXRef)+"</td><td>"+getHTMLForField(udf, udf.getSequence(), form)+"</td></tr>");
								}
							}
						}
						if(!flag) {
							page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udf.getFieldName()+" "+udfmandatory+"</td><td></td><td>"+getHTMLForField(udf, udf.getSequence(), form)+"</td></tr>");
						}
						
					}else {
						page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udf.getFieldName()+" "+udfmandatory+"</td><td>"+getHTMLForField(udf, udf.getSequence(), form)+"</td></tr>");
					}
				}
				page.append(udf_template_line4);
				page.append(udf_template_line5);
				
			}
			
			if ( module.equalsIgnoreCase("3") || module.equalsIgnoreCase("4")) {
			//StringBuffer page = new StringBuffer();
			page.append(udf_template_line_hiddenUDF);
			if (udfList_2 == null || udfList_2.size() == 0) {
				//out.println("<br><b><i>No User Defined Fields Defined for Limit-2!</b></i>");
			}
			else {
				int size = udfList_2.size();
				Collections.sort(udfList_2, new UDFComparator());
				IUdf udf;
				String rowClass;
				
				String udfmandatory = null;
				for (int i=0; i<size; i++) {
					udf = (IUdf) udfList_2.get(i);
					udfmandatory = (udf.getMandatory() != null && udf.getMandatory().equalsIgnoreCase("on")) ? "<font color='RED'>*</font>" : "";
					rowClass = (i%2==0)?"even":"odd";
					if(null!=oldUdf) {
						IUdf udfOld=null;
						boolean flag=false;
						System.out.println("udfOldList2:: "+udfOldList_2.size());
						for(int j=0;j<udfOldList_2.size();j++) {
							udfOld = (IUdf) udfOldList_2.get(j);
							if(udfOld.getSequence()==udf.getSequence()) {
								flag=true;
								if(getValue(form, (udfOld.getSequence()+115)).equals(getOldValue(sysXRef, (udf.getSequence()+115)))) {
									page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udfOld.getFieldName()+" "+udfmandatory+"</td><td>"+getHTMLForField(udfOld, (udfOld.getSequence()+115), form)+"</td><td>-</td></tr>");
								}else {
									page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udf.getFieldName()+" "+udfmandatory+"</td><td>"+getHTMLForOldField_View(udfOld, (udfOld.getSequence()+115), sysXRef)+"</td><td>"+getHTMLForField(udf, (udf.getSequence()+115), form)+"</td></tr>");
								}
							}
						}
						if(!flag) {
							page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udf.getFieldName()+" "+udfmandatory+"</td><td></td><td>"+getHTMLForField(udf, (udf.getSequence()+115), form)+"</td></tr>");
						}
						
					}else {
						page.append("<tr class=\""+rowClass+"\"><td class=\"fieldname\">"+udf.getFieldName()+" "+udfmandatory+"</td><td>"+getHTMLForField(udf, (udf.getSequence()+115), form)+"</td></tr>");
					}
				}
				page.append(udf_template_line4);
				page.append(udf_template_line5);
				
			}
			}
			out.print(page.toString());
		} 
		catch (IOException e) {
			DefaultLogger.debug(this,"Error"+e.getMessage());
		}
		return EVAL_PAGE;
	}

	public int doStartTag() throws JspException {
		return SKIP_BODY;
	}
	
	private String getHTMLForField_View(IUdf udf, int udfNo, IUserExtendable form) {
		StringBuffer udfBuffer = new StringBuffer();
		long fieldType = udf.getFieldTypeId();
		String fieldName = "udf"+udfNo;
		String fieldValue = getValue(form, udfNo);
		fieldValue = fieldValue != null ? fieldValue : "";
		String[] tokens;
		String checked = "";
		String selected = "";
		if (UDFConstants.FIELDTYPEID_TEXT == fieldType || UDFConstants.FIELDTYPEID_TEXTAREA == fieldType || UDFConstants.FIELDTYPEID_SELECT == fieldType
				|| UDFConstants.FIELDTYPEID_RADIOBUTTON == fieldType || UDFConstants.FIELDTYPEID_CHECKBOX == fieldType || UDFConstants.FIELDTYPEID_DATE == fieldType|| UDFConstants.FIELDTYPEID_NUMERIC_TEXT == fieldType) {
			udfBuffer.append(fieldValue);
			udfBuffer.append("<input type=\"hidden\" name=\""+fieldName+"\"  readonly=\"readonly\"   value=\""+fieldValue+"\"/>");

		}

		/*else if (UDFConstants.FIELDTYPEID_CHECKBOX == fieldType) {
			tokens = udf.getOptions().split(",");
			for (int j=0; j<tokens.length; j++) {
				udfBuffer.append("<input type=\"checkbox\" name=\""+fieldName+"\" value=\""+tokens[j].trim() +"\"/>"+tokens[j].trim()+"<br>");
			}
			
		}*/
		else 
			udfBuffer.append("UDF Not Supported.");
		return udfBuffer.toString();
	}
	
	
	private String getHTMLForField_Add(IUdf udf, int udfNo, IUserExtendable form) {
		StringBuffer udfBuffer = new StringBuffer();
		long fieldType = udf.getFieldTypeId();
		String fieldName = "udf"+udfNo;
		String numLength = udf.getNumericLength();
		String fieldValue = getValue(form, udfNo);
		String mandatoryError=udf.getSequence()+"udfError";
		
		fieldValue = fieldValue != null ? fieldValue : "";
		String[] tokens, fieldValueTokens;
		List fieldValueTokensList = new ArrayList();
		String checked = "";
		String selected = "";
		if (UDFConstants.FIELDTYPEID_TEXT == fieldType) {
			udfBuffer.append("<input type=\"text\" name=\""+fieldName+"\" size=\""+numLength+"\" maxlength=\""+numLength+"\" value=\""+fieldValue+"\"/>");
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
					// By pradeep for error msg
					//udfBuffer.append("<font color = \"RED\">"+mandatoryError+"</font>");
				}
				
			}
		}else if (UDFConstants.FIELDTYPEID_DATE == fieldType) {
			//System.out.println("in add tag ::::::::::::::::::::date field type :::"+fieldType);
			udfBuffer.append("<input type=\"text\" name=\""+fieldName+"\" readonly=\"readonly\" size=\"20\" value=\""+fieldValue+"\"/> <img src=\"img/calendara.gif\"  name=\"Image11a\" border=\"0\" id=\"Image1a\" onclick=\"return showCalendar(\'"+fieldName+"\', \'dd/mm/y\');\" onmouseover=\"MM_swapImage(\'Image11a\',\'\',\'img/calendarb.gif',1)\" />");
			
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
				}
				
			}
		}else if (UDFConstants.FIELDTYPEID_NUMERIC_TEXT == fieldType) {
			//System.out.println("in add tag ::::::::::::::::::::date field type :::"+fieldType);
			udfBuffer.append("<input type=\"text\" name=\""+fieldName+"\" size=\""+numLength+"\" maxlength=\""+numLength+"\" value=\""+fieldValue+"\"/>");
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
				}
				
			}
		}
		else if (UDFConstants.FIELDTYPEID_TEXTAREA == fieldType) {
			//udfBuffer.append("<input type=\"textarea\" name=\""+fieldName+"\" rows=\"5\" cols=\"30\"  value=\""+fieldValue+"\"/>");
			udfBuffer.append("<TEXTAREA name=\""+fieldName+"\" rows=6 cols=30 >"+fieldValue+"</TEXTAREA>");
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
				}
				
			}
		}
		else if (UDFConstants.FIELDTYPEID_SELECT == fieldType) {
			udfBuffer.append("<select name=\""+fieldName+"\">");
			tokens = udf.getOptions().split(",");
			udfBuffer.append("<option value=\"\">Please Select</option>");
			for (int j=0; j<tokens.length; j++) {
				selected = fieldValue.equals(tokens[j].trim()) ? "selected" : "";
				udfBuffer.append("<option "+selected+" value=\""+tokens[j].trim()+"\">"+tokens[j].trim()+"</option>");
			}
			udfBuffer.append("</select>");
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
				}
				
			}
		}
		else if (UDFConstants.FIELDTYPEID_CHECKBOX == fieldType) {
			tokens = udf.getOptions().split(",");
			fieldValueTokens = fieldValue.split(",");
			if (fieldValueTokens != null && fieldValueTokens.length > 0) {
				for (int j=0; j<fieldValueTokens.length; j++) {
					fieldValueTokensList.add(fieldValueTokens[j].trim());
				}
			}
			for (int j=0; j<tokens.length; j++) {
				checked = fieldValueTokensList.contains(tokens[j].trim()) ? "checked" : "";
				System.out.println("UDFchecked :: "+checked);
				udfBuffer.append("<input type=\"checkbox\" name=\""+fieldName+tokens[j].trim()+"\"  id=\""+fieldName+tokens[j].trim()+"\" value=\""+tokens[j].trim() +"\" "+checked +" onclick=\"processUdf('"+tokens[j].trim()+"', '"+fieldName+tokens[j].trim()+"','"+fieldName+"')\"/>"+tokens[j].trim()+"<br>");
			}
			udfBuffer.append("<input type=\"hidden\" name=\""+fieldName+"\"  id=\""+fieldName+"\" value=\""+fieldValue+"\">");
			
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
				}
				
			}
			
		}
		else if (UDFConstants.FIELDTYPEID_RADIOBUTTON == fieldType) {
			tokens = udf.getOptions().split(",");
			for (int j=0; j<tokens.length; j++) {
				checked = fieldValue.equals(tokens[j].trim()) ? "checked" : "";
				udfBuffer.append("<input type=\"radio\" group=\"field5\" name=\""+fieldName+"\"  value=\""+tokens[j].trim() +"\" selected=\"true\" "+checked+"/>"+tokens[j].trim()+"<br>");
			}
			
			if(udf.getMandatory()!=null){
				if(udf.getMandatory().equals("on")){
					udfBuffer.append("</br>");
					udfBuffer.append("<html:errors property=\""+mandatoryError+"\"/>");
				}
				
			}
			
		}
		else 
			udfBuffer.append("UDF Not Supported.");
		return udfBuffer.toString();
	}
	
	private String getHTMLForField(IUdf udf, int udfNo, IUserExtendable form) {
		if (action != null && "add".equalsIgnoreCase(action)) {
			return getHTMLForField_Add(udf, udfNo, form);
		}
		else {
			return getHTMLForField_View(udf, udfNo, form);
		}
	}

	private String getValue(IUserExtendable form, int no) {
		if (form == null) {
			return "";
		}
		switch (no) {
		    case 1:
			return form.getUdf1();
			case 2:
			return form.getUdf2();
			case 3:
			return form.getUdf3();
			case 4:
			return form.getUdf4();
			case 5:
			return form.getUdf5();
			case 6:
			return form.getUdf6();
			case 7:
			return form.getUdf7();
			case 8:
			return form.getUdf8();
			case 9:
			return form.getUdf9();
			case 10:
			return form.getUdf10();
			case 11:
			return form.getUdf11();
			case 12:
			return form.getUdf12();
			case 13:
			return form.getUdf13();
			case 14:
			return form.getUdf14();
			case 15:
			return form.getUdf15();
			case 16:
			return form.getUdf16();
			case 17:
			return form.getUdf17();
			case 18:
			return form.getUdf18();
			case 19:
			return form.getUdf19();
			case 20:
			return form.getUdf20();
			case 21:
			return form.getUdf21();
			case 22:
			return form.getUdf22();
			case 23:
			return form.getUdf23();
			case 24:
			return form.getUdf24();
			case 25:
			return form.getUdf25();
			case 26:
			return form.getUdf26();
			case 27:
			return form.getUdf27();
			case 28:
			return form.getUdf28();
			case 29:
			return form.getUdf29();
			case 30:
			return form.getUdf30();
			case 31:
			return form.getUdf31();
			case 32:
			return form.getUdf32();
			case 33:
			return form.getUdf33();
			case 34:
			return form.getUdf34();
			case 35:
			return form.getUdf35();
			case 36:
			return form.getUdf36();
			case 37:
			return form.getUdf37();
			case 38:
			return form.getUdf38();
			case 39:
			return form.getUdf39();
			case 40:
			return form.getUdf40();
			case 41:
			return form.getUdf41();
			case 42:
			return form.getUdf42();
			case 43:
			return form.getUdf43();
			case 44:
			return form.getUdf44();
			case 45:
			return form.getUdf45();
			case 46:
			return form.getUdf46();
			case 47:
			return form.getUdf47();
			case 48:
			return form.getUdf48();
			case 49:
			return form.getUdf49();
			case 50:
			return form.getUdf50();
			
			case 51:return form.getUdf51();
			case 52:return form.getUdf52();
			case 53:return form.getUdf53();
			case 54:return form.getUdf54();
			case 55:return form.getUdf55();
			case 56:return form.getUdf56();
			case 57:return form.getUdf57();
			case 58:return form.getUdf58();
			case 59:return form.getUdf59();
			case 60:return form.getUdf60();
			case 61:return form.getUdf61();
			case 62:return form.getUdf62();
			case 63:return form.getUdf63();
			case 64:return form.getUdf64();
			case 65:return form.getUdf65();
			case 66:return form.getUdf66();
			case 67:return form.getUdf67();
			case 68:return form.getUdf68();
			case 69:return form.getUdf69();
			case 70:return form.getUdf70();
			case 71:return form.getUdf71();
			case 72:return form.getUdf72();
			case 73:return form.getUdf73();
			case 74:return form.getUdf74();
			case 75:return form.getUdf75();
			case 76:return form.getUdf76();
			case 77:return form.getUdf77();
			case 78:return form.getUdf78();
			case 79:return form.getUdf79();
			case 80:return form.getUdf80();
			case 81:return form.getUdf81();
			case 82:return form.getUdf82();
			case 83:return form.getUdf83();
			case 84:return form.getUdf84();
			case 85:return form.getUdf85();
			case 86:return form.getUdf86();
			case 87:return form.getUdf87();
			case 88:return form.getUdf88();
			case 89:return form.getUdf89();
			case 90:return form.getUdf90();
			case 91:return form.getUdf91();
			case 92:return form.getUdf92();
			case 93:return form.getUdf93();
			case 94:return form.getUdf94();
			case 95:return form.getUdf95();
			case 96:return form.getUdf96();
			case 97:return form.getUdf97();
			case 98:return form.getUdf98();
			case 99:return form.getUdf99();
			case 100:return form.getUdf100();
			
			case 101:return form.getUdf101();
			case 102:return form.getUdf102();
			case 103:return form.getUdf103();
			case 104:return form.getUdf104();
			case 105:return form.getUdf105();
			case 106:return form.getUdf106();
			case 107:return form.getUdf107();
			case 108:return form.getUdf108();
			case 109:return form.getUdf109();
			case 110:return form.getUdf110();
			case 111:return form.getUdf111();
			case 112:return form.getUdf112();
			case 113:return form.getUdf113();
			case 114:return form.getUdf114();
			case 115:return form.getUdf115();
			case 116:return form.getUdf116();
			case 117:return form.getUdf117();
			case 118:return form.getUdf118();
			case 119:return form.getUdf119();
			case 120:return form.getUdf120();
			case 121:return form.getUdf121();
			case 122:return form.getUdf122();
			case 123:return form.getUdf123();
			case 124:return form.getUdf124();
			case 125:return form.getUdf125();
			case 126:return form.getUdf126();
			case 127:return form.getUdf127();
			case 128:return form.getUdf128();
			case 129:return form.getUdf129();
			case 130:return form.getUdf130();
			case 131:return form.getUdf131();
			case 132:return form.getUdf132();
			case 133:return form.getUdf133();
			case 134:return form.getUdf134();
			case 135:return form.getUdf135();
			case 136:return form.getUdf136();
			case 137:return form.getUdf137();
			case 138:return form.getUdf138();
			case 139:return form.getUdf139();
			case 140:return form.getUdf140();
			case 141:return form.getUdf141();
			case 142:return form.getUdf142();
			case 143:return form.getUdf143();
			case 144:return form.getUdf144();
			case 145:return form.getUdf145();
			case 146:return form.getUdf146();
			case 147:return form.getUdf147();
			case 148:return form.getUdf148();
			case 149:return form.getUdf149();
			case 150:return form.getUdf150();
			case 151:return form.getUdf151();
			case 152:return form.getUdf152();
			case 153:return form.getUdf153();
			case 154:return form.getUdf154();
			case 155:return form.getUdf155();
			case 156:return form.getUdf156();
			case 157:return form.getUdf157();
			case 158:return form.getUdf158();
			case 159:return form.getUdf159();
			case 160:return form.getUdf160();
			case 161:return form.getUdf161();
			case 162:return form.getUdf162();
			case 163:return form.getUdf163();
			case 164:return form.getUdf164();
			case 165:return form.getUdf165();
			case 166:return form.getUdf166();
			case 167:return form.getUdf167();
			case 168:return form.getUdf168();
			case 169:return form.getUdf169();
			case 170:return form.getUdf170();
			case 171:return form.getUdf171();
			case 172:return form.getUdf172();
			case 173:return form.getUdf173();
			case 174:return form.getUdf174();
			case 175:return form.getUdf175();
			case 176:return form.getUdf176();
			case 177:return form.getUdf177();
			case 178:return form.getUdf178();
			case 179:return form.getUdf179();
			case 180:return form.getUdf180();
			case 181:return form.getUdf181();
			case 182:return form.getUdf182();
			case 183:return form.getUdf183();
			case 184:return form.getUdf184();
			case 185:return form.getUdf185();
			case 186:return form.getUdf186();
			case 187:return form.getUdf187();
			case 188:return form.getUdf188();
			case 189:return form.getUdf189();
			case 190:return form.getUdf190();
			case 191:return form.getUdf191();
			case 192:return form.getUdf192();
			case 193:return form.getUdf193();
			case 194:return form.getUdf194();
			case 195:return form.getUdf195();
			case 196:return form.getUdf196();
			case 197:return form.getUdf197();
			case 198:return form.getUdf198();
			case 199:return form.getUdf199();
			case 200:return form.getUdf200();
			
			case 201:return form.getUdf201();
			case 202:return form.getUdf202();
			case 203:return form.getUdf203();
			case 204:return form.getUdf204();
			case 205:return form.getUdf205();
			case 206:return form.getUdf206();
			case 207:return form.getUdf207();
			case 208:return form.getUdf208();
			case 209:return form.getUdf209();
			case 210:return form.getUdf210();
			case 211:return form.getUdf211();
			case 212:return form.getUdf212();
			case 213:return form.getUdf213();
			case 214:return form.getUdf214();
			case 215:return form.getUdf215();
			
			default:
			return "";
		}
	}
	private String getOldValue(ICustomerSysXRef obj, int no) {
		if (obj == null) {
			return "";
		}
		
		ILimitXRefUdf udf[] = obj.getXRefUdfData();
		ILimitXRefUdf2 udf2[] = obj.getXRefUdfData2();
	
			switch (no) {
			
			
		    	case 1:
		    	return udf[0].getUdf1_Value();
		    	case 2:
		    	return udf[0].getUdf2_Value();
		    	case 3:
		    	return udf[0].getUdf3_Value();
		    	case 4:
				return udf[0].getUdf4_Value();
				case 5:
				return udf[0].getUdf5_Value();
				case 6:
				return udf[0].getUdf6_Value();
				case 7:
				return udf[0].getUdf7_Value();
				case 8:
				return udf[0].getUdf8_Value();
				case 9:
				return udf[0].getUdf9_Value();
				case 10:
				return udf[0].getUdf10_Value();
				case 11:
				return udf[0].getUdf11_Value();
				case 12:
				return udf[0].getUdf12_Value();
				case 13:
				return udf[0].getUdf13_Value();
				case 14:
				return udf[0].getUdf14_Value();
				case 15:
				return udf[0].getUdf15_Value();
				case 16:
				return udf[0].getUdf16_Value();
				case 17:
				return udf[0].getUdf17_Value();
				case 18:
				return udf[0].getUdf18_Value();
				case 19:
				return udf[0].getUdf19_Value();
				case 20:
				return udf[0].getUdf20_Value();
				case 21:
				return udf[0].getUdf21_Value();
				case 22:
				return udf[0].getUdf22_Value();
				case 23:
				return udf[0].getUdf23_Value();
				case 24:
				return udf[0].getUdf24_Value();
				case 25:
				return udf[0].getUdf25_Value();
				case 26:
				return udf[0].getUdf26_Value();
				case 27:
				return udf[0].getUdf27_Value();
				case 28:
				return udf[0].getUdf28_Value();
				case 29:
				return udf[0].getUdf29_Value();
				case 30:
				return udf[0].getUdf30_Value();
				case 31:
				return udf[0].getUdf31_Value();
				case 32:
				return udf[0].getUdf32_Value();
				case 33:
				return udf[0].getUdf33_Value();
				case 34:
				return udf[0].getUdf34_Value();
				case 35:
				return udf[0].getUdf35_Value();
				case 36:
				return udf[0].getUdf36_Value();
				case 37:
				return udf[0].getUdf37_Value();
				case 38:
				return udf[0].getUdf38_Value();
				case 39:
				return udf[0].getUdf39_Value();
				case 40:
				return udf[0].getUdf40_Value();
				case 41:
				return udf[0].getUdf41_Value();
				case 42:
				return udf[0].getUdf42_Value();
				case 43:
				return udf[0].getUdf43_Value();
				case 44:
				return udf[0].getUdf44_Value();
				case 45:
				return udf[0].getUdf45_Value();
				case 46:
				return udf[0].getUdf46_Value();
				case 47:
				return udf[0].getUdf47_Value();
				case 48:
				return udf[0].getUdf48_Value();
				case 49:
				return udf[0].getUdf49_Value();
				case 50:
				return udf[0].getUdf50_Value();
				
				case 51:return udf[0].getUdf51_Value();
				case 52:return udf[0].getUdf52_Value();
				case 53:return udf[0].getUdf53_Value();
				case 54:return udf[0].getUdf54_Value();
				case 55:return udf[0].getUdf55_Value();
				case 56:return udf[0].getUdf56_Value();
				case 57:return udf[0].getUdf57_Value();
				case 58:return udf[0].getUdf58_Value();
				case 59:return udf[0].getUdf59_Value();
				case 60:return udf[0].getUdf60_Value();
				case 61:return udf[0].getUdf61_Value();
				case 62:return udf[0].getUdf62_Value();
				case 63:return udf[0].getUdf63_Value();
				case 64:return udf[0].getUdf64_Value();
				case 65:return udf[0].getUdf65_Value();
				case 66:return udf[0].getUdf66_Value();
				case 67:return udf[0].getUdf67_Value();
				case 68:return udf[0].getUdf68_Value();
				case 69:return udf[0].getUdf69_Value();
				case 70:return udf[0].getUdf70_Value();
				case 71:return udf[0].getUdf71_Value();
				case 72:return udf[0].getUdf72_Value();
				case 73:return udf[0].getUdf73_Value();
				case 74:return udf[0].getUdf74_Value();
				case 75:return udf[0].getUdf75_Value();
				case 76:return udf[0].getUdf76_Value();
				case 77:return udf[0].getUdf77_Value();
				case 78:return udf[0].getUdf78_Value();
				case 79:return udf[0].getUdf79_Value();
				case 80:return udf[0].getUdf80_Value();
				case 81:return udf[0].getUdf81_Value();
				case 82:return udf[0].getUdf82_Value();
				case 83:return udf[0].getUdf83_Value();
				case 84:return udf[0].getUdf84_Value();
				case 85:return udf[0].getUdf85_Value();
				case 86:return udf[0].getUdf86_Value();
				case 87:return udf[0].getUdf87_Value();
				case 88:return udf[0].getUdf88_Value();
				case 89:return udf[0].getUdf89_Value();
				case 90:return udf[0].getUdf90_Value();
				case 91:return udf[0].getUdf91_Value();
				case 92:return udf[0].getUdf92_Value();
				case 93:return udf[0].getUdf93_Value();
				case 94:return udf[0].getUdf94_Value();
				case 95:return udf[0].getUdf95_Value();
				case 96:return udf[0].getUdf96_Value();
				case 97:return udf[0].getUdf97_Value();
				case 98:return udf[0].getUdf98_Value();
				case 99:return udf[0].getUdf99_Value();
				case 100:return udf[0].getUdf100_Value();
				
				case 101:return udf[0].getUdf101_Value();
			    case 102:return udf[0].getUdf102_Value();
			    case 103:return udf[0].getUdf103_Value();
			    case 104:return udf[0].getUdf104_Value();
				case 105:return udf[0].getUdf105_Value();
				case 106:return udf[0].getUdf106_Value();
				case 107:return udf[0].getUdf107_Value();
				case 108:return udf[0].getUdf108_Value();
				case 109:return udf[0].getUdf109_Value();
				case 110:return udf[0].getUdf110_Value();
					
				case 111:return udf[0].getUdf111_Value();
				case 112:return udf[0].getUdf112_Value();
				case 113:return udf[0].getUdf113_Value();
				case 114:return udf[0].getUdf114_Value();
				case 115:return udf[0].getUdf115_Value();
			
				case 116:return udf2[0].getUdf116_Value();
				case 117:return udf2[0].getUdf117_Value();
				case 118:return udf2[0].getUdf118_Value();
				case 119:return udf2[0].getUdf119_Value();
				case 120:return udf2[0].getUdf120_Value();
				case 121:return udf2[0].getUdf121_Value();
				case 122:return udf2[0].getUdf122_Value();
				case 123:return udf2[0].getUdf123_Value();
				case 124:return udf2[0].getUdf124_Value();
				case 125:return udf2[0].getUdf125_Value();
				case 126:return udf2[0].getUdf126_Value();
				case 127:return udf2[0].getUdf127_Value();
				case 128:return udf2[0].getUdf128_Value();
				case 129:return udf2[0].getUdf129_Value();
				case 130:return udf2[0].getUdf130_Value();
				case 131:return udf2[0].getUdf131_Value();
				case 132:return udf2[0].getUdf132_Value();
				case 133:return udf2[0].getUdf133_Value();
				case 134:return udf2[0].getUdf134_Value();
				case 135:return udf2[0].getUdf135_Value();
				case 136:return udf2[0].getUdf136_Value();
				case 137:return udf2[0].getUdf137_Value();
				case 138:return udf2[0].getUdf138_Value();
				case 139:return udf2[0].getUdf139_Value();
				case 140:return udf2[0].getUdf140_Value();
				case 141:return udf2[0].getUdf141_Value();
				case 142:return udf2[0].getUdf142_Value();
				case 143:return udf2[0].getUdf143_Value();
				case 144:return udf2[0].getUdf144_Value();
				case 145:return udf2[0].getUdf145_Value();
				case 146:return udf2[0].getUdf146_Value();
				case 147:return udf2[0].getUdf147_Value();
				case 148:return udf2[0].getUdf148_Value();
				case 149:return udf2[0].getUdf149_Value();
				case 150:return udf2[0].getUdf150_Value();
					
				case 151:return udf2[0].getUdf151_Value();
				case 152:return udf2[0].getUdf152_Value();
				case 153:return udf2[0].getUdf153_Value();
				case 154:return udf2[0].getUdf154_Value();
				case 155:return udf2[0].getUdf155_Value();
				case 156:return udf2[0].getUdf156_Value();
				case 157:return udf2[0].getUdf157_Value();
				case 158:return udf2[0].getUdf158_Value();
				case 159:return udf2[0].getUdf159_Value();
				case 160:return udf2[0].getUdf160_Value();
				case 161:return udf2[0].getUdf161_Value();
				case 162:return udf2[0].getUdf162_Value();
				case 163:return udf2[0].getUdf163_Value();
				case 164:return udf2[0].getUdf164_Value();
				case 165:return udf2[0].getUdf165_Value();
				case 166:return udf2[0].getUdf166_Value();
				case 167:return udf2[0].getUdf167_Value();
				case 168:return udf2[0].getUdf168_Value();
				case 169:return udf2[0].getUdf169_Value();
				case 170:return udf2[0].getUdf170_Value();
				case 171:return udf2[0].getUdf171_Value();
				case 172:return udf2[0].getUdf172_Value();
				case 173:return udf2[0].getUdf173_Value();
				case 174:return udf2[0].getUdf174_Value();
				case 175:return udf2[0].getUdf175_Value();
				case 176:return udf2[0].getUdf176_Value();
				case 177:return udf2[0].getUdf177_Value();
				case 178:return udf2[0].getUdf178_Value();
				case 179:return udf2[0].getUdf179_Value();
				case 180:return udf2[0].getUdf180_Value();
				case 181:return udf2[0].getUdf181_Value();
				case 182:return udf2[0].getUdf182_Value();
				case 183:return udf2[0].getUdf183_Value();
				case 184:return udf2[0].getUdf184_Value();
				case 185:return udf2[0].getUdf185_Value();
				case 186:return udf2[0].getUdf186_Value();
				case 187:return udf2[0].getUdf187_Value();
				case 188:return udf2[0].getUdf188_Value();
				case 189:return udf2[0].getUdf189_Value();
				case 190:return udf2[0].getUdf190_Value();
				case 191:return udf2[0].getUdf191_Value();
				case 192:return udf2[0].getUdf192_Value();
				case 193:return udf2[0].getUdf193_Value();
				case 194:return udf2[0].getUdf194_Value();
				case 195:return udf2[0].getUdf195_Value();
				case 196:return udf2[0].getUdf196_Value();
				case 197:return udf2[0].getUdf197_Value();
				case 198:return udf2[0].getUdf198_Value();
				case 199:return udf2[0].getUdf199_Value();
				case 200:return udf2[0].getUdf200_Value();

				case 201:return udf2[0].getUdf201_Value();
		    	case 202:return udf2[0].getUdf202_Value();
		    	case 203:return udf2[0].getUdf203_Value();
		    	case 204:return udf2[0].getUdf204_Value();
				case 205:return udf2[0].getUdf205_Value();
				case 206:return udf2[0].getUdf206_Value();
				case 207:return udf2[0].getUdf207_Value();
				case 208:return udf2[0].getUdf208_Value();
				case 209:return udf2[0].getUdf209_Value();
				case 210:return udf2[0].getUdf210_Value();
				case 211:return udf2[0].getUdf211_Value();
				case 212:return udf2[0].getUdf212_Value();
				case 213:return udf2[0].getUdf213_Value();
				case 214:return udf2[0].getUdf214_Value();
				case 215:return udf2[0].getUdf215_Value();
				
				
			default:
			return "";
		}
	}
	private String getHTMLForOldField_View(IUdf udf, int udfNo, ICustomerSysXRef obj) {
		StringBuffer udfBuffer = new StringBuffer();
		long fieldType = udf.getFieldTypeId();
		String fieldName = "udf"+udfNo;
		String fieldValue = getOldValue(obj, udfNo);
		fieldValue = fieldValue != null ? fieldValue : "";
		String[] tokens;
		String checked = "";
		String selected = "";
		if (UDFConstants.FIELDTYPEID_TEXT == fieldType || UDFConstants.FIELDTYPEID_TEXTAREA == fieldType || UDFConstants.FIELDTYPEID_SELECT == fieldType
				|| UDFConstants.FIELDTYPEID_RADIOBUTTON == fieldType || UDFConstants.FIELDTYPEID_CHECKBOX == fieldType || UDFConstants.FIELDTYPEID_DATE == fieldType|| UDFConstants.FIELDTYPEID_NUMERIC_TEXT == fieldType) {
			udfBuffer.append(fieldValue);
		}
		else 
			udfBuffer.append("UDF Not Supported.");
		return udfBuffer.toString();
	}
}
