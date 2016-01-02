package com.techvisio.eserve.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
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
		// RequestContextHolder.getRequestAttributes().
		User user = null;
		try {
			user = securityDao.authenticateNgetUser(authentication);
		} catch (Exception e) {
			throw new BadCredentialsException("No User found with provided credential", e);
		}
		//if no user found
		if(user==null){
			throw new BadCredentialsException("No User found with provided credential");
		}
		
		//if user is found but expired
		if(!user.isActive()){
			throw new AccountExpiredException("User is inactive. Please contact system admin");
		}
		
		List<GrantedAuthority> privileges = new ArrayList<GrantedAuthority>();
		GrantedAuthority basePrivilege = new SimpleGrantedAuthority("USER_ROLE");
		privileges.add(basePrivilege);
		Set<Privilege> userPrivileges = securityDao.getUserPrivilege(user
				.getUserId());

		if (userPrivileges != null) {
			for (Privilege privilege : userPrivileges) {
				privileges.add(new SimpleGrantedAuthority(privilege
						.getPrivilege()));
			}
		}

		return new UsernamePasswordAuthenticationToken(user,
				authentication.getCredentials(), privileges);
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
