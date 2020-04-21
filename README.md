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



## 前端：

使用React框架搭建前端实现todolist界面

### 工具：

- qs
- Jest

### 说明：

功能包括：
- 返回所有TODO任务
- 创建一个新的TODO任务
- 修改一个TODO任务
- 删除一个TODO任务

运行说明：

```
npm install
```

- 安装react框架 
  
    ```
    cnpm install -g create-react-app
    ```
    
- 安装跨域代理
  
    ```
    npm install http-proxy-middleware --save
    ```
    
- 安装qs库，解决axios传递数据格式问题
  
    ```
    npm install qs --sav
    ```



# 总结

- ## 后端总结（8条）：
    - 学会使用SpringBoot对RESTful接口的测试。 params接受的参数是`MultiValueMap`类型而不是`Map`.
    - 中文存储到数据库出现乱码时按顺序排查：
        - 确定发送方和接收方的编码格式均为utf8
        - 确定表的编码格式为utf8
        - 确定数据库的编码格式为utf8。 本次出现问题的地方在数据库的编码格式不是utf8，修改`my.cnf`文件，加上`character_set_server=utf8` 之后重启mysql服务之后，中文存储不再是问号乱码.
    - 定义全局的异常拦截器（使用BaseController作为所有Controller都需要继承的基类，并对BaseController加ExceptionHandler注解，实现了全局的异常拦截器。）
    - 使用视图，领域模型，DataObject进行分层通过贫血模式实现，层与层之间耦合较低，可扩展性较强。
    - 自定义了异常类， 丰富了错误类型及对应的错误信息, 方便扩展。
    - 使用了mybatis-generator，使得开发效率更加高效。
    - 学习使用JSR-303，使得参数校验更为方便。
    - 使用maven打包成jar包部署到阿里云主机，使得在外网可以调用对应api。

- ## 前端总结：

  - UI设计
    - CSS：层叠样式表
      - ps：chrome插件code cola可以方便的直接编辑网页ui并获得相关的html/css样式。神器。
      - CSS能够对网页中元素的排版进行像素级精确控制。
      - 常用的属性和方法：
        - 字体；字号；行高；字色；字型；对齐方式；文字阴影；文字描边；背景；线性渐变；透明度；盒阴影；遮罩；反射；变形；边框；圆角；布局；宽高；
        - 对于不同的组件来说有不同的侧重属性，本次大作业主要用到对文本text、按钮button、输入框input的属性；
    - material-ui/core/TextField：React 下的material-ui框架
      - 调用框架API实现ui；

  - TDD for REACT
    - 单元测试与单元测试框架Jest
      - 单元测试：测试是一种验证代码是否可以按照预期工作的手段。
      - 单元测试优势： 由于被测试对象的简单（通常只有一个或多个输入以及一个输出），这就决定了单元测试**开发起来也很简单**，通常每个测试只有几行到十几行不等。测试代码的简单表示它可以被**更频繁的执行**（事实上，很多单元测试框架都有 watch 模式。每次改动代码时都会自动执行单元测试）。更频繁的执行意味着**更早的发现问题**。 
      - 单元测试限制/不足： 单元测试覆盖率往往会给开发人员一种错觉：这段代码的单元测试都通过了（测试覆盖率以及 100% 了），肯定没有 bug。其实不然，单元测试覆盖率与代码质量没有必然的联系。 
      - 单元测试包含的几个部分：
        - 被测试的对象是什么
        - 要测试该对象的什么功能
        - 实际得到的结果
        - 期望的结果
        - mock / spy 
      - 单元测试步骤：
        - 准备阶段：构造参数，创建 spy 等
        - 执行阶段：用构造好的参数执行被测试代码
        - 断言阶段：用实际得到的结果与期望的结果比较，以判断该测试是否正常
        - 清理阶段：清理准备阶段对外部环境的影响，移除在准备阶段创建的 spy 
      - Jest简介
        -  [Jest](https://facebook.github.io/jest/) 是 Facebook 开发的一款 JavaScript 测试框架。在 Facebook 内部广泛用来测试各种 JavaScript 代码。 
        - 内置强大的断言和mock功能；内置测试覆盖率统计功能；内置snapshot机制；
        - 基础功能介绍： https://jestjs.io/docs/en/expect.html#methods 
        - Jest中的mock和spy
          - mock
            -  只需要 `jest.fn()` 就可以得到一个功能强大的 mock 函数。 在单元测试中, 必须排除本函数之外的其它因素对函数自身的影响,换句话说单元测试必须是确定性的,这种情况下就需要用到Mock。
          - spy
            -  通过 `jest.spyOn` 创建了一个监听 `xxx 对象的 `xxxx` 方法的 spy。它就像间谍一样监听了所有对 `xxx#XXXX` 方法的调用。
            -  https://jestjs.io/docs/en/jest-object.html#jestspyonobject-methodname 
    - 如何利用Jest为React组件编写单元测试
      - React组件的特点：
        - React 组件的 render 结果是一个组件树，并且整个树最终会被解析成一个纯粹由 HTML 元素构成的树形结构
        - React 组件可以拥有 state，且 state 的变化会影响 render 结果
        - React 组件可以拥有生命周期函数，这些生命周期函数会在特定时间点执行
      - 而React也为开发者提供了专门的测试库：@testing-library/react
        - 只需要import相关的包即可。
      - 测试工具
        - react-test-renderer
        - react-dom/test-utils
		
- ## 自动化测试总结（8条）：
	- 1.后代选择器与子元素选择器的使用。
		- 出处：自动化测试部分
		- 使用场景： 后代选择器用空格表示，如“div span”,它可以匹配div后面的span标签，选择的是所有特定后代标签，包括儿子、孙子等。子元素选择器用>表示，如div > span，匹配div后所有的span，所有特定的直接标签，也就是只选中儿子标签。
		- 学习二者的区别和联系对于正确的进行元素选择很有帮助。
	- 2.await page.waitFor()
		- 出处：自动化测试部分
		- 使用场景： 括号中可以是selector、function或timeout等，表示等待加载完成，等待具体时间等。
		- 应用该段代码可以确保获取的数据正确加载完后再进行获取，确保获得正确的数据。
	- 3.await fname.click({clickCount :3})
		- 出处：自动化测试部分
		- 使用场景：首先选中selector，进而可以使用改段代码控制单击的次数。
		- 这种写法简化了代码，使测试程序表意也更清晰，使用比较方便。
	- 4.await page.reload()
		- 出处：自动化测试部分
		- 使用场景：可以重新加载页面，对于不正常加载的情况下可以手动进行加载
	- 5.await page.$$()与await page.$()
		- 出处：自动化测试部分
		- 使用场景：前者可以获取多个数据，后者只能获取一个数据


  