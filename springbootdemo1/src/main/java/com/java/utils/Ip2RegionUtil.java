package com.java.utils;

import org.apache.commons.io.FileUtils;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;
import org.lionsoul.ip2region.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
/**
 * @author
 * @Date 2020/4/24 上午10:18
 * ip城市定位util
 */
public class Ip2RegionUtil {

    private static final Logger logger = LoggerFactory.getLogger(Ip2RegionUtil.class);
    private static final String defaultCityName = "北京";
    public static String getIpInfo(String ip, int algorithm) {

        // 读取本地的ip2region.db文件
        String dbPath = Ip2RegionUtil.class.getResource( "/ip2region.db").getPath();
        logger.info("Ip2RegionUtil.local.dbPath: {}", dbPath);
        File file = new File(dbPath);

        // 由于打成jar包后，ip2region.db文件路径发生变化，导致File无法读取，故通过stream流方式复制生成临时的ip2region.db的文件
        if (file.exists() == false) {
            String tmpDir = System.getProperties().getProperty("java.io.tmpdir");
            dbPath = tmpDir + File.separator + "ip2region.db";
            logger.info("Ip2RegionUtil.temp.dbPath: {}", dbPath);
            file = new File(dbPath);
            if (file.exists() == false) {
                try {
                    FileUtils.copyInputStreamToFile(Ip2RegionUtil.class.getClassLoader()
                            .getResourceAsStream("classpath:ip2region.db"), file);
                    logger.info("Ip2RegionUtil.copyInputStreamToFile");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 查询算法B-tree, Binary, Memory
        try {
            DbConfig config = new DbConfig();
            DbSearcher searcher = new DbSearcher(config, dbPath);

            // define the method
            Method method = null;
            switch (algorithm) {
                case DbSearcher.BTREE_ALGORITHM:
                    method = searcher.getClass().getMethod("btreeSearch", String.class);
                    break;
                case DbSearcher.BINARY_ALGORITHM:
                    method = searcher.getClass().getMethod("binarySearch", String.class);
                    break;
                case DbSearcher.MEMORY_ALGORITYM:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
                default:
                    method = searcher.getClass().getMethod("memorySearch", String.class);
                    break;
            }

            DataBlock dataBlock = null;
            if (Util.isIpAddress(ip) == false) {
                logger.info("Ip2RegionUtil.getCityInfo.error: Invalid ip address");
            }

            dataBlock = (DataBlock) method.invoke(searcher, ip);
            searcher.close(); // 关闭文件流
            String cityIpString = dataBlock.getRegion();
            logger.info("Ip2RegionUtil.getCityInfo.cityIpString is {}", cityIpString);
            // 获取城市的信息
            return cityIpString;
        } catch (Exception e) {
            logger.error("Ip2RegionUtil.getCityInfo.error: {}", e);
        }
        return defaultCityName;
    }
    public static void main(String[] args) throws Exception {
        String ip = "103.85.173.226";
        String ipString = getIpInfo(ip, 1);
        System.out.println(ipString);
        if (StringUtil.isNotEmptyAndNotNull(ipString)){
            String strs=ipString.split("\\|")[0];
                System.out.println(strs);
        }

    }
}
