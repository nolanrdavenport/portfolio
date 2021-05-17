`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   16:12:35 05/11/2021
// Design Name:   ALU
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/ALU_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: ALU
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module ALU_tb;

	// Inputs
	reg [23:0] A_in;
	reg [23:0] B_in;
	reg [3:0] alu_control;

	// Outputs
	wire [23:0] alu_result;
	wire cout;
	wire Z;
	wire N;

	// Instantiate the Unit Under Test (UUT)
	ALU uut (
		.A_in(A_in), 
		.B_in(B_in), 
		.alu_control(alu_control), 
		.alu_result(alu_result), 
		.cout(cout), 
		.Z(Z), 
		.N(N)
	);

	initial begin
		// Initialize Inputs
		A_in = 0;
		B_in = 0;
		alu_control = 0;

		// Wait 100 ns for global reset to finish
		#100;
        
		// Add stimulus here

	end
      
endmodule

