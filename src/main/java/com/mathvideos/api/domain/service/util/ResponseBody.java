package com.mathvideos.api.domain.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ResponseBody {
	private Integer status;
    private List<String> messages;
    private Object result;

    public ResponseBody() {
        this.status = HttpStatus.OK.value();
        this.messages = new ArrayList<>();
        this.result = null;
    }

    public void setMessage(String message) {
        getMessages().add(message);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
    
}
