package com.techvisio.eserve.factory;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Config;
import com.techvisio.eserve.beans.Invoice;
import com.techvisio.eserve.beans.InvoiceComponentAmountBean;
import com.techvisio.eserve.manager.CacheManager;
import com.techvisio.eserve.manager.impl.ClientConfiguration;
import com.techvisio.eserve.util.AppConstants;
import com.techvisio.eserve.util.CommonUtil;

@Component
public class InvoiceGeneratorFactory {

	@Autowired
	CacheManager cacheManager;

	@Autowired
	ClientConfiguration configPreferences;


	public InvoiceGenerator getInstance(){

		Config config = configPreferences.getConfigObject(AppConstants.IS_TAX_INCLUDED);

		if(config.getValue().equalsIgnoreCase("N")){
			return new InvoiceTaxExcluded(cacheManager);
		}
		else
		{
			return new InvoiceTaxIncluded(cacheManager);
		}
	}

}
