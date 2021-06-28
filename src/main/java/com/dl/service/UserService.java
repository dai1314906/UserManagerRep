package com.dl.service;

import com.dl.domain.PageBean;
import com.dl.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 实现业务接口
 */
public interface UserService {
    /**
     * 查询所有用户信息
     * @return
     */

    public List<User> findAll();

    /**
     * 登录方法
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 保存用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据id删除用户
     * @param id
     */
    void deleteUser(String id);

    /**
     * 根据id查找用户
     * @param id
     * @return
     */
    User findUserById(String id);

    /**
     * 修改用户信息
     * @param user
     */
    void updateUser(User user);

    /**
     * 批量删除用户信息
     *
     */
    void delSelectedUser(String[] ids);

    PageBean<User> findUserByPage(String currentPage, String rows, Map<String,String[]> condition);

}
