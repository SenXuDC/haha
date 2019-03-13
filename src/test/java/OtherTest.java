import com.xusenme.utils.FastDFSClientUtils;
import org.junit.Test;

import java.io.File;
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
}
