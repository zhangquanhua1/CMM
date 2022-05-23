package com.ConstructionManagement.system.domain;

import java.io.Serializable;


/**
 * check_inventory
 * @author
 */

public class CheckInventory implements Serializable {
    private Long checkId;

    private Long inventoryId;

    public Long getCheckId() {
        return checkId;
    }

    public void setCheckId(Long checkId) {
        this.checkId = checkId;
    }

    public Long getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Long inventoryId) {
        this.inventoryId = inventoryId;
    }

    private static final long serialVersionUID = 1L;
}
