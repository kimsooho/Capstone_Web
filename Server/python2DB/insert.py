import pymysql
import sys

#MySql Connection
#print("Connection...")
conn = pymysql.connect(host='localhost', user='root', passwd='16-76017662',db='capstone', charset='utf8', )
curs=conn.cursor(pymysql.cursors.DictCursor)

#Insert
#print("Insert...")
sql = "INSERT INTO chatlog(room_id, member_id, contents, chat_date) VALUES(%s, %s, %s, NOW())"
roomid = sys.argv[1]
memberid = sys.argv[2]
contents = sys.argv[3]

contents = contents.replace("+"," ", len(contents))

curs.execute(sql,(roomid, memberid, contents))
conn.commit()

print("result : " + contents)