package com.techvisio.eserve.db.impl;

import org.springframework.stereotype.Component;

import com.techvisio.eserve.db.ModuleLogDao;
@Component
public class ModuleLogDaoImpl extends BaseDao implements ModuleLogDao {
//	private static CustomLogger logger = CustomLogger.getLogger(ModuleLogDaoImpl.class);	
//	@Autowired @Qualifier(value="moduleLogQueryProps")
//	private Properties  moduleLogQueryProps;
//	
//
//	public void setModuleLogQueryProps(Properties moduleLogQueryProps) {
//		this.moduleLogQueryProps = moduleLogQueryProps;
//	}
//
//	
//// GET DATA FROM ModuleLog TABLE	
//	
//	public ModuleLog getModuleLog(int entityId) {
//		logger.info("{} : Get module log by entity id:{}",this.getClass().getName(), entityId);
//		String getQuery = moduleLogQueryProps.getProperty("getModuleLog");
//		SqlParameterSource namedParameter = new MapSqlParameterSource("ENTITY_ID", entityId);
//		
//		List<ModuleLog> moduleLogs = getNamedParamJdbcTemplate().query(getQuery, namedParameter,new RowMapper<ModuleLog>(){
//
//			public ModuleLog mapRow(ResultSet rs, int rowNum)throws SQLException {
//				ModuleLog log = new ModuleLog();
//				log.setDate(rs.getDate("DATE"));
//				log.setEntityId(rs.getInt("ENTITY_ID"));
//				log.setOperation(rs.getString("OPERATION"));
//				log.setUserId(rs.getInt("USER_ID"));
//				log.setWorkFlowOperation(rs.getString("WORK_FLOW_OPERATION"));
//				log.setErrorMessage(rs.getString("ERROR_MESSAGE"));
//				return log;
//
//			}
//			
//			
//		});
//		ModuleLog moduleLog = null;
//		if(moduleLogs != null && moduleLogs.size()>0){
//			moduleLog = moduleLogs.get(0);
//		}
//		
//		
//		return moduleLog;
//
//	}
//	
//	
//	
//// INSERT DATA IN ModuleLog TABLE	
//
//	public void addModuleLog(ModuleLog moduleLog) {
//		logger.info("{} : add module log for entity id:{}",this.getClass().getName(), moduleLog.getEntityId());
//		String addQuery = moduleLogQueryProps.getProperty("addModuleLog");
//		SqlParameterSource namedParameter = new MapSqlParameterSource("ENTITY_ID", moduleLog.getEntityId())
//											.addValue("WORK_FLOW_OPERATION", moduleLog.getWorkFlowOperation())
//											.addValue("ERROR_MESSAGE", moduleLog.getErrorMessage())
//											.addValue("DATE", moduleLog.getDate())
//											.addValue("USER_ID", moduleLog.getUserId())
//											.addValue("OPERATION", moduleLog.getOperation());
//		getNamedParamJdbcTemplate().update(addQuery, namedParameter);
//	}
//	
//	
//// UPDATE DATA IN ModuleLog TABLE	
//
//	public void updateModuleLog(ModuleLog moduleLog) {
//		logger.info("{} : update module log for entity id:{}",this.getClass().getName(), moduleLog.getEntityId());
//		String updateQuery = moduleLogQueryProps.getProperty("updateModuleLog");
//		SqlParameterSource namedParameter = new MapSqlParameterSource("ENTITY_ID", moduleLog.getEntityId())
//											.addValue("WORK_FLOW_OPERATION", moduleLog.getWorkFlowOperation())
//											.addValue("ERROR_MESSAGE", moduleLog.getErrorMessage())
//											.addValue("DATE", moduleLog.getDate())
//											.addValue("USER_ID", moduleLog.getUserId())
//											.addValue("OPERATION", moduleLog.getOperation());
//		getNamedParamJdbcTemplate().update(updateQuery, namedParameter);
//		
//	}
//	
//// DELETE DATA FROM ModuleLog TABLE
//
//	public void deleteModuleLog(int entityId) {
//		logger.info("{} : delete module log by entity id:{}",this.getClass().getName(), entityId);
//		String deleteQuery = moduleLogQueryProps.getProperty("deleteModuleLog");
//		SqlParameterSource namedParameter = new MapSqlParameterSource("ENTITY_ID", entityId);
//											
//											
//		getNamedParamJdbcTemplate().update(deleteQuery, namedParameter);
//
//		
//	}
//
	
}
