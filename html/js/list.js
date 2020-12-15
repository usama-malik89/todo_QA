function fillPage() {
    fetch('http://localhost:9092/list/read')
        .then(
            function (response) {
                if (response.status !== 200) {
                    console.log('Looks like there was a problem. Status Code: ' +
                        response.status);
                    return;
                }

                // Examine the text in the response
                response.json().then(function (listData) {


                    let row = document.getElementById("listContainer");
                    row.innerHTML = '';
                    //let data = Object.keys(listData[0]);

                    console.log(listData);
                    for (let list of listData) {
                        row.insertAdjacentHTML("beforeend", buildList(list["name"], list["id"], list["colour"]));
                    }

                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

function buildList(title, id, colour) {
    return '<div class="col-xl-4" id="' + id + '">\
    <div class="row d-flex justify-content-center container">\
        <div class="col-md-12">\
            <div class="card-hover-shadow-2x mb-3 card">\
                <div class="card-header-tab card-header" style="background: '+ colour + '">\
                    <div class="card-header-title font-size-lg text-capitalize font-weight-normal" style="color: white">\
                        <i class="fa fa-tasks"></i>&nbsp;' + title + '</div>\
                </div>\
                <div class="scroll-area-sm">\
                    <perfect-scrollbar class="ps-show-limits">\
                        <div style="position: static;" class="ps ps--active-y">\
                            <div class="ps-content">\
                                <ul class=" list-group list-group-flush" id="testing">\
    \
                                </ul>\
                            </div>\
                        </div>\
                    </perfect-scrollbar>\
                </div>\
                <div class="d-block text-right card-footer">\
                    <button class="mr-2 btn btn-link btn-sm deleteBtn">Delete List</button>\
                    <button class="btn btn-primary" style="background: '+ colour + '">Add Task</button>\
                </div>\
            </div>\
        </div>\
    </div>\
    </div>'
}

function deleteRecord(id) {
    fetch('http://localhost:9092/list/delete/' + id, {
        method: 'DELETE',
    })
        .then(function (res) {
            res.text()
            fillPage();
            //alert("danger", "Deleted!");
        }) // or res.json()
        .then(res => console.log(res))
}

function postData(data) {
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
            //alert("success", "usama");
        })
        .catch(function (error) {
            console.log('Request failed', error);
            //alert("success", "Something went wrong!");
        })
}

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

        postData(data)
    });

$(document).on("click", ".deleteBtn", function () {
    console.log("DELETE")
    var confirmation = confirm('Are you sure you want to delete this List?');
    if (confirmation == true) {


        let currentList = $(this).closest(".col-md-4");
        console.log(currentList[0].id);
        deleteRecord(currentList[0].id);

        //currentList.remove();
    }
});



fillPage();