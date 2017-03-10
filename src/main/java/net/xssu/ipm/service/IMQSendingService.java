package net.xssu.ipm.service;

import net.xssu.ipm.entity.ScanTask;

public interface IMQSendingService {
    public boolean sendTask(ScanTask msg);
}
