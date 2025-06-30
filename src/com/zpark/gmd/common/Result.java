package com.zpark.gmd.common;

/**
 * 统一响应结果类
 * @param <T> 数据类型
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;
    
    // 成功状态码
    public static final int SUCCESS_CODE = 200;
    // 失败状态码
    public static final int ERROR_CODE = 500;
    // 参数错误状态码
    public static final int PARAM_ERROR_CODE = 400;
    // 未授权状态码
    public static final int UNAUTHORIZED_CODE = 401;
    
    public Result() {}
    
    public Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功响应
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(SUCCESS_CODE, "操作成功", data);
    }
    
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(SUCCESS_CODE, message, data);
    }
    
    public static Result<Void> success() {
        return new Result<>(SUCCESS_CODE, "操作成功", null);
    }

    public static Result<Void> success(String message) {
        return new Result<>(SUCCESS_CODE, message, null);
    }
    
    /**
     * 失败响应
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ERROR_CODE, message, null);
    }
    
    public static <T> Result<T> error(int code, String message) {
        return new Result<>(code, message, null);
    }
    
    /**
     * 参数错误响应
     */
    public static <T> Result<T> paramError(String message) {
        return new Result<>(PARAM_ERROR_CODE, message, null);
    }
    
    /**
     * 未授权响应
     */
    public static <T> Result<T> unauthorized(String message) {
        return new Result<>(UNAUTHORIZED_CODE, message, null);
    }
    
    // Getters and Setters
    public int getCode() {
        return code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public T getData() {
        return data;
    }
    
    public void setData(T data) {
        this.data = data;
    }
    
    /**
     * 判断是否成功
     */
    public boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }
    
    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
