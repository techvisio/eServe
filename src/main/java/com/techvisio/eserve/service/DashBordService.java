package com.techvisio.eserve.service;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public interface DashBordService {

	public Map<String, Long> getCount();

}
