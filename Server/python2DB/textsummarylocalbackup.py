#from newspaper import Article
from gensim.summarization.summarizer import summarize
import sys
import pymysql

#MySql Connection
#print("Connection...")
conn = pymysql.connect(host='localhost', user='root', passwd='16-76017662',db='capstone', charset='utf8', )
curs=conn.cursor(pymysql.cursors.DictCursor)

sql = "SELECT * FROM chatlog WHERE room_id = %s"
roomid = sys.argv[1]

summnum = sys.argv[2]#비율 또는 문장 갯수 입력
summtype = "0"
summtype = sys.argv[3]
#curs.execute(sql,(roomid, memberid))
curs.execute(sql,roomid)

rows = curs.fetchall()


#print(summarize(news.text, ratio=0.1)) # 문장 비율
result ="\n"
for row in rows:
    result += (row['contents'])+"\n"

print(result)
#summtype->0 : ratio

if summtype == "0" :
	print('ratio')
	print(type(float(summnum)))
	print(summarize(result, ratio=float(summnum))) # 비율
else:
	print('line')
	print(type(int(summnum)))
	print(summarize(result, word_count=int(summnum))) #라인
