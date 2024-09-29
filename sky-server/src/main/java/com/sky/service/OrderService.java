package com.sky.service;

import com.sky.dto.*;
import com.sky.entity.OrderDetail;
import com.sky.result.PageResult;
import com.sky.vo.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    /**
     * 用户 下单
     */
    OrderSubmitVO submitOrder(OrdersSubmitDTO ordersSubmit);

    /**
     * 订单支付
     */
    OrderPaymentVO payment(OrdersPaymentDTO ordersPaymentDTO) throws Exception;

    /**
     * 支付成功，修改订单状态
     */
    void paySuccess(String outTradeNo);

    /**
     * 管理员 取消订单
     */
    void cancle(OrdersCancelDTO ordersCancelDTO) throws Exception;

    /**
     * 查询历史订单
     */
    PageResult pageQuery(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 用户 再来一单
     */
    void repetition(Long id);

    /**
     * 用户 取消订单
     */
    void userCancleById(Long id) throws Exception;

    /**
     * 查询订单详情
     */
    OrderVO details(Long id);

    /**
     * 订单状态统计
     */
    OrderStatisticsVO statisticsOrder();

    /**
     * 管理端 订单搜索
     */
    PageResult pageQuerySearch(OrdersPageQueryDTO ordersPageQueryDTO);

    /**
     * 接单
     */
    void confirm(OrdersConfirmDTO ordersConfirmDTO);

    /**
     * 拒单
     */
    void rejection(OrdersRejectionDTO ordersRejectionDTO) throws Exception;

    /**
     * 派送订单
     */
    void delivery(Long id);

    /**
     * 完成订单
     */
    void complete(Long id);

    /**
     * 用户催单
     */
    void reminder(Long id);


}
