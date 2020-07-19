package cn.linbin.worklog.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.beans.BeanMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.StringUtils;

/**
 * 自定义Map集合，以求实Qs开头
 * 
 * @author linbin
 */
public class LbMap extends LinkedHashMap<String, Object> implements Comparator<LbMap> {

	private static final long serialVersionUID = 1L;
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(LbMap.class);

	/**
	 * 构造方法
	 */
	public LbMap() {

	}
	
	/**
	 * 获取对应Key的Stirng值
	 * 
	 * @param key
	 * @return
	 */
	public String getString(String key) {
		Object obj = get(key);
		return obj == null ? "" : obj.toString();
	}

	/**
	 * 获取对应Key的Integer值
	 * 
	 * @param key
	 * @return
	 */
	public int getInt(String key) {
		Object o = get(key);
		if (o != null && !o.equals("")) {
			if (o instanceof Number){
				return ((Number) o).intValue();
			}else {
				return (int) getDouble(key);
			}
		} else {
			return 0;
		}
	}

	/**
	 * 获取对应Key的Double值
	 * 
	 * @param key
	 * @return
	 */
	public double getDouble(String key) {
		Object o = get(key);
		String msg = "LbMap[" + key + "] is not a number. value = [" + o + "]";
		if (o != null) {
			try {
				if (o instanceof Number){
					return ((Number) o).doubleValue();
				}else {
					return Double.parseDouble((String) o);
				}
			} catch (Exception e) {
				logger.error(msg);
				//throw new NestableRuntimeException(msg);
				return 0;
			}
		}else {
			logger.error(msg);
			return 0;
		}
	}
	
	public long getLong(String key){
		Object o = get(key);
		String msg = "LbMap[" + key + "] is not a long. value = [" + o + "]";
		if (o!=null){
			return (long) getDouble(key);
		}else {
			logger.error(msg);
			return 0;			
		}
	}

	/**
	 * 获取对应Key的boolean值
	 * 
	 * @param key
	 * @return
	 */
	public boolean getBoolean(String key) {
		Object obj = get(key);
		return obj == null ? false : Boolean.valueOf(obj.toString());
	}
	
	/**
	 * 通过json转化为LbMap
	 * 
	 * @param objString
	 * @return
	 */
	public static LbMap fromObject(String objString) {
		if (StringUtils.isEmpty(objString)) {
			return new LbMap();
		} else {
			return JSONObject.parseObject(objString, LbMap.class);
		}
	}
	
	/**
	 * 将object转化为LbMap
	 * @param o
	 * @return
	 */
	public static LbMap fromObject(Object o){
		if (o==null){
			return new LbMap();
		}else {
			return JSONObject.parseObject(JSON.toJSONString(o), LbMap.class);
		}
	}

	/**
	 * 通过key获取LbMap
	 * 
	 * @param key
	 * @return
	 */
	public LbMap getLbMap(String key) {
		Object o = get(key);
		if (StringUtils.isEmpty(key)) {
			return new LbMap();
		} else if (o == null) {
			return new LbMap();
		}
		String type = o.getClass().getName();
		if (this.getClass().getName().equals(type)) {
			return (LbMap) o;
		} else if (new String().getClass().getName().equals(type)) {
			return fromObject(JSON.toJSONString(o));
		} else {
			return fromObject(JSON.toJSONString(o));
		}
	}

