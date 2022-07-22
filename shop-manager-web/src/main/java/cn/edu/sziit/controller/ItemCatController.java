package cn.edu.sziit.controller;

import cn.edu.sziit.bean.ItemCat;
import cn.edu.sziit.service.ItemCatService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
public class ItemCatController {
    @Reference
    private ItemCatService itemCatService;

    // 根据父亲的id 去查找属于它的子级分类
    @RequestMapping("/rest/item/cat")
    public List<ItemCat> queryItemCatByParentId(@RequestParam(value = "id", defaultValue = "0") Long parentId) {
        return itemCatService.queryItemCatByParentId(parentId);
    }
}
