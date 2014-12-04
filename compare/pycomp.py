__author__ = 'jin-yc10'
import csv
outref = file('out-normal.txt')
outre = csv.reader(outref)
truthref = file('True-normal-re.txt')
truthre = csv.reader(truthref)

oa = []
ta = []
for l in outre:
    oa.append(l)

for l in truthre:
    ta.append(l)

outref.close()
truthref.close()

oas = sorted(oa, cmp=lambda x,y:cmp(int(x[0]),int(y[0])))
oasf = file('out-normal-re.txt','w')
for os in oas:
    oasf.write(os[0]+','+os[1]+','+os[2]+'\n')

td = {}

for t in ta:
    if t[0] not in td.keys():
        td[t[0]] = {}
    td[t[0]][t[1]] = (t[2],t[3],False)

all_ic = []

for o in oa:
    if td.has_key(o[0]):
        if td[o[0]].has_key(o[1]):
            v = td[o[0]][o[1]]
            td[o[0]][o[1]] = (v[0], v[1], True)
            #print 'f', o[0], o[1], o[2], v[0], v[1]
        else:
            print 'r', o[0], o[1]
            all_ic.append(('r',o[0],o[1]))
    else:
        print 'm',  o[0], o[1]
        all_ic.append(('m',o[0],o[1]))

ld = {}

for k in td.keys():
    for l in td[k].keys():
        if td[k][l][2] == False:
            print 'l', k, l, td[k][l][0]
            all_ic.append(('l',k,l))
            if(not ld.has_key(l)):
                ld[l] = 0
            ld[l] += 1


sic = sorted(all_ic, cmp=lambda x,y:cmp(x[2],y[2]))
for ic in sic:
    print ic

print ld

