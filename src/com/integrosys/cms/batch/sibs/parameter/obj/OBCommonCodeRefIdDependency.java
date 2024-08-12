package com.integrosys.cms.batch.sibs.parameter.obj;

import java.io.Serializable;

import com.integrosys.cms.app.common.constant.ICMSConstant;
import com.integrosys.cms.batch.sibs.parameter.IParameterProperty;

/**
 * @author Cynthia
 * @since Oct 7, 2008
 */
public class OBCommonCodeRefIdDependency implements IDependency, Serializable {

	private static final long serialVersionUID = -2362046574649354337L;

	private static final String[] MATCHING_PROPERTIES = new String[] { "entryCode" };

	private static final String[] IGNORED_PROPERTIES = new String[] { "entryId", "entryCode" };
                                                             
	private long entryId = ICMSConstant.LONG_INVALID_VALUE;

	private String entryCode;

	private String refEntryCode;

    private String activeStatus;

    public long getEntryId() {
		return entryId;
	}

	public void setEntryId(long entryId) {
		this.entryId = entryId;
	}

	public String getEntryCode() {
		return entryCode;
	}

	public void setEntryCode(String entryCode) {
		this.entryCode = entryCode;
	}

	public String getRefEntryCode() {
		return refEntryCode;
	}

	public void setRefEntryCode(String refEntryCode) {
		this.refEntryCode = refEntryCode;
	}

    public String getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(String activeStatus) {
        this.activeStatus = activeStatus;
    }

    public void updatePropertiesForCreateUpdate(IParameterProperty paramProperty) {
        setActiveStatus("1");  // default active status to "1" (true), if update record match
        trimStringAttributes();
    }

	/****** Methods from IDependency ******/
	public String[] getMatchingProperties() {
		return MATCHING_PROPERTIES;
	}

    public void updatePropertiesForDelete(IParameterProperty paramProperty) {
        trimStringAttributes();
    }

	public String[] getIgnoreProperties() {
		return IGNORED_PROPERTIES;
	}

     private void trimStringAttributes() {
        if(getEntryCode() != null){
            setEntryCode(getEntryCode().trim());
        }
        if(getRefEntryCode() != null){
            setRefEntryCode(getRefEntryCode().trim());
        }
    }

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(hashCode()).append("; ");
		buf.append("entry code").append("[").append(entryCode).append("]; ");
		buf.append("ref entry code").append("[").append(refEntryCode).append("]");

		return buf.toString();
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + ((entryCode == null) ? 0 : entryCode.hashCode());
		result = prime * result + ((refEntryCode == null) ? 0 : refEntryCode.hashCode());

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		OBCommonCodeRefIdDependency other = (OBCommonCodeRefIdDependency) obj;
		if (entryCode == null) {
			if (other.entryCode != null) {
				return false;
			}
		}
		else if (!entryCode.equals(other.entryCode)) {
			return false;
		}

		if (refEntryCode == null) {
			if (other.refEntryCode != null) {
				return false;
			}
		}
		else if (!refEntryCode.equals(other.refEntryCode)) {
			return false;
		}

		return true;
	}

}
