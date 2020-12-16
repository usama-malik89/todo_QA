var listToAdd = 0;
var listToEdit = 0;

/* ################ FUNCTIONS ################ */
function buildList(title, id, colour) {
    return '<div class="col-xl-4" id="' + id + '">\
    <div class="row d-flex justify-content-center container">\
        <div class="col-md-12">\
            <div class="card-hover-shadow-2x mb-3 card">\
                <div class="card-header" style="background: '+ colour + '">\
                    <div class="col-md-10">\
                        <div style="color: white">&nbsp;' + title + '</div>\
                    </div>\
                    <div class="col-md-2 float-right">\
                        <button class="border-0 btn btn-outline-success" data-toggle="modal" data-target="#editListModalCenter" type="button" data-button="'+ id + '">\
                            <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="white" class="bi bi-pencil-fill" viewBox="0 0 16 16">\
                                <path fill-rule="evenodd" d="M9.5 13a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0zm0-5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z"/>\
                            </svg>\
                        </button>\
                    </div>\
                </div>\
                <div class="scroll-area-sm">\
                    <perfect-scrollbar class="ps-show-limits">\
                        <div style="position: static;" class="ps ps--active-y">\
                            <div class="ps-content">\
                                <ul class=" list-group list-group-flush" id="'+ id + '-content">\
                                \
                                </ul>\
                            </div>\
                        </div>\
                    </perfect-scrollbar>\
                </div>\
                <div class="d-block text-right card-footer">\
                    <button type="button" class="mr-2 btn btn-link btn-sm deleteBtn">Delete List</button>\
                    <button type="button" data-button="'+ id + '" class="btn btn-primary" style="background: ' + colour + '" data-toggle="modal" data-target="#addTaskModalCenter">Add Task</button>\
                </div>\
            </div>\
        </div>\
    </div>\
    </div>'
}

function getOneList(id) {
    fetch('http://localhost:9092/list/read/' + id)
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

                    $('#edit-ListName').val(data.name);
                    $('#edit-priorityCheck').prop('checked', data.priority);
                    let colourID = data.colour.substring(1);
                    let selected = document.getElementById(colourID);
                    selected.checked = true;
                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

function deleteListRecord(id) {
    fetch('http://localhost:9092/list/delete/' + id, {
        method: 'DELETE',
    })
        .then(function (res) {
            res.text()
            fillPage();
        })
        .then(res => console.log(res))
}

function postListData(data) {
    fetch('http://localhost:9092/list/create', {
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

function putListData(data) {
    fetch('http://localhost:9092/list/update/' + listToEdit, {
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

/* ################ EVENT LISTENERS ################ */
document
    .querySelector("form.addRecord")
    .addEventListener("submit", function (stop) {

        stop.preventDefault();
        let formElements = document.querySelector("form.addRecord").elements;
        console.log(formElements)
        let name = formElements["todoName"].value;
        let colour = document.querySelector('input[name="color"]:checked').value;
        let data = {
            "name": name,
            "colour": colour
        }
        console.log("Data to post", data)

        postListData(data)
    });

$(document).on("click", ".deleteBtn", function () {
    console.log("DELETE")
    var confirmation = confirm('Are you sure you want to delete this List and it\'s Tasks?');
    if (confirmation == true) {
        let currentList = $(this).closest(".col-xl-4");
        console.log(currentList[0].id);
        deleteListRecord(currentList[0].id);
    }
});

$(document).on("click", "#confirmListEdit", function () {
    console.log("Add to " + listToAdd)

    let formElements = document.querySelector("form.editList").elements;
    let name = formElements["edit-ListName"].value;
    let colour = document.querySelector('input[name="editColor"]:checked').value;

    data = {
        "name": name,
        "colour": colour
    }
    putListData(data);
});

/* ################ MODAL GET LIST ID ################ */
$('#editListModalCenter').on('show.bs.modal', function (e) {
    var $trigger = $(e.relatedTarget);
    let id = $trigger.data('button');
    listToEdit = id;
    getOneList(id);
})