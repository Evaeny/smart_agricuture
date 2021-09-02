package com.smart.agriculture.manage.xxl.job.core.alarm;


import com.smart.agriculture.manage.xxl.job.core.model.XxlJobInfo;
import com.smart.agriculture.manage.xxl.job.core.model.XxlJobLog;

/**
 * @author xuxueli 2020-01-19
 */
public interface JobAlarm {

    /**
     * job alarm
     *
     * @param info
     * @param jobLog
     * @return
     */
    public boolean doAlarm(XxlJobInfo info, XxlJobLog jobLog);

}
