package MrBeiyes;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/*
 *   #auther:李琪
 *   #date: 2019/12/31
 *   #description
 */
public class BeiYesiCountMain {
    private static InputStream input;
    public static Integer lineCount = 1;
    static Map<String, Integer> wordMap = new HashMap<>();
    public static void main(String[] args) throws Exception{
        //读取之前训练集合中所有参数
        new BeiYesiCountMain().getAllTrain();
        System.setProperty("HADOOP_USER_NAME", "root");

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://simple02:9000");


        // 创建一个Job
        Job job = Job.getInstance(configuration);
        // 设置Job对应的参数: 主类
        job.setJarByClass(BeiYesiCountMain.class);
        // 设置Job对应的参数: 设置自定义的Mapper和Reducer处理类
        job.setMapperClass(BeiyesiCountMapper.class);
        job.setReducerClass(BeiYesiCountReducer.class);
        // 设置Job对应的参数: Mapper输出key和value的类型
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(Text.class);
        // 设置Job对应的参数: Reduce输出key和value的类型
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(Text.class);
        // 如果输出目录已经存在，则先删除
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://simple02:9000"),configuration, "root");
        Path outputPath = new Path("/BeiYesOutput_2017081211.txt");
        if(fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath,true);
        }

        // 设置Job对应的参数: Mapper输出key和value的类型：作业输入和输出的路径
        FileInputFormat.setInputPaths(job, new Path("/BeiYesiTest.txt"));
        FileOutputFormat.setOutputPath(job, outputPath);

        /* 提交job */

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }

    /**
     * @description
     * @author ClearLi
     * @date 2020/1/5 10:38
     * @param
     * @return void
     */
    @Test
    public void getAllTrain(){

        BufferedReader reader = null;
        //设置root权限
        System.setProperty("HADOOP_USER_NAME", "root");
        //创建HDFS连接对象client
        Configuration conf = new Configuration();
        conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
        conf.set("fs.defaultFS", "hdfs://simple02:9000");
        conf.set("dfs.client.use.datanode.hostname", "true");
        FileSystem client = null;
        try {
            client = FileSystem.get(conf);
            input = client.open(new Path("/output_2017081211.txt/part-r-00000"));
            reader = new BufferedReader(new InputStreamReader(input));
            String line = "";
            while ((line = reader.readLine())!=null){
                String[] words = line.split("\t");
                if(words.length==2){
                    wordMap.put(words[0],Integer.parseInt(words[1]));
                }

            }


        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                reader.close();

                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(    wordMap.size());

    }


}
