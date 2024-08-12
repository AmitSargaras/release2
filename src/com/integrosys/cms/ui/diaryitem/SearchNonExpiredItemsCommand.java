/**
 * Copyright Integro Technologies Pte Ltd
 * $Header: /home/cms2/cvsroot/cms2/src/com/integrosys/cms/ui/diaryitem/SearchNonExpiredItemsCommand.java,v 1.2 2005/10/14 09:18:23 lini Exp $
 */

package com.integrosys.cms.ui.diaryitem;

import java.util.Collection;
import java.util.HashMap;

import com.integrosys.base.businfra.search.SearchDAOException;
import com.integrosys.base.businfra.search.SearchResult;
import com.integrosys.base.techinfra.logger.DefaultLogger;
import com.integrosys.base.uiinfra.common.ICommonEventConstant;
import com.integrosys.base.uiinfra.exception.CommandProcessingException;
import com.integrosys.base.uiinfra.exception.CommandValidationException;
import com.integrosys.cms.app.diary.bus.DiaryItemException;
import com.integrosys.cms.ui.common.constant.IGlobalConstant;
import com.integrosys.component.bizstructure.app.bus.ITeam;

/**
 * This command decorates the ListNonExpiredItemsCommand with search behaviour
 * 
 * @author $Author: lini $<br>
 * @version $Revision: 1.2 $
 * @since $Date: 2005/10/14 09:18:23 $ Tag: $Name: $
 */

public class SearchNonExpiredItemsCommand extends AbstractListDiaryItemsCommand {

	private AbstractListDiaryItemsCommand cmd;

	public SearchNonExpiredItemsCommand() {
	}

	public SearchNonExpiredItemsCommand(AbstractListDiaryItemsCommand cmd) {
		this.cmd = cmd;
	}

	protected SearchResult performSearch(HashMap map) throws DiaryItemException, SearchDAOException {
		return cmd.performSearch(map);
	}

	public HashMap doExecute(HashMap map) throws CommandProcessingException, CommandValidationException {

		DefaultLogger.debug(this, "IN method SearchNonExpiredItemsCommand.doExecute !!! ");

		HashMap result = cmd.doExecute(map);

		ITeam team = (ITeam) map.get(IGlobalConstant.USER_TEAM);

		if (team == null) {
			throw new CommandProcessingException("Team information is null!");
		}

		HashMap reverseCountryMap = DiaryItemHelper.getAllowedCountries(team);

		Collection countryLabels = DiaryItemHelper.getSortedCountryLabels(reverseCountryMap);

		Collection countryValues = DiaryItemHelper.getSortedCountryValues(countryLabels, reverseCountryMap);

		((HashMap) result.get(ICommonEventConstant.COMMAND_RESULT_MAP)).put("CountryValues", countryValues);
		((HashMap) result.get(ICommonEventConstant.COMMAND_RESULT_MAP)).put("CountryLabels", countryLabels);

		HashMap rsMap = (HashMap) result.get(ICommonEventConstant.COMMAND_RESULT_MAP);

		return result;

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
