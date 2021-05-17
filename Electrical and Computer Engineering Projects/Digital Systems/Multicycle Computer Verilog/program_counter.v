`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    21:54:53 05/08/2021 
// Design Name: 
// Module Name:    program_counter 
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
module program_counter(
	input clk,
	input enable,
	input pc_increment,
   input [13:0] address_in,
   output reg [13:0] address_out
   );
	
	initial begin
		address_out <= 'h2000;
	end
	
	always @ (posedge clk) begin
		if(enable == 1)
			address_out <= address_in;
			
		if(pc_increment == 1)
			address_out <= (address_out + 1);
	end

endmodule
