__author__ = 'jin-yc10'
import csv
dbf = file('DB-normal.txt')
db = csv.reader(dbf, delimiter=':')
arr_db = []
arr_attri = set()
for dl in db:
    arr_db.append(dl)
dbf.close()
truef = file("Truth-normal.txt")
true_reorgf = file("True-normal-re.txt", 'w')
true = csv.reader(truef)
true_reorg = csv.writer(true_reorgf)
arr_true = []
for tl in true:
    arr_true.append(tl)

title = arr_db[0]
for tup in sorted(arr_true, cmp=lambda x, y: cmp(int(x[0]), int(y[0])), reverse=False):
    colid = tup[0]
    rowid = tup[1]
    rowind = title.index(rowid)
    print tup,
    print arr_db[int(colid)+1][rowind]
    true_reorg.writerow(tup + [arr_db[int(colid)+1][rowind]])

truef.close();
true_reorgf.close()