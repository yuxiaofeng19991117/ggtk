package com.bs.ggkt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bs.ggkt.model.vod.Teacher;
import com.bs.ggkt.result.Result;
import com.bs.ggkt.vo.vod.TeacherQueryVo;
import com.bs.ggkt.vod.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-22
 */
@RestController
@RequestMapping("/admin/vod/teacher")
@CrossOrigin
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 查询所有老师信息
     * @return      所有老师信息
     */
    @GetMapping("findAll")
    public Result<List<Teacher>> findAllTeacher() {

        List<Teacher> teachers = teacherService.list();

        return Result.ok(teachers);

    }

    /**
     * 根据id逻辑删除老师信息
     * @param id    根据的id
     * @return      删除结果
     */
    @DeleteMapping("remove/{id}")
    public Result removeTeacher(@PathVariable Integer id) {

        if (teacherService.removeById(id)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }

    }

    /**
     * 根据老师的属性以及分页属性分页查找老师信息
     * @param current           当前第几页
     * @param limit             每页多少个
     * @param teacherQueryVo    老师信息
     * @return                  查询出的老师信息
     */
    @PostMapping("findQueryPage/{current}/{limit}")
    public Result<IPage<Teacher>> findPageTeacher(@PathVariable Integer current, @PathVariable Integer limit, @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {

        Page<Teacher> page = new Page<>(current, limit);
        //判断body是否为空
        if (teacherQueryVo == null) {
            //查询全部
            IPage<Teacher> teacherPage = teacherService.page(page, null);
            return Result.ok(teacherPage);
        } else {
            String name = teacherQueryVo.getName();
            Integer level = teacherQueryVo.getLevel();
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();

            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if (!StringUtils.isEmpty(name)) {
                wrapper.like("name", name);
            }
            if (!StringUtils.isEmpty(level)) {
                wrapper.eq("level", level);
            }
            if (!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date", joinDateBegin);
            }
            if (!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date", joinDateEnd);
            }



            IPage<Teacher> teacherPage = teacherService.page(page, wrapper);

            return Result.ok(teacherPage);
        }

    }

    /**
     * 保存老师信息
     * @param teacher       老师信息
     * @return              保存结果
     */
    @PostMapping("saveTeacher")
    public Result saveTeacher(@RequestBody Teacher teacher) {

        if (teacherService.save(teacher)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }

    }

    /**
     * 根据id获取老师信息
     * @param id            根据的id
     * @return              老师的信息
     */
    @GetMapping("getTeacher/{id}")
    public Result<Teacher> getTeacher(@PathVariable Integer id) {

        return Result.ok(teacherService.getById(id));

    }

    /**
     * 更新老师信息
     * @param teacher       老师信息
     * @return              更新的结果
     */
    @PostMapping("updateTeacher")
    public Result updateTeacher(@RequestBody Teacher teacher) {

        if (teacherService.updateById(teacher)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }

    }

    /**
     * 批量删除老师信息
     * @param idList        老师ids
     * @return              是否删除成功
     */
    @DeleteMapping("removeBatch")
    public Result removeBatch(@RequestBody List<Integer> idList) {

        if (teacherService.removeByIds(idList)) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }

    }

}

