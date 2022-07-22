package cn.edu.sziit.service;

import cn.edu.sziit.bean.BookResult;
import cn.edu.sziit.bean.Item;

public interface SearchService {
    /**
     * 搜索商品
     *
     * @param query 搜索的关键字
     * @param page  设置页数
     * @param rows  每页显示的记录数
     * @return      返回搜索结果记录
     */
    BookResult<Item> search(String query, int page, int rows);
}
