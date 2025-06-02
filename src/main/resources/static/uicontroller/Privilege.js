window.addEventListener("load" , () => {
    refreshPrivilegeTable();
    refreshForm();
});

const refreshPrivilegeTable = () => {
     let privileges = getServiceRequest("/privilege/alldata");

    let columns = [
        {propertyName : getRole ,   dataType : 'function'},
        {propertyName : getModule , dataType : 'function'},
        {propertyName : getSelect , dataType : 'function'},
        {propertyName : getInsert , dataType : 'function'},
        {propertyName : getUpdate , dataType : 'function'},
        {propertyName : getDelete , dataType : 'function'}
    ];
    fillDataIntoTable(tableBodyPrivilege , privileges , columns , editPrivilege ,deletePrivilege, printPrivilege , true);
}

const checkFormErrors = () => {
    let errors = "";

    if (privilege.role_id == null){
        errors += "Please Select a Role";
    }

    if (privilege.module_id == null){
        errors += "Please Select a Module";
    }

    return errors;
}

const buttonPrivilegeSubmit =async () => {
    let errors = checkFormErrors();
    if(errors === ""){
        let userConfirm = window.confirm("Are you sure to submit privilege Details \n" + "Role :" + privilege.role_id.name + "\n" + "Module :" + privilege.module_id.name);
        if (userConfirm) {
            let postResponse =await getHTTPServiceRequest("/privilege/insert" , "POST" , privilege);
            if (postResponse.message === "OK"){
                window.alert("Saved Successfully");
                refreshPrivilegeTable();
                refreshForm();
            }else {
                window.alert("Failed to submit, has following Errors \n" + postResponse.message);
            }
        }
    }else {
        window.alert("Form has following errors \n" + errors);
    }
    refreshPrivilegeTable();
}

const checkFormUpdates = () => {
    let updates = "";

    if (privilege !== null && oldPrivilege !== null){
        if (privilege.role_id.name != oldPrivilege.role_id.name){
            updates += "Role is Changed \n";
        }
        if (privilege.module_id.name != oldPrivilege.module_id.name){
            updates += "Module is Changed \n";
        }
        if (privilege.privi_select != oldPrivilege.privi_select){
            updates += "Select Privilege is Changed \n";
        }
        if (privilege.privi_insert != oldPrivilege.privi_insert){
            updates += "Insert Privilege is Changed \n";
        }
        if (privilege.privi_update != oldPrivilege.privi_update){
            updates += "Update Privilege is Changed \n";
        }
        if (privilege.privi_delete != oldPrivilege.privi_delete){
            updates += "Delete Privilege is Changed \n";
        }

    }

    return updates;
}

