package io.goose.framework.web.base;

import java.util.Enumeration;

import io.goose.common.base.BaseEntity;
import io.goose.common.constant.Constants;

public class ClubRestBaseController extends BaseController {


    protected Long getClubId() {
        Long clubId = getClubIdFromHeader();
        if (clubId != null && clubId != 0) {
            return clubId;
        } else {
            return -1l;
        }
    }

    //header can be faked by client so the wrong cityId may be set by purpose or incorrectly
    //should validate it in authorization and retrieve it from token
    private Long getClubIdFromHeader() {

        Long clubId = null;
        Enumeration<?> headerNames = request.getHeaderNames();

        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();

            if (key != null && Constants.CLUB_ID.equalsIgnoreCase(key)) {
                clubId = request.getHeader(key) == null ? null : Long.valueOf(request.getHeader(key));
            } else {
                //consider retrieving cityId from token if possible??
            }

        }
        if (clubId == null) {

        }

        return clubId;

    }


    @SuppressWarnings("unchecked")
    protected <T extends BaseEntity> T setClubId(T t) {
        Long clubId = getClubId();
        if (t == null)
            t = (T) new BaseEntity();
        if (t.getClubId() == null) {
            if (clubId >= 0)
                t.setClubId(clubId);
        }

        return t;
    }

}
