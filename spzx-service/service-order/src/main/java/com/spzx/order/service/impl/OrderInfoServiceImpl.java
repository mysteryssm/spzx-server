package com.spzx.order.service.impl;

import com.spzx.common.service.exception.GlobalException;
import com.spzx.feign.cart.CartFeignClient;
import com.spzx.feign.product.ProductFeignClient;
import com.spzx.feign.user.UserFeignClient;
import com.spzx.model.dto.webapp.OrderDto;
import com.spzx.model.entity.webapp.CartInfo;
import com.spzx.model.entity.webapp.OrderInfo;
import com.spzx.model.entity.webapp.OrderItem;
import com.spzx.model.entity.webapp.OrderLog;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.entity.webapp.UserAddress;
import com.spzx.model.entity.webapp.User;
import com.spzx.model.globalConstant.PayStatusEnum;
import com.spzx.model.globalConstant.PayTypeEnum;
import com.spzx.model.globalConstant.ResultCodeEnum;
import com.spzx.model.vo.webapp.TradeVo;
import com.spzx.order.mapper.OrderInfoMapper;
import com.spzx.order.mapper.OrderItemMapper;
import com.spzx.order.mapper.OrderLogMapper;
import com.spzx.order.service.OrderInfoService;
import com.spzx.common.utils.AuthContextUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderInfoServiceImpl implements OrderInfoService {

    @Autowired
    private CartFeignClient cartFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private OrderInfoMapper orderInfoMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private OrderLogMapper orderLogMapper;

    @Override
    public TradeVo generateOrder() {

        List<CartInfo> cartInfoList = cartFeignClient.selectChecked();  // 远程调用获取购物车中选中的商品
        List<OrderItem> orderItemList = new ArrayList<>();

        for (CartInfo cartInfo : cartInfoList) {        // 将购物项数据转换成功订单明细数据
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        }

        BigDecimal totalAmount = new BigDecimal(0);
        BigDecimal amount = new BigDecimal(0);

        for(OrderItem orderItem : orderItemList) {
            amount = new BigDecimal(orderItem.getSkuNum());
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(amount));
        }
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;

    }

    @Transactional
    @Override
    public Long submitOrder(OrderDto orderDto) {
        List<OrderItem> orderItemList = orderDto.getOrderItemList();    // 获取订单中的商品列表

        if (CollectionUtils.isEmpty(orderItemList)) {   // 判断商品列表是否为空
            throw new GlobalException(ResultCodeEnum.ORDER_EMPTY_ERROR);
        }

        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.getBySkuId(orderItem.getSkuId()).getData();  // 远程调用获取商品信息

            if(null == productSku) { // 判断商品是否存在
                throw new GlobalException(ResultCodeEnum.PRODUCT_NOT_EXIST_ERROR);
            }

            // 校验商品库存量是否充足
            if(orderItem.getSkuNum().intValue() > productSku.getStockNum().intValue()) {
                throw new GlobalException(ResultCodeEnum.STOCK_LESS);
            }
        }

        // 远程调用service-user模块的用户收货地址信息
        UserAddress userAddress = userFeignClient.getUserAddress(orderDto.getUserAddressId());

        if(null == userAddress) {
            throw new GlobalException(ResultCodeEnum.USER_ADDRESS_NOT_EXIST_ERROR);
        }

        User user = AuthContextUtil.getUser();

        OrderInfo orderInfo = new OrderInfo(); // 定义 orderInfo 用于存储订单信息
        orderInfo.setUserId(user.getId());  //传入用户id
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));   // 获取当前时间戳作为订单编号
        orderInfo.setNickName(user.getNickName());  //传入用户昵称

        orderInfo.setReceiverName(userAddress.getName());   // 传入收货人姓名
        orderInfo.setReceiverPhone(userAddress.getPhone()); // 传入收货人手机号
        orderInfo.setReceiverTagName(userAddress.getTagName()); // 传入收货地址标签
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());   // 传入收获地址省级行政区区域码
        orderInfo.setReceiverCity(userAddress.getCityCode());   // 传入收获地址市级行政区区域码
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());   // 传入收获地址县级行政区区域码
        orderInfo.setReceiverAddress(userAddress.getFullAddress()); // 传入详细收获地址

        BigDecimal totalAmount = new BigDecimal(0);  //订单金额
        BigDecimal couponAmount = new BigDecimal(0);  //优惠券金额

        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        orderInfo.setTotalAmount(totalAmount.add(couponAmount.negate()));   // 传入订单总金额
        orderInfo.setCouponAmount(couponAmount);   // 暂时不考虑优惠券功能，传入 0 作为优惠券金额
        orderInfo.setOriginalTotalAmount(totalAmount);  // 传入初始订单金额，不考虑优惠券
        orderInfo.setFreightFee(orderDto.getFreightFee());   // 传入运费
        orderInfo.setPayType(PayTypeEnum.ALI_PAY.getPayTypeCode()); // 传入支付方式码
        orderInfo.setOrderStatus(PayStatusEnum.UNPAID.getPayStatusCode());    // 传入订单状态
        orderInfoMapper.insert(orderInfo);  // 将订单信息存入数据库

        // 添加List<OrderItem>里面的数据，把集合每个OrderItem添加表
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.insert(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        //远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();

        //8、返回订单id
        return orderInfo.getId();
    }

    @Override
    public OrderInfo selectByOrderId(Long orderId) {
        return orderInfoMapper.selectByOrderId(orderId);
    }

    @Override
    public TradeVo buy(Long skuId) {

        ProductSku productSku = productFeignClient.getBySkuId(skuId).getData();

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        BigDecimal totalAmount = productSku.getSalePrice();
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        return tradeVo;
    }

    @Override
    public PageInfo<OrderInfo> select(Integer page, Integer limit, Integer orderStatus) {
        PageHelper.startPage(page, limit);
        Long userId = AuthContextUtil.getUser().getId();
        List<OrderInfo> orderInfoList = orderInfoMapper.select(userId, orderStatus);

        orderInfoList.forEach(orderInfo -> {
            List<OrderItem> orderItem = orderItemMapper.selectByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItem);
        });

        return new PageInfo<>(orderInfoList);
    }

    @Override
    public OrderInfo getByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        List<OrderItem> orderItem = orderItemMapper.selectByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItem);
        return orderInfo;
    }

    @Transactional
    @Override
    public void updateOrderStatus(String orderNo, Integer orderStatus) {

        // 更新订单状态
        OrderInfo orderInfo = orderInfoMapper.getByOrderNo(orderNo);
        orderInfo.setOrderStatus(1);
        orderInfo.setPayType(orderStatus);
        orderInfo.setPaymentTime(new Date());
        orderInfoMapper.update(orderInfo);

        // 记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(1);
        orderLog.setNote("支付宝支付成功");
        orderLogMapper.save(orderLog);
    }
}

