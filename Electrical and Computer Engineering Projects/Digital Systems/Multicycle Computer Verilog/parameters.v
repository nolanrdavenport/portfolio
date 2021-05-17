`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    16:27:24 05/08/2021 
// Design Name: 
// Module Name:    `defines 
// Project Name: 
// Target Devices: 
// Tool versions: 
// Description: 
//
// Dependencies: 
//
// Revision: 
// Revision 0.01 - File Created
// Additional Comments: 
//
//////////////////////////////////////////////////////////////////////////////////
// Here are the `define definitions for all modules
`define ADDRESS_BUS_WIDTH 14
`define NUM_ADDRESS 16384
`define PROGRAM_LOAD_ADRESS 8192
`define DATA_BUS_WIDTH 24
`define REGFILE_ADDR_BITS 5
`define NUM_REGISTERS 32
`define WIDTH_REGISTER_FILE 24
`define INSTRUCTION_WIDTH 27
`define IMMEDIATE_WIDTH 8
`define NUM_INSTRUCTIONS 16
`define WIDTH_OPCODE 4    // log 2 NUM_INSTRUCTIONS
// Decoded instructions:   Fill in these instructions
`define INSTR_NOP 0
`define INSTR_ADD 1  
`define INSTR_ADDI 2  
`define INSTR_LI  3  
`define INSTR_BLEQ  4  
`define INSTR_LW 5  
`define INSTR_SW 6  
`define INSTR_BGEQ 7  
`define INSTR_BLT 8  
`define INSTR_BGT 9  
`define INSTR_BEQ 10  
`define INSTR_SUB 11  
`define INSTR_SUBI 12  
`define INSTR_AND 13  
`define INSTR_OR 14  
`define INSTR_XOR 15

// Control
`define NUM_STATE_BITS 4
`define BOOT 4'B1111
`define FETCH 4'b0000
`define DECODE 4'b0001
`define AOPB 4'b0010
`define AOPIMM 4'b0011
`define LOADW 4'b0100
`define LOADI 4'b0101
`define STOREW 4'b0110
`define BRANCH 4'b0111
`define RTYPE 4'b1000

