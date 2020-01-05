import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Download {

	private static InputStream input;
	private static OutputStream output;
	
	public static void main(String[] args) throws IOException {
		
		//设置root权限
		System.setProperty("HADOOP_USER_NAME", "root");
		//创建HDFS连接对象client
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
		conf.set("fs.defaultFS", String.valueOf(new Path("hdfs://simple02:9000")));
		conf.set("dfs.client.use.datanode.hostname", "true");
		FileSystem client=FileSystem.get(conf);
		
		output = new FileOutputStream("D:/simple02.txt");
		//创建HDFS的输入流
		input=client.open((new Path("/about.txt")));
		
		byte[] buffer= new byte[1024];
		int len=0;
		while ((len=input.read(buffer))!=-1) {
			
			
			output.write(buffer, 0, len);
			
		}
		
		
		output.flush();
		input.close();
		output.close();
		
		
		
		
	}
	
}
