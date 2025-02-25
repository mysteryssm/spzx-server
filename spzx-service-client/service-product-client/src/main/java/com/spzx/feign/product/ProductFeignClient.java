package com.spzx.feign.product;

import com.spzx.model.dto.webapp.SkuSaleDto;
import com.spzx.model.entity.common.ProductSku;
import com.spzx.model.vo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author ljl
 * @create 2023-11-04-13:59
 */
@FeignClient(value = "service-product") // value 指定远程调用的服务名称
public interface ProductFeignClient {

    /**
     * @Description: 远程调用：根据商品skuId获取商品sku信息
     * @param skuId
     */
    @GetMapping("/api/product/getBySkuId/{skuId}")
    Result<ProductSku> getBySkuId(@PathVariable(value = "skuId") Long skuId);

    /**
     * 更新商品sku销量
     * @param skuSaleDtoList
     * @return
     */
    @PostMapping("/api/product/updateSkuSaleNum")
    Boolean updateSkuSaleNum(@RequestBody List<SkuSaleDto> skuSaleDtoList);

}
