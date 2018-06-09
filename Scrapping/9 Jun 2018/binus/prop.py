import re

#username = 'willy.wijaya002@binus.ac.id'
#password = 'b!Nu$26011997'

username = 'wendy.hardynata@binus.ac.id'
password = 'evo4d9dacer'

#username = 'kevin.jaya@binus.ac.id'
#password = 'kevin234'


what = "abc {} test {}"
print(what.format(3, 4))
'''
path_to_description = "r'//div//div[@data-title='{} of {}']//div//h1[@class='heading']/text()'"
print(path_to_description.format(0+1, 9))

listt = ["\r\t\n", 'test ddf', "\t\n"]

newlist = []
for idx in range(len(listt)):
    #table = str.maketrans(dict.fromkeys('\r', '\n', '\t'))
    #listt[idx] = re.sub(r'\t\n', '', listt[idx])
    #print(listt)

    #abc = ''.join(listt[idx].split())
    abc = re.split(r"\rtn", listt[idx])
    if(abc!=''):
        newlist.append(abc)

    print("AAA:",abc)
print(newlist)
'''