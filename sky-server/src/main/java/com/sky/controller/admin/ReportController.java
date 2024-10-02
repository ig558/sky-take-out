package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.service.ReportService;
import com.sky.vo.OrderReportVO;
import com.sky.vo.SalesTop10ReportVO;
import com.sky.vo.TurnoverReportVO;
import com.sky.vo.UserReportVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/admin/report")
@Slf4j
@Api("数据统计相关接口")
public class ReportController {
    @Autowired
    private ReportService reportService;

    /**
     * 营业额统计
     */
    @GetMapping("/turnoverStatistics")
    @ApiOperation("营业额统计")
    public Result<TurnoverReportVO> turnoverStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        return Result.success(reportService.turnoverStatistics(begin, end));
    }

    /**
     * 新增用户统计
     */
    @GetMapping("/userStatistics")
    @ApiOperation("新增用户统计")
    public Result<UserReportVO> userStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        return Result.success(reportService.userStatistics(begin, end));
    }

    /**
     * 订单数量、完成率统计
     */
    @GetMapping("/ordersStatistics")
    @ApiOperation("订单数量、完成率统计")
    public Result<OrderReportVO> ordersStatistics(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        return Result.success(reportService.ordersStatistics(begin, end));
    }

    /**
     * 菜品、套餐销量排行
     */
    @GetMapping("/top10")
    @ApiOperation("菜品、套餐销量排行top10")
    public Result<SalesTop10ReportVO> top10(
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate begin,
            @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate end
    ) {
        return Result.success(reportService.top10(begin, end));
    }


}

