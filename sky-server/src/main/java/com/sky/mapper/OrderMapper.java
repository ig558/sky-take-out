package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.GoodsSalesDTO;
import com.sky.dto.OrdersCancelDTO;
import com.sky.dto.OrdersPageQueryDTO;
import com.sky.entity.Orders;
import com.sky.vo.OrderReportVO;
import com.sky.vo.OrderStatisticsVO;
import com.sky.vo.SalesTop10ReportVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据动态条件统计营业额数据
     */
    Double sumByMap(Map map);

    /**
     * 根据动态条件统计订单数据
     */
    Integer countByMap(Map map);

    /**
     * 根据 订单表 订单菜品表 统计销量前十排行
     */
    List<GoodsSalesDTO> getSalesTop10(LocalDateTime begin, LocalDateTime end);
}
