package com.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author czf
 * @Date 2020/4/8 11:31 下午
 */
@Data
@ApiModel("统一返回类型")
public class CommonReturnType {
    // state 取值范围 { "success", "fail" }
    @ApiModelProperty("操作成功：\"success\", 操作失败\"fail\"")
    private String status;
    // 若state为success, 则data为返回结果
    // 若state为fail，则data内使用通用的错误码格式
    @ApiModelProperty("若success则返回执行结果，fail则返回{errCode:..., errMsg:...}")
    private Object data;

    // 创建一个通用的创建方法
    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status) {
        CommonReturnType type = new CommonReturnType();
        type.setData(result);
        type.setStatus(status);
        return type;
    }
}
