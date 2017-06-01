package com.web.controller;

import java.util.Date;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utils.IpUtil;
import com.utils.SendRedPackUtil;
import com.web.entity.Answer;
import com.web.entity.Schedule;
import com.web.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sukey on 2016/5/17.
 */
@Controller
@RequestMapping("/meeting")
public class MeetingController {

    @Autowired
    SurveyService surveyService;

    @RequestMapping(value = "/invitation")
    public String questionnaire(HttpServletRequest request, HttpServletResponse response) {
       return "/jsp/invitation";
    }


}
