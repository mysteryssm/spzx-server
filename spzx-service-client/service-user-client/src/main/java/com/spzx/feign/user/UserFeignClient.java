package com.spzx.feign.user;

import com.spzx.model.entity.webapp.UserAddress;
import com.spzx.model.entity.webapp.UserBrowseHistory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-user")
public interface UserFeignClient {

    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public abstract UserAddress getUserAddress(@PathVariable("id") Long id);

}