`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   11:04:28 05/11/2021
// Design Name:   program_counter
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/program_counter_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: program_counter
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module program_counter_tb;

	// Inputs
	reg clk;
	reg enable;
	reg pc_increment;
	reg [13:0] address_in;

	// Outputs
	wire [13:0] address_out;

	// Instantiate the Unit Under Test (UUT)
	program_counter uut (
		.clk(clk), 
		.enable(enable), 
		.pc_increment(pc_increment), 
		.address_in(address_in), 
		.address_out(address_out)
	);
	
	integer i;

	initial begin
		// Initialize Inputs
		clk = 0;
		enable = 0;
		pc_increment = 0;
		address_in = 10;

		// Wait 100 ns for global reset to finish
		#100;
		enable = 1;
		clk = 1;
		
		#100;
		enable = 0;
		for (i = 0; i < 12; i = i + 1) begin
			pc_increment = 1;
			clk = i;
			#100;
		end 
        
		// Add stimulus here

	end
      
endmodule

