/*
 * Created on Jun 3, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.integrosys.cms.batch.reuterfeed;

import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

import com.integrosys.base.techinfra.util.PropertyUtil;

/**
 * @author Administrator
 * 
 *         TODO To change the template for this generated type comment go to
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class MappingFeedPostProcessor implements IFeedPostProcessor {
	private String FEED_DATE_FORMAT = "yyyyMMdd";

	private static final String feedLayoutPropFileName = "/feedlayout.properties";

	private PropertyUtil prop;

	public MappingFeedPostProcessor() {
		prop = PropertyUtil.getInstance(FeedConstant.feedLayoutPropFileName);
		FEED_DATE_FORMAT = prop.getProperty(FeedConstant.PROPNAME_DATE_FORMAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.integrosys.cms.batch.reuterfeed.IFeedPostProcessor#performFeedPostProcess
	 * (java.util.Map) map the data in the feed from string to correct data type
	 * and set data to null if it is null value
	 */
	public void performFeedPostProcess(Map fieldsMapping) throws Exception {
		Iterator iter = fieldsMapping.values().iterator();
		try {
			while (iter.hasNext()) {
				FeedFieldDef nextField = (FeedFieldDef) (iter.next());
				String fieldType = nextField.getFieldType();
				Object fieldData = nextField.getFieldValue();
				if (FeedUtil.checkDataIsNull(fieldData)) {
					nextField.setFieldValue(null);
				}
				else {
					nextField.setFieldValue(mapDataType(fieldType, fieldData));
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private Object mapDataType(String fieldType, Object fieldData) throws Exception {
		String origValue = (String) fieldData;
		if (FeedConfiguration.FIELD_TYPE_STRING.equalsIgnoreCase(fieldType)) {
			return origValue;
		}
		else if (FeedConfiguration.FIELD_TYPE_INTEGER.equalsIgnoreCase(fieldType)) {
			return new Integer(origValue);
		}
		else if (FeedConfiguration.FIELD_TYPE_LONG.equalsIgnoreCase(fieldType)) {
			return new Long(origValue);
		}
		else if (FeedConfiguration.FIELD_TYPE_FLOAT.equalsIgnoreCase(fieldType)) {
			return new Float(origValue);
		}
		else if (FeedConfiguration.FIELD_TYPE_DOUBLE.equalsIgnoreCase(fieldType)) {
			return new Double(origValue);
		}
		else if (FeedConfiguration.FIELD_TYPE_DATE.equalsIgnoreCase(fieldType)) {
			SimpleDateFormat sf = new SimpleDateFormat(FEED_DATE_FORMAT);
			return sf.parse(origValue);
		}
		else {
			throw new Exception("Unknow data type detected! " + fieldType);
		}
	}

}
