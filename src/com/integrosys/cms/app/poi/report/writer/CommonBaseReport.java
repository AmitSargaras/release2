package com.integrosys.cms.app.poi.report.writer;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.Date;

import com.integrosys.base.techinfra.propertyfile.PropertyManager;
import com.integrosys.cms.app.poi.report.xml.schema.IReportConstants;



public abstract class CommonBaseReport {

	protected static int textFormat = FileFormat.COL_TYPE_STRING;
	protected static int amountFormat =  FileFormat.COL_TYPE_AMOUNT_FLOAT; 
	
	public CommonBaseReport() {
		super();
	}
	
	public String getBasePath() {
		return PropertyManager.getValue(IReportConstants.BASE_PATH);
	}
	
	public String convertToString(Object object) {
		if(object != null && !object.equals("")) {
			if(object instanceof Date)
//				return DateUtils.toString((Date)object);
				return object.toString();
			else if (object instanceof Boolean)
			{
				if((Boolean)object == true)
					return "Yes";
				else
					return "No";
			}
			return object.toString();
		}
		else
			return "";
	}
	public void getStringLength(String strValue,StringBuffer strBuff,int len) {
		if ((strValue).length() > len){
			
			strBuff.append(strValue.substring(0, len).trim() + "\n");
		}else
			strBuff.append(strValue.trim() + "\n");
	}
	
	
	public int getLines(String strValue, int width) {
	
		int len = strValue.length();
		
		int lines = len/width;
		if(lines>0)
		return lines-1;
		else 
		return 0;
		
	}

	protected <T> String getValueForField(T dto, String fieldName) {
		String methodName = "get" + fieldName;
		try {
			Class dtoClass = dto.getClass();
			Method dtoMethod = dtoClass.getMethod(methodName, new Class[] {});

			Object returnValue = dtoMethod.invoke(dto);
			if (returnValue != null) {
				if("getModifiedOn".equalsIgnoreCase(dtoMethod.getName()) || "getCheckedOn".equalsIgnoreCase(dtoMethod.getName())){
//					return DateUtils.toStringWithTime((Date) returnValue);
					return returnValue.toString();
				}
				if (returnValue instanceof Date) {
//					return com.aurionpro.cashpro.commons.utils.DateUtils.toString((Date) returnValue);
					return returnValue.toString();
				}
				if (returnValue instanceof Boolean)
				{
					if((Boolean)returnValue == true)
						return "Yes";
					else
						return "No";
				}
				if (returnValue instanceof Double || returnValue instanceof Float) {
//					return getFormattedString(returnValue.toString());
					return returnValue.toString();
				}
				return returnValue.toString();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
			throw e;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
/*	public static String getFormattedString(String strValue) {
		
		strValue = StringUtils.remove(strValue, ",");
		Double amount = 0D;
		if(strValue != null && StringUtils.isNotEmpty(strValue))
			amount = Double.valueOf(strValue);
		return formatAmount(amount);
	}
	public static Double getAmount(String strValue) {
		
		strValue = StringUtils.remove(strValue, ",");
		Double amount = 0D;
		if(strValue != null && StringUtils.isNotEmpty(strValue))
			amount = Double.valueOf(strValue);
		return amount;
	}*/

	public static String getFormattedString(double amount) {
		return formatAmount(amount);
	}

	private static String formatAmount(Double amount) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		df.setGroupingUsed(true);
		df.setGroupingSize(3);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		return df.format(amount);
	}
	
	private static String formatAmountWithOutGrouping(Double amount) {
		DecimalFormat df = (DecimalFormat) DecimalFormat.getInstance();
		df.setGroupingUsed(false);
		df.setGroupingSize(0);
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		return df.format(amount);
	}
	
	public String getStringLength(String strValue,int len) {
		if ((strValue).length() > len)
			return strValue.substring(0, len).trim();
		else
			return strValue.trim();
	}
	
	public void getStringLength(String strValue, StringBuffer strBuff) {
		if ((strValue).length() > 20)
			strBuff.append(strValue.substring(0, 20).trim() + "\n");
		else
			strBuff.append(strValue.trim() + "\n");
	}
}
