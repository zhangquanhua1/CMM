package com.ConstructionManagement.system.domain;

import com.ConstructionManagement.common.core.domain.entity.SysDept;
import com.ConstructionManagement.common.utils.poi.ExcelHandlerAdapter;
import com.ConstructionManagement.common.utils.spring.SpringUtils;
import com.ConstructionManagement.system.mapper.SysDeptMapper;
import com.ConstructionManagement.system.service.ISysDeptService;
import com.ConstructionManagement.system.service.impl.SysDeptServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GetDeptNameAdapter implements ExcelHandlerAdapter {
    Logger logger = LoggerFactory.getLogger(GetDeptNameAdapter.class);
    ISysDeptService isd = SpringUtils.getBean(ISysDeptService.class);

    @Override
    public Object format(Object value, String[] args) {
        SysDept sd = isd.selectDeptById((Long) value);
        return sd.getDeptName();
    }
}
