package com.harry.data;

import java.io.Serializable;

public class Page implements Serializable {


    private int everyPage;    // 每一页有多少条记录

    private int totalCount;   // 总记录数(totalCount)

    private int totalPage;       //  总页数(totalPage)

    private int currentPage;     // 当前页(currentPage)

    private int beginIndex;      // 当前返回数据的起始点(beginIndex)

    private boolean hasPrePage;    // 是否有上一页(hasPrePage)

    private boolean hasNextPage;   // 是否有下一页(hasNextPage)


    public Page () {


    }


    public Page (int everyPage, int totalCount, int totalPage, int currentPage, int beginIndex, boolean hasPrePage, boolean hasNextPage) {
        this.everyPage = everyPage;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.beginIndex = beginIndex;
        this.hasPrePage = hasPrePage;
        this.hasNextPage = hasNextPage;
    }



    public int getEveryPage() {
        return everyPage;
    }

    public void setEveryPage(int everyPage) {
        this.everyPage = everyPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getBeginIndex() {
        return beginIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
    }

    public boolean isHasPrePage() {
        return hasPrePage;
    }

    public void setHasPrePage(boolean hasPrePage) {
        this.hasPrePage = hasPrePage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }


}
