package com.techvisio.eserve.factory;

import com.techvisio.eserve.beans.Customer;

public interface UniqueIdentifierGenerator {

    public String getUniqueIdentifierForCustomer(Customer customer);	

}
