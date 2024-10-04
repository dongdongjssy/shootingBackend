package io.goose.framework.web.base;

import io.goose.common.base.BaseEntity;
import io.goose.framework.util.ShiroUtils;

public class ClubBaseController  extends BaseController {

	   protected Long getClubId() {
		    if(ShiroUtils.getClubId() != null && ShiroUtils.getClubId() != 0) {
		    	return ShiroUtils.getClubId();
		    }
		    else {
		    	return null;
		    }
	   }
	   
	   
	   @SuppressWarnings("unchecked")
		protected <T extends BaseEntity> T setClubId(T t) {
			Long ClubId = getClubId();
			if(t == null)  
				t = (T) new BaseEntity();
			if(t.getClubId() == null) 
				t.setClubId(ClubId);

			return t;
		}
}
