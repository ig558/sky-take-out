package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    /**
     * 插入订单数据
     */
    void insert(Orders orders);

    /**
     * 根据订单号查询订单
     */
    @Select("select * from orders where number = #{orderNumber}")
    Orders getByNumber(String orderNumber);

    /**
     * 修改订单信息
     */
    void update(Orders orders);

    /**
     * 根据状态和查询订单
     */
    @Select("select * from orders where status = #{status} and order_time < #{orderTime} ;")
    List<Orders> getByStatusAndOrderTimeLt(Integer status, LocalDateTime orderTime);

    /**
     * 分页查询
     */
    Page<Orders> pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 根据id查询订单
     */
    @Select("select * from orders where id = #{id}")
    Orders getById(Long id);

    /**
     * 分组查询订单数量
     */
    @Select("select * from orders where status = #{status};")
    Integer statisticsOrder(Integer status);

    /**
     * 根据订单id和用户id查询订单信息
     *
     * @param outTradeNo
     * @param currentId
     * @return
     */
    @Select("select * from orders where id = #{currentId} and number = #{outTradeNo};")
    Orders getByNumberAndUserId(String outTradeNo, Long currentId);
}
