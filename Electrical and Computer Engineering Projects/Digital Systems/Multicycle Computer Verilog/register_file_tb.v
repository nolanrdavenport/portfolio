`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   11:32:13 05/11/2021
// Design Name:   register_file
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/register_file_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: register_file
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module register_file_tb;

	// Inputs
	reg clk;
	reg reg_write;
	reg [4:0] read_reg_1;
	reg [4:0] read_reg_2;
	reg [4:0] write_reg;
	reg [23:0] write_data;

	// Outputs
	wire [23:0] data_reg_1;
	wire [23:0] data_reg_2;

	// Instantiate the Unit Under Test (UUT)
	register_file uut (
		.clk(clk), 
		.reg_write(reg_write), 
		.read_reg_1(read_reg_1), 
		.read_reg_2(read_reg_2), 
		.write_reg(write_reg), 
		.write_data(write_data), 
		.data_reg_1(data_reg_1), 
		.data_reg_2(data_reg_2)
	);

	initial begin
		// Initialize Inputs
		clk = 0;
		reg_write = 0;
		read_reg_1 = 0;
		read_reg_2 = 0;
		write_reg = 0;
		write_data = 0;

		// Wait 100 ns for global reset to finish
		#100;
		reg_write = 1;
		write_data = 123;
		write_reg = 0;
		clk = 1;
		#100;
		clk = 0;
		#100;
		reg_write = 0;
		read_reg_1 = 0;
		read_reg_2 = 1;
		clk = 1;
		#100;
		clk = 0;
        
		// Add stimulus here

	end
      
endmodule

