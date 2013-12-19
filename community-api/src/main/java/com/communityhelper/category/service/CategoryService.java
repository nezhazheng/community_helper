package com.communityhelper.category.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import com.communityhelper.api.Page;
import com.communityhelper.category.Category;
import com.communityhelper.category.StandardCategory;
import com.communityhelper.category.api.representation.ItemDTO;
import com.communityhelper.merchant.Merchant;
import com.communityhelper.software.Image;
import com.communityhelper.user.User;
import com.communityhelper.user.UserServiceStatus;

@Service
public class CategoryService {
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;
    
    @SuppressWarnings({"unchecked","rawtypes"})
    public Page<ItemDTO> createCategoryPage(Integer categoryId, Integer communityId, Integer start, Integer size) {
        Map paramsMap = new HashMap();
        paramsMap.put("categoryId", categoryId);
        paramsMap.put("authStatus", "VALID");
        paramsMap.put("serviceEnable", true);
        paramsMap.put("start", start);
        paramsMap.put("size", size);
        List<ItemDTO> result = jdbcTemplate.query(CategoryIndexItemDTOMapper.queryCategoryIndexSql, paramsMap, new CategoryIndexItemDTOMapper());
        
        Page<ItemDTO> categoryPage = new Page<ItemDTO>(start, size);
        categoryPage.setList(result);
        categoryPage.setTotalResult(Category.countCategorysByParentCategoryId(categoryId, communityId) 
                + Merchant.countValidMerchantsByCategoryId(categoryId, communityId));
        return categoryPage;
    }
    
    @Cacheable(value = "categories", key = "'categories'")
    public List<StandardCategory> findAllStandardCategorys() {
        List<StandardCategory> categories = StandardCategory.findAllStandardCategorys();
        return categories;
    }
    
    private class CategoryIndexItemDTOMapper implements RowMapper<ItemDTO> {
        public static final String queryCategoryIndexSql = "select * from (" +
        		"select id,0 user_id,name,corder as `order`, 1 isCategory, 0 score,icon_id,category_id from category " +
        		"where category_id = :categoryId " +
        		"union all " +
        		"select id,user_id,name,morder as `order`, 0 isCategory,score,0 icon_id,category_id from merchant " +
        		"where category_id = :categoryId and auth_status = :authStatus and service_enable = :serviceEnable) o " +
        		"order by o.`order` asc limit :start,:size";
        @Override
        public ItemDTO mapRow(ResultSet rs, int rowIndex) throws SQLException {
            ItemDTO dto = new ItemDTO();
            dto.setId(rs.getInt("id"));
            dto.setCategoryId(rs.getInt("category_id"));
            dto.setName(rs.getString("name"));
            dto.setIsCategory(rs.getBoolean("isCategory"));
            if(dto.getIsCategory() && rs.getInt("icon_id") != 0) {
                dto.setIconURL(Image.findImage(rs.getInt("icon_id")).getUrl());
            }
            dto.setOrder(rs.getInt("order"));
            dto.setScore(rs.getDouble("score"));
            dto.setServiceEnable(true);
            if(rs.getInt("user_id") != 0) {
                dto.setUserServiceStatus(User.findUser(rs.getInt("user_id")).getUserServiceStatus());
            } else {
                dto.setUserServiceStatus(UserServiceStatus.DO_BUSINESS);
            }
            return dto;
        }
    }
}
