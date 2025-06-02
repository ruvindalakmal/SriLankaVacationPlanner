const fullNameValidator = (textFullName, object, property) => {
    const fullNameValue = textFullName.value;
    const fullNameReg = new RegExp("^([A-Z][a-z]{1,20}[\\s])+([A-Z][a-z]{2,20})$");
    const ob = window[object];
    if (fullNameValue !== "") {
        if (fullNameReg.test(fullNameValue)) {
            ob[property] = textFullName.value;
            textFullName.classList.add('is-valid');
            textFullName.classList.remove('is-invalid');

            let fullNameParts = fullNameValue.split(" ");
            selectCallingName.innerHTML = "";
            let option = document.createElement('option');
            option.innerText = 'Select Calling Name';
            option.selected = 'selected';
            option.disabled = 'disabled';
            selectCallingName.appendChild(option);

            //   ob[property]selectCallingName = selectCallingName.value;
            fullNameParts.forEach(name => {
                let option = document.createElement('option');
                // option.value = name;
                option.innerText = name;
                selectCallingName.appendChild(option);
            });

        } else {
            textFullName.classList.remove('is-valid');
            textFullName.classList.add('is-invalid');
            ob[property] = null;
        }
    } else {
        textFullName.classList.remove('is-valid');
        textFullName.classList.add('is-invalid');
        ob[property] = null;
    }
}

const callingNameValidator = (callingNameElement, object, property) => {
    const callingNameValue = callingNameElement.value;
    const fullNameValue = textFullName.value;
    let fullNameParts = fullNameValue.split(" ");
    const ob = window[object];

    if (callingNameValue !== "") {
        let extIndex = fullNameParts.map(fullNamePart => fullNamePart).indexOf(callingNameValue);
        if (extIndex !== -1) {
            ob[property] = callingNameValue;
            callingNameElement.classList.add('is-valid');
            callingNameElement.classList.remove('is-invalid');
        } else {
            ob[property] = null;
            callingNameElement.classList.remove('is-valid');
            callingNameElement.classList.add('is-invalid');

        }
    }
}

const nicValidator = (nicElement, object, property) => {
    const nicValue = nicElement.value;
    const nicReg = /^(\d{9}[VvXx]|\d{12})$/;
    const ob = window[object];

    if (nicValue && nicReg.test(nicValue)) {
        ob[property] = nicValue;
        nicElement.classList.add('is-valid');
        nicElement.classList.remove('is-invalid');

        // Parse NIC to get gender and DOB
        const { gender, dob } = parseNICDetails(nicValue);

        // Update Gender radio buttons
        const maleRadio = document.getElementById('selectGenderMale');
        const femaleRadio = document.getElementById('selectGenderFemale');
        if (gender === 'Male') {
            maleRadio.checked = true;
            maleRadio.dispatchEvent(new Event('change'));
        } else if (gender === 'Female') {
            femaleRadio.checked = true;
            femaleRadio.dispatchEvent(new Event('change'));
        }

        // Update Date of Birth
        const dobInput = document.getElementById('dateOfBirth');
        if (dob) {
            dobInput.value = dob;
            dobInput.dispatchEvent(new Event('change'));
        }
    } else {
        ob[property] = null;
        nicElement.classList.remove('is-valid');
        nicElement.classList.add('is-invalid');
    }
};

const dateValidator = (dateElement, object, property) => {
    const ob = window[object];
    ob[property] = dateElement.value;
    dateElement.classList.add('is-valid');
}

const emailValidator = (emailElement, object, property) => {
    const emailValue = emailElement.value;
    const emailReg = new RegExp("^[a-zA-Z0-9._%+-]{5,50}@gmail\\.com$");
    const ob = window[object];
    if (emailValue !== "") {
        if (emailReg.test(emailValue)) {
            ob[property] = emailValue;
            emailElement.classList.add('is-valid');
            emailElement.classList.remove('is-invalid');
        } else {
            ob[property] = null;
            emailElement.classList.add('is-invalid');
            emailElement.classList.remove('is-valid');
        }
    } else {
        if (emailElement.required) {
            ob[property] = null;
            emailElement.classList.add('is-invalid');
        }
    }
}

