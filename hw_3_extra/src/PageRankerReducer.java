import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankerReducer 
	extends Reducer<Text, Text, Text, Text>{
	
	@Override

	public void reduce(Text key, Iterable<Text> values, Context context) 
		throws IOException, InterruptedException{
		
		float PR = 0;
		String output = new String();
		for (Text val : values) {
			String link_value = val.toString();

			//add PR together and create new PR by key

			if (link_value.contains(",")){
				String[] pr_value = link_value.split(",");
				float PRold = Float.parseFloat(pr_value[pr_value.length-1]);
				PR += PRold;
			}

			else {
				output = link_value;
			}
		}
		String P = new DecimalFormat("##0.000000").format(PR);
		context.write(key, new Text(output + P));
	}
}
