package com.spzx.model.vo.webapp;

import com.spzx.model.entity.common.Category;
import com.spzx.model.entity.common.ProductSku;
import lombok.Data;

import java.util.List;

@Data
public class IndexVo {

    private List<Category> categoryList ;       // 一级分类的类别数据
    private List<ProductSku> productSkuList ;   // 畅销商品列表数据

}