package com.jiang.mall.exception;

import com.jiang.mall.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/*
  统一处理异常
 */
// 拦截异常的
@ControllerAdvice
public class GlobalExceptionHandler {
    private  final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        log.error("Default Exception:",e);
        return ApiRestResponse.error(MallExceptionEunm.SYSTEM_ERROR);
    }

    @ExceptionHandler(MallException.class)
    @ResponseBody
    public Object handleMallException(MallException e){
        log.error("MallException Exception:",e);
        return ApiRestResponse.error(e.getCode(),e.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public  ApiRestResponse handleMethodArgumentNotValidException(
       MethodArgumentNotValidException e

    ){
      log.error("MethodArgumentNotValidException: ", e);
      return handleBindingResult(e.getBindingResult());
    }

    private  ApiRestResponse handleBindingResult(BindingResult result){
      // 把异常处理为对外暴露的提示
        List<String> list =  new ArrayList<>();
        if(result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            for (int i = 0; i < allErrors.size() ; i++) {
                ObjectError objectError = allErrors.get(i);
               String message =  objectError.getDefaultMessage();
               list.add(message);
            }
        }
        if (list.size() ==0){
            return  ApiRestResponse.error(MallExceptionEunm.REQUEST_PARAM_ERROR);
        }
        return  ApiRestResponse.error(MallExceptionEunm.REQUEST_PARAM_ERROR.getCode(),list.toString());
    }

}
