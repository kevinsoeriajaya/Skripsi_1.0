from flask import Flask, jsonify, abort, make_response, request
from flask import url_for
from flask_httpauth import HTTPBasicAuth

app = Flask(__name__)

tasks = [
    {
        'id': 1,
        'title': u'Buy groceries',
        'description': u'Milk, Cheese, Pizza, Fruit, Tylenol',
        'done': False
    },
    {
        'id': 2,
        'title': u'Learn Python',
        'description': u'Need to find a good Python tutorial on the web',
        'done': False
    }
]

##SECURITY##

auth = HTTPBasicAuth()
@auth.get_password
def get_password(username):
    print(username)
    if username == 'miguel':
        return 'wat'
    return None

@auth.error_handler
def unauthorized():
    return make_response(jsonify({'error': 'Unauthorized access'}), 401)


##ERROR HANDLER##
@app.errorhandler(404)
def not_found(error):
    return make_response(jsonify({'error': 'Not found'}), 404)

##URI LIST##

##curl -i http://localhost:5000/todo/api/v1.0/tasks
@app.route('/todo/api/v1.0/tasks', methods=['GET'])
@auth.login_required
def get_tasks():
    return jsonify({'tasks': tasks})

@app.route('/todo/api/v1.0/tasksdetail', methods=['GET'])
def get_tasks_detail():
    return jsonify({'tasks': [make_public_task(task) for task in tasks]})

##curl -i http://localhost:5000/todo/api/v1.0/tasks/2
@app.route('/todo/api/v1.0/tasks/<int:task_id>', methods=['GET'])
def get_task(task_id):
    selTask = [task for task in tasks if task['id'] == task_id]
    if len(selTask) == 0:
        abort(404)
    return jsonify({'task': selTask[0]})

##curl -i -H "Content-Type: application/json" -X POST -d "{\"title\":\"Read a book\", """description""":"""awesome"""}" http://localhost:5000/todo/api/v1.0/tasks
@app.route('/todo/api/v1.0/tasks', methods=['POST'])
def create_task():
    if not request.json or not 'title' in request.json:
        abort(400)
    task = {
        'id': tasks[-1]['id'] + 1,
        'title': (request.json['title']+ " hmmm"),
        'description': request.json.get('description', "maybe_empty"),
        'done': False
    }
    tasks.append(task)
    return jsonify({'task': task}), 201

##curl -i -H "Content-Type: application/json" -X PUT -d "{\"title\":\"chang255\", \"description\":\"wow\"}" http://localhost:5000/todo/api/v1.0/tasks/2
@app.route('/todo/api/v1.0/tasks/<int:task_id>', methods=['PUT'])
def update_task(task_id):
    task = [task for task in tasks if task['id'] == task_id]
    if len(task) == 0:
        abort(404)
    if not request.json:
        abort(400)
    if 'title' in request.json and isinstance(type(request.json['title']), str):
        #di python3, unicode ga ada, jandi ganti dengan isinstance
        #dicheck apakah data yang baru merupakan string normal
        abort(400)
    if 'description' in request.json and isinstance(type(request.json['description']), str):
        abort(400)
    if 'done' in request.json and type(request.json['done']) is not bool:
        abort(400)
    task[0]['title'] = request.json.get('title', task[0]['title'])
    task[0]['description'] = request.json.get('description', task[0]['description'])
    task[0]['done'] = request.json.get('done', task[0]['done'])
    return jsonify({'task': task[0]})

##curl -i -X DELETE http://localhost:5000/todo/api/v1.0/tasks/2
@app.route('/todo/api/v1.0/tasks/<int:task_id>', methods=['DELETE'])
def delete_task(task_id):
    task = [task for task in tasks if task['id'] == task_id]
    if len(task) == 0:
        abort(404)
    tasks.remove(task[0])
    return jsonify({'result': True})

@app.route("/")
def index():
    return '<h1 style="color:red">Click bait<h1><input type="button" value="test" onclick=alerts()><script>function alerts(){alert("B")}</script>';

##IMPORVING WEB SERVICE##
def make_public_task(task):
    new_task = {}
    for field in task:
        if field == 'id':
            new_task['uri'] = url_for('get_task', task_id=task['id'], _external=True)
        else:
            new_task[field] = task[field]
    return new_task




##MAIN##
if __name__ == '__main__':
    app.run(debug=True, threaded=True)