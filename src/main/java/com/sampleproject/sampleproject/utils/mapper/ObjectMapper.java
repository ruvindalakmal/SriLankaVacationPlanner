package com.sampleproject.sampleproject.utils.mapper;

import com.sampleproject.sampleproject.dto.requestdto.EmployeeRequestDto;
import com.sampleproject.sampleproject.dto.requestdto.PrivilegeRequestDto;
import com.sampleproject.sampleproject.dto.requestdto.UserRequestDto;
import com.sampleproject.sampleproject.dto.requestdto.VehicleRequestDto;
import com.sampleproject.sampleproject.dto.responsedto.*;
import com.sampleproject.sampleproject.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;


import java.util.List;


@Mapper(componentModel = "spring")
public interface ObjectMapper {
    /// Mapping Employee to Dto and Back
    List<EmployeeResponseDto> employeeToEmployeeResponseDto(List<Employee> employees);
    Employee toEntity(EmployeeRequestDto employeeRequestDto);
    EmployeeResponseDto toDto(Employee employee);
    void updateEntityFromDto(EmployeeRequestDto employeeRequestDto,@MappingTarget Employee employee);


    /// Mapping Privilege to Dto and Back
    Privilege convertValue(PrivilegeRequestDto privilegeRequestDto, Class<Privilege> privilegeClass);
    void updatePrivilegeFromDto(PrivilegeRequestDto privilegeRequestDto,@MappingTarget Privilege existingPrivilege);
    PrivilegeResponseDto toDto(Privilege privilege);
    Privilege convertValue(Privilege privilege, Class<PrivilegeResponseDto> privilegeResponseDtoClass);


    RoleResponseDto roleToRoleResponseDto(Role role);
    User toEntity(UserRequestDto userRequestDto);
    /// User to UserResponseDto mapping with nested Employee mapping
    @org.mapstruct.Mapping(source = "employee" , target = "employeeResponseDto")
    @org.mapstruct.Mapping(source = "roles", target = "roles")
    UserResponseDto userToUserResponseDto(User user);
    List<UserResponseDto> userToUserResponseDto(List<User> users);
    void toEntity(UserRequestDto userRequestDto, @MappingTarget User user);

    List<VehicleResponseDto> vehicleToVehicleResponseDto(List<Vehicle> vehicles);
    VehicleResponseDto vehicleToVehicleResponseDto(Vehicle vehicle);

    Vehicle vehicleRequestDtoToVehicle(VehicleRequestDto vehicleRequestDto);
    void vehicleRequestDtoToVehicle(VehicleRequestDto vehicleRequestDto, @MappingTarget Vehicle vehicle);

    List<VehicleTypeResponseDto> vehicleTypeToVehicleTypeResponseDto(List<VehicleType> vehicleTypes);

    List<TransmissionTypeResponseDto> transmissionTypeToTransmissionTypeResponseDto(List<TransmissionType> transmissionTypes);

    List<FuelTypeResponseDto> fuelTypeToFuelTypeResponseDto(List<FuelType> fuelTypes);

    List<AirConditioningResponseDto> airConditioningToAirConditioningResponseDto(List<AirConditioning> airConditionings);

    DesignationResponseDto designationToDesignationResponseDto(Designation designation);
}
