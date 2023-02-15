package com.bs.ggkt.vod.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bs.ggkt.model.vod.VideoVisitor;
import com.bs.ggkt.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2022-11-26
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    List<VideoVisitorCountVo> findCount(@Param("courseId") Integer courseId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate") String endDate);
}
