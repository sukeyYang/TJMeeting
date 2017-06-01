package com.web.controller;

import java.util.*;

import com.utils.JxlUtil;
import com.web.entity.Schedule;
import com.web.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sukey on 2016/5/1.
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private SurveyService surveyService;

    @RequestMapping("test")
    @ResponseBody
    public String test(){
        return "test";
    }

    @RequestMapping("/import")
    public void importData(){
        List<Schedule> scheduleList=surveyService.findSchedule();
        List<Map<String,String>> answerList=new ArrayList<Map<String, String>>();
        Map<String,String> question=surveyService.getQuestion();
        for(Schedule schedule :scheduleList){
            Map<String,String> ansMap= surveyService.findAnswerByOpenid(schedule.getOpenid(),question);
            answerList.add(ansMap);
        }
        JxlUtil.writeExcel("import.xls",question,answerList);
    }




}
