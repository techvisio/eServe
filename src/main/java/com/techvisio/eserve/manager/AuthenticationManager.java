package com.techvisio.eserve.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.User;
import com.techvisio.eserve.db.SecurityDao;

public class AuthenticationManager implements AuthenticationProvider {

	@Autowired
	SecurityDao securityDao;

	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       User user= securityDao.authenticateNgetUser(authentication);
       if(user==null){
    	   return null;
       }
       List<GrantedAuthority> privileges=new ArrayList<GrantedAuthority>();
       GrantedAuthority basePrivilege=new SimpleGrantedAuthority("USER_ROLE");
       privileges.add(basePrivilege);
       Set<Privilege> userPrivileges=securityDao.getUserPrivilege(user.getUserId());
       
       if(userPrivileges!=null){
    	   for(Privilege privilege:userPrivileges){
    		   privileges.add(new SimpleGrantedAuthority(privilege.getPrivilege()));
    	   }
       }
       return new UsernamePasswordAuthenticationToken(user,authentication.getCredentials(),privileges);
    }
 
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
