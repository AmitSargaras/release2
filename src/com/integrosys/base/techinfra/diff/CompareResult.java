package com.integrosys.base.techinfra.diff;

import java.io.Serializable;

/**
 * This class holds the object that is compared and its compare result flag
 * (U/M/A/D) User: ravi Date: Jun 13, 2003 Time: 10:34:48 AM
 */
public class CompareResult implements Serializable {

	private Object obj;

	private String key;

	private boolean isModified;

	private boolean isUnmodified;

	private boolean isAdded;

	private boolean isDeleted;

	private Object original;

	public CompareResult(Object o, String key) {
		this.obj = o;
		this.key = key;
	}

	public Object getObj() {
		return obj;
	}

	public String getKey() {
		return key;
	}

	public boolean isModified() {
		return isModified;
	}

	public void setModified(boolean modified) {
		isModified = modified;
	}

	public boolean isUnmodified() {
		return isUnmodified;
	}

	public void setUnmodified(boolean unmodified) {
		isUnmodified = unmodified;
	}

	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean added) {
		isAdded = added;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean deleted) {
		isDeleted = deleted;
	}

	public Object getOriginal() {
		return original;
	}

	public void setOriginal(Object original) {
		this.original = original;
	}
}
