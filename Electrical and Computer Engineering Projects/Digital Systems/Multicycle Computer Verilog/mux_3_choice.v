`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    21:42:49 05/11/2021 
// Design Name: 
// Module Name:    mux_3_choice 
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
module mux_3_choice(
	input clk,
   input [23:0] choice_0,
   input [23:0] choice_1,
	input [23:0] choice_2,
   input [1:0] select,
   output reg [23:0] mux_output
   );
	
	always @ (posedge clk) begin
		if(select == 2'b00)
			mux_output <= choice_0;
		else if (select == 2'b01)
			mux_output <= choice_1;
		else if (select == 2'b10)
			mux_output <= choice_2;
	end

endmodule
