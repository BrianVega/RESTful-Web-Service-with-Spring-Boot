package com.gd.bvega.webservices.restful_web_services.user.repositories;

import com.gd.bvega.webservices.restful_web_services.user.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {
}
