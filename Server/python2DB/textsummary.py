from gensim.summarization.summarizer import summarize
#from newspaper import Article
import sys
import pymysql

#MySql Connection
#print("Connection...")
conn = pymysql.connect(host='localhost', user='root', passwd='16-76017662',db='capstone', charset='utf8', )
curs=conn.cursor(pymysql.cursors.DictCursor)

#Select
#print("Select...")
#sql = "SELECT * FROM chatlog WHERE room_id = %s AND member_id = %s"
sql = "SELECT * FROM chatlog WHERE room_id = %s"
roomid = sys.argv[1]

summnum = float(sys.argv[3])#비율 또는 문장 갯수 입력
summtype = 0
summtype = sys.argv[4]
#curs.execute(sql,(roomid, memberid))
curs.execute(sql,roomid)

rows = curs.fetchall()

#print(result)

#url = 'https://news.v.daum.net/v/20180206160003332'
#news = Article(url, language='ko')
#news.download()
#news.parse()
#print(news.text) # 전체 출력

#print(summarize(news.text, ratio=0.1)) # 문장 비율
result ="\n"
for row in rows:
    result += (row['contents'])+"\n"
    #result.append(row['contents'])

#print(result)
#summtype->0 : ratio
if summtype == 0:
	summnum /= float(summnum/100);
	print(summarize(result, ratio=summnum)) # 비율
else:
	print(summarize(result, word_count=summnum)) #라인
