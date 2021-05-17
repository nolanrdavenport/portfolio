`timescale 1ns / 1ps
//////////////////////////////////////////////////////////////////////////////////
// Company: 
// Engineer: 
// 
// Create Date:    14:37:47 05/11/2021 
// Design Name: 
// Module Name:    instruction_memory 
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
module instruction_memory(
   input write_enable,
	input read_enable,
	input clk,
   input [13:0] address,
	input [26:0] write_data,
   output reg [26:0] read_data
   );
	
	// TODO: ram bus idea
	// instruction memory that is 27 bits wide, and data memory that is 24 bits wide. Have "chip select" that is higher order bit to choose which chip to use. 
	
	reg [26:0] imem [0:'h1FFF];
	
	// this initial block is to fill the instructions for testing...
	initial begin
		// program 1 - IT WORKS
		imem[0] = 'h2800010; 	// lw r00, 0x10
		imem[1] = 'h2840020;		// lw r01, 0x20
		imem[2] = 'h880100;		// add r02, r00, r01
		imem[3] = 'b0110_00000_00010_00000_00110000;    // sw r02, 0x30
		
		// program 2 - IT WORKS
		imem[4] = 'b0011_00000_00000_00000_00000000; 			// li r00, 0					3 cycles
		imem[5] = 'b0011_00001_00000_00000_00000000;				// li r01, 0					3 cycles
		imem[6] = 'b0011_00010_00000_00000_00001010;				// li r02, 10					3 cycles
		imem[7] = 'b0001_00000_00001_00000_00000000; 			// add r00, r01, r00			4 cycles
		imem[8] = 'b0010_00001_00001_00000_00000001;				// addi r01, r01, 0x01		4 cycles
		imem[9] = 'b0100_00000_00001_00010_00000111;				// bleq r01, r02, 0x07		4 cycles
		imem[10] = 'b0000_00000_00000_00000_00000000;       	// nop							2 cycle
		
	end
	
	always @ (posedge clk) begin
		if(write_enable == 1'b1 && address[13] == 1'b1) begin
			imem[address[12:0]] <= write_data;
		end
		
		if(read_enable == 1'b1 && address[13] == 1'b1) begin
			read_data <= imem[address[12:0]];
		end
	end
endmodule
