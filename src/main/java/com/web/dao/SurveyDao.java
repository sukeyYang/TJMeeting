package com.web.dao;

import java.util.List;

import com.common.BaseDao;
import com.utils.StringUtil;
import com.web.entity.Answer;
import com.web.entity.Schedule;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by sukey on 2016/5/20.
 */

@Repository
public class SurveyDao extends BaseDao {

    public Schedule findScheduleByOpenid(String openid) {
        StringBuffer sql = new StringBuffer();
        sql.append("FROM Schedule where openid='").append(openid).append("'");
        Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
        List<Schedule> list = query.list();
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<Schedule> findSchedule() {
        StringBuffer sql = new StringBuffer();
        sql.append("FROM Schedule where 1=1");
        Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
        List<Schedule> list = query.list();
        return list;
    }


    public Answer findAnswerByOpenid(String openid, String question) {
        StringBuffer sql = new StringBuffer();
        sql.append("FROM Answer where openid='");
        sql.append(openid);
        sql.append("' and question='");
        sql.append(question);
        sql.append("'");
        Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
        List<Answer> list = query.list();
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    public List<Answer> findAnswerByOpenid(String openid) {
        StringBuffer sql = new StringBuffer();
        sql.append("FROM Answer where openid='");
        sql.append(openid);
        sql.append("'");

        Query query = sessionFactory.getCurrentSession().createQuery(sql.toString());
        List<Answer> list = query.list();
        return list;
    }

    public int countCollege(String college) {
        StringBuffer sql = new StringBuffer();
        sql.append("select count(*) from schedule t LEFT JOIN answer r on t.openid=r.openid where t.money is not null and r.answer='");
        sql.append(college);
        sql.append("'");
        Query query = sessionFactory.getCurrentSession().createSQLQuery(sql.toString());
        int count = Integer.parseInt(StringUtil.safeToString(query.uniqueResult(), "0"));
        return count;
    }
}
