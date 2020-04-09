package com.controller.view;

import lombok.Data;

/**
 * 返回给前端的视图
 * @author czf
 * @Date 2020/4/9 10:47 上午
 */
@Data
public class TaskVO {
    private Integer id;
    private String taskName;
    private String description;
    private String deadline;
    private Integer finished;
}
