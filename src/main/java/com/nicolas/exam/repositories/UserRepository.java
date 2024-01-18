package com.nicolas.exam.repositories;


import org.springframework.stereotype.Repository;

import com.nicolas.exam.models.User;

@Repository
public interface UserRepository extends BaseRepository<User>{
	User findByEmail(String email);
}
