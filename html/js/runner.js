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
                        let listContent = document.getElementById(list["id"] + "-content");
                        let listItems = list["items"];
                        for (let items of listItems) {
                            listContent.insertAdjacentHTML("beforeend", buildItem(items["name"], list["colour"], items["id"], items["priority"]));
                        }
                    }
                });
            }
        )
        .catch(function (err) {
            console.log('Fetch Error :-S', err);
        });
}

fillPage();