package com.cz.txl.dao.user;

import java.util.List;

import com.cz.txl.model.QueryCondition;
import com.cz.txl.model.Schedule;
import com.cz.txl.model.User;

public interface UserDAO {
	public int insert(User user) throws Exception;
    public void update(User user) throws Exception;
    public void updateBase(User user) throws Exception;
    public void delete(int userid) throws Exception;
    public User queryById(int userid) throws Exception;
    public List<User> queryAll() throws Exception;
    public User queryByName(String username) throws Exception;
    public List<User> queryPage(int start,int pageSize) throws Exception;
    public int queryTotal() throws Exception;
    public void deleteBatch(String[] userids) throws Exception;
    public void activeBatch(String[] userids) throws Exception;
    
    //TODO 如下两个方式应该是一个原子事务,不应该分开写.
    public void deleteUserRole(int userId) throws Exception;
    public void addUserRole(int userId,int roleId) throws Exception;
    
    public int getRoleNo(int userId) throws Exception;
    
    public void updatePass(int userId,String password) throws Exception;
    
    public List<Schedule> queryByUserId(int userId) throws Exception;
    
    public int reg(User user) throws Exception;
    
    //根据角色编号查询管理员列表
    public List<User> queryAllAdminUser(int roleNo) throws Exception;
    
    public List<User> queryPage(String[] fiedlList,String[] fieldValue,int start,int pageSize) throws Exception;
    
    public List<User> queryPage(List<QueryCondition> qcList,int start,int pageSize) throws Exception;
}
