package cn.edu.sziit.service.impl;

import cn.edu.sziit.bean.Item;
import cn.edu.sziit.mapper.ItemMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ReceiveMessage {
    @Autowired
    private ItemMapper itemMapper;

    // 使用Solr操作索引需要用到此对象
    @Autowired
    private SolrClient solrClient;

    // 新增、上架商品：监听item的信息
    // 接收消息需要使用到这个注解(topics={"所需要监听Topic的名称"})
    @JmsListener(destination = "addItem")
    public void receiveAddItemMessage(String itemId) {
        // 1. 查询数据库，根据商品id获取
        Item item = itemMapper.selectByPrimaryKey(Long.parseLong(itemId));
        System.out.println("addSearch: " + item);

        // 2. 构建索引，保存到索引库中
        SolrInputDocument document = new SolrInputDocument();
        // 商品id
        document.setField("id", item.getId().toString());
        // 商品标题
        document.setField("item_title", item.getTitle());
        // 商品价格
        document.setField("item_price", item.getPrice());
        // 商品图片
        document.setField("item_image", item.getImage());
        // 商品类目id
        document.setField("item_cid", item.getCid());
        // 商品状态
        document.setField("item_status", item.getStatus());

        try {
            // 3. 提交到Solr索引库
            solrClient.add(document);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 删除、下架商品：监听item的信息
    // 接收消息需要使用到这个注解(topics={"所需要监听Topic的名称"})
    @JmsListener(destination = "deleteItem")
    public void receiveDeleteItemMessage(String itemId) {
        // 1. 查询数据库，根据商品id获取
        Item item = itemMapper.selectByPrimaryKey(itemId);
        System.out.println("deleteSearch: " + item);

        try {
            // 2. 根据商品id删除索引
            solrClient.deleteById(itemId);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
