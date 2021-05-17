`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   00:26:06 05/12/2021
// Design Name:   instruction_register
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/instruction_register_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: instruction_register
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module instruction_register_tb;

	// Inputs
	reg clk;
	reg enable;
	reg reset;
	reg [26:0] instruction_in;

	// Outputs
	wire [3:0] opcode;
	wire [4:0] reg_dest;
	wire [4:0] reg_source_1;
	wire [4:0] reg_source_2;
	wire [7:0] immediate;

	// Instantiate the Unit Under Test (UUT)
	instruction_register uut (
		.clk(clk), 
		.enable(enable), 
		.reset(reset), 
		.instruction_in(instruction_in), 
		.opcode(opcode), 
		.reg_dest(reg_dest), 
		.reg_source_1(reg_source_1), 
		.reg_source_2(reg_source_2), 
		.immediate(immediate)
	);

	initial begin
		// Initialize Inputs
		clk = 0;
		enable = 0;
		reset = 0;
		instruction_in = 0;

		// Wait 100 ns for global reset to finish
		#100;
		clk = 1;
		enable = 1;
		instruction_in = 'h1800AAA;
		#100;
		clk = 0;
        
		// Add stimulus here

	end
      
endmodule

