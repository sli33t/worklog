package cn.linbin.worklog.utils;

import java.security.MessageDigest;
import java.util.Set;

import org.springframework.util.StringUtils;

public class DBUtil {
	
	/**
	 * 拼接字符串
	 * @param s
	 * @param appendStr
	 * @param split
	 * @return
	 */
	public static String StringAdd(String s, String appendStr, String split) {
		if (!StringUtils.isEmpty(appendStr)) {
			if ("".equals(s)) {
				s = appendStr;
			} else {
				if (!StringUtils.isEmpty(split)) {
					s = s + split;
				}
				s = s + appendStr;
			}
		}
		return s;
	}

	public static String StringAdd(String s, String appendStr) {
		return StringAdd(s, appendStr, ",");
	}
	
	/**
	 * 将LbMap转化为SQL语句
	 * @param table
	 * @param qsMap
	 * @return
	 */
	public static LbMap insertSql(String table, LbMap qsMap) {
//		LbMap paramMap = new LbMap();
		Set<String> keySet = qsMap.keySet();
		String keys = "";
		String vals = "";

		// 遍历Key值
		Object[] objects = new Object[keySet.size()];
		int i=0;
		for (String key : keySet) {
			Object val = qsMap.get(key);
			keys = StringAdd(keys, key);
//			vals = StringAdd(vals, ":" + key);
			vals = StringAdd(vals, "?");
//			paramMap.put(key, val, false);
			
			if (val!=null&&!val.equals("")){
				objects[i] = val;				
			}else {
				objects[i] = "NULL";
			}
			i++;
		}
		String sql = "insert into %s (%s) values (%s)";
		
		LbMap resultMap = new LbMap();
		resultMap.put("sql", String.format(sql, table, keys, vals)); //SQL语句
//		resultMap.put("param", paramMap); //参数
		resultMap.put("param", objects);
		return resultMap;
	}
	
	/**
	 * 判断字段是否存在
	 * @return
	 */
	public static String fieldExists(String tableName, String fieldName){
		return "SHOW COLUMNS FROM "+tableName+" LIKE '"+fieldName+"'";
	}
	
	
	/**
	 * 增加Varchar字段
	 * @param tableName
	 * @param fieldName
	 * @param len
	 * @param comment
	 * @param notNull
	 * @return
	 */
	public static String addFieldVarchar(String tableName, String fieldName, int len, String comment, boolean notNull){
		String nullString = "";
		if (notNull){
			nullString = "NOT NULL";
		}
		return "ALTER TABLE "+tableName+" ADD "+fieldName+" VARCHAR("+len+") "+nullString+" COMMENT '"+comment+"'";
	}
	
	/**
	 * 增加int字段
	 * @param tableName
	 * @param fieldName
	 * @param comment
	 * @param notNull
	 * @return
	 */
	public static String addFieldInteger(String tableName, String fieldName, String comment, boolean notNull){
		String nullString = "";
		if (notNull){
			nullString = "NOT NULL";
		}
		return "ALTER TABLE "+tableName+" ADD "+fieldName+" SMALLINT "+nullString+" COMMENT '"+comment+"'";
	}
	
	/**
	 * 增加日期字段
	 * @param tableName
	 * @param fieldName
	 * @param comment
	 * @param notNull
	 * @return
	 */
	public static String addFieldDate(String tableName, String fieldName, String comment, boolean notNull){
		String nullString = "";
		if (notNull){
			nullString = "NOT NULL";
		}
		return "ALTER TABLE "+tableName+" ADD "+fieldName+" DATETIME "+nullString+" COMMENT '"+comment+"'";
	}
	
	/**
	 * 增加decimal字段
	 * @param tableName
	 * @param fieldName
	 * @param comment
	 * @param notNull
	 * @return
	 */
	public static String addFieldDecimal(String tableName, String fieldName, String comment, boolean notNull){
		String nullString = "";
		if (notNull){
			nullString = "NOT NULL";
		}
		return "ALTER TABLE "+tableName+" ADD "+fieldName+" DECIMAL(18,4) "+nullString+" COMMENT '"+comment+"'";
	}
	
	/**
	 * 增加text字段
	 * @param tableName
	 * @param fieldName
	 * @param comment
	 * @param notNull
	 * @return
	 */
	public static String addFieldText(String tableName, String fieldName, String comment, boolean notNull){
		String nullString = "";
		if (notNull){
			nullString = "NOT NULL";
		}
		return "ALTER TABLE "+tableName+" ADD "+fieldName+" TEXT "+nullString+" COMMENT '"+comment+"'";
	}
	
	
	
	public static String md5(String str){
		return md5(str, true);
	}
	
	public static String md5(String str, boolean upper) {
		if (str == null) {
			return null;
		}
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(str.getBytes("utf-8"));
			byte[] digest = messageDigest.digest();
			
			int i;
            StringBuilder sb = new StringBuilder();
            for (int offset = 0; offset < digest.length; offset++) {
                i = digest[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    sb.append(0);
                sb.append(Integer.toHexString(i));//通过Integer.toHexString方法把值变为16进制
            }
            if (upper){
            	return sb.toString().toUpperCase();            	
            }else {
				return sb.toString().toLowerCase();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void main(String[] args) {
		String md5 = DBUtil.md5("123123123123123", true);
		System.out.println(md5);
	}
	
}
