package com.bs.ggkt.vod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.ggkt.model.vod.Course;
import com.bs.ggkt.model.vod.CourseDescription;
import com.bs.ggkt.model.vod.Subject;
import com.bs.ggkt.model.vod.Teacher;
import com.bs.ggkt.vo.vod.CourseFormVo;
import com.bs.ggkt.vo.vod.CoursePublishVo;
import com.bs.ggkt.vo.vod.CourseQueryVo;
import com.bs.ggkt.vod.mapper.CourseMapper;
import com.bs.ggkt.vod.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private CourseDescriptionService courseDescriptionService;

    @Autowired
    private VideoService videoService;

    @Autowired
    private ChapterService chapterService;

    /**
     * 点播课程列表
     * @param pageParam     分页参数
     * @param courseQueryVo 点播课程搜索条件
     * @return              点播课程列表
     */
    @Override
    public Map<String, Object> findPageCourse(Page<Course> pageParam, CourseQueryVo courseQueryVo) {

        //获取条件值
        String title = courseQueryVo.getTitle();
        Long subjectId = courseQueryVo.getSubjectId();
        Long subjectParentId = courseQueryVo.getSubjectParentId();
        Long teacherId = courseQueryVo.getTeacherId();

        //判断条件为空, 封装条件
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!StringUtils.isEmpty(subjectId)) {
            wrapper.eq("subject_id", subjectId);
        }
        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
        }
        if (!StringUtils.isEmpty(teacherId)) {
            wrapper.eq("teacher_id", teacherId);
        }

        //调用方法实现条件查询分页
        Page<Course> pages = baseMapper.selectPage(pageParam, wrapper);

        //封装数据
        long totalCount = pages.getTotal();
        long totalPage = pages.getPages();
        List<Course> records = pages.getRecords();

        records.forEach(this::getNameById);

        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("totalPage", totalPage);
        map.put("records", records);

        return map;

    }

    /**
     * 添加课程基本信息
     * @param courseFormVo  课程基本信息
     * @return              课程id
     */
    @Override
    public Long saveCourseInfo(CourseFormVo courseFormVo) {
        //添加课程基本信息, 操作course表
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);

        baseMapper.insert(course);

        //添加课程描述信息, 操作course_description表
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseFormVo.getDescription());
        //设置课程描述id
        courseDescription.setId(course.getId());
        courseDescriptionService.save(courseDescription);

        return course.getId();
    }

    /**
     * 根据id查询课程信息
     * @param id            课程id
     * @return              课程信息
     */
    @Override
    public CourseFormVo getCourseInfoById(Integer id) {

        //课程基本信息
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        //课程描述信息
        CourseDescription courseDescription = courseDescriptionService.getById(id);

        //数据封装
        CourseFormVo courseFormVo = new CourseFormVo();
        BeanUtils.copyProperties(course, courseFormVo);
        if (courseDescription != null) {
            courseFormVo.setDescription(courseDescription.getDescription());
        }

        return courseFormVo;

    }

    /**
     * 更新课程信息
     * @param courseFormVo  课程信息
     */
    @Override
    public void updateCourse(CourseFormVo courseFormVo) {

        //修改课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseFormVo, course);
        baseMapper.updateById(course);

        //修改课程描述信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setId(course.getId());
        courseDescription.setDescription(courseFormVo.getDescription());
        courseDescriptionService.updateById(courseDescription);

    }

    /**
     * 根据课程id查询发布课程信息
     * @param id            课程id
     * @return              课程信息
     */
    @Override
    public CoursePublishVo getCoursePublishVo(Integer id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    /**
     * 发布指定课程
     * @param id        指定课程id
     */
    @Override
    public void publishCourse(Integer id) {

        Course course = baseMapper.selectById(id);
        course.setStatus(1);
        course.setPublishTime(new Date());
        baseMapper.updateById(course);

    }

    @Override
    public void removeCourseById(Integer id) {

        //根据课程id删除小节
        videoService.removeVideoByCourseId(id);

        //根据课程id删除章节
        chapterService.removeVideoByCourseId(id);

        //根据课程id删除课程描述
        courseDescriptionService.removeById(id);

        //根据课程id删除课程
        baseMapper.deleteById(id);

    }

    private Course getNameById(Course item) {

        //根据讲师id获取讲师名称
        Teacher teacher = teacherService.getById(item.getTeacherId());
        if (teacher != null) {
            item.getParam().put("teacherName", teacher.getName());
        }
        //根据课程分类id获取课程分类名称
        Subject subjectParent = subjectService.getById(item.getSubjectParentId());
        if (subjectParent != null) {
            item.getParam().put("subjectParentTitle", subjectParent.getTitle());
        }
        Subject subject = subjectService.getById(item.getSubjectId());
        if (subject != null) {
            item.getParam().put("subjectTitle", subject.getTitle());
        }

        return item;
    }

}
