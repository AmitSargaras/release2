package com.integrosys.cms.app.common;

/**
 * A value object represent the stp trx status to be displayed and the stp ready
 * indicator. Mainly used to display both the status on the client side.
 * 
 * @author Chong Jun Yong
 * 
 */
public final class StpTrxStatusReadyIndicator {
	private String trxStatus;

	private String originalTrxStatus;

	private boolean stpReadyIndicator;

	public String getTrxStatus() {
		return trxStatus;
	}

	public String getOriginalTrxStatus() {
		return originalTrxStatus;
	}

	public boolean isStpReadyIndicator() {
		return stpReadyIndicator;
	}

	/**
	 * The status to be displayed on the screen, not the actual status stored in
	 * the persistent storage.
	 * 
	 * @param trxStatus the displayed transaction status to be set
	 */
	public void setTrxStatus(String trxStatus) {
		this.trxStatus = trxStatus;
	}

	/**
	 * The original transaction status stored in the persistent storage, to be
	 * used for further checking, such as based on the certain status what
	 * action should be taken.
	 * 
	 * @param originalTrxStatus the original transaction status that is stored
	 *        in the persistent storage
	 */
	public void setOriginalTrxStatus(String originalTrxStatus) {
		this.originalTrxStatus = originalTrxStatus;
	}

	public void setStpReadyIndicator(boolean stpReadyIndicator) {
		this.stpReadyIndicator = stpReadyIndicator;
	}

}
