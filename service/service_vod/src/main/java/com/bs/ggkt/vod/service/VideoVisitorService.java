package com.bs.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.ggkt.model.vod.VideoVisitor;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author 
 * @since 2022-11-26
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    /**
     * 课程统计接口
     * @param courseId          课程id
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @return                  统计结果
     */
    Map<String, Object> findCount(Integer courseId, String startDate, String endDate);
}
