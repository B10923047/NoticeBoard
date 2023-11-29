package com.Intumit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Intumit.model.User;

@Repository
public interface IUserRepo extends JpaRepository<User, Long>{
	User findByName(String name);
}
