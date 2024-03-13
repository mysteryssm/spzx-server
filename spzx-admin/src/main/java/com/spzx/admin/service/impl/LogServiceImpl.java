package com.spzx.admin.service.impl;

import com.spzx.common.log.service.LogService;
import com.spzx.admin.mapper.LogMapper;
import com.spzx.model.entity.admin.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    //保存日志数据
    @Override
    public void saveLog(LogEntity logEntity) {
        logMapper.insert(logEntity);
    }
}
