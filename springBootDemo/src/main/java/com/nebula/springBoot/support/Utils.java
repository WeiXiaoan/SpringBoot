package com.nebula.springBoot.support;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.message.ParameterizedMessage;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Utils {
    /**
     * MD5加密
     * @param str	被加密的字符串
     * @return	加密后的字符串
     */
    public static String md5(String str) {
        try {
            //处理空值
            if(str == null) str = "";

            //编码
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(str.getBytes("UTF-8"));
            byte[] rst = digest.digest();

            //转换为字符串
            int len = rst.length;
            char[] cs = new char[len * 2];
            int index = 0;
            for (int i = 0; i < len; i++) {
                byte byte0 = rst[i];
                cs[index++] = MD5_HEX_DIGITS[byte0 >>> 4 & 0xf];
                cs[index++] = MD5_HEX_DIGITS[byte0 & 0xf];
            }

            return new String(cs);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static final char[] MD5_HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 构造List对象
     *
     * 如果传入的是参数仅仅为一个对象数组(Object[])或原生数组(int[], long[]等)
     * 那么表现结果表现是不同的，Object[]为[obj[0], obj[1], obj[2]]
     * 而原生数组则为[[int[0], int[1]，int[2]]]
     * 多了一层嵌套，需要对原生数组进行特殊处理。
     * @param <T>
     * @param ts
     * @return
     */
    @SafeVarargs
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> List<T> ofList(T... ts) {
        List result = new ArrayList();

        //对Null进行特殊处理
        if (ts == null) {
            result.add(null);
            return result;
        }

        //对单独的原始数组类型进行特殊处理
        if (ts.length == 1 && ts[0] != null && OFLIST_ARRAY_CLASS.contains(ts[0].getClass())) {
            if (ts[0] instanceof int[]) {
                int[] val = (int[]) ts[0];
                for (int v : val) {
                    result.add(v);
                }
            } else if (ts[0] instanceof long[]) {
                long[] val = (long[]) ts[0];
                for (long v : val) {
                    result.add(v);
                }
            } else if (ts[0] instanceof boolean[]) {
                boolean[] val = (boolean[]) ts[0];
                for (boolean v : val) {
                    result.add(v);
                }
            } else if (ts[0] instanceof byte[]) {
                byte[] val = (byte[]) ts[0];
                for (byte v : val) {
                    result.add(v);
                }
            } else if (ts[0] instanceof double[]) {
                double[] val = (double[]) ts[0];
                for (double v : val) {
                    result.add(v);
                }
            }
        } else {    //对象数组
            for (T t : ts) {
                result.add(t);
            }
        }

        return result;
    }

    //专供ofList类使用 对于数组类型进行特殊处理
    private static final List<?> OFLIST_ARRAY_CLASS = Utils.ofList(int[].class, long[].class, boolean[].class, byte[].class, double[].class);


    /**
     * 构造Map对象
     */
    @SuppressWarnings("unchecked")
    public static <K, V> Map<K, V> ofMap(Object...params) {
        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();

        //无参 返回空即可
        if(params == null || params.length == 0) {
            return result;
        }

        //处理成对参数
        int len = params.length;
        for (int i = 0; i < len; i += 2) {
            K key = (K) params[i];
            V val = (V) params[i + 1];

            result.put(key, val);
        }

        return result;
    }

    public static String createStr(String str, Object... params) {
        return ParameterizedMessage.format(str, params);
    }


    /**
     * 解析JSON字符串，返回JSON对象<br/>
     * 字符串不允许为空<br/>
     * @param str 待解析字符串
     * @param blankErrorMsg 当传入数据数据为空时输出的错误信息
     */
    @SuppressWarnings("unchecked")
    public static <T> T json(String str, String blankErrorMsg, Object... blankErrorArgs) {
        if (StringUtils.isBlank(str)) {
            throw new SysException(blankErrorMsg, blankErrorArgs);
        } else {
            try {
                return (T)JSON.parse(str);
            } catch (Exception var4) {
                throw new SysException(var4, "发现无法解析的JSON字符串：str={}", str);
            }
        }
    }

    /**
     * 将对象转化为JSON字符串
     * @param obj
     * @return
     */
    public static String jsonString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 进行Get请求操作
     * @return
     */
    public static String httpGet(String url, Map<String, String> params) {
        try {
            //默认参数
            if(params == null) params = new HashMap<>();

            //1 拼接地址
            StringBuilder urlSB = new StringBuilder(url);
            //1.1 有需要拼接的参数
            if(!params.isEmpty()) {
                urlSB.append("?");
            }

            //1.2 拼接参数
            for(Entry<String, String> entry : params.entrySet()) {
                Object value = entry.getValue();
                String v = (value == null) ? "" : URLEncoder.encode(entry.getValue().toString(), "UTF-8");

                urlSB.append(entry.getKey()).append("=").append(v).append("&");
            }

            //1.3 最终地址
            String urlStrFinal = urlSB.toString();

            //1.4 去除末尾的&
            if(urlStrFinal.endsWith("&")) {
                urlStrFinal = urlStrFinal.substring(0, urlStrFinal.length() - 1);
            }

            //请求地址
            HttpGet get = new HttpGet(urlStrFinal);

            //准备环境
            try(CloseableHttpClient http = HttpClients.createDefault();
                CloseableHttpResponse response = http.execute(get)) {

                //返回内容
                HttpEntity entity = response.getEntity();

                //主体数据
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
                //读取
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString();
            }
        } catch (Exception e) {
            throw new SysException(e);
        }
    }


    private static String httpsGetConvertStreamToString(InputStream is) {
        StringBuilder sb1 = new StringBuilder();
        byte[] bytes = new byte[4096];
        boolean var3 = false;

        int size;
        try {
            while((size = is.read(bytes)) > 0) {
                String str = new String(bytes, 0, size, "UTF-8");
                sb1.append(str);
            }
        } catch (IOException var5) {
            throw new SysException(var5);
        }

        return sb1.toString();
    }

    /**
     * 构造一个可以接受任意HTTPS协议的client
     * @param base
     * @return
     */
    @SuppressWarnings("deprecation")
    public static HttpClient wrapHttpsClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs, String string) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] xcs, String string) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 进行Post请求操作
     * @return
     */
    public static String httpPost(String url, Map<String, String> params) {
        try {
            //默认参数
            if(params == null) params = new HashMap<>();

            //参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Entry<String, String> entry : params.entrySet()) {
                Object key = entry.getKey();
                Object val = entry.getValue();
                String valStr = (val == null) ? "" : val.toString();

                nvps.add(new BasicNameValuePair(key.toString(), valStr));
            }

            //请求地址
            HttpPost post = new HttpPost(url);
            //设置参数
            post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

            //准备环境
            try(CloseableHttpClient http = HttpClients.createDefault();
                CloseableHttpResponse response = http.execute(post)) {

                //返回内容
                HttpEntity entity = response.getEntity();

                //主体数据
                InputStream in = entity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                //读取
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }

                return sb.toString();
            }
        } catch (Exception e) {
            throw new SysException(e);
        }
    }


    /**
     * 行者的https
     * @param base
     * @return
     */
    private static DefaultHttpClient wrapClient(DefaultHttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }



    /**
     * 使用 HMAC-SHA1 签名方法对对encryptText进行签名
     * @param encryptText 被签名的字符串
     * @param encryptKey 密钥
     */
    public static byte[] hmacSHA1Encrypt(String encryptText, String encryptKey) throws Exception {
        byte[] data = encryptKey.getBytes("UTF-8");
        SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        byte[] text = encryptText.getBytes("UTF-8");
        return mac.doFinal(text);
    }

    /**
     * 获取对象的属性值
     * @param obj
     * @param fieldName
     * @return
     */
    public static <T> T fieldRead(Object obj, String fieldName) {
        Field f = getField(obj.getClass(), fieldName);
        try {
            return f != null ? (T) f.get(obj) : null;
        } catch (Exception e) {
            throw new SysException(e);
        }
    }

    /**
     * 设置对象的属性值
     * @param obj
     * @param fieldName
     * @return
     */
    public static void fieldWrite(Object obj, String fieldName, Object valueNew) {
        //一般对象处理
        Field f = getField(obj.getClass(), fieldName);
        if(f != null) {
            try {
                f.set(obj, valueNew);
            } catch (Exception e) {
                throw new SysException(e);
            }
        }
    }

    /**
     * 获取类的属性对象
     * @param clz
     * @param fieldName
     * @return
     */
    public static Field getField(Class<?> clz, String fieldName) {
        Map<String, Field> cf = GET_FIELD_CACHE.computeIfAbsent(clz, k -> {
            //遍历寻找符合的属性
            Map<String, Field> fs = new HashMap<>();
            do {
                for (Field f : k.getDeclaredFields()) {
                    //不可访问的强制改为可访问
                    if (!f.isAccessible()) {
                        f.setAccessible(true);
                    }

                    //缓存
                    fs.put(f.getName(), f);
                }
            } while ((k = k.getSuperclass()) != null);

            return fs;
        });

        return cf.get(fieldName);
    }
    private static final Map<Class<?>, Map<String, Field>> GET_FIELD_CACHE = new ConcurrentHashMap<>();

}
