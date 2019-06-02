import pymysql
import sys

#MySql Connection
#print("Connection...")
conn = pymysql.connect(host='localhost', user='root', passwd='16-76017662',db='capstone', charset='utf8', )
curs=conn.cursor(pymysql.cursors.DictCursor)

#Select
#print("Select...")
sql = "SELECT * FROM chatlog WHERE room_id = %s AND member_id = %s"
roomid = sys.argv[1]
memberid = sys.argv[2]
curs.execute(sql,(roomid, memberid))

rows = curs.fetchall()
#result=[]
for item in rows:
	#result.extend(item)
	print(item)

#print(result)


