package com.spzx.model.dto.webapp;

import com.spzx.model.entity.webapp.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderDto {

    private Long userAddressId;

    private BigDecimal freightFee;

    private String remark;

    private List<OrderItem> orderItemList;
}