package com.sefryek.doublepizza.device.handlers;

import com.sefryek.doublepizza.device.ServicesHandler;
import com.sefryek.doublepizza.device.ServicesConstant;
import com.sefryek.doublepizza.InMemoryData;
import com.sefryek.doublepizza.model.Store;
import com.sefryek.doublepizza.model.WorkingHour;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: vahid.s
 * Date: Mar 12, 2012
 */
public class BranchesHandler extends ServicesHandler {

    public String handleRequest(HttpServletRequest request) {
        StringBuilder response = new StringBuilder();
        String version = request.getParameter( ServicesConstant.PARAMETER_VERSION );

        /*
        The format of response:
        OK#[id;name;city;postal code;address1;address2;image url;[opening hours@closing hours@days of week]#]
         */
        response.append(ServicesConstant.SUCCESS).append(ServicesConstant.RECORD_DELIMITER);

        List<Store> storeList = InMemoryData.getAllStores();
        for (Store store : storeList) {

            response.append(store.getStoreId()).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getName()).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getCity()).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getLatitude()).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getLongitude()).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getAddress1() != null ? store.getAddress1() : ServicesConstant.NULL_VALUE).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getAddress2() != null ? store.getAddress2() : ServicesConstant.NULL_VALUE).append(ServicesConstant.FIELD_DELIMETER)
                    .append(store.getImageUrl() != null ? store.getImageUrl() : ServicesConstant.NULL_VALUE).append(ServicesConstant.FIELD_DELIMETER);

            List<WorkingHour> workingHours = InMemoryData.getWorkingHoursGroups(store.getStoreId(), request.getLocale());

            for (WorkingHour workingHour : workingHours) {
                response.append( workingHour.getOpeningHour() ).append(ServicesConstant.MULTI_VALUES_DELIMITER )
                        .append( workingHour.getClosingHour() ).append(ServicesConstant.MULTI_VALUES_DELIMITER )
                        .append( workingHour.getDayofWeekGroup() ).append( ServicesConstant.FIELD_DELIMETER );
            }

            response.append(ServicesConstant.RECORD_DELIMITER);

        }
        return response.toString();
    }
}
