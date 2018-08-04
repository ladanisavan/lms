package com.sl.lms.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.lms.domain.Role;
import com.sl.lms.domain.repository.RoleRepository;
import com.sl.lms.domain.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService{

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

	@Override
	public Role getRoleByName(String roleName) {
		return roleRepository.findByRole(roleName);
	}

}