const buttonPrivilegeUpdate =async () => {
    let errors = checkFormErrors();
    if(errors == ""){
        let updates = checkFormUpdates();
        if(updates == ""){
            window.alert("Nothing to updated");
        }
        else {
            let userConfirm = window.confirm("Are you sure to update privileges?  \n");
            if (userConfirm){
                let putResponse = await getHTTPServiceRequest(`/privilege/update/${privilege.id}` , "PUT" , privilege);
                console.log(putResponse.message === "OK");
                if (putResponse){
                    window.alert("Update Successfully");
                    refreshForm();
                    refreshPrivilegeTable();
                }else {
                    window.alert("Failed to Submit \n" + putResponse.message);
                }
            }
        }
    }else {
        window.alert("Form has following Errors \n" + errors);
    }
}

    const getRole = (dataOb) => {
        return dataOb.role_id.name;
    }
    const getModule = (dataOb) => {
        return dataOb.module_id.name;
    }
    const getSelect = (dataOb) => {
        if(dataOb.privi_select){
            return "Granted";
        }else {
            return "Not-Granted";
        }
    }
    const getInsert = (dataOb) => {
        if(dataOb.privi_insert){
            return "Granted";
        }else {
            return "Not-Granted";
        }
    }
    const getUpdate = (dataOb) => {
        if(dataOb.privi_update){
            return "Granted";
        }else {
            return "Not-Granted";
        }
    }
    const getDelete = (dataOb) => {
        if(dataOb.privi_delete){
            return "Granted";
        }else {
            return "Not-Granted";
        }
    }

    const editPrivilege = (dataOb , rowIndex) => {

        privilege = JSON.parse(JSON.stringify(dataOb));
        oldPrivilege = JSON.parse(JSON.stringify(dataOb));

        selectRole.value = JSON.stringify(dataOb.role_id);
        selectModule.value = JSON.stringify(dataOb.module_id);

        if(privilege.privi_select){
            chkboxSelect.checked = true;
            labelSelect.innerText = "Select Privilege Granted";
        }else{
            chkboxSelect.checked = false;
            labelSelect.innerText = "Select Privilege Not Granted";
        }
        if(privilege.privi_insert){
            chkboxInsert.checked = true;
            labelInsert.innerText = "Insert Privilege Granted";
        }else{
            chkboxInsert.checked = false;
            labelInsert.innerText = "Insert Privilege Not Granted";
        }
        if(privilege.privi_update){
            chkboxUpdate.checked = true;
            labelUpdate.innerText = "Insert Privilege Granted";
        }else{
            chkboxUpdate.checked = false;
            labelUpdate.innerText = "Insert Privilege Not Granted";
        }
        if(privilege.privi_delete){
            chkboxDelete.checked = true;
            labelDelete.innerText = "Insert Privilege Granted";
        }else{
            chkboxDelete.checked = false;
            labelDelete.innerText = "Insert Privilege Not Granted";
        }
        buttonUpdate.removeAttribute("style");
        buttonSubmit.style.display = "none";

    }
    const deletePrivilege = async (dataOb) => {

    let userConfirm = window.confirm("Are You Sure to Delete following Privilege \n" + "Role :" + dataOb.role_id.name);
    if(userConfirm){
        try {
            let serviceResponse = await getHTTPServiceRequest(`/privilege/delete/${dataOb.id}`, "DELETE", null);
            if (serviceResponse.message === "OK") {
                window.alert("Delete Successfully");
                refreshPrivilegeTable();
            } else {
                window.alert("Failed Delete , has following Error \n" + serviceResponse.message);
            }
        }catch (error){
            window.alert("Failed Delete , has following Error \n" + error.message);
        }
        refreshPrivilegeTable();
    }
}
    const printPrivilege = (dataOb , rowIndex) => {
        tdRole.innerText = dataOb.role_id.name;
        tdModule.innerText = dataOb.module_id.name;
        tdSelect.innerText = dataOb.privi_select;
        tdInsert.innerText = dataOb.privi_insert;
        tdUpdate.innerText = dataOb.privi_update;
        tdDelete.innerText = dataOb.privi_delete;

        $("#printPrivilegeModal").modal('show');

    }
    const printButtonRow = () => {
        let newWindow = window.open();

        let printView = "<head><title>BIT Project 2024 : D-12 Practical</title>\<link rel='stylesheet' href='../resources/bootstrap-5.0.2-dist/css/bootstrap.css'></head>" + "<body>" + tablePrivilegeModal.outerHTML + "</body>";
        newWindow.document.write(printView);

        setTimeout(() => {
            newWindow.stop();
            newWindow.print();
            newWindow.close();
        } , 500);
    }
    const refreshForm = () => {
        privilege = new Object();

        formPrivilege.reset();

        setDefault([selectRole,selectModule,chkboxSelect,chkboxInsert,chkboxUpdate,chkboxDelete]);

        let roles = getServiceRequest("/role/alldata");
        let modules = getServiceRequest("/module/alldata");

        fillDataIntoSelect("selectRole", "Please Select Role", roles, 'name');
        fillDataIntoSelect("selectModule", "Please Select Module", modules, 'name');

        privilege.privi_select = false;
        privilege.privi_insert = false;
        privilege.privi_update = false;
        privilege.privi_delete = false;

        //disabale update button

        buttonUpdate.style.display = "none";
        buttonSubmit.removeAttribute("style");

    }


