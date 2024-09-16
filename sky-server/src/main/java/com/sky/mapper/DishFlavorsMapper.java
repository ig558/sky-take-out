package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 菜品口味表
 */
@Mapper
public interface DishFlavorsMapper {

    /**
     * 批量插入口味数据
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * 根据菜品主键删除菜品口味
     */
    @Delete("delete from dish_flavor where dish_id = #{dishId};")
    void deleteByDishId(Long dishId);

    /**
     * 根据多个菜品主键删除菜品口味
     * */
    void deleteByDishIds(List<Long> dishIds);

    /**
     * 根据菜Id查询菜品数据 和 口味数据
     */
    @Select("select * from dish_flavor where dish_id = #{dishId};")
    List<DishFlavor> getByDishId(Long dishId);
}
