package com.etc.spring.util;

import com.etc.spring.model.Page;
import com.etc.spring.service.PageState;
import com.etc.spring.util.PageUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by none.none on 2017/2/13.
 */
public class BaseController {
    public static  Page executePage(HttpServletRequest request, Long totalCount) {
        if (null == totalCount) {
            totalCount = 0L;
        }
        /**
         * 页面状态,这个状态是分页自带的,与业务无关
         */
        String pageAction = request.getParameter("pageAction");
        String value = request.getParameter("pageKey");
        /**
         * 获取下标判断分页状态，
         */
        int index = PageState.getOrdinal(pageAction);
        Page page = null;
        /**
         　* index < 1 只有二种状态 1 当首次调用时,分页状态类中没有值为 NULL 返回 -1 2 当页面设置每页显示多少条:
         * index=0,当每页显示多少条时,分页类要重新计算*
         */
        Page sessionPage = getPage(request);

        if (index < 1) {
            page = PageUtil.initPage(totalCount, index, value, sessionPage);
        } else {
            page = PageUtil.execPage(index, value, sessionPage);
        }
        setSession(request, page);
        return page;
    }

    private static Page getPage(HttpServletRequest request) {
        Page page = (Page) request.getSession().getAttribute(
                PageUtil.SESSION_PAGE_KEY);
        if (page == null) {
            page = new Page();
        }
        return page;
    }

    private static void setSession(HttpServletRequest request, Page page) {
        request.getSession().setAttribute(PageUtil.SESSION_PAGE_KEY, page);
    }
}


