package MR;

import java.net.URI;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.net.URI;

/*
 *   #auther:李琪
 *   #date: 2019/12/31
 *   #description
 */
public class WordCountMain {
    public static void main(String[] args) throws Exception{

        System.setProperty("HADOOP_USER_NAME", "root");

        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS","hdfs://simple02:9000");


        // 创建一个Job
        Job job = Job.getInstance(configuration);
        // 设置Job对应的参数: 主类
        job.setJarByClass(WordCountMain.class);
        // 设置Job对应的参数: 设置自定义的Mapper和Reducer处理类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);
        // 设置Job对应的参数: Mapper输出key和value的类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        // 设置Job对应的参数: Reduce输出key和value的类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        // 如果输出目录已经存在，则先删除
        FileSystem fileSystem = FileSystem.get(new URI("hdfs://simple02:9000"),configuration, "root");
        Path outputPath = new Path("/output_2017081211.txt");
        if(fileSystem.exists(outputPath)) {
            fileSystem.delete(outputPath,true);
        }

        // 设置Job对应的参数: Mapper输出key和value的类型：作业输入和输出的路径
        FileInputFormat.setInputPaths(job, new Path("/input_2017081211.txt"));
        FileOutputFormat.setOutputPath(job, outputPath);

        // 提交job

        System.exit(job.waitForCompletion(true) ? 0 : 1);

    }


}
