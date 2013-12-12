package com.communityhelper.category.api;

import com.communityhelper.api.APIRequest;

public class CategoryListRequest extends APIRequest{
    private Integer start = 1;
    private Integer size = 10;
    public Integer getStart() {
        return start;
    }
    public void setStart(Integer start) {
        this.start = start;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
}
