package com.integrosys.cms.app.component.bus;

import com.integrosys.base.techinfra.util.ReplicateUtils;
import com.integrosys.cms.app.holiday.bus.IHoliday;

public abstract class ComponentReplicationUtils {

	public static IComponent replicateComponentForCreateStagingCopy(IComponent component) {

        IComponent replicatedIdx = (IComponent) ReplicateUtils.replicateObject(component,
				new String[] { "id"});

        return replicatedIdx;
	}
}
