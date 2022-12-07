package com.example.springsecurity2.repository;

import com.example.springsecurity2.model.Notice;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface NoticeRepository extends CrudRepository<Notice, Long> {

    //JPQL --> find notices where curr date is between notice begin date and end date
    @Query(value="FROM Notice n WHERE CURDATE() BETWEEN noticBegDt AND noticEndDt")
    List<Notice> findAllActiveNotices();

}
