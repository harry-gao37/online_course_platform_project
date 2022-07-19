package com.course.msm.service;

import java.util.Map;

public interface MsmService{
    boolean send(Map<String, Object> param, String phone);
}
