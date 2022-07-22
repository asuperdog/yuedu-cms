package cn.edu.sziit.controller;

import cn.edu.sziit.bean.BookResult;
import cn.edu.sziit.bean.Item;
import cn.edu.sziit.service.ItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin
@RestController
public class ItemController {
    @Reference
    private ItemService itemService;

    // localhost:8082/page?pageNum=1&pageSize=20;
    // 分页显示商品
    @RequestMapping("/rest/item")
    public BookResult<Item> findByPage(int page, int rows) {
        return itemService.findByPage(page, rows);
    }

    // 商品详情
    @RequestMapping("/rest/detail")
    public Item getDetail(@RequestParam("id") Long itemId) {
        return itemService.getDetail(itemId);
    }

    // 新增商品
    @RequestMapping("/rest/addItem")
    public Map<String, Object> saveItem(Item item, String desc) {
        itemService.addItem(item, desc);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        return map;
    }

    // 编辑商品
    @RequestMapping("/rest/editItem")
    public Map<String, Object> editItem(Item item, @RequestParam("id") Long itemId) {
        itemService.editItem(item, itemId);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        return map;
    }

    // 删除商品
    @RequestMapping("/rest/delItem")
    public Map<String, Object> delItem(@RequestParam("ids") String itemId) {
        itemService.delItem(itemId);
        // 返回状态码，实现操作商品后前端弹窗提示
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        return map;
    }

    // 上架商品
    @RequestMapping("/rest/onShelf")
    public Map<String, Object> onShelf(Item item, @RequestParam("ids") String itemId) {
        itemService.onShelf(item, itemId);
        // 返回状态码，实现操作商品后前端弹窗提示
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        return map;
    }

    // 下架商品
    @RequestMapping("/rest/offShelf")
    public Map<String, Object> offShelf(Item item, @RequestParam("ids") String itemId) {
        itemService.offShelf(item, itemId);
        // 返回状态码，实现操作商品后前端弹窗提示
        Map<String, Object> map = new HashMap<>();
        map.put("status", "200");
        return map;
    }
}
