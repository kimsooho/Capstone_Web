from gensim.summarization.summarizer import summarize
from newspaper import Article

url = 'https://news.v.daum.net/v/20180206160003332'
news = Article(url, language='ko')
news.download()
news.parse()
#print(news.text) # 전체 출력

#print(summarize(news.text, ratio=0.1)) # 문장 비율

print(summarize(news.text, word_count=50)) # 단어 50개 
