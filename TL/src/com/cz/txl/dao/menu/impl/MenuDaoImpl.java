package com.cz.txl.dao.menu.impl;

import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.db.JdbcUtil;
import com.cz.txl.model.Menu;

public class MenuDaoImpl implements MenuDAO{

	//����˽��ȫ�ֱ���dataSource
	private DataSource dataSource = JdbcUtil.getDataSource();
	
	@Override
	public void insert(Menu menu) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		
		String sql = "INSERT INTO t_menu(menu_no,menu_name,parent_id,action,sort_no,menu_desc) "
				+ " VALUES(?,?,?,?,?,?)";
		Object[] params = {menu.getMenuNo(),menu.getMenuName(),menu.getParentId()
				,menu.getAction(),menu.getSortNo(),menu.getMenuDesc()};		
		qr.update(sql, params);//ִ�в���
	}

	@Override
	public void update(Menu menu) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
		String sql = "UPDATE t_menu set menu_no=?,menu_name=?,action=?,"
				+" parent_id=?,sort_no=?,menu_desc=? where id=?";
		Object[] params = {menu.getMenuNo(),
							menu.getMenuName(),
							menu.getAction(),
							menu.getParentId(),
							menu.getSortNo(),
							menu.getMenuDesc(),
							menu.getId()};
		qr.update(sql,params);//ִ���޸�
	}

	@Override
	public void delete(int menuid) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Menu queryById(int menuid) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> queryAll() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select id,menu_no as menuNo,menu_name as menuName "
        		+ ",menu_desc as menuDesc,action,sort_no as sortNo,parent_id as parentId from t_menu";
        List<Menu> menuList = 
        		(List<Menu>) qr.query(sql, new BeanListHandler<>(Menu.class));
        
		return menuList;
	}

	@Override
	public Menu queryByName(String menuname) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> queryPage(int start, int pageSize) throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select id,menu_no as menuNo,menu_name as menuName "
        		+ ",menu_desc as menuDesc,action,sort_no as sortNo,parent_id as parentId from t_menu "
        		+ " limit ?,?";
        Object[] params = {start,pageSize};
        List<Menu> menuList = 
        		(List<Menu>) qr.query(sql,params, new BeanListHandler<>(Menu.class));
        
		return menuList;
	}

	@Override
	public int queryTotal() throws Exception {
		QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select count(*) from t_menu";  //[13]  list[13]
        int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
        return count;
	}

	@Override
	public void deleteBatch(String[] menuids) throws Exception {
		QueryRunner qr = new QueryRunner(JdbcUtil.getDataSource());
        String sql = "DELETE FROM t_menu where id=?";
        Object params[][] = new Object[menuids.length][];
        for (int i = 0; i < menuids.length; i++) {
            params[i] = new Object[] {menuids[i]};
        }
        qr.batch(sql, params);
	}

}
