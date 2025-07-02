package org.example.campuscartrade.pojo.VO;

import java.util.List;

/**
 * 分页结果封装类
 * @param <T> 数据列表中元素的类型，比如 Vehicle、User 等实体类
 */
public class PageResult<T> {
    // 总记录数
    private long total;
    // 当前页的数据列表
    private List<T> list;

    public PageResult(long total, List<T> list) {
        this.total = total;
        this.list = list;
    }

    // 获取总记录数
    public long getTotal() {
        return total;
    }

    // 设置总记录数
    public void setTotal(long total) {
        this.total = total;
    }

    // 获取数据列表
    public List<T> getList() {
        return list;
    }

    // 设置数据列表
    public void setList(List<T> list) {
        this.list = list;
    }
}