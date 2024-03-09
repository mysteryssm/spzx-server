package com.atguigu.spzx.common.log.service;

import com.atguigu.spzx.model.entity.log.LogEntity;
import org.springframework.stereotype.Service;

@Service
public interface LogService {			// 保存日志数据
    void saveLog(LogEntity logEntity);
}

