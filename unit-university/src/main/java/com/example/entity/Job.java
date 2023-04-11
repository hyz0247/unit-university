package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author hyz02
 * @since 2023-03-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Job对象", description="")
public class Job implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "关联单位userID")
    private Integer companyId;

    @ApiModelProperty(value = "岗位名称")
    private String title;

    @ApiModelProperty(value = "岗位类型")
    private Integer typeId;

    @ApiModelProperty(value = "岗位描述")
    private String description;

    @ApiModelProperty(value = "薪资下限(月薪/单位为k(千))")
    private Integer salaryMin;

    @ApiModelProperty(value = "薪资上限(月薪/单位为k(千))")
    private Integer salaryMax;

    @ApiModelProperty(value = "学历要求(0:专科以上，1：本科以上 2:硕士以上)")
    private String eduRequirement;

    @ApiModelProperty(value = "性别要求(0:女 1：男)")
    private String genderRequirement;

    @ApiModelProperty(value = "需求人数")
    private Integer number;

    @ApiModelProperty(value = "工作地点(市、区/县/镇、乡)")
    private String location;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发布日期")
    private LocalDateTime publishDate;

    @ApiModelProperty(value = "收藏次数")
    private Integer collectNumber;

    @ApiModelProperty(value = "状态(0:未招满、1：招满)")
    private Integer isFull;

    @ApiModelProperty(value = "1:启用 0:禁用")
    private String status;

    @TableField(exist = false)
    private boolean isFavorite;

    @TableField(exist = false)
    private Integer collectId;
}
