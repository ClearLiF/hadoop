import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AreaPartitioner<KEY, VALUE> extends Partitioner<KEY, VALUE> {
	 
    private static Map<String, Integer> cacheValues = new HashMap<String, Integer>();
 
    static {
        cacheValues.put("110", 0);
        cacheValues.put("112", 1);
        cacheValues.put("114", 2);
    }
 
    @Override
    public int getPartition(KEY key, VALUE value, int numPartitions) {
       // Integer phoneNo = cacheValues.get(key.toString().substring(0, 3));
        Integer phoneNo2 =cacheValues.getOrDefault(key.toString(), 0);
        System.out.println(phoneNo2.toString());
        return phoneNo2 == 0 ? 3 : phoneNo2;
    }
    
    
 
}