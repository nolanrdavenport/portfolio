`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    15:44:01 05/11/2021 
// Design Name: 
// Module Name:    data_register 
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
module data_register(
	input clk,
   input enable,
   input [23:0] data_in,
   output reg [23:0] data_out
   );
	
	
	always @ (posedge clk) begin
		if(enable == 1)
			data_out <= data_in;
	end

endmodule
