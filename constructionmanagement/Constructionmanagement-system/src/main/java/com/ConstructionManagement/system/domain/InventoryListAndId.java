package com.ConstructionManagement.system.domain;

import java.util.List;

public class InventoryListAndId {
    List<Inventory> list;
    Long id;

    public List<Inventory> getList() {
        return list;
    }

    public void setList(List<Inventory> list) {
        this.list = list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
