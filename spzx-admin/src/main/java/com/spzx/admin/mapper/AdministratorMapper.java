package com.spzx.admin.mapper;

import com.spzx.model.dto.system.AdministratorDto;
import com.spzx.model.entity.admin.Administrator;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ljl
 * @create 2023-10-22-16:37
 */
@Mapper
public interface AdministratorMapper {

    /**
     * 根据用户名查询用户数据
     * @param userName
     * @return
     */
    Administrator queryUserByName(String userName) ;

    /**
     * @Description: 用户条件分页查询接口
     * @param administratorDto
     */
    List<Administrator> findByPage(AdministratorDto administratorDto);

    void saveSysUser(Administrator administrator);

    void updateSysUser(Administrator administrator);

    void deleteById(Long userId);
}