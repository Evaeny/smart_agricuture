package com.smart.agriculture.manage.core.route.strategy;

import com.smart.agriculture.manage.config.ExecutorRouter;
import com.smart.agriculture.manage.entity.model.ReturnT;
import com.smart.agriculture.manage.entity.model.TriggerParam;


import java.util.List;

/**
 * Created by xuxueli on 17/3/10.
 */
public class ExecutorRouteFirst extends ExecutorRouter {

    @Override
    public ReturnT<String> route(TriggerParam triggerParam, List<String> addressList) {
        return new ReturnT<String>(addressList.get(0));
    }

}
