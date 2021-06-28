package com.dl.dao.impl;

import com.dl.dao.UserDao;
import com.dl.domain.User;
import com.dl.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<User> findAll() {
        //jdbc操作数据库
        String sql = "select * from tb_user";
        List<User> users = template.query(sql, new BeanPropertyRowMapper<User>(User.class));
        return users;
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        try{
            String sql = "select * from tb_user where username = ? and password = ? ";
            User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username, password);
            return user;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void add(User user) {
        String sql = "insert into tb_user values (null,?,?,?,?,?,?,null,null)";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail());
    }

    @Override
    public void delete(int id) {
        String sql = "delete from tb_user where id = ?";
        template.update(sql,id);
    }

    @Override
    public User findById(int id) {
        String sql = "select * from tb_user where id = ?";
        return template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
    }

    @Override
    public void update(User user) {
        String sql = "update tb_user set name = ?,gender = ?,age = ?,address = ?,qq = ?,email = ? where id = ?";
        template.update(sql,user.getName(),user.getGender(),user.getAge(),user.getAddress(),user.getQq(),user.getEmail(),user.getId());
    }

    @Override
    public int findTotalCount(Map<String, String[]> condition) {
        String sql = "select count(*) from tb_user where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        //遍历map
        Set<String> keySet = condition.keySet();

        List<Object> params = new ArrayList<Object>();
        for(String key:keySet){
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;
            }
            //从前端获取当前值
            String value = condition.get(key)[0];
            //判断value是否有值
            if(value != null && !"".equals(value)){
                //添加空格
                stringBuilder.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        //打印出sql语句
        System.out.println(stringBuilder.toString());
        //
        System.out.println(params);

        return template.queryForObject(stringBuilder.toString(),Integer.class,params.toArray());
    }

    @Override
    public List<User> findByPage(int start, int rows, Map<String, String[]> condition) {
        String sql = "select * from tb_user where 1 = 1";
        StringBuilder stringBuilder = new StringBuilder(sql);
        Set<String> keySet = condition.keySet();
        List<Object> params = new ArrayList<Object>();
        for(String key:keySet){
            if("currentPage".equals(key)||"rows".equals(key)){
                continue;
            }
            String value = condition.get(key)[0];
            if(value != null && !"".equals(value)){
                stringBuilder.append(" and "+key+" like ? ");
                params.add("%"+value+"%");
            }
        }
        stringBuilder.append(" limit ?,? ");
        params.add(start);
        params.add(rows);
        sql = stringBuilder.toString();
        System.out.println(sql);
        System.out.println(params);

        return template.query(sql,new BeanPropertyRowMapper<User>(User.class),params.toArray());
    }
}
