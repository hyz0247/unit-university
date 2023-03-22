package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @author hyz02
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="UniversityInformation对象", description="")
public class UniversityInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer userId;

    private String name;

    private String address;

    private String email;

    private String contactName;

    private String contactPhone;

    private String description;

    private String logo;

    private String website;


}
