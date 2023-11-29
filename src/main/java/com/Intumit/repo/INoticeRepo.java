package com.Intumit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Intumit.model.Notice;

@Repository
public interface INoticeRepo extends JpaRepository<Notice, Long>{

}
