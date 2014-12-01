__author__ = 'jin-yc10'
import csv
truef = file("Truth-normal.txt")
true_reorgf = file("True-normal-re.txt", 'w')
true = csv.reader(truef)
true_reorg = csv.writer(true_reorgf)
arr_true = []
for tl in true:
    arr_true.append(tl)

for tup in sorted(arr_true, cmp=lambda x, y: cmp(int(x[0]), int(y[0])), reverse=False):
    true_reorg.writerow(tup)

truef.close();
true_reorgf.close()