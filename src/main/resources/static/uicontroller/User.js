// browser load event handler
window.addEventListener('load', () => {
    // call table refresh
    refreshUserTable();
    refreshForm();
});

const refreshUserTable =  () => {
    const users =  getServiceRequest("/user/alldata");
    let propertyList = [
        {propertyName : getEmployee , dataType : 'function'},
        {propertyName: 'username', dataType : 'string'},
        {propertyName: 'email', dataType : 'string'},
        {propertyName: getRoles , dataType: 'function'},
        {propertyName: getUserStatus , dataType : 'function'},
    ];

    fillDataIntoTable(tableBodyUser , users , propertyList , userEdit , userDelete , userPrint);

}

const  getEmployee = (dataOb) => {
      return dataOb.username;

}
const  getRoles = (dataOb) => {
    let roles = "";
    dataOb.roles.forEach((role , index) => {
        if (dataOb.roles.length - 1 === index) {
            roles +=role.name;
        }else {
            roles +=role.name + "-";
        }
    });
    return roles;
}
const getUserStatus = (dataOb) => {
    if(dataOb.status) {
        return "Active";
    }else {
        return "Inactive";
    }
}

const checkFormError = () => {
    let errors = "";

    if (user.username == null){
        errors += "Please enter a username \n";
    }
    if (user.password == null){
        errors += "Please enter a password \n";
    }
    if (user.email == null){
        errors += "Please enter a email \n";
    }
    if (user.note == null){
        errors += "Please enter a note \n";
    }
    if (user.roles.length === 0){
        errors += "Please select at least one role \n";
    }

    return errors;
}

const buttonUserSubmit =async () => {
    let errors = checkFormError();
    if(errors === "") {
        let userConfirm = window.confirm("Are you sure to Add Following User? \n" + "User Name :" + user.username);
        if(userConfirm) {
           try {
               let postResponse = await getHTTPServiceRequest("/user/insert" , "POST" , user);
               console.log(postResponse);
               if (postResponse.message === "OK") {
                   window.alert("Successfully added!");
                   refreshUserTable();
                   refreshForm();
               }else {
                   window.alert("Failed to add User  Form has Following Errors \n" + errors);
               }
           }catch (error){
               console.error("Failed HTTP request:", error);
               window.alert("Failed to add User.\nError: " + error.message);
           }
        }
    }else {
        window.alert("Form has Following Errors \n" + errors);
    }
}

const checkFormUpdates = () => {
    let updates = "";

    if (user != null && oldUser != null) {
        if (user.username !== oldUser.username) {
            updates += "Username is Changed \n";
        }
        if (user.email !== oldUser.email){
            updates += "Email is Changed \n";
        }
        if (user.roles.length !== oldUser.roles.length) {
            updates += "Roles is Changed \n";
        }
        if (user.note !== oldUser.note){
            updates += "Note is Changed \n";
        }
        if (user.status !== oldUser.status){
            updates += "Status is Changed \n";
        }
    }

    return updates;
}

const buttonUserUpdate =async () => {
    let errors = checkFormError();
    if(errors === "") {
        let updates = checkFormUpdates();
        if (updates !== ""){
            let userConfirm = window.confirm("Are you sure to Update Following Changes? \n" + updates);
            if(userConfirm) {
                try {
                    let putResponse = await getHTTPServiceRequest(`/user/update/${user.id}` , "PUT" , user);
                    console.log(putResponse);
                    if (putResponse.message === "OK") {
                        window.alert("Successfully updated!");
                        refreshUserTable();
                        refreshForm();
                    }else {
                        window.alert("Failed to update User  Form has Following Errors \n" + errors);
                    }
                }catch (error){
                    console.error("Failed HTTP request:", error);
                    window.alert("Failed to update User.\nError: " + error.message);
                }
            }
        }else {
            window.alert("Form has Nothing to Updated \n" + errors);
        }

    }else {
        window.alert("Form has Following Errors :\n" + errors);
    }
}
const buttonUserClear = () =>{
    userConfirm = window.confirm("Are You Sure to Clear form?");
    if(userConfirm) {
        refreshForm();
    }
}

