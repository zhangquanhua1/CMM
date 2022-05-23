package com.ConstructionManagement.web.controller.ws;


import com.ConstructionManagement.common.core.controller.BaseController;
import com.ConstructionManagement.common.core.domain.AjaxResult;
import com.ConstructionManagement.common.core.domain.ChatMsg;
import com.ConstructionManagement.common.core.page.TableDataInfo;
import com.ConstructionManagement.system.service.IChatMsgService;
import com.ConstructionManagement.web.controller.inventorymanagement.CheckController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatMsgController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(ChatMsgController.class);
    @Autowired
    IChatMsgService icms;

    @RequestMapping("/getOffLine/{id}")
    public TableDataInfo getOffLine(@PathVariable("id")  String id){
        logger.info("getOffLine id"+id);
        List<ChatMsg> msgList = icms.selectByPrimaryKey(id);
        if(msgList!=null||msgList.size()>0){
            icms.deleteByPrimaryKey(id);
        }
        return getDataTable(msgList);
    }
}
