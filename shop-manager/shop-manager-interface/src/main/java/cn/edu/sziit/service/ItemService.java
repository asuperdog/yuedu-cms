package cn.edu.sziit.service;

import cn.edu.sziit.bean.BookResult;
import cn.edu.sziit.bean.Item;

public interface ItemService {
    /**
     * 分页显示商品
     *
     * @param page 查询显示第几页
     * @param size 每页显示多少条
     * @return 返回当前这一页的数据
     */
    BookResult<Item> findByPage(int page, int size);

    /**
     * 商品详情
     *
     * @param itemId 商品的ID
     */
    Item getDetail(Long itemId);

    /**
     * 新增商品
     *
     * @param item 商品的信息
     * @param desc 商品的描述
     */
    void addItem(Item item, String desc);

    /**
     * 编辑商品
     *
     * @param item   商品的信息
     * @param itemId 商品的ID
     */
    void editItem(Item item, Long itemId);

    /**
     * 删除商品
     *
     * @param itemId 商品的ID
     */
    void delItem(String itemId);

    /**
     * 上架商品
     *
     * @param item   商品的信息
     * @param itemId 商品的ID
     */
    void onShelf(Item item, String itemId);

    /**
     * 下架商品
     *
     * @param item   商品的信息
     * @param itemId 商品的ID
     */
    void offShelf(Item item, String itemId);

}
