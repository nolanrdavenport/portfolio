`timescale 1ns / 1ps

////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer:
//
// Create Date:   00:43:52 05/12/2021
// Design Name:   control
// Module Name:   C:/Users/guzzo/Desktop/4th Semester Coursework/4304/project/ISE/Multicycle_Computer/control_tb.v
// Project Name:  Multicycle_Computer
// Target Device:  
// Tool versions:  
// Description: 
//
// Verilog Test Fixture created by ISE for module: control
//
// Dependencies:
// 
// Revision:
// Revision 0.01 - File Created
// Additional Comments:
// 
////////////////////////////////////////////////////////////////////////////////

module control_tb;

	// Inputs
	reg program_mode;
	reg clk;
	reg reset;
	reg [3:0] opcode;
	reg result_lsb;

	// Outputs
	wire ir_enable;
	wire dmem_read;
	wire dmem_write;
	wire imem_read;
	wire imem_write;
	wire pc_increment;
	wire alu_reg_enable;
	wire pc_enable;
	wire alu_src_B;
	wire alu_out_reg_enable;
	wire dmem_out_reg_enable;
	wire reg_write_enable;
	wire [1:0] select_reg_write_data;

	// Instantiate the Unit Under Test (UUT)
	control uut (
		.program_mode(program_mode), 
		.clk(clk), 
		.reset(reset), 
		.opcode(opcode), 
		.result_lsb(result_lsb), 
		.ir_enable(ir_enable), 
		.dmem_read(dmem_read), 
		.dmem_write(dmem_write), 
		.imem_read(imem_read), 
		.imem_write(imem_write), 
		.pc_increment(pc_increment), 
		.alu_reg_enable(alu_reg_enable), 
		.pc_enable(pc_enable), 
		.alu_src_B(alu_src_B), 
		.alu_out_reg_enable(alu_out_reg_enable), 
		.dmem_out_reg_enable(dmem_out_reg_enable), 
		.reg_write_enable(reg_write_enable), 
		.select_reg_write_data(select_reg_write_data)
	);

	initial begin
		// Initialize Inputs
		program_mode = 0;
		clk = 0;
		reset = 0;
		opcode = 0;
		result_lsb = 0;

		// Wait 100 ns for global reset to finish
		#100;
		reset = 1;
		clk = 1;
		#100;
		clk = 0;
		
		#100;
		clk = 1;
		opcode = 3;
		reset = 0;
		#100
		clk = 0;
		
		#100;
		clk = 1;
		#100;
		clk = 0;
		
		#100;
		clk = 1;
		#100;
		clk = 0;
		
		#100;
		clk = 1;
		#100;
		clk = 0;
		
		#100;
        
		// Add stimulus here

	end
      
endmodule

