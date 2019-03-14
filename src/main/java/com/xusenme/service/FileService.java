package com.xusenme.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface FileService {

    public Object upload(String userId, Map<String, Object> paramMap, HttpServletRequest request);
}
