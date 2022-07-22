package cn.edu.sziit.controller;

import cn.edu.sziit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/addIndex")
    public String addIndex() {
        System.out.println("ItemController::addIndex~");
        itemService.addIndex();
        return "add success~!";
    }
}
