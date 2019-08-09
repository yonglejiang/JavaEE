package com.cz.txl.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtil {
	private static Log log = LogFactory.getLog(JdbcUtil.class);
	
	private static ComboPooledDataSource dataSource;  
	//�����̹߳����������ʵ��: ͬһ���̻߳�ȡ��������ͬһ��
    private static ThreadLocal<Connection> t = new ThreadLocal<Connection>();

    static{
        try {
        	dataSource=new ComboPooledDataSource();//ʹ��Ĭ�������������Դ
//            pool=new ComboPooledDataSource("czjy");���ʹ�õ������ļ����name-config
        } catch (Exception e) {
        	log.error("C3P0����Դ��ʼ������"+e.getMessage());
        }
    }

    public static Connection getConn() {
        //�ȴ�t���ã�����о��ó�ȥ�����û���ٵ��������ҰѸö���ŵ�t��
        Connection conn = t.get();
        if(conn==null){
            try {
                conn=dataSource.getConnection();
                t.set(conn); //�ŵ�t��
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
        log.info("��ȡһ������:"+conn);
        return conn;
    }
    
//  
    public static ComboPooledDataSource getDataSource(){  
        return dataSource;  
    }  
  
    // �ر�����  
    public static void close(Connection conn) throws SQLException {  
        if (conn != null) {  
            try {  
                conn.close();  
            } catch (SQLException e) {  
                e.printStackTrace();  
                throw e;  
            }  
        }  
    }  
  
    //�ر�Ԥ����
    public static void close(PreparedStatement pstate) throws SQLException {  
        if(pstate!=null){  
            pstate.close();  
        }  
    }  
    
    //�رս����
    public static void close(ResultSet rs) throws SQLException {  
        if(rs!=null){  
            rs.close();  
        }  
    }  

	
}