const civilStatusValidator = (civilStatusElement, object, property) => {
    const civilStatusValue = civilStatusElement.value;
    const ob = window[object];
    if (civilStatusValue !== "") {
        ob[property] = civilStatusValue;
        civilStatusElement.classList.add('is-valid');
        civilStatusElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        civilStatusElement.classList.add('is-invalid');
        civilStatusElement.classList.remove('is-valid');
    }
}

const mobileNumberValidator = (mobileElement, object, property) => {
    const mobileNumberValue = mobileElement.value;
    const ob = window[object];
    const mobileReg = new RegExp("^[0][7][01245678][0-9]{7}$");

    if (mobileReg.test(mobileNumberValue)) {
        ob[property] = mobileNumberValue;
        mobileElement.classList.add('is-valid');
        mobileElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        mobileElement.classList.add('is-invalid');
        mobileElement.classList.remove('is-valid');
    }
}

const addressValidator = (addressElement, object, property) => {
    const addressValue = addressElement.value;
    const ob = window[object];
    if (addressValue !== "") {
        ob[property] = addressValue;
        addressElement.classList.add('is-valid');
        addressElement.classList.remove('is-invalid');
    }else {
        ob[property] = null;
        addressElement.classList.add('is-invalid');
        addressElement.classList.remove('is-valid');
    }
}

const landNumberValidator = (landElement, object, property) => {
    const landValue = landElement.value;
    const landReg = new RegExp("^[0][12345689][0-9]{8}$");
    const ob = window[object];
    if (landReg.test(landValue)) {
        ob[property] = landValue;
        landElement.classList.add('is-valid');
        landElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        landElement.classList.add('is-invalid');
        landElement.classList.remove('is-valid');
    }
}

const selectValidator = (selectElement, object, property) => {
    const selectValue = selectElement.value;
    const ob = window[object];
    if (selectValue !== "") {
        ob[property] = JSON.parse(selectValue);
        selectElement.classList.add('is-valid');
        selectElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        selectElement.classList.remove('is-valid');
        selectElement.classList.add('is-invalid');
    }
}

function parseNICDetails(nic) {
    let year , dayOfYear , gender;

    nic = nic.toUpperCase().replace(/[^0-9VX]/g, '');

    /// Old NIC Format
    if (nic.length === 10){
        const nicPart = nic.substr(0 , 9);
        const suffix = nic.substr(9);
        year = 1900 + parseInt(nic.substr(0,2) , 10);
        if (suffix === "X") year += 100; /// Adjust for 2000s
        dayOfYear = parseInt(nic.substr(2,3) , 10);
    }
    /// New NIC Format
    else if (nic.length === 12){
        year = parseInt(nic.substr(0,4) , 10);
        dayOfYear = parseInt(nic.substr(4,3) , 10);
    }else {
        return { gender: null, dob: null };
    }
    /// Find Gender
    gender = dayOfYear > 500 ? 'Female' : 'Male';
    if (gender === 'Female') dayOfYear -= 500;

    /// Birth Date
    const date = new Date(year, 0); // Start at Jan 1
    date.setDate(dayOfYear + 1);

    /// Format as YYYY-MM-DD
    const dob = date.toISOString().split('T')[0];
    return { gender, dob };
}

const usernameValidator = (input, object, property) => {
    const usernameValue = input.value;
    const usernameReg = /^[a-zA-Z0-9_.-]{3,20}$/; // Allow letters, numbers, dots, underscores, and minimum 3 characters

    const ob = window[object];
    if (usernameValue !== "") {
        if (usernameReg.test(usernameValue)) {
            ob[property] = usernameValue;
            input.classList.add('is-valid');
            input.classList.remove('is-invalid');
        } else {
            input.classList.remove('is-valid');
            input.classList.add('is-invalid');
            ob[property] = null;
        }
    } else {
        input.classList.remove('is-valid');
        input.classList.add('is-invalid');
        ob[property] = null;
    }
};

