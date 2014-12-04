__author__ = 'jin-yc10'
import re
p = re.compile(r'^[A-Z][a-zA-Z\.\,]*$')
def check_digit(value, low, high):
    try:
        i = int(value)
    except ValueError, e:
        return False
    if i < low or i > high:
        return False
    return True

def check_ssn(item, value):
    if not value or not len(value) == 9:
        item['ssn'] = (value, 1)
    elif not check_digit(value, 0, 999999999):
        item['ssn'] = (value, 1)
    else:
        item['ssn'] = (value, 0)

def check_fname(item, value):
    if not value or not len(value) > 0:
        item['fname'] = (value, 1)
    else:
        c = value[0]
        if not c.isupper():
            item['fname'] = (value, 1)
            return
        elif len(value) > 1:
            for c in value[1]:
                if (not c.isalpha()) and (not c == '.') and (not c == ',' ):
                    item['fname'] = (value, 1)
                    return
        item['fname'] = (value, 0)

def check_minit(item, value):
    if not value or len(value) == 0:
        item['minit'] = (value, 0)
    elif len(value) == 1 and value[0].isupper():
        item['minit'] = (value, 0)
    else:
        item['minit'] = (value, 1)

def check_lname(item, value):
    if not value or not len(value) > 0:
        item['lname'] = (value, 1)
    else:
        c = value[0]
        if not c.isupper():
            item['lname'] = (value, 1)
            return
        elif len(value)>1:
            for c in value[1]:
                if (not c.isalpha()) and (not c == '.') and (not c == ',' ):
                    item['lname'] = (value, 1)
                    return
        item['lname'] = (value, 0)

def check_stnum(item, value):
    pass

def check_stadd(item, value):
    pass

def check_apmt(item, value):
    pass

def check_city(item, value):
    if value and len(value) > 0:
        words = value.split()
        for w in words:
            for c in w:
                if not (c.isalpha() or c == '.' or c == '-' or c == '/' or c == ' ' or c == "'"):
                    item['city'] = (value, 1)
                    return
        item['city'] = (value, 0)
    else:
        item['city'] = (value, 1)

def check_state(item, value):
    states = []
    states.append("AL")
    states.append("AK")
    states.append("AZ")
    states.append("AR")

    states.append("CA")
    states.append("CO")
    states.append("CT")

    states.append("DE")
    states.append("DC")
    states.append("FL")
    states.append("GA")
    states.append("HI")
    states.append("ID")
    states.append("IL")
    states.append("IN")
    states.append("IA")
    states.append("KS")
    states.append("KY")
    states.append("LA")
    states.append("ME")
    states.append("MD")
    states.append("MA")
    states.append("MI")
    states.append("MN")
    states.append("MS")
    states.append("MO")
    states.append("MT")
    states.append("NE")
    states.append("NV")
    states.append("NH")
    states.append("NJ")
    states.append("NM")
    states.append("NY")
    states.append("NC")
    states.append("ND")
    states.append("OH")
    states.append("OK")
    states.append("OR")
    states.append("PA")
    states.append("RI")
    states.append("SC")
    states.append("SD")
    states.append("TN")
    states.append("TX")
    states.append("UT")
    states.append("VT")
    states.append("VA")
    states.append("WA")
    states.append("WV")
    states.append("WI")
    states.append("WY")
    states.append("PR")
    states.append("VI")
    states.append("FM")
    states.append("GU")
    states.append("AS")
    states.append("MP")
    if value in states:
        item['state'] = (value, 0)
    else:
        item['state'] = (value, 1)

def check_zip(item, value):
    if not len(value) == 5:
        item['zip'] = (value, 1)
    elif check_digit(value, 0, 99999):
        item['zip'] = (value, 0)
    else:
        item['zip'] = (value, 1)

import time
def check_birth(item, value):
    if value:
        s = value.split('-')
        if not len(s) == 3:
            item['birth'] = (value, 1)
        else:
            try:
                t = time.strptime(value, '%m-%d-%Y')
                y = t.tm_year
                if (y < 1930 or y > 1989):
                    item['birth'] = (value, 1)
                    return
                else:
                    item['birth'] = (value, 0)
            except ValueError, e:
                item['birth'] = (value, 1)
    else:
        item['birth'] = (value, 1)

def check_age(item, value):
    if value:
        if check_digit(value, 14, 83):
            item['age'] = (value, 0)
        else:
            item['age'] = (value, 1)
    else:
        item['age'] = (value, 1)

def check_salary(item, value):
    if value:
        if check_digit(value, 500, 20500):
            item['salary'] = (value, 0)
        elif value == '0':
            item['salary'] = (value, 0)
        else:
            item['salary'] = (value, 1)
    else:
        item['salary'] = (value, 1)

def check_tax(item, value):
    if value:
        if check_digit(value, 0, 9999999999):
            item['tax'] = (value, 0)
        else:
            item['tax'] = (value, 1)
    else:
        item['tax'] = (value, 1)

def c_with_print(dict, value, f, key=None):
    f(dict, value)
    if key:
        print dict[key]
    else:
        print dict

if __name__ == '__main__':
    dict = {}
    c_with_print(dict, 'aa1ss.,', check_fname, 'fname')
    c_with_print(dict, ' ', check_minit, 'minit')
    c_with_print(dict, 'aa1ss.,', check_lname, 'lname')
    c_with_print(dict, "Cs. C/'cs", check_city, 'city')
    c_with_print(dict, None, check_state, 'state')
    c_with_print(dict, '1', check_zip, 'zip')
    c_with_print(dict, 'xxx', check_birth, 'birth')
    c_with_print(dict, '99', check_age, 'age')
    c_with_print(dict, '2000', check_salary, 'salary')
    c_with_print(dict, 'xx', check_tax, 'tax')

