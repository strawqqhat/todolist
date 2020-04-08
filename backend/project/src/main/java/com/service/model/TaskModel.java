package com.service.model;

import lombok.Data;

import java.util.Date;

/**
 * @author czf
 * @Date 2020/4/8 11:53 下午
 */
@Data
public class TaskModel {
    // 任务id
    public Integer id;
    // 任务名称
    public String taskName;
    // 任务描述
    public String description;
    // 截止日期
    public Date deadline;
    // 是否完成
    public Integer finished;
}
