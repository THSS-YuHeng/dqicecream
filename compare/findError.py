__author__ = 'jin-yc10'
import csv
import checkattr

dbf = file('DB-normal.txt')
db = csv.reader(dbf, delimiter=':')
arr_db = []
for dl in db:
    arr_db.append(dl)
dbf.close()
data_dict = {}
errors = []
truef = file("Truth-normal.txt")
true = csv.reader(truef)
truths = []
for tl in true:
    truths.append(tl)
truef.close()
for data in arr_db[1:]:
    cuid = data[1]
    ruid = data[0]
    if cuid not in data_dict.keys():
        data_dict[cuid] = {}
    dd = data_dict[cuid]
    if ruid not in dd.keys():
        dd[ruid] = {}
    # ssn
    ssn = data[2]
    checkattr.check_ssn(dd[ruid], ssn)
    # fname
    fname = data[3]
    checkattr.check_fname(dd[ruid], fname)
    # minit
    minit = data[4]
    checkattr.check_minit(dd[ruid], minit)
    # lname
    lname = data[5]
    checkattr.check_lname(dd[ruid], lname)
    # stnum
    stnum = data[6]
    #checkattr.check_lname(dd[ruid], lname)
    # stadd
    stadd = data[7]
    # apmt
    apmt = data[8]
    # city
    city = data[9]
    checkattr.check_city(dd[ruid], city)
    # state
    state = data[10]
    checkattr.check_state(dd[ruid], state)
    # zip
    zip = data[11]
    checkattr.check_zip(dd[ruid], zip)
    # birth
    birth = data[12]
    checkattr.check_birth(dd[ruid], birth)
    # age
    age = data[13]
    checkattr.check_age(dd[ruid], age)
    # salary
    salary = data[14]
    checkattr.check_salary(dd[ruid], salary)
    # tax
    tax = data[15]
    checkattr.check_tax(dd[ruid], tax)
total = 0
res = {}
oa = []
of = file("py-o-n.txt", 'w')
for cuid in data_dict.keys():
    for ruid in data_dict[cuid].keys():
        total += 1
        for k in data_dict[cuid][ruid].keys():
            if data_dict[cuid][ruid][k][1] != 0:
                if ruid not in res.keys():
                    res[ruid] = {}
                if k not in res[ruid].keys():
                    res[ruid][k] = 1
                #
                oa.append((ruid, k))

soa = sorted(oa, cmp=lambda x,y:cmp(int(x[0]),int(y[0])))
for soai in soa:
    of.write(soai[0] +','+ soai[1] + '\n')
of.close()
nfc = 0
fc = 0

for truth in truths:
    ruid = truth[0]
    k = truth[1]
    if ruid in res.keys():
        if k in res[ruid].keys():
            fc += 1
        else:
            nfc += 1
    else:
        nfc += 1

print nfc, fc