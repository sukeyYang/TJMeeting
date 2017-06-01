package com.web.service;

import java.util.*;

import com.web.dao.QuestionDao;
import com.web.dao.SurveyDao;
import com.web.entity.Answer;
import com.web.entity.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by sukey on 2016/5/20.
 */
@Service("surveyService")
public class SurveyService {

    @Autowired
    private SurveyDao surveyDao;

    @Autowired
    private QuestionDao questionDao;


    public void saveAnwser(Map<String, String[]> anwsers, String openid) {
        List<Answer> list = new ArrayList<Answer>();
        Date date = new Date();
        for (String question : anwsers.keySet()) {
            String[] choose=anwsers.get(question);
            if (choose.length > 1) {
                for (int i = 0; i < choose.length; i++) {
                    Answer answer = new Answer();
                    answer.setOpenid(openid);
                    answer.setAnswer(anwsers.get(question)[i]);
                    answer.setQuestion(question + "_" + (i+1));
                    answer.setCreatetime(date);
                    list.add(answer);
                }
            } else {
                Answer answer = new Answer();
                answer.setOpenid(openid);
                answer.setAnswer(anwsers.get(question)[0]);
                answer.setQuestion(question);
                answer.setCreatetime(date);
                list.add(answer);
            }

        }
        surveyDao.saveBatch(list);
    }


    public void saveSchedule(Schedule schedule) {
        surveyDao.save(schedule);
    }

    public Schedule findScheduleByOpenid(String openid) {
        Schedule schedule = surveyDao.findScheduleByOpenid(openid);
        return schedule;
    }

    public Answer findAnswerByOpenid(String openid, String question) {
        Answer answer = surveyDao.findAnswerByOpenid(openid, question);
        return answer;
    }

    public int  countCollege(String college) {
        return surveyDao.countCollege(college);
    }

    public Map<String,String> getQuestion() {
        List<Map> list=questionDao.getQuestion();
        Map<String,String> question=new TreeMap<String, String>();
        for(Map<String,String> map:list){

            question.put(map.get("qid"),map.get("question"));

        }
        return question;
    }

    public Map<String,String> findAnswerByOpenid(String openid,Map<String,String> qsType ) {
        List<Answer> ans_list= surveyDao.findAnswerByOpenid(openid);
        Map<String,String> answer=new TreeMap<String, String>();
        for(Answer ans: ans_list){
            if(ans.getQuestion().equals("part"))
                continue;
            if(qsType.containsKey(ans.getQuestion())){
                answer.put(ans.getQuestion(),ans.getAnswer());
            }else{
                String qid=ans.getQuestion().substring(0,ans.getQuestion().lastIndexOf("_"));
                if(answer.get(qid)==null){
                    answer.put(qid,ans.getAnswer());
                } else{
                    String asw=answer.get(qid) + "|"+ans.getAnswer();
                    answer.put(qid,asw);
                }

            }

        }
        return  answer;
    }

    public List<Schedule> findSchedule() {
        List<Schedule> list=surveyDao.findSchedule();

        return list;
    }
}
