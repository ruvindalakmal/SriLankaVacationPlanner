package com.sampleproject.sampleproject.service.service;
import com.sampleproject.sampleproject.dto.requestdto.EmployeeRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.EmployeeResponseDto;
import com.sampleproject.sampleproject.excep.custom.AlreadyExistsException;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponseDto> alldata();

    String saveEmployeeData(EmployeeRequestDto employeeRequestDto) throws AlreadyExistsException;

    EmployeeResponseDto deleteEmployee(Integer employeeId);

    EmployeeResponseDto updateEmployee(Integer id , EmployeeRequestDto employeeRequestDto) throws AlreadyExistsException;

    List<EmployeeResponseDto> listWithoutUserAccount();
}
