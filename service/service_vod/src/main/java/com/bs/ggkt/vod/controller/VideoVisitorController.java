package com.bs.ggkt.vod.controller;


import com.bs.ggkt.result.Result;
import com.bs.ggkt.vod.service.VideoVisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-26
 */
@RestController
@RequestMapping(value = "/admin/vod/videoVisitor")
@CrossOrigin
public class VideoVisitorController {

    @Autowired
    private VideoVisitorService videoVisitorService;

    /**
     * 课程统计接口
     * @param courseId          课程id
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @return                  统计结果
     */
    @GetMapping("findCount/{courseId}/{startDate}/{endDate}")
    public Result findCount(@PathVariable Integer courseId,
                            @PathVariable String startDate,
                            @PathVariable String endDate) {
        Map<String, Object> map = videoVisitorService.findCount(courseId, startDate, endDate);
        return Result.ok(map);
    }


}

