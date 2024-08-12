/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/RefreshNonExpiredItemsCommand.java,v 1.1 2005/11/13 12:03:50 jtan Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;

/**
 * This command decorates the ListNonExpiredItemsCommand with refresh behaviour
 * Intent is to introduce validation during refresh
 * 
 * @author $Author: jtan $<br>
 * @version $Revision: 1.1 $
 * @since $Date: 2005/11/13 12:03:50 $ Tag: $Name: $
 */

public class RefreshNonExpiredItemsCommand extends AbstractListDiaryItemsCommand {

	private AbstractListDiaryItemsCommand cmd;
	
	public RefreshNonExpiredItemsCommand() {
	}

	public RefreshNonExpiredItemsCommand(AbstractListDiaryItemsCommand cmd) {
		this.cmd = cmd;
	}

	protected SearchResult performSearch(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.performSearch(map);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "IN method RefreshNonExpiredItemsCommand.doExecute !!! ");

		return cmd.doExecute(map);
	}

	@Override
	protected SearchResult getGenericListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getGenericListWithSegment(map,segmentName);
	}

	@Override
	protected SearchResult getGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.getGenericListWithoutSegment(map);
	}

	@Override
	protected SearchResult getDropLineListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getDropLineListWithSegment(map,segmentName);
	}

	@Override
	protected SearchResult getDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.getDropLineListWithoutSegment(map);
	}

	@Override
	protected SearchResult getTotalListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getTotalListWithSegment(map,segmentName);
	}
	
	@Override
	protected SearchResult getTodayGenericListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayGenericListWithSegment(map,segmentName);
	}

	@Override
	protected SearchResult getTodayGenericListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayGenericListWithoutSegment(map);
	}

	@Override
	protected SearchResult getTodayDropLineListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayDropLineListWithSegment(map,segmentName);
	}

	@Override
	protected SearchResult getTodayDropLineListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayDropLineListWithoutSegment(map);
	}

	@Override
	protected SearchResult getTodayTotalListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayTotalListWithSegment(map,segmentName);
	}
	
	@Override
	protected SearchResult getTodayTotalListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayTotalListWithoutSegment(map);
	}
	
	@Override
	protected SearchResult getTodayOverDueListWithSegment(HashMap map,String segmentName) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayOverDueListWithSegment(map,segmentName);
	}
	
	@Override
	protected SearchResult getTodayOverDueListWithoutSegment(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.getTodayOverDueListWithoutSegment(map);
	}

}
