package MrBeiyes;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 〈〉
 *
 * @author Chkl
 * @create 2019/8/22
 * @since 1.0.0
 */
public class BeiYesiCountReducer extends Reducer<IntWritable, Text, IntWritable, Text> {

    /**
     *
     * map端 输出到reduce端，按相同的key分发到同一个reduce去执行
     *  （hello，<1,1,1>）
     *     (welcome,<1>)
     */
    static Integer count = 0;
    static Integer goodCount = 0;
    static Integer baiCount = 0;
    static Map<String, Integer> wordMap = BeiYesiCountMain.wordMap;
    /**
     * @description
     * @author ClearLi
     * @date 2020/1/5 10:36
     * @param key
     * @param values
     * @param context
     * @return void
     */
    @Override
    protected void reduce(IntWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        String s = beiYeSi(values);
        context.write(key,new Text(s));

        if (s.equals("好评")){
            goodCount++;
        }else {
            baiCount++;
        }
        if (Integer.parseInt(key.toString()) <= 1000 && s.equals("好评")
                || Integer.parseInt(key.toString()) > 1000 && Integer.parseInt(key.toString()) <= 2000 && s.equals("差评")) {
            count++;
        }
        if (key.toString().equals("2000")){
            context.write(new IntWritable(2017081211),new Text("好评"+String.valueOf(goodCount)));
            context.write(new IntWritable(2017081211),new Text("差评"+String.valueOf(baiCount)));
            context.write(new IntWritable(2017081211),new Text("正确率"+String.valueOf(count/ 2000.0)));
            System.out.println(count/ 2000.0);
        }


    }
    /**
     * @description
     * @author ClearLi
     * @date 2020/1/5 10:36
     * @param values
     * @return java.lang.String
     */
    public String beiYeSi(Iterable<Text> values){

        Iterator<Text> iterator = values.iterator();
        ArrayList<Float> goods = new ArrayList<>();
        ArrayList<Float> bads= new ArrayList<>();
        while (iterator.hasNext()){
            Text value = iterator.next();
            Integer good = wordMap.get("好评_"+value)==null?0:wordMap.get("好评_"+value);
            Integer bad = wordMap.get("差评_"+value)==null?0:wordMap.get("差评_"+value);
            //判断为空没有加
            goods.add(good.floatValue() / (good + bad));
            bads.add(bad.floatValue()/ (good + bad));

        }
        //存放每个词好评和差评的概率
        Integer allGood = wordMap.get("总共_好评");
        Integer allBad = wordMap.get("总共_差评");
        return mathCompared(goods,allGood)>=mathCompared(bads,allBad)?"好评":"差评";
    }
   /**
    * @description
    * @author ClearLi
    * @date 2020/1/5 10:36
    * @param fs
    * @param integer
    * @return java.lang.Float
    */
   public Float mathCompared(ArrayList<Float> fs,Integer integer){
    
        //p(a/b)*p(b)
        float result  = 1.0f;
        for (Float f:fs
             ) {
            result = result * f;
        }
        return  result * integer.floatValue();
    }

}
