import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRankIterative {

  public static void main(String[] args) throws Exception {
	  if (args.length != 2) {
		      System.err.println("Usage: PageRanker <input path> <output path>");
		      System.exit(-1);
		    }
	  int i = 0;
	  Path inputPath = new Path(args[0]);

	  while (i < 3) {
	  	Configuration conf = new Configuration();
	  	conf.set("mapred.textoutputformat.separator", " ");

	  	Job job = new Job(conf); 
	  	job.setJarByClass(PageRankIterative.class);
	  	job.setJobName("Page Rank Iterative");

	  	Path outputPath=new Path(args[1] + "/" + i);

	  	FileInputFormat.addInputPath(job, inputPath); 
	  	FileOutputFormat.setOutputPath(job, outputPath);

	  	job.setMapperClass(PageRankerMapper.class);
	    job.setReducerClass(PageRankerReducer.class);

	    job.setOutputKeyClass(Text.class);
	    job.setOutputValueClass(Text.class);

	    job.waitForCompletion(true);
	  	i++;
	  	
	  	inputPath = new Path(outputPath + "/part-r-00000");

	  }
  }
}