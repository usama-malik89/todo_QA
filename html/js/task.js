var taskToEdit = 0;

/* ################ FUNCTIONS ################ */
function buildItem(name, colour, id, priority) {
    let first = '<li class="list-group-item">\
    <div class="todo-indicator" style="background-color:'+ colour + '"></div>\
    <div class="widget-content p-0">\
        <div class="widget-content-wrapper">\
            <div class="mr-2">\
                <div class="custom-checkbox custom-control"> <input class="custom-control-input" id="exampleCustomCheckbox12" type="checkbox"><label class="custom-control-label" for="exampleCustomCheckbox12">&nbsp;</label> </div>\
            </div>\
            <div>\
                <div>'+ name + '';
    let badge = '<div class="badge badge-danger ml-2">Priority</div>'
    if (priority) {
        first = first.concat(badge)
    }

    let end = '</div>\
        </div>\
        <div class="widget-content-right"> <button class="border-0 btn btn-outline-success" data-toggle="modal" data-target="#editTaskModalCenter" type="button" data-button="'+ id + '"> <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="' + colour + '" class="bi bi-pencil-fill" viewBox="0 0 16 16">\
        <path fill-rule="evenodd" d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>\
    </svg> </button> </div>\
        </div>\
        </div>\
        </li>'

    return first.concat(end);
}

function getOneTask(id) {
    fetch('http://localhost:9092/todo_item/read/' + id)
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }
                // Examine the text in the response
                response.json().then(function (data) {
                    console.log("MY DATA OBJ", data)

                    $('#edit-taskName').val(data.name);
                    $('#edit-priorityCheck').prop('checked', data.priority);

                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

function postTaskData(data) {
    fetch('http://localhost:9092/todo_item/create', {
        method: 'post', //post, put,delete
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            fillPage();
        })
        .catch(function (error) {
            console.log('Request failed', error);
        })
}

function putTaskData(data) {
    fetch('http://localhost:9092/todo_item/update/' + taskToEdit, {
        method: 'put', //post, put,delete
        headers: {
            "Content-type": "application/json; charset=UTF-8"
        },
        body: JSON.stringify(data)
    })
        .then(function (data) {
            console.log('Request succeeded with JSON response', data);
            fillPage();
        })
        .catch(function (error) {
            console.log('Request failed', error);
        })
}

function deleteTaskRecord(id) {
    fetch('http://localhost:9092/todo_item/delete/' + id, {
        method: 'DELETE',
    })
        .then(function (res) {
            res.text()
            fillPage();
        })
        .then(res => console.log(res))
}

/* ################ EVENT LISTENERS ################ */
$(document).on("click", "#confirmAdd", function () {
    console.log("Add to " + listToAdd)

    let formElements = document.querySelector("form.addTask").elements;
    let name = formElements["add-taskName"].value;
    let priority = formElements["add-priorityCheck"].checked;
    console.log(priority)

    data = {
        "name": name,
        "list": {
            "id": listToAdd
        },
        "priority": priority,
        "done": false
    }
    postTaskData(data);
    $('#add-taskName').val("");
    $('#add-priorityCheck').prop('checked', false);
});

$(document).on("click", "#confirmEdit", function () {
    console.log("Add to " + listToAdd)

    let formElements = document.querySelector("form.editTask").elements;
    let name = formElements["edit-taskName"].value;
    let priority = formElements["edit-priorityCheck"].checked;
    console.log(priority)

    data = {
        "name": name,
        "priority": priority,
        "done": false
    }
    putTaskData(data);
});

$(document).on("click", "#deleteTask", function () {
    var confirmation = confirm('Are you sure you want to delete this Task?');
    if (confirmation == true) {
        deleteTaskRecord(taskToEdit);
        $('#editTaskModalCenter').modal('toggle');
    }
});

/* ################ MODAL GET TASK ID ################ */
$('#addTaskModalCenter').on('show.bs.modal', function (e) {
    var $trigger = $(e.relatedTarget);
    listToAdd = $trigger.data('button');
    console.log(listToAdd);
})

$('#editTaskModalCenter').on('show.bs.modal', function (e) {
    var $trigger = $(e.relatedTarget);
    let id = $trigger.data('button');
    taskToEdit = id;
    getOneTask(id);
})