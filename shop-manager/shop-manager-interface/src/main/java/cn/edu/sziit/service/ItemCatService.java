package cn.edu.sziit.service;

import cn.edu.sziit.bean.ItemCat;

import java.util.List;

public interface ItemCatService {
    /**
     * 查询商品的分类，根据商品的分类id来找它的子级分类
     *
     * @param parentId  商品的分类id
     * @return  返回该商品的子级分类
     */
    List<ItemCat> queryItemCatByParentId(Long parentId);
}
