`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    14:36:21 05/11/2021 
// Design Name: 
// Module Name:    data_memory 
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
module data_memory(
   input write_enable,
	input read_enable,
	input clk,
   input [13:0] address,
	input [23:0] write_data,
   output reg [23:0] read_data
   );
	
	reg [23:0] dmem [0:'h1FFF]; 	// 3 bytes per word. Use rambus.
	
	// this initial block is to fill the instructions for testing...
	initial begin
		dmem[0] = 123;
		dmem[1] = 456;
		dmem[2] = 789;
		dmem['h10] = 20;
		dmem['h20] = 22;
	end
	
	always @ (posedge clk) begin
		if(write_enable == 1'b1 && address[13] == 1'b0) begin
			dmem[address[12:0]] <= write_data;
		end
		
		if(read_enable == 1'b1 && address[13] == 1'b0) begin
			read_data <= dmem[address[12:0]];
		end
	end
endmodule
