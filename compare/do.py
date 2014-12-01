__author__ = 'jin-yc10'

import csv
outf = file("out-normal.txt")
out = csv.reader(outf)
truef = file("Truth-normal.txt")
true = csv.reader(truef)
dbf = file('DB-normal.txt')
db = csv.reader(dbf, delimiter=':')
arr_out = []
arr_true = []
arr_db = []
for dl in db:
    arr_db.append(dl)
dbf.close()
for ol in out:
    arr_out.append(ol)
for tl in true:
    arr_true.append(tl)

d = {}
for t in arr_true:
    colid = t[0]
    rowid = t[1]
    value = t[2]
    # print colid, rowid, value
    if colid not in d.keys():
        d[colid] = {}
    d[colid][rowid] = (value, 0)

for o in arr_out:
    colid = o[0]
    colidint = int(colid)
    rowid = o[1]
    value = o[2]
    err_occur = False
    if colid not in d.keys():
        print 'mis found: colid',
        print arr_db[colidint+1]
    elif rowid not in d[colid].keys():
        print 'mis found: rowid',
        print arr_db[colidint+1]
    elif not value == d[colid][rowid][0]:
        print 'wrong value, ', value, '\t', d[colid][rowid],
        print arr_db[colidint+1]
    else:
        # print 'right'
        d[colid][rowid] = (value, 1)

nfc = 0
fc = 0
for k in d.keys():
    for j in d[k].keys():
        if not d[k][j][1] == 1:
            # print 'not found'
            print k, j, d[k][j][0]
            nfc += 1
        else:
            # print 'found'
            fc += 1
outf.close()
truef.close()
