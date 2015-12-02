from twython import Twython
import cPickle
import json

 

APP_KEY = ''
APP_SECRET = ''
## private app key and secret...


twitter = Twython(APP_KEY, APP_SECRET)

json1 = json.dumps(twitter.search(q ='denim', result_type='recent'))

data = json.loads(json1)


tweets = []

for info in data['statuses']:
	for i in info['text']:
		tweets.append(i)

words = []

a = ''.join(tweets)
print a 

for i in a. split():
	words.append(i)

f = open("myData.pickle", "wb")

cPickle.dump(words, f)
f.close()

	

