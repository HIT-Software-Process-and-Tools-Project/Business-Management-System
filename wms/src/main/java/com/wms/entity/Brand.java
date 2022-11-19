package com.wms.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author wms
 * @since 2022-11-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Brand对象", description="")
public class Brand implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "品牌名")
    private String name;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "备注")
    private String remark;

}