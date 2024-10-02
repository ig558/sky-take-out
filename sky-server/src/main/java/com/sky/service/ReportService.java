package com.sky.service;

import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;

import java.time.LocalDate;

public interface ReportService {
    /**
     * 营业额统计
     */
    TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end);

    /**
     * 新增用户统计
     */
    UserReportVO userStatistics(LocalDate begin, LocalDate end);

    /**
     * 订单数量、完成率统计
     */
    OrderReportVO ordersStatistics(LocalDate begin, LocalDate end);

    /**
     * 菜品、套餐销量排行
     */
    SalesTop10ReportVO top10(LocalDate begin, LocalDate end);
}
