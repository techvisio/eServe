package com.techvisio.eserve.factory;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;
import org.springframework.stereotype.Repository;

import com.techvisio.eserve.db.impl.BaseDao;
import com.techvisio.eserve.util.SqlFunction;


@Repository
public class SequenceFactory extends BaseDao {
	
	public Long getSequence(String seqName){
		Long sequence=null;
		StoredProcedure sp=new SqlFunction(getDataSource(), "getSequence");
		sp.declareParameter(new SqlOutParameter("seq", Types.INTEGER));
		sp.declareParameter(new SqlParameter("seq_name",Types.VARCHAR));
		sp.compile();
		Map inputs = new HashMap();
		inputs.put("seq_name",seqName);
		Map output=sp.execute(inputs);
		if(output.get("seq")!=null){
		sequence=Long.valueOf(output.get("seq").toString());
		}
		return sequence;
	}

}
