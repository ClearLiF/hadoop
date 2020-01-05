package MrBeiyes;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * 〈〉
 *
 * @author Chkl
 * @create 2019/8/22
 * @since 1.0.0
 */

/**


 /////////////////////////////////////////////////////////////////这个位置换一下位置
 */
public class BeiyesiCountMapper extends Mapper<LongWritable, Text,IntWritable, Text> {

    /**
     * @description
     * @author ClearLi
     * @date 2020/1/5 10:37
     * @param key
     * @param value
     * @param context
     * @return void
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        IntWritable lineCount = new IntWritable(BeiYesiCountMain.lineCount++);

        //把value对应的行数据按照指定的间隔符拆分开
        String[] words = value.toString().split("\t");



        if (words.length == 2){
            String[] pjs = words[1].split(" ");
            for (String pj : pjs) {
                if (isAllChinese(pj)){
                    context.write(lineCount, new Text(pj));

                }
            }

        }

    }


    /**
     * 判断字符串是否全为中文
     * @param str
     * @return
     */
    public boolean isAllChinese(String str) {

        if (str == null) {
            return false;
        }
        for (char c : str.toCharArray()) {
            if (!isChinese(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断单个字符是否为中文
     * @param c
     * @return
     */
    public Boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9Fa5;
    }


}
