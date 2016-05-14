package com.techvisio.eserve.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Invoice;
import com.techvisio.eserve.beans.InvoiceComponentAmountBean;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class InvoiceGeneratorFactory {

	@Autowired
	CacheManager cacheManager;

	
	public InvoiceGenerator getInstance(){
		
		Map<Long, Map<String, Object>> configMap = cacheManager.getConfigMap(CommonUtil.getCurrentClient().getClientId());

		Map<String, Object> defaultMap = configMap.get(CommonUtil.getCurrentClient().getClientId());

		Config config = (Config) defaultMap.get(AppConstants.IS_TAX_INCLUDED);
		
		if(config.getValue().equalsIgnoreCase("N")){
		return new InvoiceTaxExcluded(cacheManager);
		}
		else
		{
		return new InvoiceTaxIncluded(cacheManager);
		}
	}
	
}
