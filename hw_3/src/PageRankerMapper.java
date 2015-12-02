import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.io.LongWritable;

public class PageRankerMapper
	extends Mapper<LongWritable, Text, Text, Text> {

	@Override

	public void map(LongWritable key, Text value, Context context) 
			throws IOException, InterruptedException{
		
		// get source page, outlinks and PR splited by space

		String tokens[] = (value.toString()).split(" ");
		int outlink_num = tokens.length - 2;
		// calculate new PR
	

		float PR_old = Float.parseFloat(tokens[outlink_num + 1]);
		String PR = Float.toString(PR_old / outlink_num);
		String links = "";
        
        for (int i = 1; i <= outlink_num; i++){
        	context.write(new Text(tokens[i]), new Text(tokens[0] + "," + PR));
			links = links + tokens[i] + " " ; 
        }

        links = links.substring(0, links.length());
        context.write(new Text(tokens[0]), new Text(links));
	}
}
