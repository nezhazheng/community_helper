package com.communityhelper.page;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PageService {
    public <T> Page<T> createPage(List<T> result, Integer totalResult, Integer start, Integer size){
        Page<T> page = new Page<T>(start, size);
        page.setList(result);
        page.setTotalResult(totalResult);
        return page;
    }
}
