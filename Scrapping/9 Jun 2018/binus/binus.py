import lxml.html
from lxml import html
import requests
import prop

try:
    #page = requests.get('https://ol.binus.ac.id/LoginBinusian')
    #tree = html.fromstring(page.content)

    session = requests.session()
    login = session.get('https://ol.binus.ac.id/LoginBinusian')
    login_html = lxml.html.fromstring(login.text)
    hidden_inputs = login_html.xpath(r'//form//input[@type="hidden"]')

    form = {}
    form = {x.attrib["name"]: x.attrib["value"] for x in hidden_inputs}
    #print(form)

    email = prop.username
    passw = prop.password

    form['TextBoxID'] = email
    form['TxtPassword'] = passw
    form['ButtonLogin'] = 'Login'

    #print(form)

    response = session.post('https://ol.binus.ac.id/LoginBinusian', data=form)
    print(response.url)

    page = session.get('https://ol.binus.ac.id/Dashboard')
    page_content = page.content
    pageElement = html.document_fromstring(page_content)

    #print(response.cookies.get_dict())
    #print(lxml.html.tostring(pageElement))


    ##GET ANNOUNCEMENT##
    judul = pageElement.xpath(r'//div//div//div//h1[@class="heading"]')
    print(len(judul))


    #print(len(tesss))
    #print(tesss)

    for idx in range(len(judul)):
        print("Announcement #"+str(idx+1)+":", judul[idx].text)
        path_to_description_p = '//div//div[@data-title="{} of {}"]//div//p/text()'
        path_to_description_div = '//div//div[@data-title="{} of {}"]//div//div/text()'

        builded_path = path_to_description_p.format(idx+1, len(judul))
        description = pageElement.xpath(builded_path)

        descList = []
        if (len(description) == 0):
            builded_path = path_to_description_div.format(idx+1, len(judul))
            description = pageElement.xpath(builded_path)

        print("==================")

        print(description)

        for descIdx in range (len(description)):
            print(description[descIdx])

        #for descIdx in range(len(description)):
        #    temp = ''.join(description[descIdx].split())
#
 #           if (temp != ''):
  #              descList.append(temp)
   #             print(temp, " ")

        print("==================")


    ##LOGOUT##
    print("NORMAL")
    page = session.get('https://ol.binus.ac.id/Logout')
    page = session.get('https://ol.binus.ac.id/Dashboard')

    page_content = page.content
    pageElement = html.document_fromstring(page_content)
except:
    print("EXCEPT")
    ##LOGOUT##
    page = session.get('https://ol.binus.ac.id/Logout')
    page = session.get('https://ol.binus.ac.id/Dashboard')

    page_content = page.content
    pageElement = html.document_fromstring(page_content)

    #print(lxml.html.tostring(pageElement))
