/**
 *
 * Copyright Integro Technologies Pte Ltd
 * $Header$
 *
 * PreDealConstants
 *
 * Created on 3:23:18 PM
 *
 * Purpose:
 * Description:
 *
 * @author $Author$<br>
 * @version $Revision$
 * @since $Date$
 * Tag: $Name$
 */
package com.integrosys.cms.app.predeal;

import com.integrosys.cms.app.common.constant.ICMSConstant;

/**
 * Created by IntelliJ IDEA. User: Eric Date: Mar 26, 2007 Time: 3:23:18 PM
 */
public final class PreDealConstants {

	private PreDealConstants() {

	}

	public static final String UPDATE_TYPE_RELEASE = ICMSConstant.ACTION_MAKER_RELEASE_EAR_MARK;

	public static final String UPDATE_TYPE_DELETE = ICMSConstant.ACTION_MAKER_DELETE_EAR_MARK;

	public static final String UPDATE_TYPE_TRANSFER = ICMSConstant.ACTION_MAKER_TRANSFER_EAR_MARK;

	public static final String EARMARK_STATUS_EARMARKED = "Earmarked";

	public static final String EARMARK_STATUS_HOLDING = "Holding";

	public static final String EARMARK_STATUS_RELEASED = "Released";

	public static final String EARMARK_STATUS_DELETED = "Deleted";

	public static final String SOURCE_SYSTEM_WAA = "804";

	public static final String SOURCE_SYSTEM_MEAA = "403";

	public static final String SOURCE_SYSTEM_WOLOC = "111";

	public static final String SOURCE_SYSTEM_MARSHA = "992";

	public static final String SOURCE_SYSTEM_NOMINEES = "900";

}
