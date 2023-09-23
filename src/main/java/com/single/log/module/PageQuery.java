package com.single.log.module;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 分页查询基类型
 *
 * @author single
 * @date 2022/6/10
 */
//@Schema(name = "PageQuery", description = "分页参数")
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    //    @Schema(name = "pageSize", description = "每页数量")
//    @NotNull(message = "每页数量不能为空")
//    @Max(value = 10000, message = "每页最大为10000")
    private Integer pageSize;

    //    @Schema(name = "pageNo", description = "第几页")
//    @NotNull(message = "分页参数不能为空")
    private Integer pageNo;

    /**
     * 排序字段信息
     */
    @Getter
    @Setter
    protected List<OrderItem> orders = new ArrayList<>();

    /*** 默认每个页面条数*/
    static final Integer DEFAULT_PAGE_SIZE = 10;
    /*** 默认页面*/
    static final Integer DEFAULT_PAGE_NO = 1;

    public Integer getPageSize() {
        if (Objects.isNull(pageSize) || pageSize < 1) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        if (Objects.isNull(pageNo) || pageNo < 1) {
            return DEFAULT_PAGE_NO;
        }
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * 获取分页位置
     *
     * @return 位置
     */
    public Integer getOffset() {
        if (Objects.isNull(this.pageNo) || Objects.isNull(this.pageSize)) {
            return 0;
        }
        return this.pageNo == 0 ? 0 : (this.pageNo - 1) * this.pageSize;
    }

    @Override
    public String toString() {
        return "PageQuery{" + "pageSize=" + pageSize + ", pageNo=" + pageNo + '}';
    }
}
