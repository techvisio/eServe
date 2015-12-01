package com.techvisio.eserve.db.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Customer;
import com.techvisio.eserve.beans.Unit;
import com.techvisio.eserve.db.CustomerDao;

@Component
public class CustomerDaoImpl extends BaseDao implements CustomerDao{

	@Override
	public Customer getCustomer(Long customerId) {
		String queryString="FROM Customer cus WHERE cus.customerId = "+customerId;
		Query query=getCurrentSession().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.list();
		if(customers != null && customers.size()>0){
			return customers.get(0);
		}
		return null;
	}
	
	@Override
	public List<Customer> getCustomers() {
		String queryString="FROM Customer";
		Query query=getCurrentSession().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Customer> customers= (List<Customer>)query.list();
		return customers;
	}

	@Override
	public Long saveCustomer(Customer customer) {
		if(customer.getCustomerId()==null){
		getCurrentSession().save(customer);
		}

		else{ 
		getCurrentSession().update(customer);
		}
		return customer.getCustomerId();
	}

	@Override
	public void saveUnit(List<Unit> units, Long customerId) {

		if(units!=null && units.size()>0){
			for(Unit unit : units){
				unit.setCustomerId(customerId);
				saveUnit(unit);
			}
		}
	}

	@Override
	public void saveUnit(Unit unit) {

		if(unit.getUnitId() == null){
			getCurrentSession().persist(unit);
		}
		else{
			getCurrentSession().update(unit);
		}
		getCurrentSession().flush();
	}	
	
	@Override
	public List<Unit> getUnits(Long customerId) {
		String queryString="FROM Unit u WHERE u.customerId = "+customerId;
		Query query=getCurrentSession().createQuery(queryString);
		@SuppressWarnings("unchecked")
		List<Unit> units= (List<Unit>)query.list();
		return units;
	}
}
