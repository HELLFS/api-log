package io.github.hellfs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * HTTP相关工具类
 * @author hellfs
 * @date 2023-09-07
 */
public class HttpUtil extends LoggerObject {

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 获取body消息体中的参数列表
     * @param request   请求对象
     * @return
     */
    public static String getBodyString(HttpServletRequest request)
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try (InputStream inputStream = request.getInputStream())
        {
            reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = reader.readLine()) != null)
            {
                sb.append(line);
            }
        }
        catch (IOException e) {
            logger.warn("获取请求体参数失败，失败原因:{}",e);
        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    logger.error("释放资源失败，失败原因:{}",e);
                }
            }
        }
        return sb.toString();
    }

}
