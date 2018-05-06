package xysoft.im.db.service;

import xysoft.im.db.dao.CurrentUserDao;
import xysoft.im.db.model.CurrentUser;
import xysoft.im.utils.DbUtils;
import org.apache.ibatis.session.SqlSession;
import org.sqlite.core.DB;

import java.util.ArrayList;
import java.util.List;


public class CurrentUserService extends BasicService<CurrentUserDao, CurrentUser>
{
    public CurrentUserService(SqlSession session)
    {
        dao = new CurrentUserDao(session);
        super.setDao(dao);
    }

    public int insertOrUpdate(CurrentUser currentUser)
    {
        if (exist(currentUser.getUserId()))
        {
            return update(currentUser);
        }else
        {
            return insert(currentUser);
        }
    }

    /*@Override
    public List<CurrentUser> findAll()
    {
        // TODO: 从数据库获取当前登录用户
        List<CurrentUser> list = new ArrayList();
        list.add(new CurrentUser("Ni7bJcX3W8yExKSa3", "song", "", "", "", "", "song", "", ""));
        return list;
    }*/
}