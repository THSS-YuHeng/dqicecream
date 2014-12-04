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
    d[colid][rowid] = (value, '', 0)

arr_out_s = sorted(arr_out, cmp=lambda x,y:cmp(int(x[0]),int(y[0])))
out_re = file('out-normal-re.txt', 'w')
for o in arr_out_s:
    colid = o[0]
    colidint = int(colid)
    rowid = o[1]
    value = o[2]
    out_re.write(colid + ',' + rowid + ',' + value + '\n')
    err_occur = False
    if colid not in d.keys():
        print 'mis found: colid',colid,
        print arr_db[colidint+1]
        #d[colid][rowid] = (value, 'col')
    elif rowid not in d[colid].keys():
        print 'mis found: rowid',rowid,
        print arr_db[colidint+1]
        #d[colid][rowid] = (value, 'row')
    elif not value == d[colid][rowid][0]:
        print 'wrong value, ', value, '\t', d[colid][rowid],
        print arr_db[colidint+1]
        rightv = d[colid][rowid][0]
        d[colid][rowid] = (rightv, value, 'val')
    else:
        # print 'right'
        d[colid][rowid] = (value, value, 'cor')

nfc = 0
fc = 0
arr_nfc = []
for k in d.keys():
    for j in d[k].keys():
        if not d[k][j][2] == 'cor':
            # print 'not found'
            #print k, j, d[k][j][0]
            arr_nfc.append((k,arr_db[int(k)+1][1],j,d[k][j][0], d[k][j][1]))
            nfc += 1
        else:
            # print 'found'
            fc += 1
print nfc, fc, float(fc)/float(nfc+fc)
print '\n'.join([str(u) for u in sorted(arr_nfc, cmp=lambda x,y:cmp(int(x[0]),int(y[0])))])
outf.close()
truef.close()
