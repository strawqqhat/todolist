package com.service.model;

import lombok.Data;
import org.joda.time.DateTime;

import javax.validation.constraints.Future;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author czf
 * @Date 2020/4/8 11:53 下午
 */
@Data
public class TaskModel {
    // 任务id
    @Min(value = 0, message = "id不能小于0")
    private Integer id;
    // 任务名称
    @NotBlank
    private String taskName;
    // 任务描述
    private String description;
    // 截止日期
    @Future
    private DateTime deadline;
    // 是否完成 0是未完成，1是完成
    @Min(value = 0, message = "finished参数只能为0或1")
    @Max(value = 1,message = "finished参数只能为0或1")
    private Integer finished;
}
