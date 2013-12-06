package com.communityhelper.api;

public class APIResponse {
    private String status;
    private String message;
    
    private Object result;
    
    public APIResponse status(Status status) {
        this.status = status.status;
        this.message = status.message;
        return this;
    }
    
    public static APIResponse response() {
        return new APIResponse();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setErrorCode(Status status) {
        this.status = status.status;
        this.message = status.message;
    }

    public static APIResponse notHaveRecord(String message){
        APIResponse apiResponse = new APIResponse();
        apiResponse.status = Status.NOT_HAVE_RECORD.status;
        apiResponse.message = message;
        return apiResponse;
    }
    
    public APIResponse result(Object result){
        this.result = result;
        return this;
    }
    
    public static APIResponse success(APIResponse response){
        response.setErrorCode(Status.SUCCESS);
        return response;
    }
    
    public static APIResponse success(String message){
        APIResponse apiResponse = response();
        apiResponse.setErrorCode(Status.SUCCESS);
        apiResponse.setMessage(message);
        return apiResponse;
    }
    
    public enum Status{
        /** User API Status Code */
        SUCCESS("000", "成功"), 
        ERROR("999", "系统错误"), 
        USER_NOT_FOUND("001", "用户不存在"), 
        PASSWORD_ERROR("002", "用户密码错误"), 
        USER_ALREADY_EXISTS("003", "用户已经存在"),
        WAIT_TO_AUTH("004", "实名认证审核中"),
        ALREADY_AUTH("005", "实名认证已审核"),
        NOT_HAVE_RECORD("999", "没有找到数据"), 
        ALREADY_FEEDBACK("101","用户已经留言"), 
        TOO_MANY_VALID_CODE("004", "验证码发送次数超出3次上限"), 
        VALID_CODE_NOT_VALID("005", "验证码校验未通过"), 
        SEND_SMS_ERROR("006", "发送短信失败");
        
        private String status;
        private String message;
        private Status(String status, String message){
            this.message = message;
            this.status = status;
        }
    }
}
