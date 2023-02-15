package com.bs.ggkt.vod.controller;


import com.bs.ggkt.model.vod.Chapter;
import com.bs.ggkt.result.Result;
import com.bs.ggkt.vo.vod.ChapterVo;
import com.bs.ggkt.vod.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
@RestController
@RequestMapping(value = "/admin/vod/chapter")
@CrossOrigin
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    /**
     * 根据课程id获取大纲树
     * @param courseId      课程id
     * @return              大纲树
     */
    @GetMapping("getNestedTreeList/{courseId}")
    public Result getTreeList(@PathVariable Integer courseId) {

        List<ChapterVo> result = chapterService.getTreeList(courseId);

        return Result.ok(result);

    }

    @PostMapping("save")
    public Result save(@RequestBody Chapter chapter) {

        chapterService.save(chapter);

        return Result.ok(null);

    }

    @GetMapping("get/{id}")
    public Result get(@PathVariable Integer id) {

        Chapter chapter = chapterService.getById(id);

        return Result.ok(chapter);

    }

    @PostMapping("update")
    public Result update(@RequestBody Chapter chapter) {

        chapterService.updateById(chapter);

        return Result.ok(null);

    }

    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Integer id) {
        chapterService.removeById(id);
        return Result.ok(null);
    }

}

