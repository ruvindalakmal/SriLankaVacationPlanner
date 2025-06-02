window.addEventListener('load', () => {
    refreshVehicleTable();
    refreshForm();
});

const refreshVehicleTable =  () => {
    const vehicles = getServiceRequest("/transport/alldata");

    let propertyList = [
        {propertyName : getVehicleType , dataType : 'function'},
        {propertyName : "licenseNo", dataType : 'string'},
        {propertyName: 'manufacturer', dataType: 'string'},
        {propertyName: 'model', dataType: 'string'},
        {propertyName: 'year', dataType: 'string'},
        {propertyName: 'seatCount', dataType: 'string'},
        {propertyName: getTransmissionType, dataType: 'function'},
        {propertyName: getFuelType, dataType: 'function'},
        // {propertyName: 'ratePerKM' , dataType: 'number'},
        {propertyName: 'insuranceExpiryDate', dataType: 'string'},
        {propertyName: 'registrationExpiryDate', dataType: 'string'},
        {propertyName: 'color', dataType: 'string'},
        // {propertyName: 'driverName', dataType: 'string'},
        {propertyName: 'ownerName', dataType: 'string'},
        {propertyName: 'ownerMobileNumber', dataType: 'string'},
    ];

    fillDataIntoTable(tbodyTransport , vehicles , propertyList , vehicleEdit , vehicleDelete , vehiclePrint);
}

const getVehicleType = (dataOb) => {
    return dataOb.vehicleType.vehicleType;
}

const getTransmissionType = (dataOb) => {
    return dataOb.transmissionType.transmissionType;
}

const getFuelType = (dataOb) => {
    return dataOb.fuelType.fuelType;
}

const vehicleEdit = (dataOb) => {
    selectVehicleType.value = dataOb.vehicleType.vehicleType;
    textVehicleLicenseNumber.value = dataOb.licenseNo;
    textVehicleRegisterDate.value = dataOb.registerDate;
    textVehicleManufacture.value = dataOb.manufacturer;
    textVehicleModel.value = dataOb.model;
    textVehicleYearOfMade.value = dataOb.year;
    textVehicleChassisNumber.value = dataOb.chassisNumber;
    textVehicleSeatCount.value = dataOb.seatCount;
    selectVehicleTransmissionType.value = dataOb.transmissionType.transmissionType;
    selectVehicleFuelType.value = dataOb.fuelType.fuelType;
    selectVehicleAirConditioning.value = dataOb.airConditioning.name;
    textVehicleRatePerKilometer.value = dataOb.vehicleType.chargeperkm;
    textVehicleInsuranceExpiryDate.value = dataOb.insuranceExpiryDate;
    textVehicleRegistrationExpiryDate.value = dataOb.registrationExpiryDate;
    textVehicleColor.value = dataOb.color;
    textVehicleDescription.value = dataOb.description;
    textVehicleCurrentOwner.value = dataOb.ownerName;
    textOwnerAddress.value = dataOb.ownerAddress;
    textOwnerContactNumber.value = dataOb.ownerMobileNumber;
    textVehicleAgencyName.value = dataOb.agencyName;
    textVehicleAgencyContactNumber.value = dataOb.contactNumber;

    vehicle = JSON.parse(JSON.stringify(dataOb));
    oldVehicle =  JSON.parse(JSON.stringify(dataOb));

    $("#modalTransportForm").modal('show');

    buttonUpdate.removeAttribute("style");
    buttonSubmit.style.display = "none";
}
const vehicleDelete = async (dataOb) => {
    console.log(dataOb);
    let userConfirm = window.confirm("Are you sure you want to delete this vehicle? \n" + "Vehicle : " + dataOb.model +"\n" + "Year : " + dataOb.year + "\n");
    if (userConfirm){
        try {
            let serviceResponse =await getHTTPServiceRequest(`/transport/delete/${dataOb.id}`, "DELETE" , dataOb);
            if (serviceResponse.message === "OK") {
                window.alert("Delete Successfully!");
                refreshVehicleTable();
            } else {
                window.alert("Failed to delete vehicle , has following errors \n" + serviceResponse.message);
            }
        }catch (error) {
            window.alert("Failed to delete vehicle, " + error.message);
        }
        refreshVehicleTable();
    }
}
const vehiclePrint = (dataOb) => {}

