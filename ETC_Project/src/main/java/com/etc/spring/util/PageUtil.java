package com.etc.spring.util;

import com.etc.spring.model.Page;

/**
 * 分页工具类
 * Created by none.none on 2017/2/13.
 */
public class PageUtil {
    public static final String ASC = "asc";
    public static final String DESC = "desc";
    public static final String PAGE_DESC = "↓";
    public static final String PAGE_ASC = "↑";
    public static final String PAGE_NULL = "  ";
    public static final String SESSION_PAGE_KEY = "page";

    /**
     * @param totalCount  总行数
     * @param index       分页状态
     * @param value       设置每页显示多少条
     * @param sessionPage
     * @return
     */
    public static Page initPage(Long totalCount, Integer index, String value, Page sessionPage) {
        Page page = null;
        if (index < 0) {
            page = new Page(totalCount);
        } else {
            /**每页显示多少条*/
            Long everPage = null == value ? 10 : Long.parseLong(value);
            /**获取Session中的分页类,方便保存页面分页状态*/
            page = sessionPage;
            page.setEveryPage(everPage);
            page.setTotalCount(totalCount);
        }
        return page;

    }

    /**
     * 当页点击：首页,前一页,后一页,末页,排序,到第多少页时进行分页操作
     * @param index 分页状态
     * @param value 排序字段名
     * @param sessionPage
     * @return
     */
    public static Page execPage(int  index,String value,Page sessionPage){
          Page page = sessionPage;
             /**调用方法进行分页计算*/
           page.pageState(index,value);
             return page;
    }

}
