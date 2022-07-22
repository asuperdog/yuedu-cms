package cn.edu.sziit.bean;

import java.io.Serializable;
import java.util.List;

public class BookResult<T> implements Serializable {

    private long total;   // 总的记录数
    private List<T> rows; // 设置这一页的数据集合

    public BookResult() {
    }

    public BookResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "BookResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