const checkFormError = () => {
    let errors = "";

    if (vehicle.vehicleType === "") {
        errors += "Please Select a Valid Vehicle Type ...! \n";
    }

    if (vehicle.ratePerKm === "") {
        errors += "Please Enter a Valid Rate Per Kilometer ...! \n";
    }

    if (vehicle.licenseNo === "") {
        errors += "Please Enter a Valid License Number ...! \n";
    }

    if (vehicle.registerDate === "") {
        errors += "Please Select a Valid Registration Date ...! \n";
    }

    if (vehicle.manufacturer === "") {
        errors += "Please Enter a Valid Manufacturer ...! \n";
    }

    if (vehicle.model === "") {
        errors += "Please Enter a Valid Vehicle Model ...! \n";
    }

    if (vehicle.year === "") {
        errors += "Please Enter a Valid Year of Manufacture ...! \n";
    }

    if (vehicle.seatCount === "") {
        errors += "Please Enter a Valid Seat Count ...! \n";
    }

    if (vehicle.vehicleStatus === "") {
        errors += "Please Select a Valid Vehicle Status ...! \n";
    }

    if (vehicle.chassisNumber === "") {
        errors += "Please Enter a Valid Chassis Number ...! \n";
    }

    if (vehicle.insuranceExpiryDate === "") {
        errors += "Please Select a Valid Insurance Expiry Date ...! \n";
    }

    if (vehicle.registrationExpiryDate === "") {
        errors += "Please Select a Valid Registration Expiry Date ...! \n";
    }

    if (vehicle.color === "") {
        errors += "Please Enter a Valid Vehicle Color ...! \n";
    }

    if (vehicle.ownerName === "") {
        errors += "Please Enter a Valid Vehicle Owner Name ...! \n";
    }

    if (vehicle.ownerAddress === "") {
        errors += "Please Enter a Valid Vehicle Owner Address ...! \n";
    }

    if (vehicle.ownerMobileNumber === "") {
        errors += "Please Enter a Valid Vehicle Owner Mobile Number ...! \n";
    }

    if (vehicle.transmissionType === "") {
        errors += "Please Select a Valid Transmission Type ...! \n";
    }

    if (vehicle.fuelType === "") {
        errors += "Please Select a Valid Fuel Type ...! \n";
    }

    if (vehicle.airConditioning === "") {
        errors += "Please Select if the Vehicle has Air Conditioning ...! \n";
    }

    return errors;
}

const buttonVehicleSubmit = async () => {
    let errors = checkFormError();

    if (errors === "") {
        let userConfirm = window.confirm("Are you sure to Add this Vehicle?  \n" + "Vehicle Name : " + vehicle.model);
        if (userConfirm) {
            try {
                let postResponse = await getHTTPServiceRequest("/transport/insert", "POST", vehicle);
                if (postResponse.message === "OK") {
                    window.alert("Successfully added!");
                    refreshVehicleTable();
                    refreshForm();
                    $("#modalTransportForm").modal('hide');
                } else {
                    window.alert("Failed to add Vehicle  Form has Following Errors \n" + errors);
                }
            }catch (error) {
                window.alert("Failed to add Vehicle , " + error.message);
            }
        }

    }else {
        window.alert("Failed to add Vehicle , Form has Following Errors \n" + errors);
    }
    refreshVehicleTable();
}

