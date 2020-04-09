# 敏捷Web开发大作业

## 后端:

使用mysql进行数据持久化存储。并使用视图，领域模型，DataObject进行分层通过贫血模式实现，可扩展性较强。自定义了异常类， 丰富了错误类型及对应的错误信息, 方便扩展.

### 工具:
- springboot
- mybatis
- maven

### 说明：
- 统一返回类型, 后端返回类型固定包含2个属性分别为: 
    - `status`:
        - `success`: 表示操作成功
        - `fail`: 表示操作失败
    - `data`: 
        - 操作成功时，`data`里是返回的数据，当无数据返回时默认为null
        - 操作失败时，`data`里是自定义异常类，包括了：
            - `errCode`: 错误码编号
            - `errMsg`: 错误信息

- `task_info`表字段：

| Field       | Type         | Null | Key | Default             | Extra          | Comment  | Required |
|-------------|--------------|------|-----|---------------------|----------------|----------|-----------| 
| id          | int(11)      | NO   | PRI | NULL                | auto_increment | 任务唯一标示|  false   |  
| task_name   | varchar(255) | NO   | UNI | NULL                |                | 任务名称  |   true    |
| description | varchar(255) | NO   |     | 无                  |                | 任务描述 |    false   |
| deadline    | datetime     | NO   |     | 1970-01-01 00:00:00 |                | 截止日期 |    false   |
| finished    | int(11)      | NO   |     | -1                  |                | 是否完成 0为未完成, 1为完成| true|  

- API:
    - 列出所有任务: 
        - `/task/list`
        - GET方法
        - 输入参数: 无
        - 返回类型：
            - `status`
            - `data`: 成功时为 `list<task>`
        - task类型包括：`id`, `description`, `deadline`, `taskName`, `finished`
    
    - 增加新任务:
        - `/task/add` 
        - POST方法
        - 输入参数: `id`, `description`, `deadline`, `taskName`, `finished` ，其中`taskName`, `finished`为必填项，其他为选填
        - 返回类型：
            - `status`
            - `data`: 成功时为 null
    
    - 修改任务:
        - `/task/modify`
        - PUT方法
        - 输入参数: `id`, `description`, `deadline`, `taskName`, `finished` ，其中`taskName`, `finished`为必填项，其他为选填
        - 返回类型：
            - `status`
            - `data`: 成功时为 null
        - 额外说明：
            - 若修改的task之前未添加，则会自动进行添加，若已添加过，则当前添加的会将旧的覆盖.
    
    - 删除任务:
        - `/task/delete`
        - DELETE方法
        - 输入参数: `id`,`taskName`, 其中，`taskName`为必填项，当两个参数都写了的时候，优先按照`id`进行删除.
        - 返回类型：
            - `status`
            - `data`: 成功时为 null
        - 额外说明：
            - 若修改的task之前未添加，则会自动进行添加，若已添加过，则当前添加的会将旧的覆盖.


后端程序已部署至阿里云上： http://123.57.34.206 