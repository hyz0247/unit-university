package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @author hyz02
 * @since 2023-03-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="StudentInformation对象", description="")
public class StudentInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer studentId;

    private Integer universityId;

    private String name;

    private String phone;

    private Integer gender;

    private LocalDateTime birthday;

    private String major;

    private String email;

    private String introduce;

    private Integer level;

    private Integer graduationYear;

    @TableField("Job1_id")
    private Integer job1Id;

    @TableField("Job2_id")
    private Integer job2Id;

    @TableField("Job3_id")
    private Integer job3Id;


}
