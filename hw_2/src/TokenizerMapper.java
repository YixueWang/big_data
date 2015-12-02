import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class TokenizerMapper
       extends Mapper<Object, Text, Text, IntWritable>{

    private final static IntWritable one = new IntWritable(1);
    private final static IntWritable zero = new IntWritable(0);
    private Text word = new Text();
    
    @Override
    public void map(Object key, Text value, Context context
                    ) throws IOException, InterruptedException {
      String lines[] = (value.toString()).split(System.getProperty("line.separator"));
      String a[] = new String[]{"hackathon", "Dec", "Chicago", "Java"};
      
      for (String tweet:lines){
    	  for (String s:a ){
          String str1 = tweet.toLowerCase();
          String str2 = s.toLowerCase();
          
          if (str1.contains(str2)){
    			  word.set(s);
    			  context.write(word, one);  
    		  }
          else{
        	  word.set(s);
        	  context.write(word, zero);
          }
    	  }
      }
    }
  }

