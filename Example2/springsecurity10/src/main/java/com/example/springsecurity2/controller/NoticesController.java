package com.example.springsecurity2.controller;

import com.example.springsecurity2.model.Notice;
import com.example.springsecurity2.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class NoticesController {

    @Autowired
    private NoticeRepository noticeRepository;

    @GetMapping("/notices")
    public ResponseEntity<List<Notice>> getNotices(){

        List<Notice> notices = noticeRepository.findAllActiveNotices();
        if( notices != null ){
            return ResponseEntity.ok()
                    .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS)) // you can use notices in cache for 60 seconds
                    .body(notices);
        }else{
            return null;
        }

    }

}
