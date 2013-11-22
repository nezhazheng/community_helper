package com.communityhelper;


import org.dbunit.dataset.IDataSet;
import org.springframework.core.io.Resource;

import com.github.springtestdbunit.dataset.AbstractDataSetLoader;

public class YamlDataSetLoader extends AbstractDataSetLoader{
    @Override
    protected IDataSet createDataSet(Resource resource) throws Exception {
        YamlDataSet dataSet = new  YamlDataSet(resource.getFile());
        return dataSet;
    }
}
