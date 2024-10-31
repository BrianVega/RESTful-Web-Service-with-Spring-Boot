package com.gd.bvega.webservices.restful_web_services.user.repositories;

import com.gd.bvega.webservices.restful_web_services.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
