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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by sukey on 2016/5/17.
 */
@Controller
@RequestMapping("/")
public class SurveyController {

    @Autowired
    SurveyService surveyService;

    @RequestMapping(value = "/questionnaire")
    public String questionnaire(HttpServletRequest request, HttpServletResponse response) {
        String openid = String.valueOf(request.getSession().getAttribute("openid"));
        openid = "ouohpwNJpCFhKGWiv9FZXZqT-3oUT";
        if ("null".equals(openid) || "".equals(openid) || openid == null) {
            return "redirect:/wechat/login/questionnaire";
        }
        Schedule schedule = surveyService.findScheduleByOpenid(openid);
        String step = null;
        if (schedule == null) {
            step = "0";
        } else {
            step = schedule.getStep();
        }
        int currentStep = Integer.parseInt(step) + 1;
//        if (currentStep == 3) {//判断是否回答模块5，即part-3
//            Answer answer = surveyService.findAnswerByOpenid(openid, "q15");
//            if ("1".equals(answer.getAnswer())) {//1="是",q15回答为是，跳过模块5，即part-3不回答
//                currentStep += 1;
//            }
//        }
        if (currentStep == 3 || currentStep == 5) {//判断是否回答模块5和8，即part-3 和 part-5
            Answer answer_q15 = surveyService.findAnswerByOpenid(openid, "q15");
            Answer answer_q14 = surveyService.findAnswerByOpenid(openid, "q14");
            if (!"2".equals(answer_q15.getAnswer()) || !"1".equals(answer_q14.getAnswer())) {
                //2="否",1="是"，q15回答为否，且q14回答为是，则回答part-5,否则跳过不回答
                currentStep += 1;
            }
        }
        if (currentStep == 6) {//判断是否回答模块9，即part-6
            Answer answer_q15 = surveyService.findAnswerByOpenid(openid, "q15");
            if ("2".equals(answer_q15.getAnswer()))//2="否",q15回答为否，跳过模块9，即part-6不回答
                currentStep += 1;
        }
        if (currentStep == 7) {//判断是否回答模块10，即part-7
            Answer answer_q22 = surveyService.findAnswerByOpenid(openid, "q22");
            Answer answer_q23 = surveyService.findAnswerByOpenid(openid, "q23");
            if ("1".equals(answer_q22.getAnswer()) && "1".equals(answer_q23.getAnswer())) {
                //1="不是"，q22、q23回答都为否，则跳过不回答,即跳过步骤7
                currentStep += 1;
            }
        }

        request.getSession().setAttribute("step", currentStep);
        System.out.println("step:" + currentStep);
        return "/jsp/survey-part-" + currentStep;
    }

    @RequestMapping(value = "/save_answer", method = RequestMethod.POST)
    public String save_answer(HttpServletRequest request) {
        String openid = String.valueOf(request.getSession().getAttribute("openid"));
        String step = String.valueOf(request.getSession().getAttribute("step"));
        String part = request.getParameter("part");
        if (!part.equals(step))//防止重复提交
            return "forward:questionnaire";
        Map params = request.getParameterMap();
        surveyService.saveAnwser(params, openid);
        Schedule schedule = surveyService.findScheduleByOpenid(openid);
        Date date = new Date();
        if (schedule == null) {
            String ip = IpUtil.getIpAddr(request);
            schedule = new Schedule();
            schedule.setIp(ip);
            schedule.setOpenid(openid);
            schedule.setStep(step);
            schedule.setCreatetime(date);
            schedule.setUpdatetime(date);
        } else {
            schedule.setStep(step);
            schedule.setUpdatetime(date);
        }
        surveyService.saveSchedule(schedule);
        System.out.println("sava_success");
        return "forward:questionnaire";
    }

//    @RequestMapping(value = "/redpack")
//    public String redpack(HttpServletRequest request, HttpServletResponse response) {
//        String openid = String.valueOf(request.getSession().getAttribute("openid"));
////        openid="ouohpwNJpCFhKGWiv9FZXZqT-3oU";
//        if ("null".equals(openid) || "".equals(openid) || openid == null) {
//            return "redirect:/wechat/login/redpack";
//        }
//        Schedule schedule = surveyService.findScheduleByOpenid(openid);
//        if (schedule == null)
//            return "forward:/questionnaire";//返回答题页面
//        if (schedule.getMoney() == null || "".equals(schedule.getMoney())) {
//            if ("8".equals(schedule.getStep())) {//答题结束
//                Answer answer_q6 = surveyService.findAnswerByOpenid(openid, "q6");
//                String college = answer_q6.getAnswer();
//                int collegeCount = surveyService.countCollege(college);
//                System.out.print(collegeCount);
//                if (collegeCount <= 10) {
//                    int money = SendRedPackUtil.getRandom(101, 200);
//                    try {
//                        SendRedPackUtil.sendRedPack(openid, String.valueOf(money));
//                        schedule.setMoney(String.valueOf(money));
//                        surveyService.saveSchedule(schedule);
//                        return "/jsp/redpack_success";//返回领取成功
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                        return "/jsp/redpack_failure";//返回领取失败
//                    }
//
//                }else{
//                    return "/jsp/redpack_failure";//返回领取失败
//                }
//
//            } else {
//                return "forward:/questionnaire";//返回答题页面
//            }
//        } else {
//            return "/jsp/redpack_already";//返回已领取
//        }
//    }

    @RequestMapping(value = "/redpack", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String redpack(HttpServletRequest request, HttpServletResponse response) {
        String openid = String.valueOf(request.getSession().getAttribute("openid"));
//        openid="ouohpwNJpCFhKGWiv9FZXZqT-3oU";
        if ("null".equals(openid) || "".equals(openid) || openid == null) {
            return "非法请求";
        }
        Schedule schedule = surveyService.findScheduleByOpenid(openid);
        if (schedule == null)
            return "请先完成问卷调查";//返回答题页面
        if (schedule.getMoney() == null || "".equals(schedule.getMoney())) {
            if ("8".equals(schedule.getStep())) {//答题结束
                Answer answer_q6 = surveyService.findAnswerByOpenid(openid, "q6");
                String college = answer_q6.getAnswer();
                Answer school_province = surveyService.findAnswerByOpenid(openid, "school_province");
                String ipProvince = IpUtil.GetAddressByIp(schedule.getIp());
                if (!school_province.getAnswer().equals(ipProvince))
                    return "您的答卷可信度过低";//返回答题页面
                int collegeCount = surveyService.countCollege(college);
                if (collegeCount <= 30) {
                    int money = SendRedPackUtil.getRandom(101, 110);
                    try {
                        SendRedPackUtil.sendRedPack(openid, String.valueOf(money));
                        schedule.setMoney(String.valueOf(money));
                        surveyService.saveSchedule(schedule);
                        return "红包发送成功，请注意查收";//返回领取成功
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "领取失败，请稍后尝试";//返回领取失败
                    }

                } else {
                    return "你来晚了……红包派完了……";//返回领取失败
                }

            } else {
                return "请先完成问卷调查 ";//返回答题页面
            }
        } else {
            return "您已领取过红包，不可重复领取";//返回已领取
        }
    }
}
