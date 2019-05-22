package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResultCode;

public class CustomerException extends RuntimeException{

    ResultCode resultCode;
    public CustomerException(ResultCode resultCode){
        super("错误代码:"+resultCode.code()+"错误信息:"+resultCode.message());
        this.resultCode = resultCode;
    }

    public ResultCode getResultCode(){
        return this.resultCode;
    }
}
