package com.example.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2023-04-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Messages对象", description="")
public class Messages implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "发送者")
    private Integer senderId;

    @ApiModelProperty(value = "接收者")
    private Integer receiverId;

    @ApiModelProperty(value = "消息类型(通知、私信)")
    private String type;

    @ApiModelProperty(value = "消息状态(0:未读 1:已读)")
    private String status;

    @ApiModelProperty(value = "发送信息")
    private String content;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @ApiModelProperty(value = "发送时间")
    private LocalDateTime sendTime;

    @TableField(exist = false)
    private Integer roleId;


}
