import com.xusenme.utils.FastDFSClientUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class OtherTest {

    @Test
    public void test1() {
        InetSocketAddress is = new InetSocketAddress("localhost", 22122);
        System.out.println(is);
    }

    @Test
    public void test2() throws Exception{
        String path = "/Users/xusen/Desktop/WechatIMG2.jpeg";
        String fileId = FastDFSClientUtils.upload(new File(path), path);
        System.out.println("本地文件：" + path + "，上传成功！ 文件ID为：" + fileId);
    }

    @Test
    public void test3() throws Exception {
//        InputStream is = FastDFSClientUtils.download("group1", "group1/M00/00/00/rBAu5VyLamqAZV_7AADSGmRq8Ew917.png");
//        System.out.println(is.available());
//        String path = System.getProperty("user.dir") + File.separatorChar + "receive" +  File.separatorChar + "001.jpg";
//        System.out.println(path);
//        FileUtils.copyInputStreamToFile(is,  new File(path));
    }
}
