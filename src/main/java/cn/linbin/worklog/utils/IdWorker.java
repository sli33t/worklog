package cn.linbin.worklog.utils;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import com.baomidou.mybatisplus.core.toolkit.Sequence;
import com.baomidou.mybatisplus.core.toolkit.StringPool;

/**
 * 生成ID的工具类，通过mybatis plus抽取
 * @author linbin
 *
 */
public class IdWorker {

	/**
     * 主机和进程的机器码
     */
    private static Sequence WORKER = new Sequence();

    /**
     * 毫秒格式化时间
     */
    public static final DateTimeFormatter MILLISECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    
    /**
     * 秒格式化时间
     */
    public static final DateTimeFormatter SECOND = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    public static long getId() {
    	long nextId = WORKER.nextId();
    	if (nextId<=0){
    		//如果生成为0，则再生成一个，并增加一个1到3的随机数
    		nextId = WORKER.nextId() + ThreadLocalRandom.current().nextLong(1, 3); 
    	}
        return nextId;
    }
    
    
    /**
     * 16位随机带大小写字母
     * @return
     */
    public static String getId16(){
        StringBuilder sb = new StringBuilder();
        //产生16位的强随机数
        Random rd = new SecureRandom();
        for (int i = 0; i < 16; i++) {
            //产生0-2的3位随机数
            int type = rd.nextInt(3);
            switch (type){
                case 0:
                    //0-9的随机数
                	sb.append(rd.nextInt(10));
                    break;
                case 1:
                    //ASCII在65-90之间为大写,获取大写随机
                	sb.append((char)(rd.nextInt(25)+65));
                    break;
                case 2:
                    //ASCII在97-122之间为小写，获取小写随机
                	sb.append((char)(rd.nextInt(25)+97));
                    break;
                default:
                    break;
            }
        }
        
        return sb.toString();
    }
    
    
    /**
     * 18位数字随机
     * @return
     */
    public static String getNumId18(){
    	String current = String.valueOf(ThreadLocalRandom.current().nextLong(1, 9999));
    	switch (current.length()) {
			case 4:
				break;
			case 3:
				current = current + String.valueOf(ThreadLocalRandom.current().nextLong(1, 9));
				break;
			case 2:
				current = String.valueOf(ThreadLocalRandom.current().nextLong(1, 9)) + 
						current + String.valueOf(ThreadLocalRandom.current().nextLong(1, 9));
				break;
			case 1:
				current = String.valueOf(ThreadLocalRandom.current().nextLong(1, 9)) + 
						String.valueOf(ThreadLocalRandom.current().nextLong(1, 9)) + current + 
						String.valueOf(ThreadLocalRandom.current().nextLong(1, 9));
				break;
			default:
                break;
		}
    	
    	return getSecond()+current;
    }
    

    public static String getIdStr() {
        //return String.valueOf(WORKER.nextId());
    	return String.valueOf(getId());
    }

    /**
     * 格式化的毫秒时间
     */
    public static String getMillisecond() {
        return LocalDateTime.now().format(MILLISECOND);
    }
    
    public static String getSecond() {
        return LocalDateTime.now().format(SECOND);
    }

    /**
     * 时间 ID = Time + ID
     * <p>例如：可用于商品订单 ID</p>
     */
    public static String getTimeId() {
        return getMillisecond() + getId();
    }

    /**
     * 有参构造器
     *
     * @param workerId     工作机器 ID
     * @param datacenterId 序列号
     */
    public static void initSequence(long workerId, long datacenterId) {
        WORKER = new Sequence(workerId, datacenterId);
    }

    /**
     * 使用ThreadLocalRandom获取UUID获取更优的效果 去掉"-"
     */
    public static String get32UUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return new UUID(random.nextLong(), random.nextLong()).toString().replace(StringPool.DASH, StringPool.EMPTY);
    }
    
    public static void main(String[] args) {
    	for (int i = 0; i <= 100; i++) {
    		System.out.println(IdWorker.getNumId18());
		}
	}
    
}
