package com.techvisio.eserve.db.impl;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


public abstract class BaseDao {

private DataSource dataSource;
private JdbcTemplate jdbcTemplate;
private NamedParameterJdbcTemplate namedParamJdbcTemplate;
private SessionFactory sessionFactory;

@Autowired
public void setDatasource(DataSource dataSource) {
	this.dataSource = dataSource;
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    this.namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
}


public JdbcTemplate getJdbcTemplate() {
	return jdbcTemplate;
}


@Autowired
public void setSessionFactory(SessionFactory sessionFactory) {
	this.sessionFactory = sessionFactory;
}


public NamedParameterJdbcTemplate getNamedParamJdbcTemplate() {
	return namedParamJdbcTemplate;
}


public DataSource getDataSource() {
	return dataSource;
}

public Session getCurrentSession(){
	return sessionFactory.getCurrentSession();
}


}
