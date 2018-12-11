package com.lhiot.ims.rbac.aspect;

import com.lhiot.ims.rbac.domain.ImsOperationLog;
import com.lhiot.ims.rbac.mapper.ImsOperationLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author xiaojian  created in  2018/12/6 8:55
 */
@Slf4j
@Component
public class LogCollectionListener {
    private ImsOperationLogMapper imsOperationLogMapper;

    public LogCollectionListener(ImsOperationLogMapper imsOperationLogMapper) {
        this.imsOperationLogMapper = imsOperationLogMapper;
    }

    @Async
    @EventListener
    public void onApplicationEvent(ImsOperationLog imsOperationLog) {
        log.info("Args: {}", imsOperationLog);

        imsOperationLogMapper.create(imsOperationLog);
    }
}
