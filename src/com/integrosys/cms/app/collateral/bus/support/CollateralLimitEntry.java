package com.integrosys.cms.app.collateral.bus.support;

/**
 * <p>
 * Used as the key (to represent the limit and collateral) inside a map, which
 * might used to retrieve status (value in the map), etc.
 * <p>
 * Override {@link Object#hashCode()} and {@link Object#equals(Object)} to
 * facilitate the usage in the Map or even in a unique collection such as
 * {@link java.util.Set}
 * @author Chong Jun Yong
 * 
 */
public final class CollateralLimitEntry {
	private long cmsCollateralId;

	private long cmsLimitId;

	/**
	 * <p>
	 * Default construct to provide cms collateral and limit key.
	 * <p>
	 * The only way to set the value into this object.
	 * 
	 * @param cmsCollateralId the cms collateral internal key
	 * @param cmsLimitId the cms limit internal key
	 */
	public CollateralLimitEntry(long cmsCollateralId, long cmsLimitId) {
		this.cmsCollateralId = cmsCollateralId;
		this.cmsLimitId = cmsLimitId;
	}

	public long getCmsCollateralId() {
		return cmsCollateralId;
	}

	public long getCmsLimitId() {
		return cmsLimitId;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;

		result = prime * result + (int) (cmsCollateralId ^ (cmsCollateralId >>> 32));
		result = prime * result + (int) (cmsLimitId ^ (cmsLimitId >>> 32));

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

		CollateralLimitEntry other = (CollateralLimitEntry) obj;
		if (cmsCollateralId != other.cmsCollateralId) {
			return false;
		}

		if (cmsLimitId != other.cmsLimitId) {
			return false;
		}

		return true;
	}

	public String toString() {
		StringBuffer buf = new StringBuffer(getClass().getName());
		buf.append("@").append(this.hashCode()).append(", ");
		buf.append("CMS Collateral Id: [").append(cmsCollateralId).append("], ");
		buf.append("CMS Limit Id: [").append(cmsLimitId).append("]");

		return buf.toString();
	}

}
