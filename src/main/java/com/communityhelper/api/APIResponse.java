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
        USER_NOT_FOUND("001", "用户不存在"), 
        PASSWORD_ERROR("002", "用户密码错误"), 
        USER_ALREADY_EXISTS("003", "用户已经存在"),
        NOT_HAVE_RECORD("999", "没有找到数据"), 
        SUCCESS("000", "成功");
        
        private String status;
        private String message;
        private Status(String status, String message){
            this.message = message;
            this.status = status;
        }
    }
}