const checkVehicleFormUpdates = () => {
    let updates = "";

    if (vehicle != null && oldVehicle != null) {
        if (vehicle.licenseNo !== oldVehicle.licenseNo) {
            updates += "License number has been updated! \n";
        }
        if (vehicle.registerDate !== oldVehicle.registerDate) {
            updates += "Register date has been updated! \n";
        }
        if (vehicle.manufacturer !== oldVehicle.manufacturer) {
            updates += "Manufacturer has been updated! \n";
        }
        if (vehicle.model !== oldVehicle.model) {
            updates += "Model has been updated! \n";
        }
        if (vehicle.year !== oldVehicle.year) {
            updates += "Year has been updated! \n";
        }
        if (vehicle.description !== oldVehicle.description) {
            updates += "Description has been updated! \n";
        }
        if (vehicle.seatCount !== oldVehicle.seatCount) {
            updates += "Seat count has been updated! \n";
        }
        if (vehicle.chassisNumber !== oldVehicle.chassisNumber) {
            updates += "Chassis number has been updated! \n";
        }
        if (vehicle.insuranceExpiryDate !== oldVehicle.insuranceExpiryDate) {
            updates += "Insurance expiry date has been updated! \n";
        }
        if (vehicle.registrationExpiryDate !== oldVehicle.registrationExpiryDate) {
            updates += "Registration expiry date has been updated! \n";
        }
        if (vehicle.color !== oldVehicle.color) {
            updates += "Color has been updated! \n";
        }
        if (vehicle.ownerName !== oldVehicle.ownerName) {
            updates += "Owner name has been updated! \n";
        }
        if (vehicle.ownerAddress !== oldVehicle.ownerAddress) {
            updates += "Owner address has been updated! \n";
        }
        if (vehicle.ownerMobileNumber !== oldVehicle.ownerMobileNumber) {
            updates += "Owner mobile number has been updated! \n";
        }
        if (vehicle.agencyName !== oldVehicle.agencyName) {
            updates += "Agency name has been updated! \n";
        }
        if (vehicle.contactNumber !== oldVehicle.contactNumber) {
            updates += "Agency contact number has been updated! \n";
        }

        // Comparisons for nested objects
        if (vehicle.vehicleType?.vehicleType !== oldVehicle.vehicleType?.vehicleType) {
            updates += "Vehicle type has been updated! \n";
        }
        if (vehicle.vehicleType?.ratePerKm !== oldVehicle.vehicleType?.ratePerKm) {
            updates += "Rate per kilometer has been updated! \n";
        }
        if (vehicle.transmissionType?.transmissionType !== oldVehicle.transmissionType?.transmissionType) {
            updates += "Transmission type has been updated! \n";
        }
        if (vehicle.fuelType?.fuelType !== oldVehicle.fuelType?.fuelType) {
            updates += "Fuel type has been updated! \n";
        }
        if (vehicle.airConditioning?.name !== oldVehicle.airConditioning?.name) {
            updates += "Air conditioning status has been updated! \n";
        }
    }

    return updates;
};

const buttonVehicleUpdate = async () => {
    let errors = checkFormError();

    if (errors === "") {
        let updates = checkVehicleFormUpdates();

        if (updates === "") {
            window.alert("No changes detected in the form.");
        } else {
            let userConfirm = window.confirm("Are you sure to update this Vehicle?  \n" + "Vehicle Name : " + vehicle.model);
            if (userConfirm) {
                try {
                    let putResponse = await getHTTPServiceRequest(`/transport/update/${vehicle.id}`, "PUT", vehicle);
                    if (putResponse.message === "OK") {
                        window.alert("Successfully updated!");
                        refreshVehicleTable();
                        refreshForm();
                        $("#modalTransportForm").modal('hide');
                    } else {
                        window.alert("Failed to update Vehicle Form has Following Errors \n" + updates);
                    }
                }catch (error) {
                    window.alert("Failed to update Vehicle , " + error.message);
                }
            }
        }
    }else {
        window.alert("Failed to update Vehicle , Form has Following Errors \n" + errors);
    }
}


const refreshForm = () => {
    vehicle = new Object();

    formTransport.reset();

    setDefault([
        textVehicleLicenseNumber,
        textVehicleRegisterDate,
        textVehicleManufacture,
        textVehicleModel,
        textVehicleYearOfMade,
        textVehicleChassisNumber,
        textVehicleSeatCount,
        textVehicleRatePerKilometer,
        textVehicleInsuranceExpiryDate,
        textVehicleRegistrationExpiryDate,
        textVehicleColor,
        textVehicleDescription,
        textVehicleCurrentOwner,
        textOwnerAddress,
        textOwnerContactNumber,
        textVehicleAgencyName,
        textVehicleAgencyContactNumber,
        selectVehicleType,
        selectVehicleTransmissionType,
        selectVehicleFuelType,
        selectVehicleAirConditioning,
        selectDriver
    ]);

    let vehicleTypes =getServiceRequest('/vehicleType/alldata');

    let transmissionTypes =getServiceRequest('/transmissionType/alldata');

    let fuelTypes =getServiceRequest('/fuelType/alldata');

    let airConditionings =getServiceRequest('/airConditioning/alldata');

    fillDataIntoSelect("selectVehicleType" , "Select Vehicle Type" , vehicleTypes , "vehicleType");
    fillDataIntoSelect("selectVehicleTransmissionType" , "Select TransmissionType" , transmissionTypes , "transmissionType");
    fillDataIntoSelect("selectVehicleFuelType" , "Select Fuel Type" , fuelTypes , "fuelType");
    fillDataIntoSelect("selectVehicleAirConditioning" , "Select" , airConditionings , "name");

    buttonUpdate.style.display = "none";
    buttonSubmit.removeAttribute("style");

}