window.addEventListener('load', () => {

    refreshEmployeeTable();
    refreshForm();

});

// Refresh Table
const refreshEmployeeTable = () => {

    let employees = getServiceRequest('/employee/alldata') ;

    let propertyList = [
                        {propertyName : 'empNo' , dataType : 'string'},
                        {propertyName : 'fullName' , dataType : 'string'},
                         {propertyName : 'address' , dataType : 'string'},
                        {propertyName : 'mobileNumber' , dataType : 'string'},
                        //{propertyName : 'landNumber' , dataType : 'string'},
                        {propertyName : 'email' , dataType : 'string'},
                        {propertyName : 'nic' , dataType : 'string'},
                        {propertyName : 'gender' , dataType : 'string'},
                        {propertyName : 'dob' , dataType : 'string'},
                        {propertyName : 'civilStatus' , dataType : 'string'},
                        {propertyName : getDesignation , dataType : 'function'},
                        {propertyName : getEmployeeStatus , dataType : 'function'}
    ];

    fillDataIntoTable(tableEmployeeBody , employees , propertyList , employeeEdit , employeeDelete , employeePrint);
}


const getDesignation = (dataOb) => {
    return dataOb.designation_id.name;
}

const getEmployeeStatus = (dataOb) => {
    return dataOb.employeeStatus_id.status;
}

const employeeEdit = (dataOb , index) => {
    textFullName.value = dataOb.fullName;
    selectCallingName.value = dataOb.callingName;
    const fullNameValue = textFullName.value;
    let fullNameParts = fullNameValue.split(" ");
    fullNameParts.forEach(name => {
        let option = document.createElement('option');
        // option.value = name;
        option.innerText = name;
        selectCallingName.appendChild(option);
    });

    textNIC.value = dataOb.nic;
    if(dataOb.gender == 'Male'){
        selectGenderMale.checked = true;
    }else {
        selectGenderFemale.checked = true;
    }
    textAddress.value = dataOb.address;
    dateOfBirth.value = dataOb.dob;
    textEmail.value = dataOb.email;
    selectCivilStatus.value = dataOb.civilStatus;
    textMobileNumber.value = dataOb.mobileNumber;
    if (dataOb.landNumber == undefined){
        textLandNumber.value = "";
    }else {
        textLandNumber.value = dataOb.landNumber;
    }
    selectDesignation.value = JSON.stringify(dataOb.designation_id);
    selectEmployeeStatus.value = JSON.stringify(dataOb.employeeStatus_id);

    employee = JSON.parse(JSON.stringify(dataOb));
    oldEmployee =  JSON.parse(JSON.stringify(dataOb));

    $("#modalEmployeeForm").modal('show');

    buttonUpdate.removeAttribute("style");
    buttonSubmit.style.display = "none";

}

const employeeDelete = async (dataOb , rowIndex) => {
    let userConfirm = window.confirm("Are you sure to Delete this Employee?  \n" + "Employee Name : " + dataOb.fullName);
    if (userConfirm) {
        let deleteResponse =await getHTTPServiceRequest(`/employee/delete/${dataOb.id} `, "DELETE" , dataOb);

        if (deleteResponse) {
            window.alert("Successfully Deleted!");
            refreshEmployeeTable();
            refreshForm();
            $("#modalEmployeeForm").modal('hide');
        } else {
            window.alert("Failed to Delete this Employee! \n" + deleteResponse);
        }
        refreshEmployeeTable();
    }
}

const employeePrint = (dataOb , index) => {
    tdFullName.innerText = dataOb.fullName;
    tdCallingName.innerText = dataOb.callingName;
    tdNic.innerText = dataOb.nic;
    tdGender.innerText = dataOb.gender;
    tdDob.innerText = dataOb.dob;
    tdEmail.innerText = dataOb.email;
    tdCivilStatus.innerText = dataOb.civilStatus;
    tdMobileNumber.innerText = dataOb.mobileNumber;
    tdLandNumber.innerText = dataOb.landNumber;
    tdDesignation.innerText = dataOb.designation_id.name;
    tdEmployeeStatus.innerText = dataOb.employeeStatus_id.status;

    $("#printModal").modal('show');

}

const buttonPrintRow = () => {
    let newWindow = window.open();

    let printView = "<head><title>BIT Project 2024 : D-12 Practical</title>\<link rel='stylesheet' href='../resources/bootstrap-5.0.2-dist/css/bootstrap.css'></head>" + "<body>" + tableEmployeePrint.outerHTML + "</body>";
    newWindow.document.write(printView);

    setTimeout(() => {
       newWindow.stop();
       newWindow.print();
       newWindow.close();
    } , 500);

}

