package com.bs.ggkt.vod.controller;


import com.bs.ggkt.model.vod.Subject;
import com.bs.ggkt.result.Result;
import com.bs.ggkt.vod.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
@RestController
@RequestMapping(value = "/admin/vod/subject")
@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    /**
     * 课程分类列表
     * @param id
     * @return
     */
    @GetMapping("getChildSubject/{id}")
    public Result<List<Subject>> getChildSubject(@PathVariable Integer id) {

        return Result.ok(subjectService.selectSubjectList(id));

    }

    /**
     * 导出课程分类
     * @param response          导出所需response
     */
    @GetMapping("exportData")
    public void exportData(HttpServletResponse response) {

        subjectService.exportData(response);

    }

    /**
     * 导入课程分类
     * @param file              导入的file
     */
    @PostMapping("importData")
    public void importData(MultipartFile file) {

        subjectService.importData(file);
        Result.ok(null);

    }


}

