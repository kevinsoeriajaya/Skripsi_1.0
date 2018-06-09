from flask import Flask,jsonify,abort, make_response,request
app = Flask(__name__)

tasks = [
    {
        'id': 1,
        'title': u'Buy groceries',
        'description': u'Milk, Cheese, Pizza, Fruit, Tylenol',
        'done': False
    }
]

@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}),404)

@app.route('/todo/api/v1.0/tasks',methods=['POST'])
def create_tasks():
    if not request.json or not 'title' in request.json:
        abort(400)
    task ={
        'id': tasks[-1]['id'] + 1,
        'title': request.json['title'],
        'description': request.json.get('description', ""),
        'done': False
    }
   # print(task[id])
    tasks.append(task)
    return jsonify({'task':task}),201

if __name__ == '__main__':
    app.run(debug=True)