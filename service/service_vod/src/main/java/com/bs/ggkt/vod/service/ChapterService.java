package com.bs.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.ggkt.model.vod.Chapter;
import com.bs.ggkt.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
public interface ChapterService extends IService<Chapter> {

    /**
     * 根据课程id获取大纲树
     * @param courseId          课程id
     * @return                  大纲树
     */
    List<ChapterVo> getTreeList(Integer courseId);

    void removeVideoByCourseId(Integer id);
}
