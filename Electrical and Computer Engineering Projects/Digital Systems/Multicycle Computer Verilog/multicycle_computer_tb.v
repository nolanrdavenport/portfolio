`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   23:43:20 05/11/2021
// Design Name:   multicycle_computer
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/multicycle_computer_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: multicycle_computer
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module multicycle_computer_tb;
	// TO PROFESSOR SWARTZ
	// For program 1, the value of memory at address 48 should be 42 after running the simulation. 
	// In the simulation window:
	// click /multicycle_computer_tb/uut/dmem/dmem and click the drop down and search for [48,23:0]
	// For program 2, the value of register 0 should be 55. r01 contains i that increments and r02 contains the value 10 used for the branch comparison. 
	// r00 is the sum 
	// click /multicycle_computer_tb/uut/rf/registers and click the drop down and search for [0,23:0] for the sum. [1,23:0] for i. [2,23:0] for 10.

	// xilinx ISE doesn't run the full simulation when you open the window, for some reason. To get the right result, you need to do simulation/restart and then simulation/run-all. 


	// Inputs
	reg clk;
	reg program_mode;
	reg reset;
	reg [26:0] external_write_data;
	reg [13:0] address;

	// Outputs
	wire [23:0] alu_out;

	// Instantiate the Unit Under Test (UUT)
	multicycle_computer uut (
		.clk(clk), 
		.program_mode(program_mode), 
		.reset(reset), 
		.external_write_data(external_write_data), 
		.address(address), 
		.alu_out(alu_out)
	);
	
	parameter cycles_p1 = 13; 		// for program 1
	parameter cycles_p2 = 143; 	// for program 2
	integer i;

	initial begin
		// Initialize Inputs
		clk = 0;
		program_mode = 0;
		reset = 0;
		external_write_data = 0;
		address = 0;

		// Reset to boot on cycle 1
		#30;
		clk = 1;
		program_mode = 0;
		reset = 1;
		external_write_data = 0;
		address = 0;
		#30;
		clk = 0;
		#30;
		reset = 0;
		for(i = 1; i <= 2 * (cycles_p1 + cycles_p2); i = i + 1) begin
			clk = i;
			#30;
		end
	end
      
endmodule

