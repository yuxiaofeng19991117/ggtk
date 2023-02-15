package com.bs.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.ggkt.model.vod.Video;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
public interface VideoService extends IService<Video> {

    void removeVideoByCourseId(Integer id);
}
