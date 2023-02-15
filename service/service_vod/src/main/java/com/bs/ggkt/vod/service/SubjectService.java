package com.bs.ggkt.vod.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.bs.ggkt.model.vod.Subject;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
public interface SubjectService extends IService<Subject> {

    /**
     * 根据id查询出课程列表
     * @param id        id
     * @return          课程列表
     */
    List<Subject> selectSubjectList(Integer id);

    /**
     * 课程分类导出功能
     * @param response      导出所需response
     */
    void exportData(HttpServletResponse response);

    /**
     * 导入课程分类
     * @param file              导入的file
     */
    void importData(MultipartFile file);
}
