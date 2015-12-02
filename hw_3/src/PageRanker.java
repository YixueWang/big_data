import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRanker {

  public static void main(String[] args) throws Exception {
	  Configuration conf = new Configuration();
	  if (args.length != 2) {
		      System.err.println("Usage: PageRanker <input path> <output path>");
		      System.exit(-1);
		    }
	  
	  conf.set("mapred.textoutputformat.separator", " ");
	  
	  Job job = new Job(conf); 
	  job.setJarByClass(PageRanker.class);
	  job.setJobName("Page Ranker");
	  
	  FileInputFormat.addInputPath(job, new Path(args[0])); 
	  FileOutputFormat.setOutputPath(job, new Path(args[1]));
	  
	  job.setMapperClass(PageRankerMapper.class);
	  job.setReducerClass(PageRankerReducer.class);
	  
	  job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(Text.class);

      System.exit(job.waitForCompletion(true) ? 0 : 1);
  }
}