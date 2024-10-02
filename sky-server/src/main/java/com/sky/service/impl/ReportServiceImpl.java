package com.sky.service.impl;

import com.sky.dto.GoodsSalesDTO;
import com.sky.entity.Orders;
import com.sky.mapper.OrderMapper;
import com.sky.mapper.UserMapper;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ReportServiceImpl implements ReportService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 统计指定区间的营业额数据
     */
    @Override
    public TurnoverReportVO turnoverStatistics(LocalDate begin, LocalDate end) {
        //获取 begin - end 之间的日期
        List<LocalDate> dateList = getDateTime(begin, end);

        //每天营业额
        List<Double> turnoverList = new ArrayList<>();

        //构造 开始时间 和 结束时间 并查询数据
        for (LocalDate localDate : dateList) {

            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);
            map.put("status", Orders.COMPLETED);

            Double number = orderMapper.sumByMap(map);
            number = number == null ? 0 : number;
            turnoverList.add(number);
        }

        return TurnoverReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .turnoverList(StringUtils.join(turnoverList, ","))
                .build();
    }

    /**
     * 统计指定区间的用户数据
     */
    @Override
    public UserReportVO userStatistics(LocalDate begin, LocalDate end) {
        //获取 begin - end 之间的日期
        List<LocalDate> dateList = getDateTime(begin, end);

        //总用户数量
        List<Integer> totalUserList = new ArrayList<>();

        //新增用户数量
        List<Integer> newUserList = new ArrayList<>();

        for (LocalDate localDate : dateList) {
            //用户总量
            //sel * from user where createTime < ?
            //新增用户
            //sel * from user where createTime < ? and create > ?
            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            Map map = new HashMap<>();
            map.put("end", endTime);
            //总用户数量
            Integer totalUser = userMapper.countByMap(map);

            map.put("begin", beginTime);
            //新增用户数量
            Integer newUser = userMapper.countByMap(map);

            totalUser = totalUser == null ? 0 : totalUser;
            newUser = newUser == null ? 0 : newUser;

            totalUserList.add(totalUser);
            newUserList.add(newUser);
        }

        return UserReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .totalUserList(StringUtils.join(totalUserList, ","))
                .newUserList(StringUtils.join(newUserList, ","))
                .build();
    }

    /**
     * 订单数量、完成率统计
     */
    @Override
    public OrderReportVO ordersStatistics(LocalDate begin, LocalDate end) {

        //获取 begin - end 之间的日期
        List<LocalDate> dateList = getDateTime(begin, end);

        //每日总订单数
        List<Integer> orderCountList = new ArrayList<>();

        //每日有效订单数据
        List<Integer> validOrderCountList = new ArrayList<>();

        for (LocalDate localDate : dateList) {

            //sel count(id) from order where create_time = #{begin} and create_time = #{end} and status > #{status};

            LocalDateTime beginTime = LocalDateTime.of(localDate, LocalTime.MIN);
            LocalDateTime endTime = LocalDateTime.of(localDate, LocalTime.MAX);

            Map map = new HashMap<>();
            map.put("begin", beginTime);
            map.put("end", endTime);

            //每日总订单数
            Integer orderCount = orderMapper.countByMap(map);
            orderCountList.add(orderCount);

            //每日有效订单数据
            map.put("status", Orders.COMPLETED);
            Integer validOrderCount = orderMapper.countByMap(map);
            validOrderCountList.add(validOrderCount);
        }
        //订单总数
        Integer totalOrderCounts = orderCountList.stream().reduce(Integer::sum).get();

        //有效订单数
        Integer validOrderCounts = validOrderCountList.stream().reduce(Integer::sum).get();

        Double orderCompletionRate = 0.0;
        if (totalOrderCounts != 0) {
            orderCompletionRate = validOrderCounts.doubleValue() / totalOrderCounts;
        }

        return OrderReportVO.builder()
                .dateList(StringUtils.join(dateList, ","))
                .orderCountList(StringUtils.join(orderCountList, ","))
                .validOrderCountList(StringUtils.join(validOrderCountList, ","))
                .totalOrderCount(totalOrderCounts)
                .validOrderCount(validOrderCounts)
                .orderCompletionRate(orderCompletionRate)
                .build();
    }

    /**
     * 统计销量排行 Top10
     */
    @Override
    public SalesTop10ReportVO top10(LocalDate begin, LocalDate end) {

        LocalDateTime beginTime = LocalDateTime.of(begin, LocalTime.MIN);
        LocalDateTime endTime = LocalDateTime.of(end, LocalTime.MAX);

        List<GoodsSalesDTO> salesTop10 = orderMapper.getSalesTop10(beginTime, endTime);
        List<String> nameList = salesTop10.stream()
                .map(GoodsSalesDTO::getName)
                .collect(Collectors.toList());
        List<Integer> numberList = salesTop10.stream()
                .map(GoodsSalesDTO::getNumber)
                .collect(Collectors.toList());

        return SalesTop10ReportVO.builder()
                .nameList(StringUtils.join(nameList, ","))
                .numberList(StringUtils.join(numberList, ","))
                .build();
    }

    /**
     * 获取 begin - end 之间的所有日期
     */
    private List<LocalDate> getDateTime(LocalDate begin, LocalDate end) {
        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(begin);

        while (!begin.equals(end)) {
            begin = begin.plusDays(1);
            dateList.add(begin);
        }

        return dateList;
    }
}
