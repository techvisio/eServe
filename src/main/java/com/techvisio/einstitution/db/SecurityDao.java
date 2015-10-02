package com.techvisio.einstitution.db;

import java.util.List;
import java.util.Set;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.techvisio.eserve.beans.Privilege;
import com.techvisio.eserve.beans.Role;
import com.techvisio.eserve.beans.User;

@Component
public interface SecurityDao {

	User authenticateNgetUser(Authentication  authentication);

	Set<Privilege> getUserPrivilege(Long userId);

}
