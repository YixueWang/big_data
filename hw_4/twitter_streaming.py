#Import from tweepy library, json and time 
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
import json
import time

#keys and secrets to access API 
consumer_key = ''
consumer_secret = ''
access_token = ""
access_token_secret = ""

# A listener to print tweets to file

class StdOutListener(StreamListener):

    def __init__(self,limit):
        super(StdOutListener, self).__init__()
        self.num_tweets = 0
        self.file_name = open('twitter.txt','w+')
        self.limit = limit

    def on_data(self, data):
        jsondata = json.loads(data)
        if self.num_tweets < int(self.limit):
            self.file_name.write(str(jsondata))

        else:
            print "finish"
            return False
        self.num_tweets += 1
    def on_error(self, status):
        print status

if __name__ == "__main__":
    l = StdOutListener(10) # can be changed
    auth = OAuthHandler(consumer_key, consumer_secret)
    auth.set_access_token(access_token, access_token_secret)
    stream = Stream(auth, l)
    stream.filter(track=['white lace', 'denim']) # can be changed 