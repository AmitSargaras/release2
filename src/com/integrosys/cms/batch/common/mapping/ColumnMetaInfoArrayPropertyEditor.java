package com.integrosys.cms.batch.common.mapping;

import java.beans.PropertyEditorSupport;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * Property Editor to transform lines of column meta info into a array of
 * <tt>ColumnMetaInfo</tt>
 * <p>
 * Each line is a key and value pair, key is the column number (start from 1),
 * value is the meta info in text.
 * <p>
 * <ul>
 * Available format for the meta info:
 * <li><b>string</b> (java.lang.String)
 * <li><b>boolean</b> (java.lang.Boolean), the value represent boolean true
 * <li><b>long</b> (java.lang.Long)
 * <li><b>double</b> (java.lang.Double), decimal points can be provided
 * <li><b>integer</b> (java.lang.Integer)
 * <li><b>date</b> (java.util.Date), date format <b>must</b> be provided
 * <li><b>bigdecimal</b> (java.math.BigDecimal), decimal points can be provided
 * </ul>
 * 
 * Example format:<br>
 * <i>1=string<br>
 * 2=decimal,DECIMAL_3<br>
 * 3=date,DATEFORMAT_ddMMyyyy</i> <br>
 * 4=boolean,BOOLEANTRUE_Y
 * <p>
 * For above case, <br>
 * column 1 will be string type <br>
 * column 2 is decimal value, having 3 decimal points <br>
 * column 3 is a date value, having format of ddMMyyyy <br>
 * column 4 is a boolean value, Y = true, other value is false
 * 
 * @author Chong Jun Yong
 * 
 */
public class ColumnMetaInfoArrayPropertyEditor extends PropertyEditorSupport {

	/**
	 * simple type name class mapping when doing property editor, key is the
	 * simple type name, value is the class
	 */
	private static final Map simpleTypeNameClassMap = new HashMap();

	static {
		simpleTypeNameClassMap.put("string", String.class);
		simpleTypeNameClassMap.put("long", Long.class);
		simpleTypeNameClassMap.put("double", Double.class);
		simpleTypeNameClassMap.put("integer", Integer.class);
		simpleTypeNameClassMap.put("bigdecimal", BigDecimal.class);
		simpleTypeNameClassMap.put("date", Date.class);
		simpleTypeNameClassMap.put("boolean", Boolean.class);
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.isBlank(text)) {
			throw new IllegalArgumentException("no value specify for column meta info");
		}

		String[] lines = StringUtils.split(text.trim(), "\r\n");

		List columnMetaInfoList = new ArrayList();
		for (int i = 0; i < lines.length; i++) {
			String[] keyValue = StringUtils.split(lines[i].trim(), '=');
			if (keyValue.length != 2) {
				throw new IllegalArgumentException("Incorrect meta info format [" + lines[i].trim()
						+ "], correct format is x=y, x=y,prefix_z");
			}

			String key = keyValue[0];
			String value = keyValue[1];

			// check against column number
			int column = 0;
			try {
				column = Integer.parseInt(key);
			}
			catch (NumberFormatException ex) {
				throw new IllegalArgumentException("key provided [" + key + "] is not a valid column number");
			}

			if (column <= 0) {
				throw new IllegalArgumentException("column number provided [" + column
						+ "] is not valid, must at least 1.");
			}

			// check against the type, prefix
			String[] tokens = StringUtils.split(value, ',');
			String simpleClassTypeName = tokens[0];

			Class classType = (Class) simpleTypeNameClassMap.get(simpleClassTypeName);

			if (classType == null) {
				throw new IllegalArgumentException("class type provided [" + simpleClassTypeName
						+ "] is not supported currently. Supported classes are ["
						+ ArrayUtils.toString(simpleTypeNameClassMap.values().toArray()) + "]");
			}

			if (tokens.length > 1) {
				if (tokens[1].startsWith(ColumnMetaInfo.DECIMAL_PREFIX)) {
					if (classType != Double.class && classType != BigDecimal.class) {
						throw new IllegalArgumentException("Invalid class type [" + classType
								+ "] for decimal value, try double or bigdecimal");
					}

					int decimalPoints = 0;
					try {
						decimalPoints = Integer.parseInt(tokens[1].substring(ColumnMetaInfo.DECIMAL_PREFIX.length()));
					}
					catch (NumberFormatException ex) {
						throw new IllegalArgumentException("Invalid decimal point value [" + tokens[1] + "]");
					}

					columnMetaInfoList.add(new ColumnMetaInfo(column, classType, decimalPoints));
				}
				else if (tokens[1].startsWith(ColumnMetaInfo.DATE_FORMAT_PREFIX)) {
					String dateFormat = tokens[1].substring(ColumnMetaInfo.DATE_FORMAT_PREFIX.length());
					columnMetaInfoList.add(new ColumnMetaInfo(column, classType, dateFormat));
				}
				else if (tokens[1].startsWith(ColumnMetaInfo.BOOLEAN_TRUE_PREFIX)) {
					String booleanTrueValue = tokens[1].substring(ColumnMetaInfo.BOOLEAN_TRUE_PREFIX.length());
					columnMetaInfoList.add(new ColumnMetaInfo(column, booleanTrueValue));
				}
				else if (tokens[1].startsWith(ColumnMetaInfo.NUMBER_PATTERN_PREFIX)) {
					String patternValue = tokens[1].substring(ColumnMetaInfo.NUMBER_PATTERN_PREFIX.length());
					if (tokens.length > 2) {
						for (int j = 2; j < tokens.length; j++) {
							patternValue += ","+tokens[j];
						}
					}
					columnMetaInfoList.add(new ColumnMetaInfo(column, classType, 0, patternValue));
				}
				else {
					throw new IllegalArgumentException("Invalid prefix value [" + tokens[1] + "]");
				}
			}
			else {
				if (classType == Date.class) {
					throw new IllegalArgumentException("Date format must be provided for 'date' type column");
				}
				else if (classType == Boolean.class) {
					throw new IllegalArgumentException("Boolean true value must be provided for 'boolean' type column");
				}

				columnMetaInfoList.add(new ColumnMetaInfo(column, classType));
			}
		}

		setValue((ColumnMetaInfo[]) columnMetaInfoList.toArray(new ColumnMetaInfo[0]));
	}
}