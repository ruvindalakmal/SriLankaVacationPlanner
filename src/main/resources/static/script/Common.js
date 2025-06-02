const fillDataIntoTable = (tableBodyId , dataList , propertyList , editFunction , deleteFunction , printFunction) =>{
    tableBodyId.innerHTML = '';

    dataList.forEach((dataOb , index) => {
        let tr = document.createElement('tr');

        let tdId = document.createElement("td");
        tdId.className = "text-center";
        tdId.innerText = parseInt(index) + 1;
        tr.appendChild(tdId);

        for (const property of propertyList) {
            let td = document.createElement('td');

            if(property.dataType === 'string'){
                td.className = "text-center";
                td.innerText = dataOb[property.propertyName];
            }else if(property.dataType === 'function'){
                td.innerHTML = property.propertyName(dataOb);
            }
            tr.appendChild(td);
        }

        let tdButtons = document.createElement("td");

        let buttonEdit = document.createElement("button");
        buttonEdit.innerText = "Edit";
        buttonEdit.className = "btn btn-outline-secondary btn-sm me-1";
        tdButtons.appendChild(buttonEdit);
        buttonEdit.onclick = () =>{
            editFunction(dataOb , index);
        }

        let buttonDelete = document.createElement("button");
        buttonDelete.innerText = "Delete";
        buttonDelete.className = "btn btn-outline-danger btn-sm me-1";
        tdButtons.appendChild(buttonDelete);
        buttonDelete.onclick = () =>{
            deleteFunction(dataOb , index);

        }

        let buttonPrint = document.createElement("button");
        buttonPrint.innerText = "Print";
        buttonPrint.className = "btn btn-outline-success btn-sm me-1";
        tdButtons.appendChild(buttonPrint);
        buttonPrint.onclick = () =>{
            printFunction(dataOb , index);

        }

        tr.appendChild(tdButtons);

        tableBodyId.appendChild(tr);
    });
}


const fillDataIntoSelect = (parentId , message , dataList , displayProperty) => {

    let selectElement = document.getElementById(parentId);

    if (!selectElement) {
        console.error(`Element with ID '${parentId}' not found.`);
        return;
    }

    selectElement.innerHTML = "";

    let option = document.createElement('option');
    option.value = "";
    option.innerText = message;
    option.selected = 'selected';
    option.disabled = 'disabled';

    selectElement.appendChild(option);

    dataList.forEach(dataOb => {
        let option = document.createElement('option');
        option.value = JSON.stringify(dataOb);
        option.innerText = dataOb[displayProperty];

        selectElement.appendChild(option);
    });
}

const setDefault = (elements) => {
    elements.forEach(element => {
        element.classList.remove('is-valid');
        // element.classList.add('is-invalid');
    })
}

const getServiceRequest = (url) => {

    let getServiceResponse = [];

    $.ajax({
        url : url,
        type : 'GET',
        contentType : 'json',
        async : false,
        success : function (response) {
            getServiceResponse = response;
        },
        error : function (xhr , status , error){
            console.log('Error' , error);
        }

    });

    return getServiceResponse;
}

const getHTTPServiceRequest = (url, method, data) => {
    console.log(url);
    return new Promise((resolve, reject) => {
        $.ajax({
            url: url,
            type: method,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (response) {
                resolve({ message: response.message || "OK", data: response });
            },
            error: (xhr) => {
                let errorMsg = "An error occurred";
                try {
                    const errorResponse = JSON.parse(xhr.responseText);
                    errorMsg = errorResponse.message || xhr.statusText;
                } catch (e) {
                    if (xhr.status === 403) errorMsg = "Permission denied.";
                    else if (xhr.status === 404) errorMsg = "Resource not found.";
                    else if (xhr.status === 500) errorMsg = "Server error.";
                    else errorMsg = xhr.statusText;
                }
                reject({ status: xhr.status, message: errorMsg });
            }
        });
    });
};