	/**
	 * 通过key获取List
	 * 
	 * @param key
	 * @return
	 */
	public List<LbMap> getList(String key) {
		try {
			Object o = get(key);
			if (o==null){
				return new ArrayList<LbMap>();
			}

			//先转化一下o，可能传入的是=，不是:
			String oString = JSON.toJSONString(o, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
			return JSONObject.parseArray(oString, LbMap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<LbMap>();
		}
	}

	/**
	 * 将LbMap转化为String
	 */
	public String toString() {
		return JSON.toJSONString(this, SerializerFeature.WRITE_MAP_NULL_FEATURES, SerializerFeature.QuoteFieldNames);
	}

	/**
	 * 克隆，效率低但是安全
	 */
	public LbMap clone() {
		LbMap map = new LbMap();
		for (Entry<String, Object> entry: this.entrySet()){
			map.put(entry.getKey(), entry.getValue(), false);
		}
		return map;
	}

	@Override
	public int compare(LbMap o1, LbMap o2) {
		return new Integer(o1.getClass().getName()).compareTo(new Integer(o2.getClass().getName()));
	}
	
	/**
	 * 检查值是否存在
	 */
	public boolean contains(Object value){
		return super.containsValue(value);
	}
	
	/**
	 * 检查键是否存在
	 * @param key
	 * @return
	 */
	public boolean containsKey(String key){
		return super.containsKey(key);
	}
	
	/**
	 * 将Map里的key下划线转小驼峰
	 * @return
	 */
	public LbMap underlineToCamel(){
		LbMap map = new LbMap();
		for (Entry<String, Object> entry: this.entrySet()){
			map.put(underlineToCamel(entry.getKey()), entry.getValue());
		}
		return map;
	}
	
	/**
	 * 将Map里的key小驼峰转下划线
	 * @param isUpper：true：统一都转大写；false-统一转小写
	 * @return
	 */
	public LbMap camelToUnderline(boolean isUpper){
		LbMap map = new LbMap();
		for (Entry<String, Object> entry: this.entrySet()){
			map.put(camelToUnderline(entry.getKey(), isUpper), entry.getValue());
		}
		return map;
	}
	
	/**
	 * 获取map里所有的key(重点注意所有的key都加了单引号)
	 * @param link 连接符，一般是逗号
	 * @return
	 */
	public String getKeys(String link){
		String keys = "";
		for (Entry<String, Object> entry: this.entrySet()){
			keys = keys + "'" + entry.getKey() + "'" + link; 
		}
		
		//去掉最后一个逗号
		keys = keys.substring(0, keys.length()-1);
		return keys;
	}
	
	/**
	 * 重写put方法，默认转化小驼峰命名
	 * 
	 * @param key
	 * @param value
	 */
	public Object put(String key, Object value){
		return this.put(key, value, true);
	}
	
	
	/**
	 * 重写put方法，转化小驼峰命名
	 * @param key
	 * @param value
	 * @param toCamel：true:将key转化为小驼峰，false:不转化
	 * @return
	 */
	public Object put(String key, Object value, boolean toCamel){
		String newKey;
		if (toCamel){
			newKey = underlineToCamel(key);	
		}else {
			newKey = key;
		}
		
		/*if (this.containsKey(newKey)&&this.containsValue(value)){
			return null;
		}*/
		
		/*if (value==null){
			value = "";
		}*/
		
		return super.put(newKey, value);
	}
	
	/**
	 * 下划线转小驼峰
	 * @param param 
	 * @return
	 */
	public static String underlineToCamel(String param) {
		char UNDERLINE = '_';
		
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        
        int len = param.length();

        //不包含下划线的，需要检查并转化为小写
        if (!param.contains("_")){
        	boolean isUpper = false;
        	boolean isLow = false;
        	for (int i = 0; i < len; i++) {
        		char c = param.charAt(i);
        		if (Character.isLowerCase(c)){
        			isLow = true; //检查到小写
        		}else
        		if (Character.isUpperCase(c)){
        			isUpper = true; //检查到大写
        		}else if (Character.isDigit(c)) {
					isLow = true; //检查到数字就是小写
				}
        	}
        	
        	//全部是大写的，需要转化为小写
        	if (isUpper&&!isLow){
        		return param.toLowerCase();        		
        	}else {
        		//有大写，有小写，直接返回原值
        		return param;				
			}
        }
        
        StringBuilder sb = new StringBuilder(len);
        Boolean flag = false; // "_" 后转大写标志,默认字符前面没有"_"
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                flag = true;
                continue;   //标志设置为true,跳过
            } else {
                if (flag == true) {
                    //表示当前字符前面是"_" ,当前字符转大写
                    sb.append(Character.toUpperCase(param.charAt(i)));
                    flag = false;  //重置标识
                } else {
                    sb.append(Character.toLowerCase(param.charAt(i)));
                }
            }
        }
        return sb.toString();
    }
	
	/**
	 * 小驼峰转下划线
	 * @param param
	 * @param toUpper： true：统一都转大写；false-统一转小写
	 * @return
	 */
	public static String camelToUnderline(String param, boolean toUpper) {
		char UNDERLINE = '_';
		
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        
        int len = param.length();

        //已经包含下划线的，返回原值
        if (param.contains("_")){
        	return param;
        }
        
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
            }
            if (toUpper) {
                sb.append(Character.toUpperCase(c));  //统一都转大写
            } else {
                sb.append(Character.toLowerCase(c));  //统一都转小写
            }
        }
        return sb.toString();
    }
	
	/**
	 * 将bean转化为map
	 * @param bean
	 * @return
	 */
	public static <T> LbMap beanToMap(T bean){
		LbMap lbMap = new LbMap();
		if (bean!=null){
			BeanMap beanMap = BeanMap.create(bean);
			for (Object key : beanMap.keySet()) {
				Object o = beanMap.get(key);
				//ConcurrentHashMap 不能put null 的原因是因为：无法分辨是key没找到的null还是有key值为null值，
				//而这在多线程里面是模糊不清的，所以压根就不让put null。
				if (key!=null){
					/*if (o==null){
						logger.error(key+" value is null. ");
						o = "";
					}*/
					lbMap.put(key+"", o);
				}
            }
		}
		return lbMap;
	}
	
	public int getPageIndex(){
		Object o = get("pageIndex");
		if (o==null||o.equals("")){
			return 1; //默认第一页
		}else {
			return this.getInt("pageIndex");			
		}
	}
	
	public int getPageSize(){
		Object o = get("pageSize");
		if (o==null||o.equals("")){
			return 10000; //默认10行
		}else {
			return this.getInt("pageSize");			
		}
	}


	public static LbMap successResult(String msg){
		return successResult(msg, 0);
	}

	public static LbMap successResult(String msg, int code, int count){
		return setResult(true, code, msg, null, count);
	}

	public static LbMap successResult(String msg, int count){
		return successResult(msg, null, count);
	}

	public static LbMap successResult(String msg, Object data, int count){
		return setResult(true, 0, msg, data, count);
	}

	public static LbMap failResult(String msg){
		return setResult(false, 1, msg, null, 0);
	}

	public static LbMap setResult(boolean result, int code, String msg, Object data, int count){
		LbMap map = new LbMap();
		map.put("result", result);
		map.put("code", code);
		map.put("msg", msg);
		map.put("data", data);
		map.put("count", count);
		return map;
	}
	
	
	/**
	 * //测试map与bean的转换
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

	}
}
