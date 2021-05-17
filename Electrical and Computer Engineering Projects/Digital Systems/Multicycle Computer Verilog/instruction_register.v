`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    16:48:09 05/08/2021 
// Design Name: 
// Module Name:    instruction_register 
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
`include "parameters.v"
module instruction_register(
	input clk,
	input enable,
	input reset,
   input [`INSTRUCTION_WIDTH-1:0] instruction_in,
   output reg [`WIDTH_OPCODE-1:0] opcode,
   output reg [`REGFILE_ADDR_BITS-1:0] reg_dest,
   output reg [`REGFILE_ADDR_BITS-1:0] reg_source_1,
   output reg [`REGFILE_ADDR_BITS-1:0] reg_source_2,
   output reg [`IMMEDIATE_WIDTH-1:0] immediate
   );
		
	always @ (posedge clk) begin
		if(enable == 1) begin
			{ opcode, reg_dest, reg_source_1, reg_source_2, immediate } <= instruction_in;
		end
	end

endmodule
