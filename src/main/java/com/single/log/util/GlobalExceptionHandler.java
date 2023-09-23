//package com.single.log.util;
//
//import cn.hutool.core.util.StrUtil;
//import cn.hutool.json.JSONUtil;
//
//import com.single.log.module.R;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.TypeMismatchException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.converter.HttpMessageNotReadableException;
//import org.springframework.validation.BindException;
//import org.springframework.validation.FieldError;
//import org.springframework.web.HttpMediaTypeNotSupportedException;
//import org.springframework.web.HttpRequestMethodNotSupportedException;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.support.RequestContextUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.ConstraintViolation;
//import javax.validation.ConstraintViolationException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Locale;
//import java.util.stream.Collectors;
//
///**
// * 全局异常处理类-项目使用可继承该类
// * @author single
// * @date 2021/12/30 22:06
// */
//@Slf4j
//public class GlobalExceptionHandler {
//
//
//    @ExceptionHandler(Exception.class)
//    public R<?> exceptionHandler( Exception ex) {
//        log.error("sys error, message={}", ex.getMessage(), ex);
//        return R.failed("系统错误");
//    }
//
////    @ExceptionHandler(BizException.class)
////    public R<?> bizExceptionHandler( BizException ex) {
////        return R.fail(ex.getCode(), getI18nMessage(request,ex.getMessage(),ex.getArgs()));
////    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public R<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException ex) {
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        String msg = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining());
//        return R.failed(msg);
//    }
//
//    @ExceptionHandler(ConstraintViolationException.class)
//    public R<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
//        List<String> msgList = ex.getConstraintViolations().stream()
//                .map(ConstraintViolation::getMessage).collect(Collectors.joininag());
//        return R.failed(msg);
//    }
//
//    @ExceptionHandler({BindException.class})
//    public R<?> bindExceptionHandler( BindException ex) {
//        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
//        List<String> msgList = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.toList());
//        return getMessageList(request, msgList);
//    }
//
//
//
//    @ExceptionHandler(TypeMismatchException.class)
//    public R<?> typeMismatchExceptionHandler(TypeMismatchException ex) {
//        log.error("param type error , message={}", ex.getMessage(), ex);
//        return R.fail(CommonResultCode.ERROR_PARAM.getCode()
//                ,getI18nMessage(request,CommonResultCode.ERROR_PARAM.getMsg()));
//    }
//
//    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
//    public R<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
//        return R.fail(CommonResultCode.REQUEST_METHOD_ERROR.getCode()
//                ,getI18nMessage(request,CommonResultCode.REQUEST_METHOD_ERROR.getMsg()));
//    }
//
//    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
//    public R<?> httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException ex) {
//        return R.fail(CommonResultCode.REQUEST_METHOD_ERROR.getCode()
//                ,getI18nMessage(request,CommonResultCode.REQUEST_METHOD_ERROR.getMsg()));
//    }
//
//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    public R<?> httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
//        return R.fail(CommonResultCode.ILLEGAL_PARAM.getCode()
//                ,getI18nMessage(request,CommonResultCode.ILLEGAL_PARAM.getMsg()));
//    }
//
//
//
//}
