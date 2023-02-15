package com.bs.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.ggkt.model.vod.Course;
import com.bs.ggkt.vo.vod.CourseFormVo;
import com.bs.ggkt.vo.vod.CoursePublishVo;
import com.bs.ggkt.vo.vod.CourseQueryVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
public interface CourseService extends IService<Course> {

    /**
     * 点播课程列表
     * @param pageParam     分页参数
     * @param courseQueryVo 点播课程搜索条件
     * @return              点播课程列表
     */
    Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo);

    /**
     * 添加课程基本信息
     * @param courseFormVo  课程基本信息
     * @return              课程id
     */
    Long saveCourseInfo(CourseFormVo courseFormVo);

    /**
     * 根据id查询课程信息
     * @param id            课程id
     * @return              课程信息
     */
    CourseFormVo getCourseInfoById(Integer id);

    /**
     * 更新课程信息
     * @param courseFormVo  课程信息
     */
    void updateCourse(CourseFormVo courseFormVo);

    /**
     * 根据课程id查询发布课程信息
     * @param id            课程id
     * @return              课程信息
     */
    CoursePublishVo getCoursePublishVo(Integer id);

    /**
     * 发布指定课程
     * @param id        指定课程id
     */
    void publishCourse(Integer id);

    /**
     * 通过id删除课程
     * @param id        课程id
     */
    void removeCourseById(Integer id);
}