const userEdit = (dataOb) => {

    console.log(dataOb);

    user = JSON.parse(JSON.stringify(dataOb));
    oldUser = JSON.parse(JSON.stringify(dataOb));


    let employes = getServiceRequest("/employee/alldata");
    fillDataIntoSelect("selectEmployee" , "Select Employee" , employes , "fullName" , ["email"]);
    selectEmployee.disabled = true;

    selectEmployee.value = JSON.stringify(user.employeeResponseDto);

    if(user.status){
        chkBoxStatus.checked = "Checked";
        labelUserStatus.innerText = "User Account is Active";
    } else {
        chkBoxStatus.checked = "";
        labelUserStatus.innerText = "User Account is In-Active";
    }

    textUserName.value = user.username;
    textEmail.value = user.email;
    textNote.value = user.note;

    textPassword.disabled = true;
    textRetypePassword.disabled = true;

    let roles = getServiceRequest("/role/alldata");
    let divRoles = document.querySelector("#divRoles");
    divRoles.innerHTML = "";

    roles.forEach((role , index) => {
        let div = document.createElement("div");
        div.className = "form-check form-check-inline ms-5";
        divRoles.appendChild(div);

        let inputCheckBox = document.createElement("input");
        inputCheckBox.type = "checkbox";
        inputCheckBox.className = "form-check-input";

        inputCheckBox.onclick =function () {
            if(this.checked) {
                user.roles.push(role);
            }else {
                let extIndex = user.roles.map(userRole => userRole.name).indexOf(role.name);
                if (extIndex !== -1) {
                    user.roles.splice(extIndex, 1);
                }
            }
        }

        let extIndex = user.roles.map(userRole => userRole.name).indexOf(role.name);
        if (extIndex !== -1) {
            inputCheckBox.checked = true;
        }

        const checkBoxId = `roleCheckbox-${index}`
        inputCheckBox.id = checkBoxId;

        div.appendChild(inputCheckBox);

        let label = document.createElement("label");
        label.className = "form-check-label";
        label.setAttribute("for", checkBoxId);
        label.innerText = role.name;
        div.appendChild(label);
    });

    buttonUpdate.removeAttribute("style");
    buttonSubmit.style.display = "none";

}
const userDelete = async (dataOb) => {
    let userConfirm = confirm("Are you sure you want to delete following user? \n" + "Username :" + dataOb.username + "\n");
    if (userConfirm) {
        try {
            let serviceResponse = await getHTTPServiceRequest(`/user/delete/${dataOb.id}` , "DELETE" , dataOb );
            console.log(serviceResponse);
            if(serviceResponse.message === "OK") {
                window.alert("Delete Successfully!");
                refreshUserTable();
            }else {
                window.alert("Failed to delete user , has following errors \n" + serviceResponse.message );
            }
        }catch (error) {
            window.alert("Failed to delete user, " + error.message);
        }
        refreshUserTable();
    }
}
const userPrint = (dataOb) => {}

const refreshForm = () => {

    formUser.reset();

     user = {};
     user.roles = [];

    try {
        let employees = getServiceRequest("/employee/listwithoutuseraccount");
        fillDataIntoSelect("selectEmployee" , "Select Employee" , employees , "fullName");
    }catch (error) {
        console.error("Error Loading Employee Data" , error);
    }
    chkBoxStatus.checked = "Checked";
    labelUserStatus.innerText = "User Account is Active";
    user.status = true;

    let roles = getServiceRequest("/role/alldata");
    let divRoles = document.querySelector("#divRoles");
    divRoles.innerHTML = "";

    roles.forEach((role , index) => {
        let div = document.createElement("div");
        div.className = "form-check form-check-inline ms-5";
        divRoles.appendChild(div);

        let inputCheckBox = document.createElement("input");
        inputCheckBox.type = "checkbox";
        inputCheckBox.className = "form-check-input";

        inputCheckBox.onclick =function () {
            if(this.checked) {
                user.roles.push(role);
            }else {
                let extIndex = user.roles.map(userRole => userRole.name).indexOf(role.name);
                if (extIndex !== -1) {
                    user.roles.splice(extIndex, 1);
                }
            }
        }

        const checkBoxId = `roleCheckbox-${index}`
        inputCheckBox.id = checkBoxId;

        div.appendChild(inputCheckBox);

        let label = document.createElement("label");
        label.className = "form-check-label";
        label.setAttribute("for", checkBoxId);
        label.innerText = role.name;
        div.appendChild(label);
    });
    selectEmployee.disabled = false;
    textPassword.disabled = false;
    textRetypePassword.disabled = false;


    setDefault([selectEmployee , textUserName , textPassword , textRetypePassword , textEmail , textNote]);

    buttonUpdate.style.display = "none";
    buttonSubmit.removeAttribute("style");

}