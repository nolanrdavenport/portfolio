`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    18:07:10 05/11/2021 
// Design Name: 
// Module Name:    mux_2_choice 
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
module mux_2_choice(
	input clk,
   input [23:0] choice_0,
   input [23:0] choice_1,
   input select,
   output reg [23:0] mux_output
   );
	
	always @ (posedge clk) begin
		if(select == 1'b0)
			mux_output <= choice_0;
		else if (select == 1'b1)
			mux_output <= choice_1;
	end
	
endmodule
