package cn.edu.sziit.service.impl;

import cn.edu.sziit.bean.ItemCat;
import cn.edu.sziit.mapper.ItemCatMapper;
import cn.edu.sziit.service.ItemCatService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     *
     * @param parentId  商品的分类id
     * @return list
     */
    @Override
    public List<ItemCat> queryItemCatByParentId(Long parentId) {
        ItemCat cat = new ItemCat();
        cat.setParentId(parentId);
        return itemCatMapper.select(cat);
    }
}
