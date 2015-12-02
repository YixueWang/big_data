RawData = LOAD '/home/cloudera/tweets.txt' AS (tweet:chararray);

-- Lower-case words.
LowerData = FOREACH RawData GENERATE LOWER(tweet) as tweet;
G = GROUP LowerData ALL;

-- Initialize the original word list.
H = FOREACH G GENERATE 'hackathon' as keyword, 0 as count;
D = FOREACH G GENERATE 'Dec' as keyword, 0 as count;
C = FOREACH G GENERATE 'Chicago' as keyword, 0 as count;
J = FOREACH G GENERATE 'Java' as keyword, 0 as count;

-- Find words
NewData1 = FILTER LowerData BY (tweet MATCHES '.*hackathon.*');
NewData1 = FOREACH NewData1 GENERATE tweet, 'hackathon' AS Keyword;

NewData2 = FILTER LowerData BY (tweet MATCHES '.*dec.*');
NewData2 = FOREACH NewData2 GENERATE tweet, 'Dec' AS Keyword;

NewData3 = FILTER LowerData BY (tweet MATCHES '.*chicago.*');
NewData3 = FOREACH NewData3 GENERATE tweet, 'Chicago' AS Keyword;

NewData4 = FILTER LowerData BY (tweet MATCHES '.*java.*');
NewData4 = FOREACH NewData4 GENERATE tweet, 'Java' AS Keyword;


NewData = UNION NewData1,NewData2,NewData3,NewData4;

word = GROUP NewData BY Keyword;

-- COUNT function to get the count (occurrences) of each word.
WordCount = FOREACH word GENERATE group as keyword, COUNT(NewData) as count;

WordCount_new = UNION WordCount,H,D,C,J;
Word = GROUP WordCount_new BY keyword;
W_Count = FOREACH Word GENERATE group as keyword, SUM(WordCount_new.count) as count;



-- print output result.

STORE W_Count INTO '/home/cloudera/output' USING PigStorage();
