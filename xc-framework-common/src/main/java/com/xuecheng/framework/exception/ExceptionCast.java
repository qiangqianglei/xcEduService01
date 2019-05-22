package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

/**
 * 异常捕获类
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomerException(resultCode);
    }
}
