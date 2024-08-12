package com.integrosys.cms.batch.common.mapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.mapping.FieldSetMapper;

/**
 * <p>
 * Abstract implementation of {@link FieldSetMapper}, if the field set passed in
 * is null, then return null, else do the actual map line action.
 * 
 * <p>
 * Subclass to implements {@link #doMapLine(FieldSet)} to provide the actual
 * field set mapping action. And also provide the date format required to parse
 * the date field.
 * 
 * @author Chong Jun Yong
 * 
 */
public abstract class AbstractFieldSetMapper implements FieldSetMapper {

	/**
	 * key is the divider string, such as 100, 1000, value is the it's
	 * BigDecimal object
	 */
	protected static final Map DIVIDER_STRING_BIGDECIMAL_OBJECTS = Collections.synchronizedMap(new HashMap());

	private String dateFormat;

	/**
	 * To set the date format required to parse the date field from the flat
	 * file
	 * 
	 * @param dateFormat date format required to parse the date field
	 */
	public final void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * Return the date format required to parse the date field from flat file
	 * 
	 * @return date format required to parse the date field
	 */
	public String getDateFormat() {
		return dateFormat;
	}

	public Object mapLine(FieldSet fs) {
		if (fs == null) {
			return null;
		}

		return doMapLine(fs);
	}

	/**
	 * To do actual mapping on fieldset to domain object, null field set will be
	 * taken care at the first place, ie. return null for null field set
	 * supplied.
	 * 
	 * @param fs field set containing raw string that is tokenized earlier
	 * @return domain objects mapped from field set supplied
	 */
	public abstract Object doMapLine(FieldSet fs);

}