const passwordValidator = (input, object, property) => {
    const passwordValue = input.value;

    // Regex to check for password criteria
    const passwordReg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[!@#$%^&*(),.?":{}|<>])[A-Za-z\d!@#$%^&*(),.?":{}|<>]{8,}$/;

    const ob = window[object];

    if (passwordValue !== "") {
        if (passwordReg.test(passwordValue)) {
            ob[property] = passwordValue;
            input.classList.add('is-valid');
            input.classList.remove('is-invalid');
        } else {
            input.classList.remove('is-valid');
            input.classList.add('is-invalid');
            ob[property] = null;
        }
    } else {
        input.classList.remove('is-valid');
        input.classList.add('is-invalid');
        ob[property] = null;
    }
};

const retypePasswordValidator = (input, object, selectedValueproperty, originalPasswordId) => {
    const retypeValue = input.value;
    const originalPassword = document.getElementById(originalPasswordId).value;

    const ob = window[object];

    if (retypeValue !== "") {
        if (retypeValue === originalPassword) {
            ob[property] = retypeValue;
            input.classList.add('is-valid');
            input.classList.remove('is-invalid');
        } else {
            input.classList.remove('is-valid');
            input.classList.add('is-invalid');
            ob[property] = null;
        }
    } else {
        input.classList.remove('is-valid');
        input.classList.add('is-invalid');
        ob[property] = null;
    }
};

const noteValidator = (input, object, property) => {
    const noteValue = input.value.trim();
    const ob = window[object];

    if (noteValue !== "") {
        if (noteValue.length >= 5 && noteValue.length <= 500) {
            ob[property] = noteValue;
            input.classList.add('is-valid');
            input.classList.remove('is-invalid');
        } else {
            ob[property] = null;
            input.classList.add('is-invalid');
            input.classList.remove('is-valid');
        }
    } else {
        ob[property] = null;
        input.classList.add('is-invalid');
        input.classList.remove('is-valid');
    }
};

function populateEmail(selectElement) {
    if (!selectElement.value) return;

    // Parse selected employee data
    const employee = JSON.parse(selectElement.value);
    const emailInput = document.getElementById('textEmail');

    // Set email value and trigger validation
    emailInput.value = employee.email;
    emailValidator(emailInput, 'user', 'email');
}

const vehicleTypeValidator = (selectElement, object, property) => {
    const selectedValue = selectElement.value.trim();
    const ob = window[object];

    const typeMap = {
        "Sedan" : 1,
        "Van" : 2,
        "Bus" : 3,
        "Suv" : 4
    };

    if (typeMap[selectedValue]) {
        ob[property] = { id : typeMap[selectedValue] }; // assuming an object with a property `vehicleType`
        selectElement.classList.add('is-valid');
        selectElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        selectElement.classList.add('is-invalid');
        selectElement.classList.remove('is-valid');
    }
};

const vehicleLicenseValidator = (licenseElement, object, property) => {
    const licenseValue = licenseElement.value.trim().toUpperCase();

    const licenseReg = new RegExp("^[A-Z]{2,3}-\\d{4}$");
    const ob = window[object];
    if (licenseReg.test(licenseValue)) {
        ob[property] = licenseValue;
        licenseElement.classList.add('is-valid');
        licenseElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        licenseElement.classList.add('is-invalid');
        licenseElement.classList.remove('is-valid');
    }
};

const vehicleManufacturerValidator = (manufacturerElement, object, property) => {
    const manufacturerValue = manufacturerElement.value.trim();

    // List of allowed vehicle manufacturers
    const allowedManufacturers = ["Toyota", "Honda", "Ford", "BMW", "Mercedes", "Nissan", "Hyundai"];

    const ob = window[object];

    if (allowedManufacturers.some(m => m.toLowerCase() === manufacturerValue.toLowerCase())) {
        ob[property] = manufacturerValue;
        manufacturerElement.classList.add('is-valid');
        manufacturerElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        manufacturerElement.classList.add('is-invalid');
        manufacturerElement.classList.remove('is-valid');
    }
};

const vehicleModelValidator = (modelElement, object, property) => {
    const modelValue = modelElement.value.trim();

    // Define basic rules for a vehicle model: must be alphanumeric, allow spaces, 2 to 20 characters
    const modelReg = /^[A-Za-z0-9 ]{2,20}$/;

    const ob = window[object];

    if (modelReg.test(modelValue)) {
        ob[property] = modelValue;
        modelElement.classList.add('is-valid');
        modelElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        modelElement.classList.add('is-invalid');
        modelElement.classList.remove('is-valid');
    }
};

const yearOfMadeValidator = (yearElement, object, property) => {
    const yearValue = yearElement.value.trim();
    const currentYear = new Date().getFullYear();

    const yearReg = /^\d{4}$/;
    const ob = window[object];

    if (yearReg.test(yearValue)) {
        const yearNum = parseInt(yearValue, 10);
        if (yearNum >= 1900 && yearNum <= currentYear) {
            ob[property] = yearNum;
            yearElement.classList.add('is-valid');
            yearElement.classList.remove('is-invalid');
            return;
        }
    }

    ob[property] = null;
    yearElement.classList.add('is-invalid');
    yearElement.classList.remove('is-valid');
};

const vehicleChassisNumberValidator = (chassisElement, object, property) => {
    const chassisValue = chassisElement.value.trim().toUpperCase();
    const ob = window[object];

    // VIN pattern: 17 characters, excludes I, O, Q
    const chassisReg = /^[A-HJ-NPR-Z0-9]{17}$/;

    if (chassisReg.test(chassisValue)) {
        ob[property] = chassisValue;
        chassisElement.classList.add('is-valid');
        chassisElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        chassisElement.classList.add('is-invalid');
        chassisElement.classList.remove('is-valid');
    }
};

const seatCountValidator = (seatElement, object, property) => {
    const seatValue = seatElement.value.trim();
    const ob = window[object];

    const seatReg = /^\d+$/;
    const seatNumber = parseInt(seatValue, 10);

    if (seatReg.test(seatValue) && seatNumber >= 1 && seatNumber <= 70) {
        ob[property] = seatNumber;
        seatElement.classList.add('is-valid');
        seatElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        seatElement.classList.add('is-invalid');
        seatElement.classList.remove('is-valid');
    }
};

const transmissionTypeValidator = (selectElement, object, property) => {
    const selectedValue = selectElement.value.trim();
    const ob = window[object];

    const typeMap = {
        "Auto": 1,
        "Manual": 2,
        "Electric": 3
    };

    if (typeMap[selectedValue]) {
        ob[property] = { id: typeMap[selectedValue] };
        selectElement.classList.add('is-valid');
        selectElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        selectElement.classList.add('is-invalid');
        selectElement.classList.remove('is-valid');
    }
};

const fuelTypeValidator = (selectElement, object, property) => {
    const selectedValue = selectElement.value.trim();
    const ob = window[object];

    const typeMap = {
        "Petrol": 1,
        "Diesel": 2,
        "Electric": 3
    };

    if (typeMap[selectedValue]) {
        ob[property] = { id: typeMap[selectedValue] }; // ✅ send as object
        selectElement.classList.add('is-valid');
        selectElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        selectElement.classList.add('is-invalid');
        selectElement.classList.remove('is-valid');
    }
};

const airConditioningValidator = (selectElement, object, property) => {
    const selectedValue = selectElement.value.trim();
    const ob = window[object];

    const typeMap = {
        "Yes": { id: 1 },
        "No": { id: 2 }
    };

    if (typeMap[selectedValue]) {
        ob[property] = typeMap[selectedValue];
        selectElement.classList.add('is-valid');
        selectElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        selectElement.classList.add('is-invalid');
        selectElement.classList.remove('is-valid');
    }
};

const ratePerKilometerValidator = (inputElement, object, property) => {
    const rateValue = parseFloat(inputElement.value.trim());
    const ob = window[object];

    // Define a realistic range (e.g., minimum LKR 50, max LKR 500)
    if (!isNaN(rateValue) && rateValue >= 50 && rateValue <= 500) {
        ob[property] = rateValue;
        inputElement.classList.add('is-valid');
        inputElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        inputElement.classList.add('is-invalid');
        inputElement.classList.remove('is-valid');
    }
};

const expiryDateValidator = (dateElement, object, property) => {
    const inputDate = dateElement.value;
    const ob = window[object];

    if (!inputDate) {
        // No date selected: treat as invalid or null based on your requirement
        ob[property] = null;
        dateElement.classList.remove('is-valid');
        dateElement.classList.remove('is-invalid');
        return;
    }

    const selectedDate = new Date(inputDate);
    const today = new Date();
    today.setHours(0, 0, 0, 0); // Normalize today's date to midnight

    if (selectedDate >= today) {
        ob[property] = inputDate;
        dateElement.classList.add('is-valid');
        dateElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        dateElement.classList.add('is-invalid');
        dateElement.classList.remove('is-valid');
    }
};

const vehicleColorValidator = (inputElement, object, property) => {
    const colorValue = inputElement.value.trim();
    const ob = window[object];

    // Regex to allow only letters and spaces (case-insensitive)
    const colorRegex = /^[A-Za-z\s]+$/;

    if (colorValue !== "" && colorRegex.test(colorValue)) {
        ob[property] = colorValue;
        inputElement.classList.add('is-valid');
        inputElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        inputElement.classList.add('is-invalid');
        inputElement.classList.remove('is-valid');
    }
};

const vehicleDescriptionValidator = (textareaElement, object, property) => {
    const description = textareaElement.value.trim();
    const ob = window[object];

    // Define maximum length, e.g., 500 characters
    const maxLength = 500;

    if (description.length > 0 && description.length <= maxLength) {
        ob[property] = description;
        textareaElement.classList.add('is-valid');
        textareaElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        textareaElement.classList.add('is-invalid');
        textareaElement.classList.remove('is-valid');
    }
};

const vehicleCurrentOwnerValidator = (inputElement, object, property) => {
    const ownerName = inputElement.value.trim();
    const ob = window[object];

    // Allow alphabetic characters and spaces (minimum 3 characters)
    const nameRegex = /^[A-Za-z\s]{3,}$/;

    if (ownerName !== "" && nameRegex.test(ownerName)) {
        ob[property] = ownerName;
        inputElement.classList.add('is-valid');
        inputElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        inputElement.classList.add('is-invalid');
        inputElement.classList.remove('is-valid');
    }
};

const vehicleOwnerAddressValidator = (inputElement, object, property) => {
    const address = inputElement.value.trim();
    const ob = window[object];

    // Allow letters, numbers, commas, periods, and common punctuation (min 5 chars)
    const addressRegex = /^[A-Za-z0-9\s,.'\-/#]{5,}$/;

    if (address !== "" && addressRegex.test(address)) {
        ob[property] = address;
        inputElement.classList.add('is-valid');
        inputElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        inputElement.classList.add('is-invalid');
        inputElement.classList.remove('is-valid');
    }
};

const vehicleAgencyNameValidator = (inputElement, object, property) => {
    const agencyName = inputElement.value.trim();
    const ob = window[object];

    // Allow letters, numbers, spaces, and common punctuation, minimum 3 characters if entered
    const nameRegex = /^[A-Za-z0-9\s&.'\-]{3,}$/;

    if (agencyName === "") {
        // Optional: empty is acceptable
        ob[property] = null;
        inputElement.classList.remove('is-valid', 'is-invalid');
    } else if (nameRegex.test(agencyName)) {
        ob[property] = agencyName;
        inputElement.classList.add('is-valid');
        inputElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        inputElement.classList.add('is-invalid');
        inputElement.classList.remove('is-valid');
    }
};

const vehicleAgencyContactNumberValidator = (inputElement, object, property) => {
    const contact = inputElement.value.trim();
    const ob = window[object];

    const contactRegex = /^(07\d{8}|0\d{2}\d{7})$/;

    if (contact === "") {
        ob[property] = null;
        inputElement.classList.remove('is-valid', 'is-invalid');
    } else if (contactRegex.test(contact)) {
        ob[property] = contact;
        inputElement.classList.add('is-valid');
        inputElement.classList.remove('is-invalid');
    } else {
        ob[property] = null;
        inputElement.classList.add('is-invalid');
        inputElement.classList.remove('is-valid');
    }
};






















