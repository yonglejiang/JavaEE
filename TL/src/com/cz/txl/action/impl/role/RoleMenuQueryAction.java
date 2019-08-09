package com.cz.txl.action.impl.role;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cz.txl.action.Action;
import com.cz.txl.dao.DaoFactory;
import com.cz.txl.dao.menu.MenuDAO;
import com.cz.txl.dao.role.RoleDAO;
import com.cz.txl.model.Menu;
import com.cz.txl.model.MenuTree;
import com.cz.txl.util.ResponseUtil;

import net.sf.json.JSONArray;

//���ݴ���Ľ�ɫid,
//��̬���ɸý�ɫ����Ӧ��Ȩ���б����β˵�
public class RoleMenuQueryAction implements Action {
	Log log = LogFactory.getLog(RoleMenuQueryAction.class);
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		//��һ��,��ô���Ľ�ɫid
		String roleid = request.getParameter("id");
		//��ѯ��Ӧid�Ľ�ɫ���߱��Ĳ˵�Ȩ���б�
		RoleDAO roleDao = DaoFactory.getRoleDao();
		List<Integer> menuNos = null;
		try {
			menuNos = (List<Integer>)roleDao.queryMenus(
					Integer.parseInt(roleid));
		} catch (Exception e) {
			log.error("��ѯȨ���б��쳣"+e.getMessage());
		}
//		System.out.println(menuNos+"--------=====");
		
		//�ڶ���,��ѯĿǰϵͳ�е����в˵��γ�һ��list
		MenuDAO menuDao = DaoFactory.getMenuDao();
		List<Menu> menuList = null;
		
		try {
			menuList = menuDao.queryAll();
		} catch (Exception e) {
			log.error("��ѯ���в˵��б����"+e.getMessage());
		}
		
		
//		JSONObject result = new JSONObject();
		
		List<MenuTree> menuTreeList = new ArrayList<MenuTree>();
		
		//��menuList ����tree-datajson��ʽ����ת��ΪmenuTreeList
//		��Ҫת�����ӹ�ϵ
		//������Ȩ��
		MenuTree mt_zuzong = new MenuTree();
		
		for (int i = 0;i<menuList.size();i++) {
			Menu temp = menuList.get(i);
			//==1 ��ʾ������Ȩ��������ڵ�
			if (temp.getMenuNo() == 1) {
				mt_zuzong.setId(temp.getMenuNo());
				mt_zuzong.setText(temp.getMenuName());
				//�жϸ��ڵ�Ҳ��������Ȩ��,�Ƿ��ڵ�ǰ
				//Ҫ�༭�������ɫ��Ȩ���б���
				if (menuNos.contains(temp.getMenuNo())) {
					mt_zuzong.setChecked(true);
				}
				
				mt_zuzong.setState("open");
				break;
			}
		}
		
		//��һ���˵��б�
		List<MenuTree> menuTreeList_Die = new ArrayList<MenuTree>();
		
		for (int i = 0;i<menuList.size();i++) {
			Menu temp = menuList.get(i);
			if (temp.getParentId() == 0 && temp.getMenuNo()!=1) {
				MenuTree mt_die = new MenuTree();
				mt_die.setId(temp.getMenuNo());
				//��������˵�����ڵ�ǰҪ�޸ĵ������ɫ�Ĳ˵��б���
				//��ֵΪѡ��
				if (menuNos.contains(temp.getMenuNo())) {
					mt_die.setChecked(true);
				}
				
				mt_die.setText(temp.getMenuName());
				mt_die.setState("open");
				menuTreeList_Die.add(mt_die);
			}
		}
		//��һ���˵��б���ֵΪ���ڵ������
		mt_zuzong.setChildren(menuTreeList_Die);
		
		//������,����������˵�
		for (int i = 0;i<menuTreeList_Die.size();i++) {
			
			MenuTree mt_die = menuTreeList_Die.get(i);
			//tempList���������ڵ�
			List<MenuTree> tempList = new ArrayList<>();
			for (int j = 0;j<menuList.size();j++) {
				Menu menu_sun = menuList.get(j);
				if (mt_die.getId() == menu_sun.getParentId()) {
					MenuTree mt_sun = new MenuTree();
					mt_sun.setId(menu_sun.getMenuNo());
					mt_sun.setText(menu_sun.getMenuName());
					
					//��������˵��ڵ�ǰҪ�޸ĵ������ɫ�Ĳ˵��б���
					//����ֵΪѡ��
					if (menuNos.contains(menu_sun.getMenuNo())) {
						mt_sun.setChecked(true);
					}
					
					tempList.add(mt_sun);
				}
			}
			
			//�������ڵ�tempList��ʼ�������ļ��ڵ�
			for (int k=0;k<tempList.size();k++) {
				MenuTree mt_sun = tempList.get(k);
				
				List<MenuTree> siji = new ArrayList<>();
				for (int j = 0;j<menuList.size();j++) {
					Menu menu_chongsun = menuList.get(j);
					if (mt_sun.getId() == menu_chongsun.getParentId()) {
						MenuTree mt_chongsun = new MenuTree();
						mt_chongsun.setId(menu_chongsun.getMenuNo());
						mt_chongsun.setText(menu_chongsun.getMenuName());
						
						//����ļ��˵��ڵ�ǰҪ�޸ĵ������ɫ�Ĳ˵��б���
						//����ֵΪѡ��
						if (menuNos.contains(menu_chongsun.getMenuNo())) {
							mt_chongsun.setChecked(true);
						}
						
						siji.add(mt_chongsun);
					}
				}
				//��ÿһ�������˵���ֵ����
				mt_sun.setChildren(siji);
			}
			//��ÿһ�������˵���������
			mt_die.setChildren(tempList);
		}
		
		//�����ڵ���ӵ�menuTreeList��
		menuTreeList.add(mt_zuzong);
		
		JSONArray jsonArray = JSONArray.fromObject(menuTreeList);
		System.out.println(jsonArray);
		//ֱ�ӽ�json�������
		try {
			ResponseUtil.write(response, jsonArray);
		} catch (Exception e) {
			log.error("��������쳣");
		}
		
		return null;
	}

}
