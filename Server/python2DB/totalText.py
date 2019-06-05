import sys
import pymysql

conn = pymysql.connect(host='localhost', user='root', passwd='16-76017662', db='capstone', charset='utf8',)
curs.conn.cursor(pymysql.cursors.DictCursor)

sql = "SELECT * FROM chatlog WHERE room_id = %s"
roomid = sys.argv[1]

curs.execute(sql, roomid)

rows = curs.fetchall()

result = "\n"
for row in rows:
	result += (row['contents'])+"\n"
print(result)
