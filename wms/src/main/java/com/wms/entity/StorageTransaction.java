package com.wms.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageTransaction {
    private Integer u_id;
    private Integer it_id;
    private Integer i_id_s;
    private Integer il_id_d;
    private Integer quantity;
    private String goods_name;
    private String s_storage_name;
    private String d_storage_name;
    private Integer s_quantity;
    private Integer d_quantity;
    private Integer i_id_d;
    private Integer il_id_s;
    private Long g_id;
}
