package cn.edu.sziit.controller;

import cn.edu.sziit.bean.BookResult;
import cn.edu.sziit.bean.Item;
import cn.edu.sziit.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class SearchController {
    @Reference // 使用的是dubbo远程调用shop-search-service的服务
    private SearchService searchService;

    // 每页显示的记录数
    private Integer rows = 16;

    // 搜索商品
    @RequestMapping("search")
    public BookResult<Item> search(@RequestParam("q") String query, @RequestParam(value = "page", defaultValue = "1") Integer page) {
        //执行查询的操作，去索引库里面查询商品。
        return this.searchService.search(query, page, this.rows);
    }
}
