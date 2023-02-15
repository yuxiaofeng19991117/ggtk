package com.bs.ggkt.vod.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bs.ggkt.model.vod.Subject;
import com.bs.ggkt.vo.vod.SubjectEeVo;
import com.bs.ggkt.vod.listener.SubjectListener;
import com.bs.ggkt.vod.mapper.SubjectMapper;
import com.bs.ggkt.vod.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author 
 * @since 2022-11-25
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectListener subjectListener;

    /**
     * 根据id查询出课程列表
     * @param id        id
     * @return          课程列表
     */
    @Override
    public List<Subject> selectSubjectList(Integer id) {

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);

        List<Subject> subjects = baseMapper.selectList(wrapper);

        subjects.forEach(subject -> {
            subject.setHasChildren(this.hasChild(subject.getId()));
        });

        return subjects;
    }


    private boolean hasChild(Long id) {

        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);

        return baseMapper.selectCount(wrapper) > 0;
    }

    /**
     * 课程分类导出功能
     * @param response      导出所需response
     */
    @Override
    public void exportData(HttpServletResponse response) {

        try {
            //设置下载信息
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("课程分类", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            List<Subject> subjectList = baseMapper.selectList(null);

            List<SubjectEeVo> subjectEeVos = new ArrayList<>();
            subjectList.forEach(subject -> {
                SubjectEeVo item = new SubjectEeVo();
                item.setId(subject.getId());
                item.setParentId(subject.getParentId());
                item.setTitle(subject.getTitle());
                item.setSort(subject.getSort());
                //可以使用工具类复制
                //BeanUtils.copyProperties(subject, item);
                subjectEeVos.add(item);
            });

            EasyExcel.write(response.getOutputStream(), SubjectEeVo.class)
                    .sheet("课程分类")
                    .doWrite(subjectEeVos);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 导入课程分类
     * @param file              导入的file
     */
    @Override
    public void importData(MultipartFile file) {

        try {
            EasyExcel.read(file.getInputStream(), SubjectEeVo.class, subjectListener);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
