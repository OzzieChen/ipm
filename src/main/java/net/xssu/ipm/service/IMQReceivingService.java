package net.xssu.ipm.service;

import net.xssu.ipm.entity.ScanResult;

/**
 * Created by ozziechen on 2017/3/10.
 */
public interface IMQReceivingService {
    public String organiseResult(int taskId);

    public void storeOneResult(ScanResult scanResult);
}
