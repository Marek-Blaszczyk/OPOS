################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/MapFileDsc.cpp \
../src/MapFileDscHandler.cpp \
../src/gason.cpp 

OBJS += \
./src/MapFileDsc.o \
./src/MapFileDscHandler.o \
./src/gason.o 

CPP_DEPS += \
./src/MapFileDsc.d \
./src/MapFileDscHandler.d \
./src/gason.d 


# Each subdirectory must supply rules for building sources it contributes
src/%.o: ../src/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: Cygwin C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


