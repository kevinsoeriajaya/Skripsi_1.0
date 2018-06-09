from lxml import html
import requests

from flask import request,jsonify

url = 'http://127.0.0.1:5000/todo/api/v1.0/tasks'

form ={
    'title': 'call',
    'description': 'willy call u'
}

session_requests = requests.session()

response = session_requests.post(url,json ={'title': 'call','description': 'willy call u'})

print(response.json())


