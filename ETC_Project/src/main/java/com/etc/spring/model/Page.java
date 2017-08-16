package com.etc.spring.model;

import com.etc.spring.util.PageUtil;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;

/**
 * Created by none.none on 2017/2/13.
 */
public class Page implements Serializable {
    private static final long serialVersionUID = 1L;
    //前一页
    private Boolean hasPrePage;
    //后一页
    private Boolean hasNextPage;
    //每页显示多少条:默认10条
    private Long everyPage = 10L;
    //总页数
    private Long totalPage;
    //当前第多少页:默认第1页
    private Long currentPage = 1L;
    //开始下标
    private Long beginIndex=0L;
    //结束下标
    private Long endIndex=0L;
    //总共多少条
    private Long totalCount;

    private String defaultInfo = "  ";

    public String getDefaultInfo() {
        return defaultInfo;
    }

    public void setDefaultInfo(String defaultInfo) {
        this.defaultInfo = defaultInfo;
    }


    public Boolean getHasPrePage() {
        this.hasPrePage = currentPage != 1;
        return hasPrePage;
    }

    public void setHasPrePage(Boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public Boolean getHasNextPage() {
        this.hasNextPage = (currentPage != totalPage) && (totalPage != 0);
        return hasNextPage;
    }

    public void setHasNextPage(Boolean hasNextPage)
    {
        this.hasNextPage = hasNextPage;
    }

    public Long getEveryPage() {
        this.everyPage = everyPage == 0 ? 10 : everyPage;
        return everyPage;
    }

    public void setEveryPage(Long everyPage) {
        this.everyPage = everyPage;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        if (this.currentPage > totalPage) {
            this.currentPage = totalPage;
        }

        this.totalPage = totalPage;
    }

    public Long getCurrentPage() {
        this.currentPage = currentPage == 0 ? 1 : currentPage;
        return currentPage;
    }

    public void setCurrentPage(Long currentPage) {
        if (0 == currentPage) {
            currentPage = 1L;
        }

        this.currentPage = currentPage;
    }

    public Long getBeginIndex() {
        this.beginIndex = (currentPage - 1) * everyPage;
        return beginIndex;
    }

    public void setBeginIndex(Long beginIndex) {
        this.beginIndex = beginIndex;
    }

    public Long getEndIndex() {
        this.endIndex = (currentPage) * everyPage;
        return endIndex;
    }

    public void setEndIndex(Long endIndex) {
        this.endIndex = endIndex;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }


    public Page() {

    }

    /**
     * 用于计算分页
     *
     * @param totalCount
     */
    public Page(Long totalCount) {
        this.totalCount = totalCount;
        setTotalPage(getTotalPage(totalCount));
    }

    /**
     * 设置每页显示多少条时使用
     *
     * @param everyPage
     * @param totalCount
     */
    public Page(Long everyPage, Long totalCount) {
        this.totalCount = totalCount;
        this.everyPage = everyPage;
        setTotalPage(getTotalPage(totalCount));
    }

    /**
     * @param index 状态码
     * @param value
     */
    public void pageState(int index, String value) {

        switch (index) {
            case 0:
                setEveryPage(Long.parseLong(value));
                break;
            case 1:
                first();
                break;
            case 2:
                previous();
                break;
            case 3:
                next();
                break;
            case 4:
                last();
                break;
            case 5://到指定第多少页
                setCurrentPage(Long.parseLong(value));
                break;
        }
    }

    private void first() {
        currentPage = 1L;
    }

    private void previous() {
        currentPage--;
    }

    private void next() {
        currentPage++;
    }

    private void last() {
        currentPage = totalPage;
    }



    /**
     * 计算总页数
     *
     * @param totalCount
     * @return totalPage
     */
    private Long getTotalPage(Long totalCount) {
        Long totalPage = 0L;
        everyPage = everyPage == null ? 10L : everyPage;
        if (totalCount % everyPage == 0)
            totalPage = totalCount / everyPage;
        else {
            totalPage = totalCount / everyPage + 1;
        }
        return totalPage;
    }

}
