package com.dl.service.impl;

import com.dl.dao.UserDao;
import com.dl.dao.impl.UserDaoImpl;
import com.dl.domain.PageBean;
import com.dl.domain.User;
import com.dl.service.UserService;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public User login(User user) {
        return dao.findUserByUsernameAndPassword(user.getUsername(),user.getPassword());
    }

    @Override
    public void addUser(User user) {
        dao.add(user);
    }

    @Override
    public void deleteUser(String id) {
        dao.delete(Integer.parseInt(id));
    }

    @Override
    public void updateUser(User user) {
        dao.update(user);
    }

    @Override
    public User findUserById(String id) {
        return dao.findById(Integer.parseInt(id));
    }

    @Override
    public void delSelectedUser(String[] ids) {
        /**/
        if(ids != null && ids.length>0){
            for (String id : ids) {
                dao.delete(Integer.parseInt(id));
            }
        }
    }

    @Override
    public PageBean<User> findUserByPage(String _currentPage, String _rows, Map<String, String[]> condition) {
        int currentPage = Integer.parseInt(_currentPage);
        int rows = Integer.parseInt(_rows);
        if(currentPage<=0){
            currentPage = 1;
        }
        //创建空的user对象

        PageBean<User> pb = new PageBean<User>();
        //设置参数
        pb.setCurrentPage(currentPage);

        pb.setRows(rows);
        //调用dao查询总记录数
        int totalCount = dao.findTotalCount(condition);
        pb.setTotalCount(totalCount);

        int start = (currentPage - 1) * rows;
        //调用list查询
        List<User> list = dao.findByPage(start, rows, condition);

        pb.setList(list);
        //计算总页数
        int totalPage = (totalCount % rows) == 0 ? totalCount/rows : (totalCount/rows) +1;
        pb.setTotalPage(totalPage);

        return pb;
    }
}
