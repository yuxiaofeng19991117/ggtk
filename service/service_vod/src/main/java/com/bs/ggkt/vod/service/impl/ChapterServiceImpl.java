package com.bs.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.ggkt.model.vod.Chapter;
import com.bs.ggkt.model.vod.Video;
import com.bs.ggkt.vo.vod.ChapterVo;
import com.bs.ggkt.vo.vod.VideoVo;
import com.bs.ggkt.vod.mapper.ChapterMapper;
import com.bs.ggkt.vod.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bs.ggkt.vod.service.VideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author
 * @since 2022-11-25
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private VideoService videoService;

    /**
     * 根据课程id获取大纲树
     *
     * @param courseId 课程id
     * @return 大纲树
     */
    @Override
    public List<ChapterVo> getTreeList(Integer courseId) {
        List<ChapterVo> res = new ArrayList<>();
        //根据课程id获取课程里所有章节
        QueryWrapper<Chapter> chapterWrapper = new QueryWrapper<>();
        chapterWrapper.eq("course_id", courseId);
        List<Chapter> chapters = baseMapper.selectList(chapterWrapper);
        //根据课程id获取课程里所有小节
        LambdaQueryWrapper<Video> videoWrapper = new LambdaQueryWrapper<>();
        videoWrapper.eq(Video::getCourseId, courseId);
        List<Video> videos = videoService.list(videoWrapper);
        //封装章节
        chapters.forEach(chapter -> {
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            res.add(chapterVo);

            List<VideoVo> videoVos = new ArrayList<>();
            videos.forEach(video -> {
                //判断小节是此章节下的
                if (chapter.getId().equals(video.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(video, videoVo);
                    videoVos.add(videoVo);
                }
            });
            chapterVo.setChildren(videoVos);
        });
        return res;
    }

    @Override
    public void removeVideoByCourseId(Integer id) {
        QueryWrapper<Chapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", id);
        baseMapper.delete(wrapper);
    }
}
