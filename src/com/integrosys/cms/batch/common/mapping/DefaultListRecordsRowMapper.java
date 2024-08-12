package com.integrosys.cms.batch.common.mapping;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.integrosys.base.techinfra.converter.EnhancedStringLocaleConverter;
import com.integrosys.cms.app.common.constant.ICMSConstant;

public class DefaultListRecordsRowMapper extends AbstractRowMapper {
	private ColumnMetaInfo[] columnMetaInfos;
	
	private Map columnFieldMap;
	
	/**
	 * Set the column meta infos. Used in conjunction with the
	 * {@link ColumnMetaInfoArrayPropertyEditor}. This property can be set in
	 * the form of a String describing the column properties.
	 * 
	 * @param columnMetaInfos the column meta info
	 */
	public void setColumnMetaInfos(ColumnMetaInfo[] columnMetaInfos) {
		this.columnMetaInfos = columnMetaInfos;
	}
	
	public void setColumnFieldMap(Map columnFieldMap) {
		this.columnFieldMap = columnFieldMap;
	}

	public Object doMapRow(ResultSet rs, int arg1) throws SQLException {
		List records = new ArrayList();		
		for (int i = 0; i < this.columnMetaInfos.length; i++) {
			ColumnMetaInfo metaInfo = columnMetaInfos[i];
			
			String columnName = null;
			if (columnFieldMap != null) {
				columnName = (String)columnFieldMap.get(String.valueOf(metaInfo.getColumnNumber()));
			}
			//System.out.println("<<<<<<<<<<<< "+metaInfo.getColumnNumber()+"\tcolumnName: "+ columnName);
			if (metaInfo.getClassType() == String.class) {
				records = addStringRecord(records, rs, columnName, metaInfo.getColumnNumber());
			} else if (metaInfo.getClassType() == Double.class ||
					metaInfo.getClassType() == BigDecimal.class) {
				double value = getDoubleRecord(rs, columnName, metaInfo.getColumnNumber());
				if (StringUtils.isNotBlank(metaInfo.getNumberPattern())) {
					records.add(new EnhancedStringLocaleConverter(Locale.getDefault(), metaInfo.getNumberPattern()).setHasSeperator(false).convert(new Double(value)));
				} else if (metaInfo.getDecimalPoints() <= 0) {
					records.add(new EnhancedStringLocaleConverter(Locale.getDefault()).setHasSeperator(false).convert(new Double(value)));
				} else
					records.add(new EnhancedStringLocaleConverter(
							Locale.getDefault(), metaInfo.getDecimalPoints()).setHasSeperator(false).convert(new Double(value)));				
			} else if (metaInfo.getClassType() == Date.class) {
				java.sql.Date date = getDateRecord(rs, columnName, metaInfo.getColumnNumber());
				if (date != null) {
					SimpleDateFormat formatter = new SimpleDateFormat(metaInfo.getDateFormat());
					records.add(formatter.format(date));
				} else {
					records.add(null);
				}
			} else if (metaInfo.getClassType() == Long.class) {
				records.add(String.valueOf(getLongRecord(rs, columnName, metaInfo.getColumnNumber())));
			} else if (metaInfo.getClassType() == Integer.class) {
				records.add(String.valueOf(getIntRecord(rs, columnName, metaInfo.getColumnNumber())));
			} else if (metaInfo.getClassType() == Boolean.class) {
				if (getBooleanRecord(rs, columnName, metaInfo.getColumnNumber())) {
					records.add(metaInfo.getBooleanTrueValue());
				} else {
					records.add(ICMSConstant.FALSE_VALUE);
				}
			} else {
				throw new IllegalArgumentException("unknown data type [" + metaInfo + "]");
			}
		}
		return records;
	}
	
	private List addStringRecord(List records, ResultSet rs, String columnName, int columnNumber) throws SQLException {
		//System.out.println(" <<<<<<<<< columnName: "+columnName+"\t columnNumber: "+columnNumber);		
		if (columnName != null)
			records.add(rs.getString(columnName));
		else
			records.add(rs.getString(columnNumber));	
		
		return records;
	}

	private double getDoubleRecord(ResultSet rs, String columnName, int columnNumber) throws SQLException {
		double value;
		if (columnName != null)
			value = rs.getDouble(columnName);
		else
			value = rs.getDouble(columnNumber);
		
		return value;
	}
	
	private java.sql.Date getDateRecord(ResultSet rs, String columnName, int columnNumber) throws SQLException {
		java.sql.Date date;
		if (columnName != null)
			date = rs.getDate(columnName);
		else
			date = rs.getDate(columnNumber);
		
		return date;
	}
	
	private int getIntRecord(ResultSet rs, String columnName, int columnNumber) throws SQLException {
		int value;
		if (columnName != null)
			value = rs.getInt(columnName);
		else
			value = rs.getInt(columnNumber);
		
		return value;
	}
	
	private long getLongRecord(ResultSet rs, String columnName, int columnNumber) throws SQLException {
		long value;
		if (columnName != null)
			value = rs.getLong(columnName);
		else
			value = rs.getLong(columnNumber);
		
		return value;
	}	
	
	private boolean getBooleanRecord(ResultSet rs, String columnName, int columnNumber) throws SQLException {
		boolean value;
		if (columnName != null)
			value = rs.getBoolean(columnName);
		else
			value = rs.getBoolean(columnNumber);
		return value;
	}
}
