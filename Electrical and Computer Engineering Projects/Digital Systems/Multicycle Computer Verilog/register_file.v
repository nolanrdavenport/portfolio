`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    11:26:43 05/11/2021 
// Design Name: 
// Module Name:    register_file 
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
module register_file(
	input clk,
	input write_enable,
	input read_enable,
   input [4:0] read_reg_1,
   input [4:0] read_reg_2,
	input [4:0] write_reg,
   input [23:0] write_data,
   output reg [23:0] data_reg_1,
   output reg [23:0] data_reg_2
   );
	
	reg [23:0] registers [0:31];
	
	always @ (posedge clk) begin
		if(write_enable == 1) begin
			registers[write_reg] <= write_data;
		end else if(read_enable) begin
			data_reg_1 <= registers[read_reg_1];
			data_reg_2 <= registers[read_reg_2];
		end
	end
	
endmodule
