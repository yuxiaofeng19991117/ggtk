package com.bs.ggkt.vod.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.ggkt.model.vod.Course;
import com.bs.ggkt.result.Result;
import com.bs.ggkt.vo.vod.CourseFormVo;
import com.bs.ggkt.vo.vod.CoursePublishVo;
import com.bs.ggkt.vo.vod.CourseQueryVo;
import com.bs.ggkt.vod.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
@RestController
@RequestMapping(value = "/admin/vod/course")

@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 点播课程列表
     * @param page          第几页
     * @param limit         每页多少个
     * @return              点播课程列表
     */
    @GetMapping("{page}/{limit}")
    public Result courseList(@PathVariable Integer page, @PathVariable Integer limit, CourseQueryVo courseQueryVo) {

        Page<Course> pageParam = new Page<Course>(page, limit);
        Map<String, Object> map = courseService.findPageCourse(pageParam, courseQueryVo);

        return Result.ok(map);

    }

    /**
     * 添加课程基本信息
     * @param courseFormVo  课程基本信息
     * @return              课程id
     */
    @PostMapping("save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {

        Long courseId = courseService.saveCourseInfo(courseFormVo);

        return Result.ok(courseId);
    }

    /**
     * 根据id查询课程信息
     * @param id            课程id
     * @return              课程信息
     */
    @GetMapping("get/{id}")
    public Result get(@PathVariable Integer id) {

        CourseFormVo courseFormVo = courseService.getCourseInfoById(id);
        return Result.ok(courseFormVo);

    }

    /**
     * 更新课程信息
     * @param courseFormVo  课程信息
     * @return              更新结果
     */
    @PostMapping("update")
    public Result update(@RequestBody CourseFormVo courseFormVo) {

        courseService.updateCourse(courseFormVo);

        return Result.ok(courseFormVo.getId());

    }

    /**
     * 根据课程id查询发布课程信息
     * @param id            课程id
     * @return              课程信息
     */
    @GetMapping("getCoursePublishVo/{id}")
    public Result getCoursePublishVo(@PathVariable Integer id) {

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);

        return Result.ok(coursePublishVo);
    }

    /**
     * 最终发布课程
     * @param id            课程id
     * @return              发布结果
     */
    @PutMapping("publishCourse/{id}")
    public Result publishCourse(@PathVariable Integer id) {

        courseService.publishCourse(id);
        return Result.ok(null);

    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {

        courseService.removeCourseById(id);
        return Result.ok(null);

    }


}

