package com.web.dao;

import java.util.*;

import com.common.BaseDao;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by sukey on 2016/7/11.
 */
@Repository
public class QuestionDao extends BaseDao {

    public List<Map> getQuestion() {
        String sql = "select * from question";
        List<Map> list = findResult(sql, new TreeMap());
        return list;
    }


}