const checkFormErrors = () => {
    let errors = "";

    if(employee.fullName == ""){
        errors += "Please Enter Valid Full Name ...! \n";
    }
    if(employee.callingName == ""){
        errors += "Please Select Valid Calling Name ...! \n";
    }
    if(employee.nic == ""){
        errors += "Please Enter Valid NIC ...! \n";
    }
    if(employee.gender == ""){
        errors += 'Please Enter Valid Gender ...! \n';
    }
    if(employee.dob == ""){
        errors += 'Please Select Valid Date of Birth ...! \n';
    }
    if (employee.address == ""){
        errors += 'Please Enter Valid Address ...! \n';
    }
    if(employee.email == ""){
        errors += 'Please Enter Valid Email Address ...! \n';
    }
    if(employee.civilStatus == ""){
        errors += 'Please Select Valid Civil Status ...! \n';
    }
    if(employee.mobileNumber == ""){
        errors += 'Please Enter Valid Mobile Number ...! \n';
    }
    if(employee.landNumber == ""){
        errors += 'Please Enter Valid Land Number ...! \n';
    }
    if(employee.designation_id== ""){
        errors += 'Please Enter Valid Designation ...! \n';
    }
    if(employee.employeeStatus_id == ""){
        errors += 'Please Select Employee Status ...! \n';
    }
    return errors;
}

const buttonEmployeeSubmit = async () =>{
    let errors = checkFormErrors();
    if(errors == ""){
        let userConfirm = window.confirm("Are you sure to Add this Employee?  \n" + "Employee Name : " + employee.fullName);
        if(userConfirm){
           try {
               let postResponse = await getHTTPServiceRequest("/employee/insert" , "POST" , employee);
               if(postResponse){
                   window.alert("Successfully Added!");
                   refreshEmployeeTable();
                   refreshForm();
                   $("#modalEmployeeForm").modal('hide');
               }else {
                   window.alert("Failed to submit this Employee! \n" + postResponse);
               }
           }catch (error){
               console.error("Error during HTTP request:", error);
               window.alert("An unexpected error occurred. Please try again.");
           }
        }
    }else {
        window.alert("Form has Following Errors ... \n" + errors);
    }

    refreshEmployeeTable();
}

const checkFormUpdates = () =>{

    let updates = "";

    if (employee != null && oldEmployee != null){
        if (employee.fullName != oldEmployee.fullName){
            updates += "Full name has been updated! \n";
        }
        if (employee.callingName != oldEmployee.callingName){
            updates += "Calling name has been updated! \n";
        }
        if (employee.nic != oldEmployee.nic){
            updates += "NIC has been updated! \n";
        }
        if (employee.gender != oldEmployee.gender){
            updates += "Gender has been updated! \n";
        }
        if (employee.dob != oldEmployee.dob){
            updates += "Date of Birth has been updated! \n";
        }
        if (employee.email != oldEmployee.email){
            updates += "Email has been updated! \n";
        }
        if (employee.civilStatus != oldEmployee.civilStatus){
            updates += "Civil Status has been updated! \n";
        }
        if (employee.mobileNumber != oldEmployee.mobileNumber){
            updates += "Mobile Number has been updated! \n";
        }
        if (employee.landNumber != oldEmployee.landNumber){
            updates += "Land Number has been updated! \n";
        }
        if (employee.designation_id.name != oldEmployee.designation_id.name){
            updates += "Designation has been updated! \n";
        }
        if (employee.employeeStatus_id.status != oldEmployee.employeeStatus_id.status){
            updates += "Empoyee Status has been updated! \n";
        }
    }

    return updates;
}

const buttonEmployeeUpdate = async () =>{
    let errors = checkFormErrors();
    if(errors == ""){
        let updates = checkFormUpdates();
        if(updates == ""){
            window.alert("Nothing To Updated!   \n");
        }else{
            let userConfirm = window.confirm("Are you sure to update following Updates \n" + updates);
            if (userConfirm){
                let putResponse = await getHTTPServiceRequest(`/employee/update/${employee.id}` , "PUT" , employee);
                if(putResponse){
                    window.alert("Successfully Updated!");
                    refreshEmployeeTable();
                    refreshForm();
                    $("#modalEmployeeForm").modal('hide');
                }else {
                    window.alert("Failed to update this Employee! \n" + putResponse);
                }
            }
        }
    }else {
        window.alert("You Have this Following Errors ... \n" + errors);
    }
}

const refreshForm = () => {
        employee = new Object();

        formEmployee.reset();

        setDefault([textFullName,selectCallingName,textNIC,selectGenderMale,selectGenderFemale,dateOfBirth,textEmail,selectCivilStatus,textMobileNumber,textLandNumber,selectDesignation,selectEmployeeStatus]);

        let designations =getServiceRequest('/designation/alldata');

        let employeeStatus = getServiceRequest('/employeeStatus/alldata');

        fillDataIntoSelect("selectDesignation", "Please Select Designation", designations, 'name');
        fillDataIntoSelect("selectEmployeeStatus", "Please Select Employee Status", employeeStatus, 'status');

    buttonUpdate.style.display = "none";
    buttonSubmit.removeAttribute("style");

    }

