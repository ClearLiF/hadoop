package upload;

import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.stereotype.Component;

@Component
public  class UploadHadoop {

	private static InputStream input;
	private static OutputStream output;
	public static int upload(String path,String name) throws IOException {
		path=path.concat("\\");
		path=path.replaceAll("\\\\","/");
		System.out.println(path+name);
		//设置root权限
		System.setProperty("HADOOP_USER_NAME", "root");
		//创建HDFS连接对象client
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
		conf.set("fs.defaultFS", "hdfs://simple02:9000");
		conf.set("dfs.client.use.datanode.hostname", "true");
		FileSystem client = FileSystem.get(conf);

		/*//要上传的资源路径
		String src = "C:/Users/Desktop/bcdf.txt";
		//要上传的hdfs路径
		String hdfsDst = "/aadir";

		client.copyFromLocalFile(new Path(src), new Path(hdfsDst));


		System.out.println("Success");*/

		//创建HDFS的输入流
		input = new FileInputStream(path+name);
		System.out.println(path+name);
		//创建HDFS的输出流
		output = client.create(new Path("/"+name));
		//写文件到HDFS
		byte[] buffer = new byte[1024];
		int len=0;
		while ((len=input.read(buffer))!=-1) {

			output.write(buffer, 0, len);

		}

		//防止输出数据不完整
		output.flush();
		//使用工具类IOUtils上传或下载
		input.close();
		output.close();

		return 0;
	}
	
	public static void main(String[] args) throws IOException {
		
		//设置root权限
		System.setProperty("HADOOP_USER_NAME", "root");
		//创建HDFS连接对象client
		Configuration conf = new Configuration();
		conf.set("fs.hdfs.impl","org.apache.hadoop.hdfs.DistributedFileSystem");
		conf.set("fs.defaultFS", "hdfs://simple02:9000");
		conf.set("dfs.client.use.datanode.hostname", "true");
		FileSystem client = FileSystem.get(conf);
		
		/*//要上传的资源路径
		String src = "C:/Users/Desktop/bcdf.txt";
		//要上传的hdfs路径
		String hdfsDst = "/aadir";
		
		client.copyFromLocalFile(new Path(src), new Path(hdfsDst));
		
		
		System.out.println("Success");*/
		
		//创建HDFS的输入流
		input = new FileInputStream("D:/upload.java");
		//创建HDFS的输出流
		output = client.create(new Path("/upload.java"));
		//写文件到HDFS
		byte[] buffer = new byte[1024];
		int len=0;
		while ((len=input.read(buffer))!=-1) {
			
			output.write(buffer, 0, len);
			
		}
		
		//防止输出数据不完整
		output.flush();
		//使用工具类IOUtils上传或下载
		input.close();
		output.close();
		
		
	}
	
}

