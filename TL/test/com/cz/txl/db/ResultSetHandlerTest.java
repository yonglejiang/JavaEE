package com.cz.txl.db;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.commons.dbutils.handlers.ArrayListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.KeyedHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import junit.framework.TestCase;

/**
 * ResultSetHandler�ĸ���ʵ���ࣺ
	ArrayHandler���ѽ�����еĵ�һ������ת�ɶ������顣
	ArrayListHandler���ѽ�����е�ÿһ�����ݶ�ת��һ���������飬�ٴ�ŵ�List�С�
	BeanHandler����������еĵ�һ�����ݷ�װ��һ����Ӧ��JavaBeanʵ���С�
	BeanListHandler����������е�ÿһ�����ݶ���װ��һ����Ӧ��JavaBeanʵ���У���ŵ�List�//�ص�
	MapHandler����������еĵ�һ�����ݷ�װ��һ��Map�key��������value���Ƕ�Ӧ��ֵ��//�ص�
	MapListHandler����������е�ÿһ�����ݶ���װ��һ��Map�Ȼ���ٴ�ŵ�List
	ColumnListHandler�����������ĳһ�е����ݴ�ŵ�List�С�
	KeyedHandler(name)����������е�ÿһ�����ݶ���װ��һ��Map��(List<Map>)���ٰ���Щmap�ٴ浽һ��map���keyΪָ�����С�
 */
public class ResultSetHandlerTest extends TestCase{
	
private ComboPooledDataSource dataSource;
	
    @Override
	protected void setUp() throws Exception {
		super.setUp();
		dataSource = JdbcUtil.getDataSource();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		dataSource.close();
	}
 
    public void testArrayHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        Object result[] = (Object[]) qr.query(sql, new ArrayHandler());
        System.out.println(Arrays.asList(result));  //list  toString()
    }
    
    
    public void testArrayListHandler() throws SQLException{
        
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List<Object[]> list = (List) qr.query(sql, new ArrayListHandler());
        for(Object[] o : list){
            System.out.println(Arrays.asList(o));
        }
    }
    
    
    public void testColumnListHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List list = (List) qr.query(sql, new ColumnListHandler("id"));
        System.out.println(list);
    }
    
    
    public void testKeyedHandler() throws Exception{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        
        Map<Integer,Map> map = (Map) qr.query(sql, new KeyedHandler("id"));
        for(Map.Entry<Integer, Map> me : map.entrySet()){
            int  id = me.getKey();
            Map<String,Object> innermap = me.getValue();
            for(Map.Entry<String, Object> innerme : innermap.entrySet()){
                String columnName = innerme.getKey();
                Object value = innerme.getValue();
                System.out.println(columnName + "=" + value);
            }
            System.out.println("----------------");
        }
    }
    
    
    public void testMapHandler() throws SQLException{
        
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        
        Map<String,Object> map = (Map) qr.query(sql, new MapHandler());
        for(Map.Entry<String, Object> me : map.entrySet())
        {
            System.out.println(me.getKey() + "=" + me.getValue());
        }
    }
    
    
    
    public void testMapListHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select * from t_user";
        List<Map> list = (List) qr.query(sql, new MapListHandler());
        for(Map<String,Object> map :list){
            for(Map.Entry<String, Object> me : map.entrySet())
            {
                System.out.println(me.getKey() + "=" + me.getValue());
            }
        }
    }
    
    
    public void testScalarHandler() throws SQLException{
        QueryRunner qr = new QueryRunner(dataSource);
        String sql = "select count(*) from t_user";  //[13]  list[13]
        int count = ((Long)qr.query(sql, new ScalarHandler(1))).intValue();
        System.out.println(count);
    }

}
