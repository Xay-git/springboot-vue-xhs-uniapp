package com.dd.admin;

import com.dd.admin.business.operationLog.entity.OperationLog;
import com.dd.admin.business.operationLog.service.OperationLogService;
import com.dd.admin.common.utils.AddressUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AdminApplicationTests {
    @Autowired
    OperationLogService operationLogService;

    @Test
    void contextLoads() {
        List<OperationLog> list = operationLogService.list();
        list.stream().forEach(operationLog -> {
            String realAddress = AddressUtils.getRealAddress(operationLog.getOperIp());
            System.out.println(realAddress);
            operationLog.setOperIpAddress(realAddress);
        });
        operationLogService.updateBatchById(list);
    }

}
