package com.spzx.model.dto.webapp;

import com.spzx.model.entity.webapp.OrderItem;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


//dto封装提交过来的数据
@Data
public class OrderDto {

    //送货地址id
    private Long userAddressId;

    //运费
    private BigDecimal feightFee;

    //备注
    private String remark;

    //订单明细
    private List<OrderItem> orderItemList;
}