package com.sl.lms.domain.service;

import java.util.List;

import com.sl.lms.domain.Role;

public interface RoleService {
	public List<Role> getAllRoles();
	public Role getRoleByName(String roleName);
}
