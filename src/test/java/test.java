import org.junit.jupiter.api.Test;
import upload.UploadHadoop;

import java.io.IOException;
import java.util.Arrays;

/*
 *   #auther:李琪
 *   #date: 2019/12/30
 *   #description
 */
public class test {

    public void te() throws IOException {

        UploadHadoop.upload("D:","woodstox-core-5.0.3.jar");
    }

    public static void main(String[] args) throws IOException {
        new test().te();
    }
    @Test
    public  void  tests(){
    String s = "好评\t几乎 凌晨 才 到 包头 包头 没有 什么 特别 好 酒店 每次 来 就是 住 这家 所以 没有 忒 多 对比 感觉 行 下次 还是 得到 这里 来 住";
    String[] s1 = s.split("\t");
        System.out.println(s1[0]);
        System.out.println(Arrays.toString(s1[1].split("\\s+")));


    }
}
