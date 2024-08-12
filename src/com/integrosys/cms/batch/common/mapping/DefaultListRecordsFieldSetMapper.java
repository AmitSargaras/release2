package com.integrosys.cms.batch.common.mapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.batch.item.file.mapping.FieldSet;

/**
 * <p>
 * Map a line of feed of batch into a list instead of domain objects.
 * <p>
 * Base on the <tt>ColumnMetaInfo</tt> of each column <i>(starting from 1, will
 * minus internal when interact with <tt>FieldSet</tt>)</i> of the feed, it
 * retrieve the correct value from the <tt>FieldSet</tt>. And do some
 * manipulation on decimal values or date/timestamp
 * @author Chong Jun Yong
 * @see ColumnMetaInfoArrayPropertyEditor
 * 
 */
public class DefaultListRecordsFieldSetMapper extends AbstractFieldSetMapper {
	private ColumnMetaInfo[] columnMetaInfos;

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

	public Object doMapLine(FieldSet fs) {
		List records = new ArrayList();
		for (int i = 0; i < this.columnMetaInfos.length; i++) {
			ColumnMetaInfo metaInfo = columnMetaInfos[i];
			int column = metaInfo.getColumnNumber();
			// for fieldset, 1st column is 0
			int columnIndexValue = column - 1;

			// early checking on blank value
			if (StringUtils.isBlank(fs.readString(columnIndexValue))) {
				records.add(null);
				continue;
			}

			if (metaInfo.getClassType() == String.class) {
				records.add(fs.readString(columnIndexValue));
			}
			else if (metaInfo.getClassType() == Double.class) {
				int divider = (metaInfo.getDecimalPoints() <= 0) ? 1 : (int) Math.pow(10, metaInfo.getDecimalPoints());
				records.add(new Double(fs.readDouble(columnIndexValue) / divider));
			}
			else if (metaInfo.getClassType() == BigDecimal.class) {
				int divider = (metaInfo.getDecimalPoints() <= 0) ? 1 : (int) Math.pow(10, metaInfo.getDecimalPoints());
				BigDecimal value = new BigDecimal(fs.readString(columnIndexValue));

				BigDecimal dividerDecimal = (BigDecimal) AbstractFieldSetMapper.DIVIDER_STRING_BIGDECIMAL_OBJECTS
						.get(Integer.toString(divider));
				if (dividerDecimal == null) {
					dividerDecimal = new BigDecimal(Integer.toString(divider));
					AbstractFieldSetMapper.DIVIDER_STRING_BIGDECIMAL_OBJECTS.put(Integer.toString(divider),
							dividerDecimal);
				}

				if (divider > 1) {
					value = value.divide(dividerDecimal, metaInfo.getDecimalPoints(), BigDecimal.ROUND_HALF_UP);
				}
				records.add(value);
			}
			else if (metaInfo.getClassType() == Date.class) {
				records.add(fs.readDate(columnIndexValue, metaInfo.getDateFormat()));
			}
			else if (metaInfo.getClassType() == Long.class) {
				records.add(new Long(fs.readLong(columnIndexValue)));
			}
			else if (metaInfo.getClassType() == Integer.class) {
				records.add(new Integer(fs.readInt(columnIndexValue)));
			}
			else if (metaInfo.getClassType() == Boolean.class) {
				records.add(new Boolean(fs.readBoolean(columnIndexValue, metaInfo.getBooleanTrueValue())));
			}
			else {
				throw new IllegalArgumentException("unknown data type [" + metaInfo + "]");
			}
		}

		return records;
	}
}
