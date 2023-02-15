package com.bs.ggkt.vod.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.bs.ggkt.model.vod.Subject;
import com.bs.ggkt.vo.vod.SubjectEeVo;
import com.bs.ggkt.vod.mapper.SubjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectListener extends AnalysisEventListener<SubjectEeVo> {

    @Autowired
    private SubjectMapper subjectMapper;

    @Override
    public void invoke(SubjectEeVo subjectEeVo, AnalysisContext analysisContext) {

        Subject subject = new Subject();
        BeanUtils.copyProperties(subjectEeVo, subject);

        subjectMapper.insert(subject);

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
