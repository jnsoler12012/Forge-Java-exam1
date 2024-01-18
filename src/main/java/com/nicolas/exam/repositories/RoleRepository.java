package com.nicolas.exam.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nicolas.exam.models.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role>{
	List<Role> findByName(String name);
	Role findRoleByName(String Name);
}
