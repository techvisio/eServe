package com.techvisio.eserve.factory;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;

public interface UniqueIdentifierGenerator {

    public String getUniqueIdentifierForCustomer(Customer customer);

    public String getUniqueIdentifierForUnit(Unit unit);	

}
