package com.techvisio.einstitution.db.impl;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.techvisio.einstitution.db.CacheDao;

@Transactional
@Component
public class CacheDaoImpl extends BaseDao implements CacheDao {
//	private static CustomLogger logger = CustomLogger.getLogger(CacheDaoImpl.class);
//
//
//	@Autowired @Qualifier(value="masterQueryProps")
//	private Properties masterQueryProps;
//
//	public void setmasterQueryProps(Properties masterQueryProps) {
//
//		this.masterQueryProps = masterQueryProps;
//	}
//
//	public List<Branch> getBranch(){  
//		String queryString="FROM Branch";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Branch> result= query.list();
//		return result;
//	}
//
//	public List<Course> getCourse(){
//		String queryString="FROM Course";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Course> result= query.list();
//		return result;
//	}
//
//	public List<CasteCategory> getCatagory(){
//		String queryString="FROM CasteCategory";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<CasteCategory> result= query.list();
//		return result;
//	}
//
//	public List<CounsellingBody> getCounsellingBody(){
//		String queryString="FROM CounsellingBody";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<CounsellingBody> result= query.list();
//		return result;	
//	}
//
//	public List<Subject> getSubject(){
//		String queryString="FROM Subject";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Subject> result= query.list();
//		return result;
//	}
//
//	public List<Qualification> getQualification(){
//		String queryString="FROM Qualification";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Qualification> result= query.list();
//		return result;
//	}
//
//	public List<QuotaCode> getQuotaCode(){
//		String queryString="FROM QuotaCode";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<QuotaCode> result= query.list();
//		return result;
//	}
//
//	public List<State> getState(){
//		String queryString="FROM State";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<State> result= query.list();
//		return result;
//	}
//
//	public List<Consultant> getConsultant(){
//		String queryString="FROM Consultant";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Consultant> result= query.list();
//		return result;
//	}
//
//	public List<FeeDiscountHead> getFeeDiscountHeadMaster() {
//		String queryString="FROM FeeDiscountHead";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<FeeDiscountHead> result= query.list();
//		return result;
//	}
//
//	public List<Semester> getSemester(){
//		String queryString="FROM Semester";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Semester> result= query.list();
//		return result;	
//	}
//
//	@Override
//	public List<CodeMapping> getCodeMapping(){
//		String queryString="FROM CodeMapping";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<CodeMapping> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Batch> getBatch() {
//		String queryString="FROM Batch";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Batch> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Session> getSession() {
//		String queryString="FROM Session";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Session> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Centre> getCentre() {
//		String queryString="FROM Centre";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Centre> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Shift> getShift() {
//		String queryString="FROM Shift";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Shift> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Section> getSection() {
//		String queryString="FROM Section";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Section> result= query.list();
//		return result;
//	}
//
//	public List<Wing> getWing(){
//		String queryString="FROM Wing";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Wing> result= query.list();
//		return result;
//	}
//
//	public List<Block> getBlock(){
//		String queryString="FROM Block";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Block> result= query.list();
//		return result;
//	}
//
//	public List<Floor> getFloor(){
//		String queryString="FROM Floor";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Floor> result= query.list();
//		return result;
//	}
//
//	public List<RoomTypeDetail> getRoomTypeDetail(){
//		String queryString="FROM RoomTypeDetail";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<RoomTypeDetail> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<VehicleDetail> getVehicleDetail() {
//		String queryString="FROM VehicleDetail";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<VehicleDetail> result= query.list();
//		return result;
//	}
//
//	@Override	
//	public List<VehicleType> getVehicleTypes(){
//		String queryString="FROM VehicleType";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<VehicleType> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<RoomType> getRoomType() {
//		String queryString="FROM RoomType";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<RoomType> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Transport> getTransport() {
//		String queryString="FROM Transport";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Transport> result= query.list();
//		return result;
//	}
//	
//	@Override
//	public List<Route> getRoutes() {
//		String queryString="FROM Route";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Route> result= query.list();
//		return result;
//	}
//	
//	@Override
//	public List<RouteStoppage> getRouteStoppages() {
//		String queryString="FROM RouteStoppage";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<RouteStoppage> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Amenities> getAmenitiesCharges() {
//		String queryString="FROM Amenities";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Amenities> result= query.list();
//		return result;
//	}
//
//	@Override
//	public List<Workflow> getAdmissionWorkFlow() {
//		String queryString="FROM Workflow where workflow='ADMISSION'";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Workflow> result= query.list();
//		return result;
//	}
//	
//	@Override
//	public List<Activity> getActivities() {
//		String queryString="FROM Activity";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<Activity> result= query.list();
//		return result;
//	}
//	
//	@Override
//	public List<QuestionMaster> getQuestions() {
//		String queryString="FROM QuestionMaster";
//		Query query=getCurrentSession().createQuery(queryString);
//		List<QuestionMaster> result= query.list();
//		return result;
//	}
}
