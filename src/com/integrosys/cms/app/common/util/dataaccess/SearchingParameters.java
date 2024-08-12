/*
 * Created on Jun 4, 2004
 *
 * 
 */
package com.integrosys.cms.app.common.util.dataaccess;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author heju
 * 
 */
public class SearchingParameters implements Serializable {
	private Map contents = new HashMap();

	public SearchingParameters() {
		contents.put("$LESSTHAN", "<");
		contents.put("$LESSEQUAL", "<=");
		contents.put("$GREATERTHAN", ">");
		contents.put("$GREATEREQUAL", ">=");
		contents.put("$NOTEQUAL", "<>");
	}

	public SearchingParameters(Map contents_) {
		this.contents = contents_;
	}

	public SearchingParameters(Object key, Object value) {
		this();
		contents.put(key, value);
	}

	public Object get(Object key) {
		return contents.get(key);
	}

	public void put(Object key, Object value) {
		contents.put(key, value);
	}

	public boolean containsKey(String key) {
		return contents.containsKey(key);
	}

	public String toString() {
		Iterator keysets = contents.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		String key, value;
		while (keysets.hasNext()) {
			key = (String) keysets.next();
			value = (String) contents.get(key);
			sb.append(key + "=" + value + "\n");
		}
		return sb.toString();
	}
}
