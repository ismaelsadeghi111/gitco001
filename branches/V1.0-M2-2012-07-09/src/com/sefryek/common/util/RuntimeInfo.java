package com.sefryek.common.util;

import java.util.List;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

/**
 * Created by IntelliJ IDEA.
 * User: hassan.abdi
 * Date: May 12, 2012
 * Time: 6:45:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class RuntimeInfo {
    public static boolean isDeveleopMode(){
        RuntimeMXBean RuntimemxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = RuntimemxBean.getInputArguments();
        for (String param : arguments){
            if (param.equalsIgnoreCase("-drun_mode=dev"))
                return true;
        }
        return false;
    }
}
