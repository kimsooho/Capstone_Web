import pymysql

#MySql Connection
conn = pymysql.connect(host='localhost', user='root', passwd='16-76017662',db='python', charset='utf8', )
curs=conn.cursor(pymysql.cursors.DictCursor)

#========= select ===========
sql = "SELECT * FROM paper"
curs.execute(sql)

# Fetch Data
rows = curs.fetchall()

print(rows)

#========= Insert ===========
sql = "INSERT INTO paper(talkcontents) VALUES(%s)"
var = "[이정원], 말 테스트"
#print("INSERT INTO paper(talkcontents) VALUES('%s')"%var)
curs.execute(sql, var)
conn.commit()


