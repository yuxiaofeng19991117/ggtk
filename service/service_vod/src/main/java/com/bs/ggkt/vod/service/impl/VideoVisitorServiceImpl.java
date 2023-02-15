package com.bs.ggkt.vod.service.impl;

import com.bs.ggkt.model.vod.VideoVisitor;
import com.bs.ggkt.vo.vod.VideoVisitorCountVo;
import com.bs.ggkt.vo.vod.VideoVisitorVo;
import com.bs.ggkt.vod.mapper.VideoVisitorMapper;
import com.bs.ggkt.vod.service.VideoVisitorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 视频来访者记录表 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-11-26
 */
@Service
public class VideoVisitorServiceImpl extends ServiceImpl<VideoVisitorMapper, VideoVisitor> implements VideoVisitorService {

    /**
     * 课程统计接口
     * @param courseId          课程id
     * @param startDate         开始时间
     * @param endDate           结束时间
     * @return                  统计结果
     */
    @Override
    public Map<String, Object> findCount(Integer courseId, String startDate, String endDate) {
        List<VideoVisitorCountVo> videoVisitorVos = baseMapper.findCount(courseId, startDate, endDate);

        Map<String, Object> map = new HashMap<>();

        List<String> dataList = videoVisitorVos.stream().map(VideoVisitorCountVo::getJoinTime).collect(Collectors.toList());

        List<Integer> countList = videoVisitorVos.stream().map(VideoVisitorCountVo::getUserCount).collect(Collectors.toList());

        map.put("xData", dataList);
        map.put("yData", countList);

        return map;
    }
}
