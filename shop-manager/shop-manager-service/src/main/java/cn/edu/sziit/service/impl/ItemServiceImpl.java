package cn.edu.sziit.service.impl;

import cn.edu.sziit.bean.BookResult;
import cn.edu.sziit.bean.Item;
import cn.edu.sziit.mapper.ItemMapper;
import cn.edu.sziit.service.ItemService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;

import java.util.Date;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    // 发送消息需要用到此对象
    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    /**
     * 分页显示商品
     *
     * @param page 查询显示第几页
     * @param size 每页显示多少条
     * @return result   返回商品
     */
    @Override
    public BookResult<Item> findByPage(int page, int size) {
        // 1.设置查询第几页，每页显示多少条
        PageHelper.startPage(page, size);
        // 2.开始查询，得到这一页的数据返回
        Page<Item> pageData = (Page<Item>) itemMapper.selectByExample(null);
        // 3.返回bookResult
        return new BookResult<>(pageData.getTotal(), pageData.getResult());
    }

    /**
     * 商品详情
     *
     * @param itemId 商品的ID
     * @return detail
     */
    @Override
    public Item getDetail(Long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    /**
     * 新增商品
     *
     * @param item 商品的信息
     * @param desc 商品的描述
     */
    @Override
    public void addItem(Item item, String desc) {
        // 保存商品，设置商品的状态，商品是上架还是下架，如果商品是上架(1)，下架(0)
        item.setStatus(1);
        // 设置商品的创建时间和更新时间
        item.setCreated(new Date());
        item.setUpdated(item.getCreated());
        itemMapper.insert(item);
        // 发送消息，通知搜索系统更新索引，只需告诉搜索系统商品的id即可
        jmsMessagingTemplate.convertAndSend("addItem", "" + item.getId());
    }

    /**
     * 编辑商品
     *
     * @param item   商品的信息
     * @param itemId 商品的ID
     */
    @Override
    public void editItem(Item item, Long itemId) {
        item.setId(itemId);
        item.setUpdated(new Date());
        itemMapper.updateByPrimaryKeySelective(item);
        // 发送消息，通知搜索系统更新索引，只需告诉搜索系统商品的id即可
        jmsMessagingTemplate.convertAndSend("addItem", "" + item.getId());
    }

    /**
     * 删除商品
     *
     * @param itemId 商品的ID
     */
    @Override
    public void delItem(String itemId) {
        String[] itemIds = itemId.split(",");
        for (String id : itemIds) {
            itemMapper.deleteByPrimaryKey(id);
            // 发送消息，通知搜索系统更新索引，只需告诉搜索系统商品的id即可
            jmsMessagingTemplate.convertAndSend("deleteItem", "" + id);
        }
    }

    /**
     * 上架商品
     *
     * @param item   商品的信息
     * @param itemId 商品的ID
     */
    @Override
    public void onShelf(Item item, String itemId) {
        String[] itemIds = itemId.split(",");
        for (String id : itemIds) {
            Long ID = Long.valueOf(id);
            item.setId(ID);
            item.setStatus(1);
            itemMapper.updateByPrimaryKeySelective(item);
            // 发送消息，通知搜索系统更新索引，只需告诉搜索系统商品的id即可
            jmsMessagingTemplate.convertAndSend("addItem", "" + id);
        }
    }

    /**
     * 下架商品
     *
     * @param item   商品的信息
     * @param itemId 商品的ID
     */
    @Override
    public void offShelf(Item item, String itemId) {
        String[] itemIds = itemId.split(",");
        for (String id : itemIds) {
            Long Id = Long.valueOf(id);
            item.setId(Id);
            item.setStatus(2);
            itemMapper.updateByPrimaryKeySelective(item);
            // 发送消息，通知搜索系统更新索引，只需告诉搜索系统商品的id即可
            jmsMessagingTemplate.convertAndSend("deleteItem", "" + id);
        }
    }
}
