package com.ConstructionManagement.system.domain;

import java.util.List;

public class KitConfirmForm {
    //信息确认 id号
    private Long ids[];
    //信息确认数据
    private List<WmKitEntry> items;

    public Long[] getIds() {
        return ids;
    }

    public void setIds(Long[] ids) {
        this.ids = ids;
    }

    public List<WmKitEntry> getItems() {
        return items;
    }

    public void setItems(List<WmKitEntry> items) {
        this.items = items;
    }
}
