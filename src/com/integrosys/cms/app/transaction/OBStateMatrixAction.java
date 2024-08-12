package com.integrosys.cms.app.transaction;

import java.io.Serializable;

/**
 * Entity represent a entry in state matrix action table.
 * @author Chong Jun Yong
 * 
 */
public final class OBStateMatrixAction implements Serializable {

	private static final long serialVersionUID = 7712147033218957700L;

	private int stateId;

	private int teamMembershipTypeId;

	private String userAction;

	private String url;

	private String toTrackUrl;

	/**
	 * The only constructor to provide all columns of a entry
	 * @param stateId the state matrix entry id
	 * @param teamMembershipTypeId team membership type id
	 * @param userAction user action shown on the screen
	 * @param url the url direct user to
	 * @param toTrackUrl to track url direct user to
	 */
	public OBStateMatrixAction(int stateId, int teamMembershipTypeId, String userAction, String url, String toTrackUrl) {
		this.stateId = stateId;
		this.teamMembershipTypeId = teamMembershipTypeId;
		this.userAction = userAction;
		this.url = url;
		this.toTrackUrl = toTrackUrl;
	}

	/**
	 * @return the state id for the state matrix entry (ie, from state, to
	 *         state)
	 */
	public int getStateId() {
		return stateId;
	}

	/**
	 * @return the team membership type id, (ie, id of COU Maker, COU Checker,
	 *         SA Maker, SA Checker)
	 */
	public int getTeamMembershipTypeId() {
		return teamMembershipTypeId;
	}

	/**
	 * @return the user action, shown on the screen (eg, Process, Close)
	 */
	public String getUserAction() {
		return userAction;
	}

	/**
	 * @return the url that will direct user to upon click the user action
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return to track url, that will direct user to upon click the 'View'
	 *         action
	 */
	public String getToTrackUrl() {
		return toTrackUrl;
	}

}
