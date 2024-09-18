package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

public interface DishService {

    /**
     * 新增菜品和对应口味
     */
    void saveWithFlavor(DishDTO dishDTO);

    /**
     * 菜品分页查询
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * 菜品批量删除
     */
    void delete(List<Long> ids);

    /**
     * 根据菜Id查询菜品信息 和 口味信息
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * 根据菜Id修改菜品信息 和 口味信息
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * 菜品起售停售
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询菜品和口味
     *
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * 根据分类id查询菜品
     *
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}