__author__ = 'jin-yc10'
import csv
dbf = file('DB-normal.txt')
db = csv.reader(dbf, delimiter=':')
arr_db = []
arr_attri = set()
for dl in db:
    arr_db.append(dl)
    arr_attri.add(dl[9])
dbf.close()

for a in arr_attri:
    print a