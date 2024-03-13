package com.spzx.common.log.service;

import com.spzx.model.entity.admin.LogEntity;
import org.springframework.stereotype.Service;

@Service
public interface LogService {			// 保存日志数据
    void saveLog(LogEntity logEntity);
}

