`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   14:45:53 05/11/2021
// Design Name:   instruction_memory
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/instruction_memory_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: instruction_memory
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module instruction_memory_tb;

	// Inputs
	reg write_enable;
	reg read_enable;
	reg clk;
	reg [13:0] address;
	reg [26:0] write_data;

	// Outputs
	wire [26:0] read_data;

	// Instantiate the Unit Under Test (UUT)
	instruction_memory uut (
		.write_enable(write_enable), 
		.read_enable(read_enable), 
		.clk(clk), 
		.address(address), 
		.write_data(write_data), 
		.read_data(read_data)
	);

	initial begin
		// Initialize Inputs
		write_enable = 0;
		read_enable = 0;
		clk = 0;
		address = 'b10000000000000;
		write_data = 0;

		// Wait 100 ns for global reset to finish
		#100;
		write_data = 'hFFFFFF10;
		write_enable = 1;
		clk = 1;
		#100;
		clk = 0;
		#100;
		read_enable = 1;
		write_enable = 0;
		clk = 1;
		#100;
		clk = 0;
		// Add stimulus here

	end
      
endmodule

